package io.atal.butterfly.action
import io.atal.butterfly.Editor
import io.atal.butterfly.Clipboard

/** Implement the action MoveStartSelection
  * Move the start of the selections
  *
  * @constructeur Create the action
  * @param move The number of character (absolute value) that moves of the start of the selection (on the left if move < 0, one the right if move > 0)
  */
class MoveStartSelection(move: Int) extends Action {
  
  /** Execute the action
    *
    * @param editor The editor onto the action is executed
    * @param clipboard The clipboard onto the action is executed
    */
  def execute(editor: Editor, clipboard: Clipboard): Unit = editor.moveStartSelection(move)
  
}

