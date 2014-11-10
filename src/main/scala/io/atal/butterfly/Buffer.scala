package io.atal.butterfly

class Buffer(s: String) {
  var _content: String = s

  def content: String = _content
  def content_=(s: String): Unit = _content = s

  override def toString(): String = _content
  
  /* Return a selected substring in the buffer
   * beginIndex is selected, but not endIndex
   */
  def select(beginIndex: Int, endIndex: Int): String = _content.substring(beginIndex, endIndex)

  /* Insert a string at the index
   */
  def insert(string: String, index: Int): Unit = _content = _content.substring(0, index).concat(string).concat(_content.substring(index))
  
  /* Remove the substring between beginIndex (included) and endIndex(excluded)
   */
  def remove(beginIndex: Int, endIndex: Int): Unit = _content = _content.substring(0, beginIndex).concat(_content.substring(endIndex))
}
