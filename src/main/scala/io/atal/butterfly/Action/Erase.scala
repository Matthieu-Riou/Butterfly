package io.atal.butterfly

/** Implement the action Erase
  * Erase the character right before the cursor's position
  *
  * @constructeur Create the action
  */
class Erase() extends Action {

  /** Execute the action
    *
    * @param editor The editor onto the action is executed
    * @param clipboard The clipboard onto the action is executed
    */
  def execute(editor: Editor, clipboard: Clipboard): Unit = editor.erase
}
