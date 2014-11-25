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
    *
    * Erase the character before the cursors, then move left
    */
  def erase: Unit = {
    for (cursor <- cursors) {
      // End index position is excluded, we need to add 1 to actually remove the character
      if(cursor.position._2 -1 > 0) {
        buffer.remove((cursor.position._1, cursor.position._2 - 1), cursor.position)
        cursor.moveLeft()
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
  def moveCursorUp(cursor: Cursor, row: Int = 1): Unit = {
    val lines = buffer.lines

    if (cursor.position._1 - row >= 0) {
      cursor.moveUp(row)

      val posX = cursor.position._1
      val posY = cursor.position._2

      if (posY > lines(posX).length)
        cursor.position = (posX, lines(posX).length)
    }
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
    val lines = buffer.lines

    if (cursor.position._1 + row < lines.length) {
      cursor.moveDown(row)

      val posX = cursor.position._1
      val posY = cursor.position._2

      if (posY > lines(posX).length)
        cursor.position = (posX, lines(posX).length)
    }
  }

  /** Move left all cursors
    *
    * @param column Number of column to move, default 1
    */
  def moveCursorsLeft(column: Int = 1): Unit = {
    for (cursor <- cursors) {
      if (cursor.position._2 - column >= 0)
        cursor.moveLeft(column)
      else
        moveCursorUp(cursor, 1)
    }

    removeMergedCursors
  }

  /** Move right all cursors
    *
    * @param column Number of column to move, default 1
    */
  def moveCursorsRight(column: Int = 1): Unit = {
    val lines = buffer.lines

    for (cursor <- cursors) {
      if (cursor.position._2 + column <= lines(cursor.position._1).length)
        cursor.moveRight(column)
      else {
        cursor.position = (cursor.position._1, 0)
        moveCursorDown(cursor, 1)
      }
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
