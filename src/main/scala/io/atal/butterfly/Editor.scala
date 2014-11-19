package io.atal.butterfly

/** An editor is the place where you edit a buffer thanks to cursors
  * It tracks user events and coordinate the communication between cursors and the buffer
  *
  * @constructor Create a new editor for the buffer
  * @param buffer The buffer to edit, default empty buffer
  */
class Editor(var buffer: Buffer = new Buffer("")) {
  var _cursors: List[Cursor] = List[Cursor](new Cursor(this))

  def cursors: List[Cursor] = _cursors

  def cursors_=(cursors: List[Cursor]): Unit = _cursors = cursors

  /** Write a text into the buffer at the current cursor(s) position
    *
    * @param text The text to be inserted in the buffer
    */
  def write(text: String): Unit = {
    // @todo implements insertion
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
