package io.atal.butterfly

/** Define an Action
  */
trait Action {
  def execute(buffer: Buffer)
}

/** Implement the event Insertion
  *
  * @constructor Create the event
  * @param string The string inserted
  * @param index The index where the string is inserted
  */
class Insertion(val string: String, val index: Int) extends Action {

  /** Execute the event on a buffer
    * @param The buffer onto we execute the event
    */
  def execute(buffer: Buffer) = buffer.insert(string, index)
}

/** Implement the event Deletion
  *
  * @constructor Create the event
  * @param beginIndex The beginning index of the deletion
  * @param endIndex The ending index of the deletion
  */
class Deletion(val beginIndex: Int, val endIndex: Int) extends Action {
  
  /** Execute the event on a buffer
    * @param The buffer onto we execute the event
    */
  def execute(buffer: Buffer) = buffer.remove(beginIndex, endIndex)
}
