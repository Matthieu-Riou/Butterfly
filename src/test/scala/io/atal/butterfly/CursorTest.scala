package io.atal.butterfly

import org.scalatest._

/** Cursor unit tests
  */
class CursorTest extends FlatSpec {

  "The Cursor buffer position accessor and mutator" should "be as expected" in {
    val cursor = new Cursor

    assert(cursor.position == (0, 0))

    val expected = (10, 10)
    cursor.position = expected

    assert(cursor.position == expected)
  }
}

