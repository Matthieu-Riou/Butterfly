package io.atal.butterfly

import org.scalatest._

/** Selection unit tests
  */
class SelectionTest extends FlatSpec {

  "The Selection begin and end position accessor and mutator" should "be as expected" in {
    val selection = new Selection((0, 0), (1, 1))

    assert(selection.begin == (0, 0))
    assert(selection.end == (1, 1))

    selection.begin = (1, 1)
    selection.end = (0, 0)

    assert(selection.end == (0, 0))
    assert(selection.begin == (1, 1))
  }
}
