package io.atal.butterfly
import scala.collection.mutable.ListBuffer

class Buffer(s: String) {
  var _content: String = s
  
  var _historicBefore: ListBuffer[Historique] = new ListBuffer()
  var _historicAfter: ListBuffer[Historique] = new ListBuffer()

  def content: String = _content
  def content_=(s: String): Unit = _content = s

  override def toString(): String = _content
  
  /** Return a selected substring in the buffer
    * @param beginIndex The beginning index of the selection
    * @param endIndex The endding index of the selection
    * @return beginIndex The substring between the beginIndex (including) and the endding index (excluded)
    */
  def select(beginIndex: Int, endIndex: Int): String = _content.substring(beginIndex, endIndex)

  /** Add the inserting event to the historic and call simple insert
    * @param string The string to insert
    * @param index The position of the insertion
    */
  def insert(string: String, index: Int): Unit = {
  	_historicBefore.prepend(new Insertion(this, string, index))
  	simpleInsert(string, index)
  }
  
  /** Add the removing event to the historic and call simpleRemove
    * @param beginIndex The beginning index of the deletion
    * @param endIndex The endding index of the deletion
    */
  def remove(beginIndex: Int, endIndex: Int): Unit = {
  	_historicBefore.prepend(new Deletion(this, beginIndex, endIndex))
  	simpleRemove(beginIndex, endIndex)
  }
  
  /** Insert a string at the index
    * @param string The string to insert
    * @param index The position of the insertion
    */
  def simpleInsert(string: String, index: Int): Unit = _content = _content.substring(0, index).concat(string).concat(_content.substring(index))
    
  /** Remove the substring between beginIndex (included) and endIndex(excluded)
    * @param beginIndex The beginning index of the deletion
    * @param endIndex The endding index of the deletion
    */
  def simpleRemove(beginIndex: Int, endIndex: Int): Unit = _content = _content.substring(0, beginIndex).concat(_content.substring(endIndex))
  
  /** Undo the last event
    */
  def undo(): Unit = {
  	_historicBefore.head.undo()
  	_historicAfter.prepend(_historicBefore.remove(0))
  }
  
  /** Redo the last undo event
    */
  def redo(): Unit = {
  	_historicAfter.head.redo()
  	_historicBefore.prepend(_historicAfter.remove(0))
  }
  
}


trait Historique {
  def undo(): Unit
  def redo(): Unit
}

class Insertion(b:Buffer, s: String, i: Int) extends Historique {
  val _string = s
  val _index = i
  val _buffer = b

  def string: String = _string
  def index: Int = _index
  
  def undo(): Unit = new Deletion(_buffer, _index, _index + _string.length).redo()
  def redo(): Unit = _buffer.simpleInsert(_string, _index)
}
 
class Deletion(b: Buffer, begin: Int, end:Int) extends Historique {
  val _beginIndex = begin
  val _endIndex = end
  val _buffer = b
  val _string = _buffer.select(_beginIndex, _endIndex)

  def beginIndex: Int = _beginIndex
  def endIndex: Int = _endIndex
  def string: String = _string

  def undo(): Unit = new Insertion(_buffer, _string, _beginIndex).redo()
  def redo(): Unit = _buffer.simpleRemove(_beginIndex, _endIndex)
}
