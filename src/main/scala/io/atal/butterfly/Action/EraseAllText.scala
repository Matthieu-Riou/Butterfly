package io.atal.butterfly.action

import io.atal.butterfly.{Editor, Clipboard}

/** Implement the action EraseAllText
  * Erase all the occurences of a string
  */
class EraseAllText(text: String) extends Action {

  /** Execute the action
    *
    * @param editor The editor onto the action is executed
    * @param clipboard The clipboard onto the action is executed
    */
  def execute(editor: Editor, clipboard: Clipboard): Unit = {
    new SelectAllText(text).execute(editor, clipboard)
    new Erase().execute(editor, clipboard)
  }
}
