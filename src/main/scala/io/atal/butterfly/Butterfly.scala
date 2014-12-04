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
    println("Buffer\n-----")
    editorManager.content match {
      case "" => Unit
      case x => println(x)
    }
    println("-----")
    
    println("""|What do you want to do ?
               |  - write [text]
               |  - erase""".stripMargin)
               
    var input = readLine("> ")
    
    input.split(" ")(0) match {
      case "write" => execute(new Write(input.splitAt(6)._2)); true
      case "erase" => execute(new Erase()); true
      case "quit" => false
      case x => true
    }
  }
  
  def execute(action: Action): Unit = {
    editorManager.execute(action)
  }
}
