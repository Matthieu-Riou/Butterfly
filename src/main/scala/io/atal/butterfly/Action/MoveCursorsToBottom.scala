package io.atal.butterfly

/** Implement the action MoveCursorsToBottom
  * Move the cursors to the bottom
  *
  * @constructeur Create the action
  */
class MoveCursorsToBottom() extends Action {

  /** Execute the action
    *
    * @param editor The editor onto the action is executed
    * @param clipboard The clipboard onto the action is executed
    */
  def execute(editor: Editor, clipboard: Clipboard): Unit = editor.moveCursorsToBottom
}
