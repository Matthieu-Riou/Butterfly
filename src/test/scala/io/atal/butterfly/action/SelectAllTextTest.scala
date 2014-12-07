package io.atal.butterfly.action

import io.atal.butterfly.{Editor, Clipboard, Cursor}
import org.scalatest._
import Matchers._

/** SelectAllText action unit test
  */
class SelectAllTextTest extends FlatSpec {

  "The select all text action" should "select all occurences of the given text" in {
    val action = new SelectAllText("we")
    val editor = new Editor()
    val clipboard = new Clipboard()

    editor.buffer.content = "California here we come, right back where we started from"

    action.execute(editor, clipboard)

    editor.cursors should have length 2

    editor.cursors should equal (List(
      new Cursor((0, 42), Some(new Cursor((0, 44)))),
      new Cursor((0, 16), Some(new Cursor((0, 18))))
    ))
  }
}
