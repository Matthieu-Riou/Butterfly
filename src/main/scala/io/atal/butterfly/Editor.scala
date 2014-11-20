package io.atal.butterfly

/** An editor is the place where you edit a buffer thanks to cursors and selections
  * It coordinate the communication between cursors, selections and the buffer
  *
  * @constructor Create a new editor for the buffer
  * @param buffer The buffer to edit, default empty buffer
  */
class Editor(var buffer: Buffer = new Buffer("")) {
  var _cursors: List[Cursor] = List(new Cursor(this))
  var _selections: List[Selection] = List()

  def cursors: List[Cursor] = _cursors

  def cursors_=(cursors: List[Cursor]): Unit = _cursors = cursors

  def selections: List[Selection] = _selections

  def selections_=(selections: List[Selection]): Unit = _selections = selections

  /** Write a text into the buffer at the current cursor(s) position
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
    */
  def erase: Unit = {
    for (cursor <- cursors) {
      // End index position is excluded, we need to add 1 to actually remove the character
      buffer.remove(cursor.position, (cursor.position._1, cursor.position._2 + 1))
      cursor.moveLeft()
    }
  }

  /** Erase all selections content
    */
  def eraseSelection: Unit = {
    // @todo Unit test brother !
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
    // @todo Unit test brother !
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

  /** Move up cursors
    *
    * @param row Number of row to move, default 1
    */
  def moveCursorUp(row: Int = 1): Unit = {
    cursors.foreach { _.moveUp(row) }
    removeMergedCursors
  }

  /** Move down cursors
    *
    * @param row Number of row to move, default 1
    */
  def moveCursorDown(row: Int = 1): Unit = {
    cursors.foreach { _.moveDown(row) }
    removeMergedCursors
  }

  /** Move left cursors
    *
    * @param column Number of column to move, default 1
    */
  def moveCursorLeft(column: Int = 1): Unit = {
    cursors.foreach { _.moveLeft(column) }
    removeMergedCursors
  }

  /** Move right the cursor
    *
    * @param column Number of column to move, default 1
    */
  def moveCursorRight(column: Int = 1): Unit = {
    cursors.foreach { _.moveRight(column) }
    removeMergedCursors
  }

  /** Move to the top cursors
    */
  def moveCursorToTop: Unit = {
    cursors.foreach { _.moveToTop }
    removeMergedCursors
  }

  /** Move to botom the cursor
    */
  def moveCursorToBottom: Unit = {
    cursors.foreach { _.moveToBottom }
    removeMergedCursors
  }
}
