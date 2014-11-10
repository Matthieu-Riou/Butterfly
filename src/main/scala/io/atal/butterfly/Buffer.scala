package io.atal.butterfly

class Buffer(s: String) {
  var _content: String = s

  def content: String = _content
  def content_=(s: String): Unit = _content = s

  override def toString(): String = _content

}
