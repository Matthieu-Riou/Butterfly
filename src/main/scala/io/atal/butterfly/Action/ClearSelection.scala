package io.atal.butterfly

/** Implement the action ClearSelection
  * Remove a cursor
  */
class ClearSelection extends Action {

  /** Execute the action
    *
    * @param editor The editor onto the action is executed
    * @param clipboard The clipboard onto the action is executed
    */
  def execute(editor: Editor, clipboard: Clipboard): Unit = {
    editor.clearSelection
  }
}