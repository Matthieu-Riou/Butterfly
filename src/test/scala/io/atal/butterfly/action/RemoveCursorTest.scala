package io.atal.butterfly.action

import io.atal.butterfly.{Editor, Clipboard, Cursor}
import org.scalatest._
import Matchers._

/** RemoveCursor action unit test
  */
class RemoveCursorTest extends FlatSpec {

  "The RemoveCursor action" should "remove a cursor to the editor" in {
    val action = new RemoveCursor((0, 0))
    val editor = new Editor()
    val clipboard = new Clipboard()

    action.execute(editor, clipboard)

    editor.cursors should have length 0
  }
}
