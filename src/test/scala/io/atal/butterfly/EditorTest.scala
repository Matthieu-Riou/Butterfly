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

    assert(editor.buffer.content == "")
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
    val cursor = new Cursor(editor, (1, 1))

    editor.addCursor(cursor)
    editor.removeCursor(cursor)

    editor.cursors should have length 1
    editor.cursors should not contain (cursor)
  }

  "The Editor remove merged cursors method" should "remove cursors with the same position" in {
    val editor = new Editor
    val cursor = new Cursor(editor, (0, 0))

    editor.addCursor(cursor)

    // By default, an editor has a cursor lying at (0, 0)
    editor.removeMergedCursors

    editor.cursors should have length 1
  }

  "The Editor default selections" should "be empty" in {
    val editor = new Editor

    editor.selections should have length 0
  }

  "The Editor add selection method" should "actually add the selection" in {
    val editor = new Editor
    val selection = new Selection((0, 0), (1, 1))

    editor.addSelection(selection)

    editor.selections should have length 1
    editor.selections should contain (selection)
  }

  "The Editor remove selection method" should "actually remove the selection" in {
    val editor = new Editor
    val selection = new Selection((0, 0), (1, 1))
    val selection1 = new Selection((1, 1), (0, 0))

    editor.addSelection(selection)
    editor.addSelection(selection1)
    editor.removeSelection(selection)

    editor.selections should have length 1
    editor.selections should not contain (selection)
  }

  "The Editor clear selection method" should "actually remove all selections" in {
    val editor = new Editor
    val selection = new Selection((0, 0), (1, 1))
    val selection1 = new Selection((1, 1), (0, 0))

    editor.addSelection(selection)
    editor.addSelection(selection1)

    editor.clearSelection

    editor.selections should have length 0
  }

  "The Editor write method" should "write the given text at all cursors position" in {
    val editor = new Editor
    val cursor = new Cursor(editor, (1, 0))

    editor.buffer.content = "Wow\nWow"

    editor.addCursor(cursor)

    // There are two cursors, lying at (0, 0) and (1, 0)
    editor.write("So ")

    assert(editor.buffer.content == "So Wow\nSo Wow")
  }

  "The Editor erase method" should "erase the character at all cursors position" in {
    val editor = new Editor
    val cursor = new Cursor(editor, (1, 2))

    editor.buffer.content = "Wow\nSon"

    editor.addCursor(cursor)

    // There are two cursors, lying at (0, 0) and (1, 2)
    editor.erase

    assert(editor.buffer.content == "ow\nSo")
  }
}
