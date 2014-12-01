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
}

