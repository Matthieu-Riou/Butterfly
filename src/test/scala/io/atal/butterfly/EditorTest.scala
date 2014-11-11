package io.atal.butterfly

import org.scalatest._
import Matchers._

/** Editor unit tests
  */
class EditorTest extends FlatSpec {

  "The Editor buffer accessor and mutator" should "be as expected" in {
    val buffer = new Buffer("#buffer")
    val editor = new Editor(buffer)

    assert(editor.buffer == buffer)

    val expected = new Buffer("#newBuffer")
    editor.buffer = expected

    assert(editor.buffer == expected)
  }
}
