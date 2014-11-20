package io.atal.butterfly

import org.scalatest._

/** Cursor unit tests
  */
class CursorTest extends FlatSpec {

  "The Cursor buffer position accessor and mutator" should "be as expected" in {
    val cursor = new Cursor(new Editor)

    assert(cursor.position == (0, 0))

    val expected = (10, 10)
    cursor.position = expected

    assert(cursor.position == expected)
  }

  "The Cursor editor accessor" should "be as expected" in {
    val editor = new Editor
    val cursor = new Cursor(editor)

    assert(cursor.editor == editor)
  }

  "The Cursor moveToTop method" should "place the cursor at the beginning" in {
    val cursor = new Cursor(new Editor)

    val expected = (0, 0)
    cursor.position = (10, 10)
    cursor.moveToTop

    assert(cursor.position == expected)
  }

  "The Cursor default moveUp method" should "place the cursor one row above" in {
    val cursor = new Cursor(new Editor)
    cursor.position = (1, 0)

    val expected = (0, 0)
    cursor.moveUp()

    assert(cursor.position == expected)
  }

  "The Cursor default moveDown method" should "place the cursor one row below" in {
    val cursor = new Cursor(new Editor)
    cursor.position = (1, 0)

    val expected = (2, 0)
    cursor.moveDown()

    assert(cursor.position == expected)
  }

  "The Cursor default moveLeft method" should "place the cursor one column to the left" in {
    val cursor = new Cursor(new Editor)
    cursor.position = (0, 1)

    val expected = (0, 0)
    cursor.moveLeft()

    assert(cursor.position == expected)
  }

  "The Cursor default moveRight method" should "place the cursor one column to the right" in {
    val cursor = new Cursor(new Editor)
    cursor.position = (0, 1)

    val expected = (0, 2)
    cursor.moveRight()

    assert(cursor.position == expected)
  }
}
