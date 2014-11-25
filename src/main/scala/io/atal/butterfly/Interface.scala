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
    var isShift: Boolean = false
    var isCapsLock: Boolean = false
    
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
        
        case KeyPressed(_, Key.Shift, _, _) => isShift = true
        case KeyReleased(_, Key.Shift, _, _) => isShift = false
        
        case KeyPressed(_, Key.CapsLock, _, _) => {
          if(isCapsLock)
            isCapsLock = false
          else
            isCapsLock = true
        }
        
        case KeyPressed(_, Key.A, _, _) => keyChar("a")
        case KeyPressed(_, Key.B, _, _) => keyChar("b")
        case KeyPressed(_, Key.C, _, _) => keyChar("c")
        case KeyPressed(_, Key.D, _, _) => keyChar("d")
        case KeyPressed(_, Key.E, _, _) => keyChar("e")
        case KeyPressed(_, Key.F, _, _) => keyChar("f")
        case KeyPressed(_, Key.G, _, _) => keyChar("g")
        case KeyPressed(_, Key.H, _, _) => keyChar("h")
        case KeyPressed(_, Key.I, _, _) => keyChar("i")
        case KeyPressed(_, Key.J, _, _) => keyChar("j")
        case KeyPressed(_, Key.K, _, _) => keyChar("k")
        case KeyPressed(_, Key.L, _, _) => keyChar("l")
        case KeyPressed(_, Key.M, _, _) => keyChar("m")
        case KeyPressed(_, Key.N, _, _) => keyChar("n")
        case KeyPressed(_, Key.O, _, _) => keyChar("o")
        case KeyPressed(_, Key.P, _, _) => keyChar("p")
        case KeyPressed(_, Key.Q, _, _) => keyChar("q")
        case KeyPressed(_, Key.R, _, _) => keyChar("r")
        case KeyPressed(_, Key.S, _, _) => keyChar("s")
        case KeyPressed(_, Key.T, _, _) => keyChar("t")
        case KeyPressed(_, Key.U, _, _) => keyChar("u")
        case KeyPressed(_, Key.V, _, _) => keyChar("v")
        case KeyPressed(_, Key.W, _, _) => keyChar("w")
        case KeyPressed(_, Key.X, _, _) => keyChar("x")
        case KeyPressed(_, Key.Y, _, _) => keyChar("y")
        case KeyPressed(_, Key.Z, _, _) => keyChar("z")
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

