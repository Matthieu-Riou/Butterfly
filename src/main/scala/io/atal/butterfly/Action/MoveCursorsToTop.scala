package io.atal.butterfly

/** Implement the action MoveCursorsToTop
  * Move the cursors to the top
  *
  * @constructeur Create the action
  */
class MoveCursorsToTop() extends Action {
  
  /** Execute the action
    *
    * @param editor The editor onto the action is executed
    * @param clipboard The clipboard onto the action is executed
    */
  def execute(editor: Editor, clipboard: Clipboard): Unit = editor.moveCursorsToTop
  
}

