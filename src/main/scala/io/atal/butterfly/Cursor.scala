package io.atal.butterfly

/** A cursor is the little blinking vertical line where the text is inserted
  *
  * @constructor Create a new cursor linked to an editor
  * @param ed The editor where the cursor lives
  */
class Cursor(ed: Editor, pos: (Int, Int) = (0, 0)) extends EventTrait {
  val _editor: Editor = ed
  var _position: (Int, Int) = pos

  def editor: Editor = _editor

  def position: (Int, Int) = _position

  def position_=(position: (Int, Int)): Unit = _position = (position._1, position._2)

  override def hashCode = _position.hashCode

  override def equals(obj: Any) = obj match {
    case c: Cursor => c.hashCode == this.hashCode
    case _ => false
  }

  /** Move up the cursor
    *
    * @param row Number of row to move, default 1
    */
  def moveUp(row: Int = 1): Unit = {
    // @todo check from editor.buffer if possible
    position = (_position._1 - row, _position._2)
  }

  /** Move down the cursor
    *
    * @param row Number of row to move, default 1
    */
  def moveDown(row: Int = 1): Unit = {
    // @todo check from editor.buffer if possible
    position = (_position._1 + row, _position._2)
  }

  /** Move left the cursor
    *
    * @param column Number of column to move, default 1
    */
  def moveLeft(column: Int = 1): Unit = {
    // @todo check from editor.buffer if possible
    position = (_position._1, _position._2 - column)
  }

  /** Move right the cursor
    *
    * @param column Number of column to move, default 1
    */
  def moveRight(column: Int = 1): Unit = {
    // @todo check from editor.buffer if possible
    position = (_position._1, _position._2 + column)
  }

  /** Move to the top the cursor
    */
  def moveToTop: Unit = position = (0, 0)

  /** Move to the nottom the cursor
    */
  def moveToBottom: Unit = {
    // @todo get from editor.buffer the last line/column
  }
}
