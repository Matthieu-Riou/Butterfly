package io.atal.butterfly.action
import io.atal.butterfly.Editor
import io.atal.butterfly.Clipboard

/** Implement the action EraseAll
  * Erase all the buffer
  */
class EraseAll() extends Action {

  /** Execute the action
    *
    * @param editor The editor onto the action is executed
    * @param clipboard The clipboard onto the action is executed
    */
  def execute(editor: Editor, clipboard: Clipboard): Unit = {
    new SelectAll().execute(editor, clipboard)
    new Erase().execute(editor, clipboard)
  }
}
