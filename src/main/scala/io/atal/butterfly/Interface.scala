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
        
        case KeyPressed(_, Key.A, _, _) => keyChar("a", "A")
        case KeyPressed(_, Key.B, _, _) => keyChar("b", "B")
        case KeyPressed(_, Key.C, _, _) => keyChar("c", "C")
        case KeyPressed(_, Key.D, _, _) => keyChar("d", "D")
        case KeyPressed(_, Key.E, _, _) => keyChar("e", "E")
        case KeyPressed(_, Key.F, _, _) => keyChar("f", "F")
        case KeyPressed(_, Key.G, _, _) => keyChar("g", "G")
        case KeyPressed(_, Key.H, _, _) => keyChar("h", "H")
        case KeyPressed(_, Key.I, _, _) => keyChar("i", "I")
        case KeyPressed(_, Key.J, _, _) => keyChar("j", "J")
        case KeyPressed(_, Key.K, _, _) => keyChar("k", "K")
        case KeyPressed(_, Key.L, _, _) => keyChar("l", "L")
        case KeyPressed(_, Key.M, _, _) => keyChar("m", "M")
        case KeyPressed(_, Key.N, _, _) => keyChar("n", "N")
        case KeyPressed(_, Key.O, _, _) => keyChar("o", "O")
        case KeyPressed(_, Key.P, _, _) => keyChar("p", "P")
        case KeyPressed(_, Key.Q, _, _) => keyChar("q", "Q")
        case KeyPressed(_, Key.R, _, _) => keyChar("r", "R")
        case KeyPressed(_, Key.S, _, _) => keyChar("s", "S")
        case KeyPressed(_, Key.T, _, _) => keyChar("t", "T")
        case KeyPressed(_, Key.U, _, _) => keyChar("u", "U")
        case KeyPressed(_, Key.V, _, _) => keyChar("v", "V")
        case KeyPressed(_, Key.W, _, _) => keyChar("w", "W")
        case KeyPressed(_, Key.X, _, _) => keyChar("x", "X")
        case KeyPressed(_, Key.Y, _, _) => keyChar("y", "Y")
        case KeyPressed(_, Key.Z, _, _) => keyChar("z", "Z")
        
        case KeyPressed(_, Key.Ampersand, _, _) => keyOther("&", "1")
        //case KeyPressed(_, Key.Key2, _, _) => keyCapsSpec("é", "2", "É") Code d'erreur
        case KeyPressed(_, Key.Quotedbl, _, _) => keyOther("\"", "3")
        case KeyPressed(_, Key.Quote, _, _) => keyOther("'", "4")
        case KeyPressed(_, Key.LeftParenthesis, _, _) => keyOther("(", "5")
        case KeyPressed(_, Key.Minus, _, _) => keyOther("-", "6")
        //case KeyPressed(_, Key.Key7, _, _) => keyCapsSpec("è", "7", "È") Code d'erreur
        case KeyPressed(_, Key.Underscore, _, _) => keyOther("_", "8")
        //case KeyPressed(_, Key.Key9, _, _) => keyCapsSpec("ç", "9", "Ç") Code d'erreur
        //case KeyPressed(_, Key.Key0, _, _) => keyCapsSpec("à", "0", "À") Code d'erreur
        case KeyPressed(_, Key.RightParenthesis, _, _) => keyOther(")", "°")
        case KeyPressed(_, Key.Equals, _, _) => keyOther("=", "+")
      }
    }
    
    contents = new FlowPanel {
      contents.append(editor)
      border = Swing.EmptyBorder(10, 10, 10, 10)
      
    }
    
    
    def keyChar(lowerChar: String, upperChar: String): Unit = {
      if((isShift && !isCapsLock) || (isCapsLock && !isShift))
        current.write(upperChar)
      else
        current.write(lowerChar)
        
      updateLabel
    }
    
    def keyCapsSpec(lower: String, shift: String, capsLock: String): Unit = {
      if(isShift)
        current.write(shift)
      else if(isCapsLock)
        current.write(capsLock)
      else
        current.write(lower)
      
      updateLabel
    }
    
    def keyOther(lower: String, shift: String): Unit = {
      if(isShift)
        current.write(shift)
      else
        current.write(lower)
        
      updateLabel
    }
    
    def updateLabel: Unit = editor.text = bufferToLabel(current.buffer.content)
  }
  
  def bufferToLabel(s: String) : String = return "<html>" + s.replaceAll("\n", "<br/>") + "</html>"

}

