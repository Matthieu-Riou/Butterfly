package io.atal.butterfly.action

import io.atal.butterfly.{Editor, Clipboard, Cursor}
import org.scalatest._

/** Cut action unit test
  */
class CutTest extends FlatSpec {

  "The cut action" should "remove the selection content and add it to the clipboard" in {
    val action = new Cut
    val editor = new Editor()
    val clipboard = new Clipboard()

    editor.buffer.content = "California here we come, right back where we started from"

    // With one selection
    editor.cursors = List(new Cursor((0, 0), Some(new Cursor((0, 11)))))

    action.execute(editor, clipboard)

    assert(clipboard.data == "California ")
    assert(editor.buffer.content == "here we come, right back where we started from")

    editor.buffer.content = "California here we come, right back where we started from"

    // With multiple selections
    editor.cursors = List(
      new Cursor((0, 0), Some(new Cursor((0, 11)))),
      new Cursor((0, 45), Some(new Cursor((0, 53))))
    )

    action.execute(editor, clipboard)
    assert(clipboard.data == "California \nstarted ")
    assert(editor.buffer.content == "here we come, right back where we from")
  }
}
