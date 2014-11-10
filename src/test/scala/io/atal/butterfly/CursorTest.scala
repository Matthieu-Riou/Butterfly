package io.atal.butterfly

import org.scalatest._
import Matchers._

/** Cursor unit tests
  */
class CursorTest extends FlatSpec {

  "The Cursor buffer position accessor and mutator" should "be as expected" in {
    val cursor = new Cursor(new Editor)

    assert(cursor.bufferPosition == (0, 0))

    val expected = (10, 10)
    cursor.bufferPosition = expected

    assert(cursor.bufferPosition == expected)
  }

  "The Cursor editor accessor and mutator" should "be as expected" in {
    val editor1 = new Editor
    val editor2 = new Editor
    val cursor = new Cursor(editor1)

    assert(cursor.editor == editor1)

    cursor.editor = editor2

    assert(cursor.editor == editor2)
  }

  "The Cursor moveToTop method" should "place the cursor at the beginning" in {
    val cursor = new Cursor(new Editor)

    val expected = (0, 0)
    cursor.bufferPosition = (10, 10)
    cursor.moveToTop

    assert(cursor.bufferPosition == expected)
  }
}
