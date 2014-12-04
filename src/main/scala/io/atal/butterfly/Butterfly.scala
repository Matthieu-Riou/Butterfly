package io.atal.butterfly

import scala.io.StdIn.readLine

object butterfly {

  val editorManager = new EditorManager
  
  def main(args: Array[String]) {    
    editorManager.openEditor
    
    println("Hey! This is butterfly !")
    
    while(update) {}
  }
  
  def update: Boolean = {
    printBuffer
    
    println
    
    println("""|What do you want to do ?
               |  - write [text]
               |  - line
               |  - erase
               |  - cursor [left/right/up/down] [int]
               |  - quit """.stripMargin)
               
    var input = readLine("> ")
    
    println
    
    input.split(" ")(0) match {
      case "write" => execute(new Write(input.splitAt(6)._2)); true
      case "line" => execute(new Write('\n'.toString)); true
      case "erase" => execute(new Erase()); true
      case "cursor" => input.split(" ")(1) match {
        case "left" => execute(new MoveCursorsLeft(input.split(" ")(2).toInt)); true
        case "right" => execute(new MoveCursorsRight(input.split(" ")(2).toInt)); true
        case "up" => execute(new MoveCursorsUp(input.split(" ")(2).toInt)); true
        case "down" => execute(new MoveCursorsDown(input.split(" ")(2).toInt)); true
      }
      case "quit" => false
      case x => true
    }
  }
  
  def execute(action: Action): Unit = {
    editorManager.execute(action)
  }
  
  def printBuffer: Unit = {
    println("Buffer\n-----")
    val lines = editorManager.contentByLines
    for(i <- 0 until lines.length) {
      println(lines(i))
      var strCursor: String = " "*(lines(i).length+1)
      
      for(cursor <- editorManager.cursors) {
        if(cursor.position._1 == i) {
          strCursor = strCursor.updated(cursor.position._2, '^')
        }
      }
      
      println(strCursor)
    }
    println("-----")
  }
}
