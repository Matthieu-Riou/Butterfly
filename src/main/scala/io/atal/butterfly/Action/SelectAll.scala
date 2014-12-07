package io.atal.butterfly

/** Implement the action SelectAll
  * Select all the buffer
  */
class SelectAll extends Action {

  /** Execute the action
    *
    * @param editor The editor onto the action is executed
    * @param clipboard The clipboard onto the action is executed
    */
  def execute(editor: Editor, clipboard: Clipboard): Unit = {
    editor.removeAllCursors
    
    editor.addCursor(0,0)
    
    new MoveSelection(editor.buffer.content.length).execute(editor, clipboard)
      
  }
}

