package io.atal.butterfly
import scala.collection.mutable.ListBuffer

class Historic {
  var _historicBefore: ListBuffer[HistoricEvent] = new ListBuffer()
  var _historicAfter: ListBuffer[HistoricEvent] = new ListBuffer()

  /** Add a new event to the historic
    * @param event The new event
    */
  def newEvent(event: HistoricEvent): Unit = {
  	_historicBefore.prepend(event)
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
}


trait HistoricEvent {
  def undo(): Unit
  def redo(): Unit
}

class Insertion(b:Buffer, s: String, i: Int) extends HistoricEvent {
  val _string = s
  val _index = i
  val _buffer = b

  def string: String = _string
  def index: Int = _index
  
  def undo(): Unit = new Deletion(_buffer, _index, _index + _string.length).redo()
  def redo(): Unit = _buffer.simpleInsert(_string, _index)
}
 
class Deletion(b: Buffer, begin: Int, end:Int) extends HistoricEvent {
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
