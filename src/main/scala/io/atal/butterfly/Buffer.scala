package io.atal.butterfly

class Buffer(s: String) {
  var _content: String = s
  var _history: History = new History()

  def content: String = _content
  def content_=(s: String): Unit = _content = s

  override def toString(): String = _content
  
  /** Return a selected substring in the buffer
    * @param beginIndex The beginning index of the selection
    * @param endIndex The endding index of the selection
    * @return beginIndex The substring between the beginIndex (including) and the endding index (excluded)
    */
  def select(beginIndex: Int, endIndex: Int): String = _content.substring(beginIndex, endIndex)

  /** Add the inserting event to the history and call simple insert
    * @param string The string to insert
    * @param index The position of the insertion
    */
  def insert(string: String, index: Int): Unit = {
  	_history.newEvent(new Insertion(this, string, index))
  	simpleInsert(string, index)
  }
  
  /** Add the removing event to the history and call simpleRemove
    * @param beginIndex The beginning index of the deletion
    * @param endIndex The endding index of the deletion
    */
  def remove(beginIndex: Int, endIndex: Int): Unit = {
  	_history.newEvent(new Deletion(this, beginIndex, endIndex))
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
    _history.undo()
  }
  
  /** Redo the last undo event
    */
  def redo(): Unit = {
  	_history.redo()
  }
}
