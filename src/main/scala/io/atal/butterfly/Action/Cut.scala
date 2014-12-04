package io.atal.butterfly

/** Implement the action Cut
  * Cut the text of the selection
  * If several selections, concat all the selection's texts
  */
class Cut extends Action {

  /** Execute the action
    *
    * @param editor The editor onto the action is executed
    * @param clipboard The clipboard onto the action is executed
    */
  def execute(editor: Editor, clipboard: Clipboard): Unit = {
    new Copy().execute(editor, clipboard)
    new Erase().execute(editor, clipboard)
  }
}
