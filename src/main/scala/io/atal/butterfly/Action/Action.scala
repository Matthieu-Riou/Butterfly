package io.atal.butterfly

/** Define an Action
  */
trait Action {
  def execute(editor: Editor): Unit
}  
