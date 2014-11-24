package io.atal.butterfly

/** A selection is a portion of the buffer you highlight
  * It's meant to be removed, copied, or cut
  *
  * @constructor Create a new selection from the two positions
  * @param begin The begin of the selection
  * @param end The end of the selection
  */
class Selection(var begin: (Int, Int), var end: (Int, Int))
