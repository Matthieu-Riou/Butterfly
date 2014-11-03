package io.atal.butterfly

class Buffer(s : String) {
  def buf = s
  override def toString(): String = buf
}
