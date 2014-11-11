package io.atal.butterfly
import scala.collection.mutable.ListBuffer

class History {
  var _historyBefore: ListBuffer[HistoryEvent] = new ListBuffer()
  var _historyAfter: ListBuffer[HistoryEvent] = new ListBuffer()

  /** Add a new insertion to the history
    * @param buffer The buffer of the event
    * @param string The string inserted
    * @param index The position of the insertion
    */
  def newInsertion(buffer: Buffer, string: String, index: Int): Unit = _historyBefore.prepend(new Insertion(buffer, string, index))
  
  /** Add a new deletion to the history
    * @param buffer The buffer of the event
    * @param beginIndex The beginning of the deletion (included)
    * @param endIndex The ending of the deletion (excluded)
    */
  def newDeletion(buffer: Buffer, beginIndex: Int, endIndex: Int): Unit = _historyBefore.prepend(new Deletion(buffer, beginIndex, endIndex))
  
  /** Undo the last event
    */
  def undo(): Unit = {
    _historyBefore.head.undo()
    _historyAfter.prepend(_historyBefore.remove(0))
  }

  /** Redo the last undo event
    */
  def redo(): Unit = {
    _historyAfter.head.redo()
    _historyBefore.prepend(_historyAfter.remove(0))
  }
}

trait HistoryEvent {
  def undo(): Unit
  def redo(): Unit
}

class Insertion(b: Buffer, s: String, i: Int) extends HistoryEvent {
  val _string = s
  val _index = i
  val _buffer = b

  def string: String = _string
  
  def index: Int = _index

  def undo(): Unit = new Deletion(_buffer, _index, _index + _string.length).redo()
  
  def redo(): Unit = _buffer.simpleInsert(_string, _index)
}
 
class Deletion(b: Buffer, begin: Int, end:Int) extends HistoryEvent {
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
