package io.atal.butterfly.action

import io.atal.butterfly.{Editor, Clipboard, Cursor}
import org.scalatest._

/** Copy action unit test
  */
class CopyTest extends FlatSpec {

  "The Copy action" should "add the selection content to the clipboard" in {
    val action = new Copy
    val editor = new Editor()
    val clipboard = new Clipboard()

    editor.buffer.content = "California here we come, right back where we started from"

    // With one selection
    editor.cursors = List(new Cursor((0, 0), Some(new Cursor((0, 10)))))

    action.execute(editor, clipboard)
    assert(clipboard.data == "California")

    // With multiple selections
    editor.cursors = List(
      new Cursor((0, 0), Some(new Cursor((0, 10)))),
      new Cursor((0, 45), Some(new Cursor((0, 52))))
    )

    action.execute(editor, clipboard)
    assert(clipboard.data == "California\nstarted")
  }
}
