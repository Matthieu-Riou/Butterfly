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

  "The Editor default buffer" should "be empty" in {
    val editor = new Editor

    // @todo Update it when Buffer branch will be merged
    assert(editor.buffer.buf == "")
  }

  "The Editor default cursors" should "contain only one cursor" in {
    val editor = new Editor

    editor.cursors should have length 1
  }

  "The Editor add cursor method" should "actually add a cursor" in {
    val editor = new Editor
    val cursor = new Cursor(editor)

    editor.addCursor(cursor)

    editor.cursors should have length 2
    editor.cursors should contain (cursor)
  }

  "The Editor remove cursor method" should "actually remove the cursor" in {
    val editor = new Editor
    val cursor = new Cursor(editor)

    editor.addCursor(cursor)
    editor.removeCursor(cursor)

    editor.cursors should have length 1
    editor.cursors should not contain (cursor)
  }
}
