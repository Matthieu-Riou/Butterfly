package io.atal.butterfly

class History(val buffer: Buffer) {
  var _historyBefore: List[HistoryEvent] = List()
  var _historyAfter: List[HistoryEvent] = List()

  /** Add a new insertion to the history
    *
    * @param string The string inserted
    * @param index The position of the insertion
    */
  def newInsertion(string: String, index: Int): Unit = {
    _historyBefore = new Insertion(buffer, string, index) :: _historyBefore
    _historyAfter = List()
  }

  /** Add a new deletion to the history
    *
    * @param beginIndex The beginning of the deletion (included)
    * @param endIndex The ending of the deletion (excluded)
    */
  def newDeletion(beginIndex: Int, endIndex: Int): Unit = {
    _historyBefore = new Deletion(buffer, beginIndex, endIndex) :: _historyBefore
    _historyAfter = List()
  }

  /** Undo the last event
    */
  def undo(): Unit = {
    _historyBefore.head.undo()
    _historyAfter = _historyBefore.head :: _historyAfter
    _historyBefore = _historyBefore.tail
  }

  /** Redo the last undo event
    */
  def redo(): Unit = {
    _historyAfter.head.redo()
    _historyBefore = _historyAfter.head :: _historyBefore
    _historyAfter = _historyAfter.tail
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
