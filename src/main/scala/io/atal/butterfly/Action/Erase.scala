package io.atal.butterfly

/** Implement the action Erase
  * Erase the character right before the cursor's position
  *
  * @constructeur Create the action
  */
class Erase() extends Action {
  
  def erase(editor: Editor): Unit = {
    for (cursor <- editor.cursors) {
      cursor.position match {
        case (0, 0) => Unit
        case (x, 0) => {
          val lines = editor.buffer.lines
          val lastColOfPrevLine = lines(x - 1).length

          // Remove the \n of the previous line
          editor.buffer.remove((x - 1, lastColOfPrevLine), (x, 0))
          cursor.position = (x - 1, lastColOfPrevLine)
        }
        case (x, y) => {
          editor.buffer.remove((x, y - 1), (x, y))
          editor.moveCursorLeft(cursor)
        }
      }
    }
  }
  
  def eraseSelection(editor: Editor): Unit = {
    for (cursor <- editor.cursors) {
      editor.buffer.remove(cursor.position, cursor.cursorSelection.get.position)
    }

    editor.clearSelection
  }
   
  /** Execute the action
    *
    * @param editor The editor onto the action is executed
    */
  def execute(editor: Editor): Unit = {
    if(editor.isSelectionMode)
      eraseSelection(editor)
    else
      erase(editor)
  }
}
