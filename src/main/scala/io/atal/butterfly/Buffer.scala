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

  /** Select a substring from the buffer with 2 two dimensions positions
    *
    * @param beginPosition The begin of the selection
    * @param endPosition The end of the selection
    * @return The substring between these two positions
    */
  def select(beginPosition: (Int, Int), endPosition: (Int, Int)): String = {
    select(convertToLinearPosition(beginPosition), convertToLinearPosition(endPosition))
  }

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

  /** Remove the string between 2 two dimensions positions
    *
    * @param beginPosition The begin position
    * @param endPosition The end position
    */
  def remove(beginPosition: (Int, Int), endPosition: (Int, Int)): Unit = {
    remove(convertToLinearPosition(beginPosition), convertToLinearPosition(endPosition))
  }

  /** Insert a string at the index
    *
    * @param string The string to insert
    * @param index The position of the insertion
    */
  def simpleInsert(string: String, index: Int): Unit = {
    content = content.substring(0, index).concat(string).concat(content.substring(index))
  }

  /** Remove the substring between beginIndex (included) and endIndex (excluded)
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
    var lines = content.split("\n")
    var linearPosition = position._1 + position._2

    for (i <- 0 until position._1) {
      linearPosition += lines(i).length
    }

    return linearPosition
  }

  /** Undo the last event
    */
  def undo(): Unit = history.undo()

  /** Redo the last undo event
    */
  def redo(): Unit = history.redo()
}
