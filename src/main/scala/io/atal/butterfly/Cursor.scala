package io.atal.butterfly

/** A cursor is the little blinking vertical line where the text is inserted
  *
  * @constructor Create a new cursor with a position
  * @param position Cursor's position, default (0, 0)
  */
class Cursor(var position: (Int, Int) = (0, 0)) {

  var _cursorSelection: Option[Cursor] = None

  def cursorSelection: Option[Cursor] = _cursorSelection

  def cursorSelection_=(cursor: Option[Cursor]): Unit = _cursorSelection = cursor

  override def hashCode: Int = position.hashCode

  override def equals(obj: Any): Boolean = obj match {
    case c: Cursor => c.hashCode == this.hashCode
    case _ => false
  }
  
  /** Compare two cursors
    * Return true if this > cursor (false if equals)
    *
    * @param cursor The other cursor
    */
  def greaterThan(cursor: Cursor): Boolean = (position, cursor.position) match {
    case((x1,y1),(x2,y2)) if x1 > x2 => true
    case((x1,y1),(x2,y2)) if x1 < x2 => false
    case((_,y1),(_, y2)) if y1 > y2 => true
    case(_) => false
  }
  
  /** Compare two cursors
    * Return true if this < cursor (false if equals)
    *
    * @param cursor The other cursor
    */
  def lowerThan(cursor: Cursor): Boolean = (position, cursor.position) match {
    case((x1,y1),(x2,y2)) if x1 < x2 => true
    case((x1,y1),(x2,y2)) if x1 > x2 => false
    case((_,y1),(_, y2)) if y1 < y2 => true
    case(_) => false
  }
}
