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

    editor.addCursor((0,0))

    editor.cursors should have length 2
  }

  "The Editor remove cursor method" should "actually remove the cursor" in {
    val editor = new Editor

    editor.addCursor((1,1))
    val cursor = editor.cursors.head

    editor.removeCursor((1,1))

    editor.cursors should have length 1
    editor.cursors should not contain (cursor)
  }

  "The Editor remove merged cursors method" should "remove cursors with the same position" in {
    val editor = new Editor
    val cursor = new Cursor((0, 0))

    editor.addCursor((0,0))

    // By default, an editor has a cursor lying at (0, 0)
    editor.removeMergedCursors

    editor.cursors should have length 1
  }

  "The Editor default selections" should "be empty" in {
    val editor = new Editor

    assert(editor.cursors.head.cursorSelection == None)
  }

  "The Editor move selection method" should "add the selections if there isn't" in {
    val editor = new Editor

    editor.buffer.content = "Hello\nthe world"

    assert(editor.cursors.head.cursorSelection == None)

    editor.moveSelection(2)

    assert(editor.cursors.head.cursorSelection == Some(new Cursor((0, 2))))
  }

    "The Editor move selection method" should "work as expected" in {
    val editor = new Editor
    val cursor = new Cursor((2,0))

    editor.buffer.content = "Oh\nHello\nthe world"

    editor.addCursor((2,0))

    editor.moveSelection(4)

    assert(editor.cursors.head.cursorSelection == Some(new Cursor((2, 4))))
    assert(editor.cursors.tail.head.cursorSelection == Some(new Cursor((1, 1))))

    editor.moveSelection(-2)

    assert(editor.cursors.head.cursorSelection == Some(new Cursor((2, 2))))
    assert(editor.cursors.tail.head.cursorSelection == Some(new Cursor((0, 2))))
  }

  "The Editor clear selection method" should "actually remove all selections" in {
    val editor = new Editor
    val cursor = new Cursor((2,0))

    editor.buffer.content = "Oh\nHello\nthe world"

    editor.addCursor((2,0))

    editor.moveSelection(4)

    assert(editor.cursors.head.cursorSelection != None)
    assert(editor.cursors.tail.head.cursorSelection != None)

    editor.clearSelection

    assert(editor.cursors.head.cursorSelection == None)
    assert(editor.cursors.tail.head.cursorSelection == None)
  }

  "The Editor getIndexPosition method" should "actually return the index position of the cursor" in {
    val editor = new Editor
    val cursor = new Cursor((1,3))

    editor.buffer.content = "Hello\nthe world"

    assert(editor.getIndexPosition(cursor) == 9)

  }

  "The Editor get selection content method" should "return the content of every selections" in {
    val editor = new Editor
    editor.cursors.head.position = (0, 4)

    editor.buffer.content = "Wow Please\nSon Please\nNo Please\nDon't chair me. Please."

    editor.addCursor((1,4))
    editor.addCursor((2,3))
    editor.addCursor((3,16))

    // Selections are on every "Please"
    editor.moveSelection(6)

    var content = editor.getSelectionContent
    var expected = "Please\nPlease\nPlease\nPlease"

    assert(content == expected)
  }

  "The Editor move cursors up method" should "work as expected" in {
    val editor = new Editor
    val cursor = new Cursor((1, 2))

    editor.addCursor((1,2))
    editor.buffer.content = "Wow\nSon"

    editor.moveCursorsUp()

    var expected = List(new Cursor((0, 2)), new Cursor((0, 0)))
    editor.cursors should equal (expected)

    editor.buffer.content = "Wow\nSon my chair"

    editor.addCursor((1,7))

    editor.moveCursorsUp()

    expected = List(new Cursor((0, 3)) ,new Cursor((0, 2)), new Cursor((0, 0)))
    editor.cursors should equal (expected)
  }

  "The Editor move cursors down method" should "work as expected" in {
    val editor = new Editor

    editor.addCursor((1,2))
    editor.buffer.content = "Wow\nSon"

    editor.moveCursorsDown()

    var expected = List(new Cursor((1, 2)), new Cursor((1, 0)))
    editor.cursors should equal (expected)

    editor.buffer.content = "Wow\nSon my chair\nHey"

    editor.addCursor((1,7))

    editor.moveCursorsDown()

    expected = List(new Cursor((2, 3)), new Cursor((2, 2)), new Cursor((2, 0)))
    editor.cursors should equal (expected)
  }

  "The Editor move cursors left method" should "work as expected" in {
    val editor = new Editor

    editor.addCursor((1,2))
    editor.buffer.content = "Wow\nSon"

    editor.moveCursorsLeft()

    var expected = List(new Cursor((1, 1)), new Cursor((0, 0)))
    editor.cursors should equal (expected)

    editor.buffer.content = "Wow\nSon my chair\nHey"

    editor.addCursor((2,0))

    editor.moveCursorsLeft()

    expected = List(new Cursor((1, 12)), new Cursor((1, 0)), new Cursor((0, 0)))
    editor.cursors should equal (expected)
  }

  "The Editor move cursors left (with several steps) method" should "work as expected" in {
    val editor = new Editor

    editor.addCursor((1,2))
    editor.buffer.content = "Wow\nSon"

    editor.moveCursorsLeft(2)

    var expected = List(new Cursor((1, 0)), new Cursor((0, 0)))
    editor.cursors should equal (expected)

    editor.buffer.content = "Wow\nSon my chair\nHey"

    editor.addCursor((2,0))

    editor.moveCursorsLeft(4)

    // Due to merge
    expected = List(new Cursor((1, 9)), new Cursor((0, 0)))
    editor.cursors should equal (expected)
  }

  "The Editor move cursors right method" should "work as expected" in {
    val editor = new Editor

    editor.addCursor((1,2))
    editor.buffer.content = "Wow\nSon"

    editor.moveCursorsRight()

    var expected = List(new Cursor((1, 3)), new Cursor((0, 1)))
    editor.cursors should equal (expected)

    editor.buffer.content = "Wow\nSon my chair\nHey!"

    editor.addCursor((1,12))

    editor.moveCursorsRight()

    expected = List(new Cursor((2, 0)), new Cursor((1, 4)), new Cursor((0, 2)))
    editor.cursors should equal (expected)
  }

  "The Editor move cursors right (with several steps) method" should "work as expected" in {
    val editor = new Editor

    editor.addCursor((1,2))
    editor.buffer.content = "Wow\nSon"

    editor.moveCursorsRight(2)

    var expected = List(new Cursor((1, 3)), new Cursor((0, 2)))
    editor.cursors should equal (expected)

    editor.buffer.content = "Wow\nSon my chair\nHey!"

    editor.moveCursorsRight(15)

    expected = List(new Cursor((2, 4)), new Cursor((2, 0)))
    editor.cursors should equal (expected)
  }

  "The Editor move cursors to top method" should "work as expected" in {
    val editor = new Editor

    editor.addCursor((1,2))
    editor.buffer.content = "Wow\nSon"

    editor.moveCursorsToTop

    var expected = List(new Cursor((0, 0)))
    editor.cursors should equal (expected)
  }

  "The Editor move cursors to bottom method" should "work as expected" in {
    val editor = new Editor

    editor.addCursor((1,2))
    editor.buffer.content = "Wow\nSon\nWhere is my chair ?"

    editor.moveCursorsToBottom

    var expected = List(new Cursor((2, 19)))
    editor.cursors should equal (expected)
  }
}
