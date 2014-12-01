package io.atal.butterfly

/** Define an Action
  */
trait Action {
  def execute(editor: Editor): Unit
}

/** Implement the action Write
  * Write a text in the buffer at the cursor's position
  *
  * @constructeur Create the action
  * @param text The text to write
  */
class Write(text: String) extends Action {
  
  /** Execute the action
    *
    * @param editor The editor onto the action is executed
    */
  def execute(editor: Editor): Unit = {
    for(cursor <- editor.cursors) {
      editor.buffer.insert(text, cursor.position)
      editor.moveCursorRight(cursor, text.length)
    }
  }
}

/** Implement the action Erase
  * Erase the character right before the cursor's position
  *
  * @constructeur Create the action
  */
class Erase() extends Action {
  
  /** Execute the action
    *
    * @param editor The editor onto the action is executed
    */
  def execute(editor: Editor): Unit = {
    for (cursor <- editor.cursors) {
      cursor.position match {
        case (0, 0) => Unit
        case (x, 0) => {
          val lines = editor.buffer.lines
          val lastColOfPrevLine = lines(x - 1).length

          // Remove the \n of the previous line
          editor.buffer.remove((x - 1, lastColOfPrevLine), (x, 0))
          cursor.position = (x - 1, lastColOfPrevLine)
        }
        case (x, y) => {
          editor.buffer.remove((x, y - 1), (x, y))
          editor.moveCursorLeft(cursor)
        }
      }
    }
  }
}
