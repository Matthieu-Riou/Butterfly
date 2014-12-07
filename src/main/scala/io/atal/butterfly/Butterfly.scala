package io.atal.butterfly

import scala.io.StdIn.readLine

object butterfly {

  val editorManager = new EditorManager
  var _macro: Map[String, Action] = Map()

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
                 |  - cursor ([left/right/up/down] [int]) | ([top/bottom]) | ([add/remove] [int] [int])
                 |  - selection ([left/right] [int]) | ([clear])
                 |  - cut / copy / paste
                 |  - selectAll [text]
                 |  - eraseAll [text]
                 |  - macro ([list/add]) | ([apply] [name])
                 |  - quit """.stripMargin)
                 
      val input = readLine("> ")
      
      println
      
      val split = input.split(" ")
      
      try {
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
            case "add" => execute(new AddCursor((split(2).toInt, split(3).toInt)))
            case "remove" => execute(new RemoveCursor((split(2).toInt, split(3).toInt)))
            case _ => Unit
          }
          case "selection" => split(1) match {
            case "left" => execute(new MoveSelection(-1 * split(2).toInt))
            case "right" => execute(new MoveSelection(split(2).toInt))
            case "clear" => execute(new ClearSelection())
            case _ => Unit
          }
          case "cut" => execute(new Cut())
          case "copy" => execute(new Copy())
          case "paste" => execute(new Paste())
          case "selectAll" => if(input.splitAt(10)._2 != "") execute(new SelectAll(input.splitAt(10)._2))
          case "eraseAll" => if(input.splitAt(9)._2 != "") execute(new EraseAll(input.splitAt(9)._2))
          case "macro" => split(1) match {
            case "list" => printMacro; println
            case "add" => addMacro
            case "apply" => execute(_macro.get(split(2)).get)
          }
          case "quit" => continue = false
          case _ => Unit
        }
      }
      catch {
        case _: Throwable => Unit
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
  
  def printMacro : Unit = {
    println("Macro\n-----")
    for((k,_) <- _macro) {
      println(k)
    }
    println("-----")
  
  }
  
  def addMacro: Unit = {
    var action = new Composite()
    
    val name = readLine("> Give a name to your macro : ")
    
    println
    
    var continue = true
    var valid = false
    
    while(continue) {
      println("""|Compose your macro : 
                   |  - write [text]
                   |  - line
                   |  - erase
                   |  - cursor ([left/right/up/down] [int]) | ([top/bottom]) | ([add/remove] [int] [int])
                   |  - selection ([left/right] [int]) | ([clear])
                   |  - cut / copy / paste
                   |  - selectAll [text]
                   |  - eraseAll [text]
                   |  - valid
                   |  - quit""".stripMargin)
                   
      val input = readLine("> ")
      
      println
        
      val split = input.split(" ")
      
      try {
        split(0) match {
          case "write" => action.add(new Write(input.splitAt(6)._2))
          case "line" => action.add(new Write('\n'.toString))
          case "erase" => action.add(new Erase())
          case "cursor" => split(1) match {
            case "left" => action.add(new MoveCursorsLeft(split(2).toInt))
            case "right" => action.add(new MoveCursorsRight(split(2).toInt))
            case "up" => action.add(new MoveCursorsUp(split(2).toInt))
            case "down" => action.add(new MoveCursorsDown(split(2).toInt))
            case "top" => action.add(new MoveCursorsToTop())
            case "bottom" => action.add(new MoveCursorsToBottom())
            case "add" => action.add(new AddCursor((split(2).toInt, split(3).toInt)))
            case "remove" => action.add(new RemoveCursor((split(2).toInt, split(3).toInt)))
            case _ => Unit
          }
          case "selection" => split(1) match {
            case "left" => action.add(new MoveSelection(-1 * split(2).toInt))
            case "right" => action.add(new MoveSelection(split(2).toInt))
            case "clear" => action.add(new ClearSelection())
            case _ => Unit
          }
          case "cut" => action.add(new Cut())
          case "copy" => action.add(new Copy())
          case "paste" => action.add(new Paste())
          case "selectAll" => if(input.splitAt(10)._2 != "") action.add(new SelectAll(input.splitAt(10)._2))
          case "eraseAll" => if(input.splitAt(9)._2 != "") action.add(new EraseAll(input.splitAt(9)._2))
          case "valid" => continue = false; valid = true
          case "quit" => continue = false; valid = false
          case _ => Unit
        }
      }
      catch {
        case _: Throwable => Unit
      }  
    }
   
    if(valid)
      _macro = _macro updated (name, action)
      
    println
  }
}
