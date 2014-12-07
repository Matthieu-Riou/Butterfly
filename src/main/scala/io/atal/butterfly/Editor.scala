package io.atal.butterfly

/** An editor is the place where you edit a buffer thanks to cursors and selections
  * It coordinate the communication between cursors, selections and the buffer
  *
  * @constructor Create a new editor for the buffer
  * @param buffer The buffer to edit, default empty buffer
  * @param editorManager A potential parent manager, default None
  * @param cursors Cursors of the editor, default with one cursor at (0, 0)
  */
class Editor(
    var buffer: Buffer = new Buffer(""),
    var editorManager: Option[EditorManager] = None,
    var cursors: List[Cursor] = List(new Cursor()))
  extends EventHandler {

  /** Return all selections' content
    * Used to put it inside the Butterfly clipboard (copy event)
    *
    * @return The text of all selections
    */
  def getSelectionContent: String = {
    var content: String = ""

    if (isSelectionMode) {
      for (cursor <- cursors) {
        if (cursor.greaterThan(cursor.cursorSelection.get))
          content += buffer.select(cursor.cursorSelection.get.position, cursor.position) + "\n"
        else
          content += buffer.select(cursor.position, cursor.cursorSelection.get.position) + "\n"
      }
    }

    content.dropRight(1)
  }

 /** Write a text into the buffer at the current cursors position
   *
   * @param text The text to be inserted in the buffer
   */
  def write(text: String): Unit = {
    if (isSelectionMode) {
      erase
    }

    for (cursor <- cursors) {
      buffer.insert(text, cursor.position)
      for (cursorGreater <- cursors.filter(c => c.position._1 == cursor.position._1 && c.greaterThan(cursor))) {
        moveCursorRight(cursorGreater, text.length)
      }
      moveCursorRight(cursor, text.length)
    }
  }

  /** A simple eraser, character by character
    * Erase the character before the cursors
    */
  def erase: Unit = {
    if (isSelectionMode) {
      for (cursor <- cursors) {
        var length = 0
        var diffLines = 0
        val (x1, y1) = cursor.position
        val selection = cursor.cursorSelection.get
        val (x2, y2) = selection.position

        if (cursor.greaterThan(selection)) {
          length = getIndexPosition(cursor) - getIndexPosition(selection)
          diffLines = x1 - x2

          buffer.remove((x2, y2), (x1, y1))
          cursor.position = (x2, y2)
        }
        else {
          length = getIndexPosition(selection) - getIndexPosition(cursor)
          diffLines = x2 - x1
          buffer.remove((x1, y1), (x2, y2))
        }

        for (cursorGreater <- cursors.filter(c => c.greaterThan(cursor))) {
          val (x, y) = cursorGreater.position
          cursorGreater.position = (x - diffLines, y)
        }

        for (cursorGreater <- cursors.filter(c => c.position._1 == cursor.position._1 && c.greaterThan(cursor))) {
            moveCursorLeft(cursorGreater, length)
            moveCursorLeft(cursorGreater.cursorSelection.get, length)
        }
      }
      clearSelection
    }
    else {
      for (cursor <- cursors) {
        cursor.position match {
          case (0, 0) => Unit
          case (x, 0) => {
            val lines = buffer.lines
            val lastColOfPrevLine = lines(x - 1).length

            // Remove the \n of the previous line
            buffer.remove((x - 1, lastColOfPrevLine), (x, 0))
            cursor.position = (x - 1, lastColOfPrevLine)

            for (cursorGreater <- cursors.filter(c => c.position._1 == cursor.position._1 + 1)) {
              val (x, y) = cursorGreater.position
              cursorGreater.position = (x - 1, lastColOfPrevLine + y)
            }

            for (cursorGreater <- cursors.filter(c => c.position._1 > cursor.position._1 + 1)) {
              val (x, y) = cursorGreater.position
              cursorGreater.position = (x - 1, y)
            }
          }
          case (x, y) => {
            buffer.remove((x, y - 1), (x, y))
            for (cursorGreater <- cursors.filter(c => c.position._1 == cursor.position._1 && c.position._2 > c.position._1)) {
              val (x, y) = cursorGreater.position
              cursorGreater.position = (x, y - 1)
            }
            cursor.position = (x, y - 1)
          }
        }
      }
    }
    removeMergedCursors
  }

  /** Undo the last event
    */
  def undo: Unit = {
    clearSelection
    buffer.undo

    val lines = buffer.lines

    for (cursor <- cursors) {
      val (x, y) = cursor.position

      if (y > lines(x).length)
        cursor.position = (x, lines(x).length)
    }
  }

  /** Redo the last undo event
    */
  def redo: Unit = {
    clearSelection
    buffer.redo

    val lines = buffer.lines

    for (cursor <- cursors) {
      val (x, y) = cursor.position

      if (x >= lines.length)
        cursor.position = (lines.length - 1, y)

      if (y > lines(x).length)
        cursor.position = (x, lines(x).length)
    }
  }

  /** Add a cursor
    *
    * @param cursor Cursor to add
    */
  def addCursor(position: (Int,Int)): Unit = cursors = new Cursor(position) :: cursors

  /** Remove a cursor
    *
    * @param cursor Cursor to remove
    */
  def removeCursor(position: (Int,Int)): Unit = cursors = cursors.diff(List(new Cursor(position)))

  /** Remove all cursors
    */
  def removeAllCursors: Unit = cursors = List()

  /** Remove merged cursors (in other words with the same position)
    * It occurs each time a cursor has moved
    */
  def removeMergedCursors: Unit = cursors = cursors.distinct

  /** Move the selections for all cursors
    * Create the selections if they don't exist
    *
    * @param move The number of character (absolute value) that moves of the selection (on the left if move < 0, one the right if move > 0)
    */
  def moveSelection(move: Int): Unit = {
    for (cursor <- cursors) {
      if (cursor.cursorSelection == None) {
        cursor.cursorSelection = Some(new Cursor(cursor.position))
      }

      if (move < 0) {
        moveCursorLeft(cursor.cursorSelection.get, Math.abs(move))
      }
      else {
        moveCursorRight(cursor.cursorSelection.get, Math.abs(move))
      }
    }
  }

  /** Move the start of the selections for all cursors
    * Create the selections if they don't exist
    *
    * @param move The number of character (absolute value) that moves of the start of the selection (on the left if move < 0, one the right if move > 0)
    */
  def moveStartSelection(move: Int): Unit = {
    for (cursor <- cursors) {
      if (cursor.cursorSelection == None) {
        cursor.cursorSelection = Some(new Cursor(cursor.position))
      }

      if (move < 0) {
        if (cursor.greaterThan(cursor.cursorSelection.get))
          moveCursorLeft(cursor.cursorSelection.get, Math.abs(move))
        else
          moveCursorLeft(cursor, Math.abs(move))
      }
      else {
        if (cursor.greaterThan(cursor.cursorSelection.get))
          moveCursorRight(cursor.cursorSelection.get, Math.abs(move))
        else
          moveCursorRight(cursor, Math.abs(move))
      }
    }
  }

  /** Move the end of the selections for all cursors
    * Create the selections if they don't exist
    *
    * @param move The number of character (absolute value) that moves of the end of the selection (on the left if move < 0, one the right if move > 0)
    */
  def moveEndSelection(move: Int): Unit = {
    for (cursor <- cursors) {
      if (cursor.cursorSelection == None) {
        cursor.cursorSelection = Some(new Cursor(cursor.position))
      }

      if (move < 0) {
        if(cursor.lowerThan(cursor.cursorSelection.get))
          moveCursorLeft(cursor.cursorSelection.get, Math.abs(move))
        else
          moveCursorLeft(cursor, Math.abs(move))
      }
      else {
        if(cursor.lowerThan(cursor.cursorSelection.get))
          moveCursorRight(cursor.cursorSelection.get, Math.abs(move))
        else
          moveCursorRight(cursor, Math.abs(move))
      }
    }
  }

  /** Clear all selections
    */
  def clearSelection: Unit = {
    for (cursor <- cursors) {
      cursor.cursorSelection = None
    }
  }

  /** Check if the editor is in selection mode
    * The editor is in selection mode if there is at least one selection
    */
  def isSelectionMode: Boolean = cursors(0).cursorSelection match {
    case None => false
    case Some(_) => true
  }

  /** Return the position of the cursor in the whole string
    *
    * @param cursor The cursor that we want
    * @return The position in the buffer
    */
  def getIndexPosition(cursor: Cursor): Int = buffer.convertToLinearPosition(cursor.position)

  /** Move up all cursors
    *
    * @param row Number of row to move, default 1
    */
  def moveCursorsUp(row: Int = 1): Unit = {
    cursors.foreach { moveCursorUp(_, row) }
    removeMergedCursors
  }

  /** Move down all cursors
    *
    * @param row Number of row to move, default 1
    */
  def moveCursorsDown(row: Int = 1): Unit = {
    cursors.foreach { moveCursorDown(_, row) }
    removeMergedCursors
  }

  /** Move left all cursors
    *
    * @param column Number of column to move, default 1
    */
  def moveCursorsLeft(column: Int = 1): Unit = {
    for (cursor <- cursors) {
      moveCursorLeft(cursor, column)
    }
    removeMergedCursors
  }

  /** Move right all cursors
    *
    * @param column Number of column to move, default 1
    */
  def moveCursorsRight(column: Int = 1): Unit = {
    for (cursor <- cursors) {
      moveCursorRight(cursor, column)
    }
    removeMergedCursors
  }

  /** Move to the top all cursors
    */
  def moveCursorsToTop: Unit = {
    cursors.foreach { _.position = (0, 0) }
    removeMergedCursors
  }

  /** Move to the bottom all cursors
    */
  def moveCursorsToBottom: Unit = {
    val lines = buffer.lines
    val lastLine = lines.length - 1
    val lastColumn = lines(lastLine).length

    val bottomPosition = (lastLine, lastColumn)

    for (cursor <- cursors) {
      cursor.position = bottomPosition
    }

    removeMergedCursors
  }

  /** Move up a single cursor
    *
    * @param cursor The cursor to move up
    * @param row Number of row to move, default 1
    */
  private def moveCursorUp(cursor: Cursor, row: Int = 1): Unit = cursor.position match {
    case (0, y) => Unit
    case (x, y) if (x <= row) => {
      if (y > buffer.lines(0).length) {
        cursor.position = (0, buffer.lines(0).length)
      }
      else {
        cursor.position = (0, y)
      }
    }
    case (x, y) if (y > buffer.lines(x - row).length) => cursor.position = (x - row, buffer.lines(x - row).length)
    case (x, y) => cursor.position = (x - row, y)
  }

  /** Move down a single cursor
    *
    * @param cursor The cursor to move down
    * @param row Number of row to move, default 1
    */
  private def moveCursorDown(cursor: Cursor, row: Int = 1): Unit = {
    // LastLine and LastColumn need to start with an uppercase to be stable identifiers, and to be used in the match
    val LastLine = buffer.lines.length - 1

    cursor.position match {
      case (LastLine, y) => Unit
      case (x, y) if x + row >= LastLine => {
        if (y > buffer.lines(LastLine).length) {
          cursor.position = (LastLine, buffer.lines(LastLine).length)
        }
        else {
          cursor.position = (LastLine, y)
        }
      }
      case (x, y) if (y > buffer.lines(x + 1).length) => cursor.position = (x + 1, buffer.lines(x + 1).length)
      case (x, y) => cursor.position = (x + 1, y)
    }
  }

  /** Move left a single cursor
    *
    * @param cursor The cursor to move left
    * @param column Number of column to move, default 1
    */
  private def moveCursorLeft(cursor: Cursor, column: Int = 1): Unit = cursor.position match {
    case (0, 0) => Unit
    case (x, y) if (y - column >= 0) => cursor.position = (x, y - column)
    case (0, y) => cursor.position = (0, 0)
    case (x, y) => {
      val lastColumn = buffer.lines(x - 1).length
      cursor.position = (x - 1, lastColumn)
      moveCursorLeft(cursor, column - y - 1)
    }
  }

  /** Move right a single cursor
    *
    * @param cursor The cursor to move right
    * @param column Number of column to move, default 1
    */
  private def moveCursorRight(cursor: Cursor, column: Int = 1): Unit = {
    // LastLine and LastColumn need to start with an uppercase to be stable identifiers, and to be used in the match
    val LastLine = buffer.lines.length - 1
    val LastColumn = buffer.lines(cursor.position._1).length

    cursor.position match {
      case (LastLine, LastColumn) => Unit
      case (x, y) if (y + column <= LastColumn) => cursor.position = (x, y + column)
      case (LastLine, y) => cursor.position = (LastLine, LastColumn)
      case (x, y) => {
        cursor.position = (x + 1, 0)
        moveCursorRight(cursor, column - (LastColumn - y) - 1)
      }
    }
  }

  /** Event registration on buffer changed
    * If the changed buffer is the Editor's one, tell it to EditorManager
    */
  event.on(
    "buffer-changed",
    (buffer) => {
      editorManager match {
        case Some(e) if (buffer == this.buffer) => e.editorChanged(this)
        case Some(e) => Unit
        case None => Unit
      }
    }
  )
}
