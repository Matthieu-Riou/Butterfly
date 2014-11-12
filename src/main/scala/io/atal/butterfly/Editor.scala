package io.atal.butterfly

/** An editor is the place where you edit a buffer thanks to a cursor (or more ? #later)
  * It tracks user events and coordinate the communication between cursor(s) and the buffer
  * User events tracking is done by the EventManager through the EventTrait
  *
  * @constructor Create a new editor for the buffer
  * @param buff The buffer to edit, default empty buffer
  */
class Editor(buff: Buffer = new Buffer("")) extends EventTrait {
  var _buffer: Buffer = buff
  var _cursor: Cursor = new Cursor(this)

  def buffer: Buffer = _buffer

  def buffer_=(buffer: Buffer): Unit = _buffer = buffer

  /** Move up the cursor
    *
    * @param row Number of row to move, default 1
    * @return true if the cursor has moved
    */
  def moveCursorUp(row: Int = 1): Boolean = {
    // @todo check if going up is possible
    _cursor.moveUp(row)
    return true
  }

  /** Move down the cursor
    *
    * @param row Number of row to move, default 1
    * @return true if the cursor has moved
    */
  def moveCursorDown(row: Int = 1): Boolean = {
    // @todo check if going down is possible
    _cursor.moveDown(row)
    return true
  }

  /** Move left the cursor
    *
    * @param column Number of column to move, default 1
    * @return true if the cursor has moved
    */
  def moveCursorLeft(column: Int = 1): Boolean = {
    // @todo check if going left is possible
    _cursor.moveLeft(column)
    return true
  }

  /** Move right the cursor
    *
    * @param column Number of column to move, default 1
    * @return true if the cursor has moved
    */
  def moveCursorRight(column: Int = 1): Boolean = {
    // @todo check if going right is possible
    _cursor.moveRight(column)
    return true
  }

  /** Move to top the cursor
    */
  def moveCursorToTop: Unit = {
    _cursor.moveToTop
  }

  /** Move to botom the cursor
    */
  def moveCursorToBottom: Unit = {
    // @todo get last line and last column of the buffer
    // _cursor.position = ()
  }

  // Register to events
  // May be useful later
  _event.on("cursor-move", (None) => println("It moved #cursor"))
}
