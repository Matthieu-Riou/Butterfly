package io.atal.butterfly

/** An editor is the place where you edit a buffer thanks to cursors and selections
  * It coordinate the communication between cursors, selections and the buffer
  *
  * @constructor Create a new editor for the buffer
  * @param buffer The buffer to edit, default empty buffer
  */
class Editor(var buffer: Buffer = new Buffer("")) {
  var _cursors: List[Cursor] = List(new Cursor())

  def cursors: List[Cursor] = _cursors

  def cursors_=(cursors: List[Cursor]): Unit = _cursors = cursors

  /** Return all selections' content
    * Used to put it inside the Butterfly clipboard (copy event)
    *
    * @return The text of all selections
    */
  def getSelectionContent: String = {
    var content: String = ""
    
    if(isSelectionMode) {

      for (cursor <- cursors) {
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
    if(isSelectionMode) {
      erase
    }
    
    for(cursor <- cursors) {
      buffer.insert(text, cursor.position)
      moveCursorRight(cursor, text.length)
    }
  }
  
  /** A simple eraser, character by character
  * Erase the character before the cursors
  */
  def erase: Unit = {
    if(isSelectionMode) {
      for (cursor <- cursors) {
        buffer.remove(cursor.position, cursor.cursorSelection.get.position)
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
          }
          case (x, y) => {
            buffer.remove((x, y - 1), (x, y))
            moveCursorLeft(cursor)
          }
        }
      }
    }
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

  /** Move the selections for all cursors
    * Create the selections if they does'nt exits
    *
    * @param move The number of character (absolute value) that moves of the selection (on the left if move < 0, one the right if move > 0)
    */
  def moveSelection(move: Int): Unit = {
    for(cursor <- cursors)
    {
      if(cursor.cursorSelection == None) {
        cursor.cursorSelection = Some(new Cursor(cursor.position))
      }
      
      if(move < 0) {
        moveCursorLeft(cursor.cursorSelection.get, Math.abs(move))
      }
      else {
        moveCursorRight(cursor.cursorSelection.get, Math.abs(move))
      }
    }
  }
  
  /** Clear all selections
    */
  def clearSelection: Unit = {
    for(cursor <- cursors)
    {
      cursor.cursorSelection = None
    }
  }
  
  /** Notify if the editor is in selection's mode
    * The editor is in selection's mode if there is at least one current selection
    */
  def isSelectionMode: Boolean = cursors.head.cursorSelection match {
    case None => false
    case Some(_) => true
  }
  
  /** Return the position of the cursor in the whole string
    *
    * @param cursor The cursor that we want
    * @retutrn The position in the buffer
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
    case (x, y) if y > buffer.lines(x - 1).length => cursor.position = (x - 1, buffer.lines(x - 1).length)
    case (x, y) => cursor.position = (x - 1, y)
  }
  
  /** Move down a single cursor
    *
    * @param cursor The cursor to move down
    * @param row Number of row to move, default 1
    */
  private def moveCursorDown(cursor: Cursor, row: Int = 1): Unit = {
    // LastLine and LastColumn need to start with an uppercase to be stable identifiers, and to be used in the match
    val LastLine = buffer.lines.length - 1
    val LastColumn = buffer.lines(cursor.position._1).length

    cursor.position match {
      case (LastLine, y) => Unit
      case (x, y) if y > buffer.lines(x + 1).length => cursor.position = (x + 1, buffer.lines(x + 1).length)
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
}
