package io.atal.butterfly.action
import io.atal.butterfly.Editor
import io.atal.butterfly.Clipboard

/** Implement the action Paste
  * Paste the text of the selection
  * If several selections, concat all the selection's texts
  */
class Paste extends Action {
  def execute(editor: Editor, clipboard: Clipboard): Unit = {
    editor.write(clipboard.data)
  }
}
