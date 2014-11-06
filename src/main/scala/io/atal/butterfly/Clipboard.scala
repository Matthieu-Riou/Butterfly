package io.atal.butterfly

/** The Clipboard keeps a single text data
  *
  * @constructor Create a new Clipboard with empty data
  */
class Clipboard {
  var _data: String = ""

  def data: String = _data
  def data_=(data: String): Unit = _data = data

  /** Clear the data with an empty String
    */
  def clearData: Unit = _data = ""
}
