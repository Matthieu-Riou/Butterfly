package io.atal.butterfly.action

import io.atal.butterfly.{Editor, Clipboard, Cursor}
import org.scalatest._
import Matchers._

/** AddCursor action unit test
  */
class AddCursorTest extends FlatSpec {

  "The AddCursor action" should "add a cursor to the editor" in {
    val action = new AddCursor((1, 1))
    val editor = new Editor()
    val clipboard = new Clipboard()

    action.execute(editor, clipboard)

    editor.cursors should have length 2
    editor.cursors should contain (new Cursor((1, 1)))
  }
}
