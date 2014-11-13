package io.atal.butterfly

/** A cursor is the little blinking vertical line where the text is inserted
  *
  * @constructor Create a new cursor linked to an editor
  * @param ed The editor where the cursor lives
  */
class Cursor(ed: Editor) extends EventTrait {
  val _editor: Editor = ed
  var _position: (Int, Int) = (0, 0)

  def editor: Editor = _editor

  def position: (Int, Int) = _position

  def position_=(position: (Int, Int)): Unit = {
    _position = (position._1, position._2)
  }

  /** Move up the cursor
    *
    * @param row Number of row to move, default 1
    */
  def moveUp(row: Int = 1): Unit = position = (_position._1 - row, _position._2)

  /** Move down the cursor
    *
    * @param row Number of row to move, default 1
    */
  def moveDown(row: Int = 1): Unit = position = (_position._1 + row, _position._2)

  /** Move left the cursor
    *
    * @param column Number of column to move, default 1
    */
  def moveLeft(column: Int = 1): Unit = position = (_position._1, _position._2 - column)

  /** Move right the cursor
    *
    * @param column Number of column to move, default 1
    */
  def moveRight(column: Int = 1): Unit = position = (_position._1, _position._2 + column)

  /** Move to the top the cursor
    */
  def moveToTop: Unit = position = (0, 0)
}
