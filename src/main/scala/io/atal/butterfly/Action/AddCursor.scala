package io.atal.butterfly

/** Implement the action AddCursor
  * Add a new cursor
  */
class AddCursor(line: Int, column:Int) extends Action {

  /** Execute the action
    *
    * @param editor The editor onto the action is executed
    * @param clipboard The clipboard onto the action is executed
    */
  def execute(editor: Editor, clipboard: Clipboard): Unit = {
    editor.addCursor(line, column)
  }
}
