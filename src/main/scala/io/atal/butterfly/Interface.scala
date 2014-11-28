package io.atal.butterfly

import scala.swing._
import scala.swing.event._

/**
 * A simple swing demo.
 */
object HelloWorld extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "Hello, World!"
    
    var editorManager = new EditorManager
    editorManager.openEditor
      
    var current: Editor = editorManager.currentEditor.get
    var isSpec = false
    
    object editor extends EditorPane {
      text = current.buffer.content
      preferredSize = new Dimension(1000,500)
      
      editable = false
      caret.visible = true
      
      focusable = true
      requestFocus
            
      listenTo(keys)
      reactions += {
        /**
          * KeyPressed and KeyTyped are too different event, both sent everytime.
          * KeyPressed allow to do any action for a key (those which are non buguy (all accents)), for exemple erase for backspace
          * KeyTyped allow to capture the character generate with a key (taking account shift, alt, ...) => no buguy accent, but buguy char for BackSpace
          *   It doesn't recognize a key, so no filter :(
          */
          
        case KeyPressed(_, Key.BackSpace, _, _) => {
          isSpec = true
          current.erase
          updateLabel
        }
        
        case KeyPressed(_, Key.Left, _, _) => {
          isSpec = true
          current.moveCursorsLeft()
        }
        
        case KeyPressed(_, Key.Right, _, _) => {
          isSpec = true
          current.moveCursorsRight()
        }
        
        case KeyPressed(_, x, _, _) => isSpec = false //Allow for non-specified KeyPressed (like Key.BackSpace) to be match with KeyTyped. It's ugly. Better way ?
        
        case KeyTyped(_, y, _, _) => {
          if(!isSpec) {
            current.write(y.toString)
            updateLabel
          }
        }
      }
    }
    
    contents = new FlowPanel {
      contents.append(editor)
      border = Swing.EmptyBorder(10, 10, 10, 10)
      
    
    }
    
    def updateLabel: Unit = {
      editor.text = current.buffer.content
      editor.caret.position = current.getIndexPosition(current.cursors.head)
    }
  }

}

