package io.atal.butterfly.action

import io.atal.butterfly.{Editor, Clipboard}

/** Implement the action Write
  * Write a text in the buffer at the cursor's position
  *
  * @constructor Create the action with the given text
  * @param text The text to write
  */
class Write(text: String) extends Action {

  /** Execute the action
    *
    * @param editor The editor onto the action is executed
    * @param clipboard The clipboard onto the action is executed
    */
  def execute(editor: Editor, clipboard: Clipboard): Unit = editor.write(text)
}
