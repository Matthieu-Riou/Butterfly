package io.atal.butterfly

/** An history is linked to a buffer and memorized events (insertion and deletion)
  * It offers undo and redo features
  *
  * @constructor Create a new History sticked to a buffer
  * @param buffer The linked buffer
  * @param historyBefore Past history event, default empty
  * @param historyAfter
  */
class History(
    val buffer: Buffer,
    var historyBefore: List[HistoryEvent] = List(),
    var historyAfter: List[HistoryEvent] = List()
  ) {

  /** Add a new insertion to the history
    *
    * @param string The string inserted
    * @param index The position of the insertion
    */
  def newInsertion(string: String, index: Int): Unit = {
    historyBefore = new HistoryInsertion(buffer, string, index) :: historyBefore
    historyAfter = List()
  }

  /** Add a new deletion to the history
    *
    * @param beginIndex The beginning of the deletion (included)
    * @param endIndex The ending of the deletion (excluded)
    */
  def newDeletion(beginIndex: Int, endIndex: Int): Unit = {
    historyBefore = new HistoryDeletion(buffer, beginIndex, endIndex) :: historyBefore
    historyAfter = List()
  }

  /** Undo the last event
    */
  def undo(): Unit = {
    historyBefore(0).undo()
    historyAfter = historyBefore(0) :: historyAfter
    historyBefore = historyBefore.tail
  }

  /** Redo the last undo event
    */
  def redo(): Unit = {
    historyAfter(0).redo()
    historyBefore = historyAfter(0) :: historyBefore
    historyAfter = historyAfter.tail
  }
}

/** Define an Event
  */
trait HistoryEvent {

  /** Define an undo function
    */
  def undo(): Unit

  /** Define a redo function
    */
  def redo(): Unit
}

/** Implement the event Insertion
  *
  * @constructor Create the event
  * @param buffer The buffer where the event occurs
  * @param string The string inserted
  * @param index The index where the string is inserted
  */
class HistoryInsertion(val buffer: Buffer, val string: String, val index: Int) extends HistoryEvent {

  /** Undo an Insertion
    * Delete the string inserted
    */
  def undo(): Unit = new HistoryDeletion(buffer, index, index + string.length).redo()

  /** Redo an Insertion
    */
  def redo(): Unit = buffer.simpleInsert(string, index)
}

/** Implement the event Deletion
  *
  * @constructor Create the event
  * @param buffer The buffer where the event occurs
  * @param beginIndex The beginning index of the deletion
  * @param endIndex The ending index of the deletion
  */
class HistoryDeletion(val buffer: Buffer, val beginIndex: Int, val endIndex: Int) extends HistoryEvent {
  val string = buffer.select(beginIndex, endIndex)

  /** Undo the Deletion
    * Re-insert the deleted string
    */
  def undo(): Unit = new HistoryInsertion(buffer, string, beginIndex).redo()

  /** Redo the Deletion
    */
  def redo(): Unit = buffer.simpleRemove(beginIndex, endIndex)
}
