package io.atal.butterfly

/** A cursor is the little blinking vertical line where the text is inserted
  *
  * @constructor Create a new cursor linked to an editor
  * @param ed The editor
  */
class Cursor(ed: Editor) {
  var _editor: Editor = ed
  var _bufferPosition: (Int, Int) = (0, 0)

  def editor: Editor = _editor

  def editor_=(editor: Editor): Unit = _editor = editor

  def bufferPosition: (Int, Int) = _bufferPosition

  def bufferPosition_=(position: (Int, Int)): Unit = _bufferPosition = (position._1, position._2)

  /** Move up the cursor
    *
    * @param row Number of row to move, default 1
    */
  def moveUp(row: Int = 1): Unit = {}

  /** Move down the cursor
    *
    * @param row Number of row to move, default 1
    */
  def moveDown(row: Int = 1): Unit = {}

  /** Move left the cursor
    *
    * @param column Number of column to move, default 1
    */
  def moveLeft(column: Int = 1): Unit = {}

  /** Move right the cursor
    *
    * @param column Number of column to move, default 1
    */
  def moveRight(column: Int = 1): Unit = {}

  /** Move to the top the cursor
    */
  def moveToTop: Unit = _bufferPosition = (0, 0)
}
