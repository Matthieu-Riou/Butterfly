package io.atal.butterfly.action
import io.atal.butterfly.Editor
import io.atal.butterfly.Clipboard

/** Define an Action
  */
trait Action {
  def execute(editor: Editor, clipboard: Clipboard): Unit
}  
