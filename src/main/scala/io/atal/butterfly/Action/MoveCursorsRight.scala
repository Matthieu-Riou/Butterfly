package io.atal.butterfly

/** Implement the action MoveCursorsRight
  * Move the cursors right
  *
  * @constructeur Create the action
  * @param move The number of columns you want to move
  */
class MoveCursorsRight(move: Int) extends Action {

  /** Execute the action
    *
    * @param editor The editor onto the action is executed
    * @param clipboard The clipboard onto the action is executed
    */
  def execute(editor: Editor, clipboard: Clipboard): Unit = editor.moveCursorsRight(move)
}
