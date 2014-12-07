package io.atal.butterfly.action

import io.atal.butterfly.{Editor, Clipboard, Cursor}
import org.scalatest._
import Matchers._

/** SelectAll action unit test
  */
class SelectAllTest extends FlatSpec {

  "The select all action" should "select all" in {
    val action = new SelectAll
    val editor = new Editor()
    val clipboard = new Clipboard()

    editor.buffer.content = "California here we come, right back where we started from"
    editor.cursors = List(
      new Cursor((0, 0), Some(new Cursor((0, 11)))),
      new Cursor((0, 45), Some(new Cursor((0, 53))))
    )

    action.execute(editor, clipboard)

    editor.cursors should have length 1
    editor.cursors should equal (List(new Cursor((0, 0), Some(new Cursor((0, 57))))))
  }
}
