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
    
    object editor extends Label {
      text = current.buffer.content
      preferredSize = new Dimension(1000,500)
      
      focusable = true
      requestFocus
            
      listenTo(keys)
      reactions += {
        case KeyPressed(_, Key.BackSpace, _, _) => {
          current.erase
          updateLabel
        }
        
        case KeyPressed(_, Key.A, _, _) => keyChar("a")
      }
    }
    
    contents = new FlowPanel {
      contents.append(editor)
      border = Swing.EmptyBorder(10, 10, 10, 10)
      
    }
    
    
    def keyChar(char: String): Unit = {
      current.write(char)
      updateLabel
    }
      
    def updateLabel: Unit = editor.text = bufferToLabel(current.buffer.content)
  }
  
  def bufferToLabel(s: String) : String = return "<html>" + s.replaceAll("\n", "<br/>") + "</html>"

}

