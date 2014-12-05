package io.atal.butterfly

import scala.io.StdIn.readLine

object butterfly {

  val editorManager = new EditorManager

  def main(args: Array[String]) {
    editorManager.openEditor

    println("Hey! This is butterfly !")

    println
    
    var continue = true
    
    while(continue) {
      printBuffer
      
      println
      
      println("""|What do you want to do ?
                 |  - write [text]
                 |  - line
                 |  - erase
                 |  - cursor ([left/right/up/down] [int]) | ([top/bottom])
                 |  - selection [left/right] [int]
                 |  - quit """.stripMargin)
                 
      val input = readLine("> ")
      
      println
      
      val split = input.split(" ")
      
      split(0) match {
        case "write" => execute(new Write(input.splitAt(6)._2))
        case "line" => execute(new Write('\n'.toString))
        case "erase" => execute(new Erase())
        case "cursor" => split(1) match {
          case "left" => execute(new MoveCursorsLeft(split(2).toInt))
          case "right" => execute(new MoveCursorsRight(split(2).toInt))
          case "up" => execute(new MoveCursorsUp(split(2).toInt))
          case "down" => execute(new MoveCursorsDown(split(2).toInt))
          case "top" => execute(new MoveCursorsToTop())
          case "bottom" => execute(new MoveCursorsToBottom())
        }
        case "selection" => split(1) match {
          case "left" => execute(new MoveSelection(-1 * split(2).toInt))
          case "right" => execute(new MoveSelection(split(2).toInt))
        }
        case "quit" => continue = false
        case _ => Unit
      }
    }
  }

  def execute(action: Action): Unit = {
    editorManager.execute(action)
  }

  def printBuffer: Unit = {
    println("Buffer\n-----")
    val lines = editorManager.contentByLines
    for (i <- 0 until lines.length) {
      println(lines(i))
      var strCursor: String = " "*(lines(i).length+1)

      val selection = editorManager.isSelectionMode

      for (cursor <- editorManager.cursors) {
        if (cursor.position._1 == i) {
          if(selection) {
            if(cursor.greaterThen(cursor.cursorSelection.get))
              strCursor = strCursor.updated(cursor.position._2, '<')
            else
              strCursor = strCursor.updated(cursor.position._2, '>')
          }
          else 
            strCursor = strCursor.updated(cursor.position._2, '^')
        }
        
        if(selection && cursor.cursorSelection.get.position._1 == i) {
          if(cursor.greaterThen(cursor.cursorSelection.get))
            strCursor = strCursor.updated(cursor.cursorSelection.get.position._2, '>')
          else
            strCursor = strCursor.updated(cursor.cursorSelection.get.position._2, '<')
        }
      }

      println(strCursor)
    }
    println("-----")
  }
}
