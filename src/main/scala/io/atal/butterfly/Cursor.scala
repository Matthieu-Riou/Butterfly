package io.atal.butterfly

/** A cursor is the little blinking vertical line where the text is inserted
  *
  * @constructor Create a new cursor with a position
  * @param position Cursor's position, default (0, 0)
  */
class Cursor(var position: (Int, Int) = (0, 0)) {

  override def hashCode: Int = position.hashCode

  override def equals(obj: Any): Boolean = obj match {
    case c: Cursor => c.hashCode == this.hashCode
    case _ => false
  }

  /** Move up the cursor
    *
    * @param row Number of row to move, default 1
    */
  def moveUp(row: Int = 1): Unit = {
    position = (position._1 - row, position._2)
  }

  /** Move down the cursor
    *
    * @param row Number of row to move, default 1
    */
  def moveDown(row: Int = 1): Unit = {
    position = (position._1 + row, position._2)
  }

  /** Move left the cursor
    *
    * @param column Number of column to move, default 1
    */
  def moveLeft(column: Int = 1): Unit = {
    position = (position._1, position._2 - column)
  }

  /** Move right the cursor
    *
    * @param column Number of column to move, default 1
    */
  def moveRight(column: Int = 1): Unit = {
    position = (position._1, position._2 + column)
  }

  /** Move to the top the cursor
    */
  def moveToTop: Unit = position = (0, 0)
}
