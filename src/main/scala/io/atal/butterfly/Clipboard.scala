package io.atal.butterfly

/** The Clipboard keeps a single text data
  *
  * @constructor Create a new Clipboard with a data
  * @param data Data inside the clipboard, default empy
  */
class Clipboard(var data: String = "") {

  /** Clear the data with an empty String
    */
  def clearData: Unit = data = ""
}
