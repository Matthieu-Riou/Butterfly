package io.atal.butterfly.action
import io.atal.butterfly.Editor
import io.atal.butterfly.Clipboard

/** Implement the action SelectAllText
  * Select all the occurences of a string
  */
class SelectAllText(text: String) extends Action {

  /** Execute the action
    *
    * @param editor The editor onto the action is executed
    * @param clipboard The clipboard onto the action is executed
    */
  def execute(editor: Editor, clipboard: Clipboard): Unit = {
    editor.removeAllCursors
    
    val lines = editor.buffer.lines
    
    for(i <- 0 until lines.length) {
      var index = lines(i).indexOf(text)
      
      while(index != -1) {
        editor.addCursor((i,index))
        index = lines(i).indexOf(text, index +1)
      }
    }
    
    new MoveSelection(text.length).execute(editor, clipboard)
      
  }
}
