package io.atal.butterfly

/** The Clipboard keeps a single text data
  *
  * @constructor Create a new Clipboard with empty data
  */
class Clipboard {
  var _data: String = ""

  def data = _data
  def data_=(data: String) = _data = data

  /** Clear the data with an empty String
    */
  def clearData = _data = ""
}
