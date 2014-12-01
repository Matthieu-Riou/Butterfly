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
    val cursor = new Cursor

    editor.addCursor(cursor)

    editor.cursors should have length 2
    editor.cursors should contain (cursor)
  }

  "The Editor remove cursor method" should "actually remove the cursor" in {
    val editor = new Editor
    val cursor = new Cursor((1, 1))

    editor.addCursor(cursor)
    editor.removeCursor(cursor)

    editor.cursors should have length 1
    editor.cursors should not contain (cursor)
  }

  "The Editor remove merged cursors method" should "remove cursors with the same position" in {
    val editor = new Editor
    val cursor = new Cursor((0, 0))

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
  
  "The Editor getIndexPosition method" should "actually return the index position of the cursor" in {
    val editor = new Editor
    val cursor = new Cursor((1,3))
    
    editor.buffer.content = "Hello\nthe world"
    
    assert(editor.getIndexPosition(cursor) == 9)
    
  }

  "The Editor write method" should "write the given text at all cursors position" in {
    val editor = new Editor
    val cursor = new Cursor((1, 0))

    editor.buffer.content = "Wow\nWow"

    editor.addCursor(cursor)

    // There are two cursors, lying at (0, 0) and (1, 0)
    editor.write("So ")

    assert(editor.buffer.content == "So Wow\nSo Wow")
  }

  "The Editor erase method" should "erase the character at all cursors position" in {
    val editor = new Editor
    val cursor = new Cursor((1, 2))

    editor.buffer.content = "Wow\nSon"

    editor.addCursor(cursor)

    // Two cursors, lying at (0, 0) and (1, 2)
    editor.erase

    assert(editor.buffer.content == "Wow\nSn")
    assert(editor.cursors(0).position == (1, 1))
    assert(editor.cursors(1).position == (0, 0))

    // Two cursors, lying at (0, 0) and (1, 1)
    editor.erase

    assert(editor.buffer.content == "Wow\nn")
    assert(editor.cursors(0).position == (1, 0))
    assert(editor.cursors(1).position == (0, 0))

    // Two cursors, lying at (0, 0) and (1, 0)
    editor.erase

    assert(editor.buffer.content == "Wown")
    assert(editor.cursors(0).position == (0, 3))
    assert(editor.cursors(1).position == (0, 0))
  }

  "The Editor erase method" should "erase the selections if there are' content" in {
    val editor = new Editor
    val selection1 = new Selection((0, 4), (0, 10))
    val selection2 = new Selection((1, 4), (1, 10))
    val selection3 = new Selection((2, 3), (2, 9))
    val selection4 = new Selection((3, 16), (3, 22))

    editor.buffer.content = "Wow Please\nSon Please\nNo Please\nDon't chair me. Please."

    // Selections are on every "Please"
    editor.addSelection(selection1)
    editor.addSelection(selection2)
    editor.addSelection(selection3)
    editor.addSelection(selection4)

    editor.erase

    var expected = "Wow \nSon \nNo \nDon't chair me. ."
    assert(editor.buffer.content == expected)

    editor.selections should have length 0
  }

  "The Editor get selection content method" should "return the content of every selections" in {
    val editor = new Editor
    val selection1 = new Selection((0, 4), (0, 10))
    val selection2 = new Selection((1, 4), (1, 10))
    val selection3 = new Selection((2, 3), (2, 9))
    val selection4 = new Selection((3, 16), (3, 22))

    editor.buffer.content = "Wow Please\nSon Please\nNo Please\nDon't chair me. Please."

    // Selections are on every "Please"
    editor.addSelection(selection1)
    editor.addSelection(selection2)
    editor.addSelection(selection3)
    editor.addSelection(selection4)

    var content = editor.getSelectionContent
    var expected = "Please\nPlease\nPlease\nPlease"

    assert(content == expected)
  }

  "The Editor move cursors up method" should "work as expected" in {
    val editor = new Editor
    val cursor = new Cursor((1, 2))

    editor.addCursor(cursor)
    editor.buffer.content = "Wow\nSon"

    editor.moveCursorsUp()

    var expected = List(new Cursor(0, 2), new Cursor(0, 0))
    editor.cursors should equal (expected)

    editor.buffer.content = "Wow\nSon my chair"

    val cursor1 = new Cursor((1, 7))
    editor.addCursor(cursor1)

    editor.moveCursorsUp()

    expected = List(new Cursor(0, 3) ,new Cursor(0, 2), new Cursor(0, 0))
    editor.cursors should equal (expected)
  }

  "The Editor move cursors down method" should "work as expected" in {
    val editor = new Editor
    val cursor = new Cursor((1, 2))

    editor.addCursor(cursor)
    editor.buffer.content = "Wow\nSon"

    editor.moveCursorsDown()

    var expected = List(new Cursor(1, 2), new Cursor(1, 0))
    editor.cursors should equal (expected)

    editor.buffer.content = "Wow\nSon my chair\nHey"

    val cursor1 = new Cursor((1, 7))
    editor.addCursor(cursor1)

    editor.moveCursorsDown()

    expected = List(new Cursor(2, 3), new Cursor(2, 2), new Cursor(2, 0))
    editor.cursors should equal (expected)
  }

  "The Editor move cursors left method" should "work as expected" in {
    val editor = new Editor
    val cursor = new Cursor((1, 2))

    editor.addCursor(cursor)
    editor.buffer.content = "Wow\nSon"

    editor.moveCursorsLeft()

    var expected = List(new Cursor(1, 1), new Cursor(0, 0))
    editor.cursors should equal (expected)

    editor.buffer.content = "Wow\nSon my chair\nHey"

    val cursor1 = new Cursor((2, 0))
    editor.addCursor(cursor1)

    editor.moveCursorsLeft()

    expected = List(new Cursor(1, 12), new Cursor(1, 0), new Cursor(0, 0))
    editor.cursors should equal (expected)
  }

  "The Editor move cursors left (with several steps) method" should "work as expected" in {
    val editor = new Editor
    val cursor = new Cursor((1, 2))

    editor.addCursor(cursor)
    editor.buffer.content = "Wow\nSon"

    editor.moveCursorsLeft(2)

    var expected = List(new Cursor(1, 0), new Cursor(0, 0))
    editor.cursors should equal (expected)

    editor.buffer.content = "Wow\nSon my chair\nHey"

    val cursor1 = new Cursor((2, 0))
    editor.addCursor(cursor1)

    editor.moveCursorsLeft(4)

    // Due to merge
    expected = List(new Cursor(1, 9), new Cursor(0, 0))
    editor.cursors should equal (expected)
  }

  "The Editor move cursors right method" should "work as expected" in {
    val editor = new Editor
    val cursor = new Cursor((1, 2))

    editor.addCursor(cursor)
    editor.buffer.content = "Wow\nSon"

    editor.moveCursorsRight()

    var expected = List(new Cursor(1, 3), new Cursor(0, 1))
    editor.cursors should equal (expected)

    editor.buffer.content = "Wow\nSon my chair\nHey!"

    val cursor1 = new Cursor((1, 12))
    editor.addCursor(cursor1)

    editor.moveCursorsRight()

    expected = List(new Cursor(2, 0), new Cursor(1, 4), new Cursor(0, 2))
    editor.cursors should equal (expected)
  }

  "The Editor move cursors right (with several steps) method" should "work as expected" in {
    val editor = new Editor
    val cursor = new Cursor((1, 2))

    editor.addCursor(cursor)
    editor.buffer.content = "Wow\nSon"

    editor.moveCursorsRight(2)

    var expected = List(new Cursor(1, 3), new Cursor(0, 2))
    editor.cursors should equal (expected)

    editor.buffer.content = "Wow\nSon my chair\nHey!"

    editor.moveCursorsRight(15)

    expected = List(new Cursor(2, 4), new Cursor(2, 0))
    editor.cursors should equal (expected)
  }

  "The Editor move cursors to top method" should "work as expected" in {
    val editor = new Editor
    val cursor = new Cursor((1, 2))

    editor.addCursor(cursor)
    editor.buffer.content = "Wow\nSon"

    editor.moveCursorsToTop

    var expected = List(new Cursor(0, 0))
    editor.cursors should equal (expected)
  }

  "The Editor move cursors to bottom method" should "work as expected" in {
    val editor = new Editor
    val cursor = new Cursor((1, 2))

    editor.addCursor(cursor)
    editor.buffer.content = "Wow\nSon\nWhere is my chair ?"

    editor.moveCursorsToBottom

    var expected = List(new Cursor(2, 19))
    editor.cursors should equal (expected)
  }
}
