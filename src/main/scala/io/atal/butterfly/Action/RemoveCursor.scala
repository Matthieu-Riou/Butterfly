package io.atal.butterfly

/** Implement the action RemoveCursor
  * Remove a cursor
  */
class RemoveCursor(line: Int, column:Int) extends Action {

  /** Execute the action
    *
    * @param editor The editor onto the action is executed
    * @param clipboard The clipboard onto the action is executed
    */
  def execute(editor: Editor, clipboard: Clipboard): Unit = {
    editor.removeCursor(line, column)
  }
}
