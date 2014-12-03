package io.atal.butterfly

/** Implement the action Write
  * Write a text in the buffer at the cursor's position
  *
  * @constructeur Create the action
  * @param text The text to write
  */
class MoveCursorsLeft(move: Int) extends Action {
  
  /** Execute the action
    *
    * @param editor The editor onto the action is executed
    */
  def execute(editor: Editor, clipboard: Clipboard): Unit = editor.moveCursorsLeft(move)
  
}

