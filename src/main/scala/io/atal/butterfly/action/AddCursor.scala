package io.atal.butterfly.action

import io.atal.butterfly.{Editor, Clipboard}

/** Implement the action AddCursor
  */
class AddCursor(position: (Int, Int)) extends Action {

  /** Execute the action
    *
    * @param editor The editor onto the action is executed
    * @param clipboard The clipboard onto the action is executed
    */
  def execute(editor: Editor, clipboard: Clipboard): Unit = {
    editor.addCursor(position)
  }
}
