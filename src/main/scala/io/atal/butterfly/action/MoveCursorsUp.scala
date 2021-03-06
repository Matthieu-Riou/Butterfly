package io.atal.butterfly.action

import io.atal.butterfly.{Editor, Clipboard}

/** Implement the action MoveCursorsUp
  * Move the cursors up
  *
  * @constructor Create the action
  * @param move The number of lines you want to move
  */
class MoveCursorsUp(move: Int) extends Action {

  /** Execute the action
    *
    * @param editor The editor onto the action is executed
    * @param clipboard The clipboard onto the action is executed
    */
  def execute(editor: Editor, clipboard: Clipboard): Unit = editor.moveCursorsUp(move)
}
