package io.atal.butterfly

/** Implement the action MoveCursorsLeft
  * Move the cursors left
  *
  * @constructeur Create the action
  * @param move The number of columns you want to move
  */
class MoveCursorsLeft(move: Int) extends Action {

  /** Execute the action
    *
    * @param editor The editor onto the action is executed
    * @param clipboard The clipboard onto the action is executed
    */
  def execute(editor: Editor, clipboard: Clipboard): Unit = editor.moveCursorsLeft(move)
}
