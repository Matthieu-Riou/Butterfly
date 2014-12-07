package io.atal.butterfly.action
import io.atal.butterfly.Editor
import io.atal.butterfly.Clipboard

/** Implement the action Undo
  * Undo the last action on the buffer
  *
  * @constructeur Create the action
  */
class Undo extends Action {

  /** Execute the action
    *
    * @param editor The editor onto the action is executed
    * @param clipboard The clipboard onto the action is executed
    */
  def execute(editor: Editor, clipboard: Clipboard): Unit = editor.undo
}
