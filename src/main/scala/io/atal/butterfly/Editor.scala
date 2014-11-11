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

  // Register to events
  _event.on("cursor-move", (None) => println("It moved #cursor"))
}
