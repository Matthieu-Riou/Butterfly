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

  /** Insert a string at the index
    * @param string The string to insert
    * @param index The position of the insertion
    */
  def insert(string: String, index: Int): Unit = {
  	_historicBefore.prepend(new Insertion(string, index))
  	_historicBefore.head.redo()
  }
  
  /** Remove the substring between beginIndex (included) and endIndex(excluded)
    * @param beginIndex The beginning index of the deletion
    * @param endIndex The endding index of the deletion
    */
  def remove(beginIndex: Int, endIndex: Int): Unit = {
  	_historicBefore.prepend(new Deletion(beginIndex, endIndex))
  	_historicBefore.head.redo()
  }
  
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
  
  
  
  
  trait Historique {
	def undo(): Unit
	def redo(): Unit
  }

  class Insertion(s: String, i: Int) extends Historique {
	val _string = s
	val _index = i
	
	def string: String = _string
	def index: Int = _index
	
	def undo(): Unit = new Deletion(_index, _index + _string.length).redo()
	def redo(): Unit = _content = _content.substring(0, index).concat(string).concat(_content.substring(index))
  }
 
  class Deletion(b: Int, e:Int) extends Historique {
  	val _beginIndex = b
  	val _endIndex = e
  	val _string = _content.substring(_beginIndex, _endIndex)
  	
  	def beginIndex: Int = _beginIndex
  	def endIndex: Int = _endIndex
  	def string: String = _string
  	
  	def undo(): Unit = new Insertion(_string, _beginIndex).redo()
  	def redo(): Unit = _content = _content.substring(0, beginIndex).concat(_content.substring(endIndex))
  }
  
}
