package io.atal.butterfly

/** Editor class
  */
class Editor {
  var _buffers: Array[Buffer] = Array()

  def buffers = _buffers

  /** Add a buffer to the editor
    *
    * @param buffer The buffer to add
    */
  def addBuffer(buffer: Buffer): Unit = _buffers = _buffers :+ buffer

  /** Remove a buffer from the editor
    *
    * @param buffer The buffer to remove
    */
  def removeBuffer(buffer: Buffer): Unit = _buffers = _buffers diff Array(buffer)
}
