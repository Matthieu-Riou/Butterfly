package io.atal.butterfly

/** An editor is the place where you edit a buffer thanks to cursors and selections
  * It coordinate the communication between cursors, selections and the buffer
  *
  * @constructor Create a new editor for the buffer
  * @param buffer The buffer to edit, default empty buffer
  */
class Editor(var buffer: Buffer = new Buffer("")) {
  var _cursors: List[Cursor] = List(new Cursor())
  var _selections: List[Selection] = List()

  def cursors: List[Cursor] = _cursors

  def cursors_=(cursors: List[Cursor]): Unit = _cursors = cursors

  def selections: List[Selection] = _selections

  def selections_=(selections: List[Selection]): Unit = _selections = selections

  /** Write a text into the buffer at the current cursors position
    *
    * @param text The text to be inserted in the buffer
    */
  def write(text: String): Unit = {
    for (cursor <- cursors) {
      buffer.insert(text, cursor.position)
      cursor.moveRight(text.length)
    }
  }

  /** A simple eraser, character by character
    * Erase the character before the cursors
    */
  def erase: Unit = {
    for (cursor <- cursors) {
      cursor.position match {
        case (0, 0) => Unit
        case (x, 0) => {
          val lines = buffer.lines
          val lastColOfPrevLine = lines(x - 1).length

          // Remove the \n of the previous line
          buffer.remove((x - 1, lastColOfPrevLine), (x, 0))
          cursor.position = (x - 1, lastColOfPrevLine)
        }
        case (x, y) => {
          buffer.remove((x, y - 1), (x, y))
          moveCursorLeft(cursor)
        }
      }
    }
  }

  /** Erase all selections content
    */
  def eraseSelection: Unit = {
    for (selection <- selections) {
      buffer.remove(selection.begin, selection.end)
    }

    clearSelection
  }

  /** Return all selections' content
    * Used to put it inside the Butterfly clipboard (copy event)
    *
    * @return The text of all selections
    */
  def getSelectionContent: String = {
    var content: String = ""

    for (selection <- selections) {
      content += buffer.select(selection.begin, selection.end) + "\n"
    }

    content.dropRight(1)
  }

  /** Add a cursor
    *
    * @param cursor Cursor to add
    */
  def addCursor(cursor: Cursor): Unit = cursors = cursor :: cursors

  /** Remove a cursor
    *
    * @param cursor Cursor to remove
    */
  def removeCursor(cursor: Cursor): Unit = cursors = cursors.diff(List(cursor))

  /** Remove merged cursors (in other words with the same position)
    * It occurs each time a cursor has moved
    */
  def removeMergedCursors: Unit = cursors = cursors.distinct

  /** Add a selection
    *
    * @param selection Selection to add
    */
  def addSelection(selection: Selection): Unit = selections = selection :: selections

  /** Remove a selection
    *
    * @param selection Selection to remove
    */
  def removeSelection(selection: Selection): Unit = selections = selections.diff(List(selection))

  /** Clear all selections
    */
  def clearSelection: Unit = selections = List()

  /** Move up all cursors
    *
    * @param row Number of row to move, default 1
    */
  def moveCursorsUp(row: Int = 1): Unit = {
    cursors.foreach { moveCursorUp(_, row) }
    removeMergedCursors
  }

  /** Move up a single cursor
  *
  * @param cursor The cursor to move up
  * @param row Number of row to move, default 1
  */
  def moveCursorUp(cursor: Cursor, row: Int = 1): Unit =  cursor.position match {
    case (0, y) => Unit
    case (x, y) if y > buffer.lines(x-1).length => cursor.position = (x-1, buffer.lines(x-1).length)
    case (x, y) => cursor.position = (x-1, y)
  }

  /** Move down all cursors
    *
    * @param row Number of row to move, default 1
    */
  def moveCursorsDown(row: Int = 1): Unit = {
    cursors.foreach { moveCursorDown(_, row) }
    removeMergedCursors
  }

  /** Move down a single cursor
    *
    * @param cursor The cursor to move down
    * @param row Number of row to move, default 1
    */
  def moveCursorDown(cursor: Cursor, row: Int = 1): Unit = {
    // LastLine and LastColumn need to start with an uppercase to be stable identifiers, and to be used in the match
    val LastLine = buffer.lines.length - 1
    val LastColumn = buffer.lines(cursor.position._1).length

    cursor.position match {
      case (LastLine, y) => Unit
      case (x, y) if y > buffer.lines(x+1).length => cursor.position = (x+1, buffer.lines(x+1).length)
      case (x, y) => cursor.position = (x+1, y)
    } 
  }

  /** Move left a single cursor
    *
    * @param cursor The cursor to move left
    * @param column Number of column to move, default 1
    */
  def moveCursorLeft(cursor: Cursor, column: Int = 1): Unit = cursor.position match {
    case (0, 0) => Unit
    case (x, y) if (y - column >= 0) => cursor.moveLeft(column)
    case (0, y) => cursor.position = (0, 0)
    case (x, y) => {
      val lastColumn = buffer.lines(x-1).length
      cursor.position = (x-1, lastColumn)
      moveCursorLeft(cursor, column - y-1)
    }
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

  /** Move right a single cursor
    *
    * @param cursor The cursor to move right
    * @param column Number of column to move, default 1
    */
  def moveCursorRight(cursor: Cursor, column: Int = 1): Unit = {
    // LastLine and LastColumn need to start with an uppercase to be stable identifiers, and to be used in the match
    val LastLine = buffer.lines.length - 1
    val LastColumn = buffer.lines(cursor.position._1).length

    cursor.position match {
      case (LastLine, LastColumn) => Unit
      case (x, y) if (y + column <= LastColumn) => cursor.moveRight(column)
      case (LastLine, y) => cursor.position = (LastLine, LastColumn)
      case (x, y) => {
        cursor.position = (x+1, 0)
        moveCursorRight(cursor, column - (LastColumn-y) -1)
      }
    }
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
    cursors.foreach { _.moveToTop }
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
}
