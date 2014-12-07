package io.atal.butterfly.action
import io.atal.butterfly.Editor
import io.atal.butterfly.Clipboard

/** Implement the action Copy
  * Copy the text of the selection
  * If several selections, concat all the selection's texts
  */
class Copy extends Action {

  /** Execute the action
    *
    * @param editor The editor onto the action is executed
    * @param clipboard The clipboard onto the action is executed
    */
  def execute(editor: Editor, clipboard: Clipboard): Unit = {
    clipboard.data = editor.getSelectionContent
  }
}
