package io.atal.butterfly

import scala.collection.mutable.ListBuffer

class History(val buffer: Buffer) {
  var _historyBefore: ListBuffer[HistoryEvent] = new ListBuffer()
  var _historyAfter: ListBuffer[HistoryEvent] = new ListBuffer()

  /** Add a new insertion to the history
    *
    * @param string The string inserted
    * @param index The position of the insertion
    */
  def newInsertion(string: String, index: Int): Unit = {
    _historyBefore.prepend(new Insertion(buffer, string, index))
    _historyAfter.clear()
  }

  /** Add a new deletion to the history
    *
    * @param beginIndex The beginning of the deletion (included)
    * @param endIndex The ending of the deletion (excluded)
    */
  def newDeletion(beginIndex: Int, endIndex: Int): Unit = {
    _historyBefore.prepend(new Deletion(buffer, beginIndex, endIndex))
    _historyAfter.clear()
  }

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

class Insertion(val buffer: Buffer, val string: String, val index: Int) extends HistoryEvent {
  def undo(): Unit = new Deletion(buffer, index, index + string.length).redo()
  def redo(): Unit = buffer.simpleInsert(string, index)
}

class Deletion(val buffer: Buffer, val beginIndex: Int, val endIndex: Int) extends HistoryEvent {
  val string = buffer.select(beginIndex, endIndex)
  
  def undo(): Unit = new Insertion(buffer, string, beginIndex).redo()
  def redo(): Unit = buffer.simpleRemove(beginIndex, endIndex)
}
