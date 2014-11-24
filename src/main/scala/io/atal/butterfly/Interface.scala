package io.atal.butterfly

import scala.swing._
import scala.swing.event._

/**
 * A simple swing demo.
 */
object HelloWorld extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "Hello, World!"
    
    object editor extends Label {
      var buffer = new Buffer("")
      var _cursor = 0

      text = buffer.toString()
      preferredSize = new Dimension(1000,500)
            
      listenTo(button)
      reactions += {
        case ButtonClicked(button) =>
          if(input.text != "")
          {
            buffer.insert(input.text, _cursor)
            _cursor += input.text.length
            text = bufferToLabel(buffer.toString())
            input.text = ""
          }
      }
    }
    
    object button extends Button {
      text = "Insert"
    }
    
    val input = new TextArea(50,100)
    
    contents = new BorderPanel {
      add(editor, BorderPanel.Position.North)
      add(input, BorderPanel.Position.West)
      add(button, BorderPanel.Position.East)
      border = Swing.EmptyBorder(10, 10, 10, 10)
      
      focusable = true
      requestFocus
    }
    
  }
  
  def bufferToLabel(s: String) : String = return "<html>" + s.replaceAll("\n", "<br/>") + "</html>"
}

