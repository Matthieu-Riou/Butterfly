package io.atal.butterfly

/** Editor class
  */
class Editor {
  var _currentBuffer: Buffer = new Buffer("")
  var _buffers: Array[Buffer] = Array()
  var _cursor: Cursor = new Cursor(this)

  def currentBuffer: Buffer = _currentBuffer

  def buffers: Array[Buffer] = _buffers

  def buffers_=(buffers: Array[Buffer]): Unit = _buffers = buffers

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
