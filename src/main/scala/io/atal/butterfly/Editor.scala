package io.atal.butterfly

/** An editor is the place where you edit a buffer thanks to cursors
  * It tracks user events and coordinate the communication between cursors and the buffer
  * User events tracking is done by the EventManager through the EventTrait
  *
  * @constructor Create a new editor for the buffer
  * @param buff The buffer to edit, default empty buffer
  */
class Editor(buff: Buffer = new Buffer("")) extends EventTrait {
  var _buffer: Buffer = buff
  var _cursors: List[Cursor] = List[Cursor](new Cursor(this))

  def buffer: Buffer = _buffer

  def buffer_=(buffer: Buffer): Unit = _buffer = buffer

  /** Write a text into the buffer at the current cursor(s) position
    *
    * @param text The text to be inserted in the buffer
    */
  def write(text: String): Unit = {
    // @todo implements insertion when the Buffer branch will be merged
  }

  /** Add a cursor
    *
    * @param cursor Cursor to add
    */
  def addCursor(cursor: Cursor): Unit = _cursors = _cursors :+ cursor

  /** Remove a cursor
    *
    * @param cursor Cursor to remove
    */
  def removeCursor(cursor: Cursor): Unit = _cursors.diff(List(cursor))

  /** Move up cursors
    *
    * @param row Number of row to move, default 1
    * @return true if the cursor has moved
    */
  def moveCursorUp(row: Int = 1): Boolean = {
    _cursors.foreach { _.moveUp(row) }
    return true
  }

  /** Move down cursors
    *
    * @param row Number of row to move, default 1
    * @return true if the cursor has moved
    */
  def moveCursorDown(row: Int = 1): Boolean = {
    // @todo check if going down is possible
    _cursors.foreach { _.moveDown(row) }
    return true
  }

  /** Move left cursors
    *
    * @param column Number of column to move, default 1
    * @return true if the cursor has moved
    */
  def moveCursorLeft(column: Int = 1): Boolean = {
    // @todo check if going left is possible
    _cursors.foreach { _.moveLeft(column) }
    return true
  }

  /** Move right the cursor
    *
    * @param column Number of column to move, default 1
    * @return true if the cursor has moved
    */
  def moveCursorRight(column: Int = 1): Boolean = {
    // @todo check if going right is possible
    _cursors.foreach { _.moveRight(column) }
    return true
  }

  /** Move to the top cursors
    */
  def moveCursorToTop: Unit = {
    _cursors.foreach { _.moveToTop }
  }

  /** Move to botom the cursor
    */
  def moveCursorToBottom: Unit = {
    _cursors.foreach { _.moveToBottom }
  }

  // Register to events
  // May be useful later
  _event.on("cursor-move", (None) => println("It moved #cursor"))
}
