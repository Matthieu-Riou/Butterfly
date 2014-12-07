package io.atal.butterfly.action

import io.atal.butterfly.{Editor, Clipboard}

/** Implement the action Composite
  * An action composed of other actions
  */
class Composite extends Action {

  var actions: List[Action] = List()

  /** Add an action
    *
    * @param action The action to add
    */
  def add(action: Action): Unit = actions = action :: actions

  /** Execute the action
    *
    * @param editor The editor onto the action is executed
    * @param clipboard The clipboard onto the action is executed
    */
  def execute(editor: Editor, clipboard: Clipboard): Unit = {
    for (action <- actions.reverse) {
      action.execute(editor, clipboard)
    }
  }
}
