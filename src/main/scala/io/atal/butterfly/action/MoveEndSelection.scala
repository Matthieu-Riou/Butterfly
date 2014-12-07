package io.atal.butterfly.action

import io.atal.butterfly.{Editor, Clipboard}

/** Implement the action MoveEndSelection
  * Move the end of the selections
  *
  * @constructor Create the action
  * @param move The number of character (absolute value) that moves of the end of the selection (on the left if move < 0, one the right if move > 0)
  */
class MoveEndSelection(move: Int) extends Action {

  /** Execute the action
    *
    * @param editor The editor onto the action is executed
    * @param clipboard The clipboard onto the action is executed
    */
  def execute(editor: Editor, clipboard: Clipboard): Unit = editor.moveEndSelection(move)
}
