package io.atal.butterfly

/** Implement the action EraseAll
  * Erase all the occurences of a string
  */
class EraseAll(text: String) extends Action {

  /** Execute the action
    *
    * @param editor The editor onto the action is executed
    * @param clipboard The clipboard onto the action is executed
    */
  def execute(editor: Editor, clipboard: Clipboard): Unit = {
    new SelectAll(text).execute(editor, clipboard)
    new Erase().execute(editor, clipboard)
  }
}
