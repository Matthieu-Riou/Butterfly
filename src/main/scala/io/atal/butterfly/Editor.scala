package io.atal.butterfly

/** An editor is the place where you edit a buffer thanks to a cursor (or more ? #later)
  * It tracks user events and coordinate the communication between cursor(s) and the buffer
  *
  * @constructor Create a new editor for the buffer
  * @param buff The buffer to edit
  */
class Editor(buff: Buffer = new Buffer("")) {
  var _buffer: Buffer = buff
  var _cursor: Cursor = new Cursor(this)

  def buffer: Buffer = _buffer

  def buffer_=(buffer: Buffer): Unit = _buffer = buffer
}
