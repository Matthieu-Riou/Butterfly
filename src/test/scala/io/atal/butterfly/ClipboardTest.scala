package io.atal.butterfly

import org.scalatest._

/** Clipboard unit test
  */
class ClipboardTest extends FlatSpec {

  "The Clipboard accessor and mutator" should "be as expected" in {
    val clipboard = new Clipboard

    assert(clipboard.data == "")

    val expected = "#clipboard"
    clipboard.data = expected

    assert(clipboard.data == expected)
  }

  "The Clipboard clear method" should "empty the data" in {
    val clipboard = new Clipboard
    clipboard.data = "#dirty"

    val expected = ""
    clipboard.clearData

    assert(clipboard.data == expected)
  }
}
