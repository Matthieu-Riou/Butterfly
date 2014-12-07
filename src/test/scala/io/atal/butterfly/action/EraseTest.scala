package io.atal.butterfly.action

import io.atal.butterfly.{Editor, Clipboard, Cursor}
import org.scalatest._

/** Erase action unit test
  */
class EraseTest extends FlatSpec {

  "The erase action" should "remove a character or the selection" in {
    val action = new Erase
    val editor = new Editor()
    val clipboard = new Clipboard()

    editor.buffer.content = "California here we come, right back where we started from"

    // With one selection
    editor.cursors = List(new Cursor((0, 0), Some(new Cursor((0, 11)))))

    action.execute(editor, clipboard)

    assert(editor.buffer.content == "here we come, right back where we started from")

    editor.buffer.content = "California here we come, right back where we started from"

    // With multiple selections
    editor.cursors = List(
      new Cursor((0, 0), Some(new Cursor((0, 11)))),
      new Cursor((0, 45), Some(new Cursor((0, 53))))
    )

    action.execute(editor, clipboard)

    assert(editor.buffer.content == "here we come, right back where we from")

    // With no selection, simple character by character eraser
    editor.buffer.content = "California here we come, right back where we started from"
    editor.cursors = List(
      new Cursor((0, 1)),
      new Cursor((0, 45))
    )

    action.execute(editor, clipboard)

    assert(editor.buffer.content == "alifornia here we come, right back where westarted from")
  }
}
