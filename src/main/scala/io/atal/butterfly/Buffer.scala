package io.atal.butterfly

class Buffer(var content: String) {
  var history: History = new History(this)

  override def toString(): String = content

  /** Return a selected substring in the buffer
    *
    * @param beginIndex The beginning index of the selection
    * @param endIndex The ending index of the selection
    * @return The substring between the beginIndex (including) and the ending index (excluded)
    */
  def select(beginIndex: Int, endIndex: Int): String = content.substring(beginIndex, endIndex)

  /** Add the inserting event to the history and call simple insert
    *
    * @param string The string to insert
    * @param index The position of the insertion
    */
  def insert(string: String, index: Int): Unit = {
    history.newInsertion(string, index)
    simpleInsert(string, index)
  }

  /** Insert a string using a two dimensions position
    *
    * @param string The string to insert
    * @param position The two dimensions position of the insertion
    */
  def insert(string: String, position: (Int, Int)): Unit = {
    insert(string, convertToLinearPosition(position))
  }

  /** Add the removing event to the history and call simpleRemove
    *
    * @param beginIndex The beginning index of the deletion
    * @param endIndex The ending index of the deletion
    */
  def remove(beginIndex: Int, endIndex: Int): Unit = {
    history.newDeletion(beginIndex, endIndex)
    simpleRemove(beginIndex, endIndex)
  }

  /** Insert a string at the index
    *
    * @param string The string to insert
    * @param index The position of the insertion
    */
  def simpleInsert(string: String, index: Int): Unit = {
    content = content.substring(0, index).concat(string).concat(content.substring(index))
  }

  /** Remove the substring between beginIndex (included) and endIndex(excluded)
    *
    * @param beginIndex The beginning index of the deletion
    * @param endIndex The ending index of the deletion
    */
  def simpleRemove(beginIndex: Int, endIndex: Int): Unit = {
    content = content.substring(0, beginIndex).concat(content.substring(endIndex))
  }

  /** Convert a two dimensions position to its linear equivalent
    *
    * @param position The two dimensions coordinate to convert
    * @return The linear coordinate equivalent for the given position
    */
  def convertToLinearPosition(position: (Int, Int)): Int = {
    (position._1 * position._2) - position._1
  }

  /** Undo the last event
    */
  def undo(): Unit = history.undo()

  /** Redo the last undo event
    */
  def redo(): Unit = history.redo()
}
