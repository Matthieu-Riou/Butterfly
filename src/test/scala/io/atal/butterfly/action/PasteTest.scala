package io.atal.butterfly.action

import io.atal.butterfly.{Editor, Clipboard, Cursor}
import org.scalatest._

/** Paste action unit test
  */
class PasteTest extends FlatSpec {

  "The paste action" should "get the clipboard data and insert it to the editor" in {
    val action = new Paste
    val editor = new Editor()
    val clipboard = new Clipboard("California ")

    editor.buffer.content = "here we come"
    editor.cursors = List(new Cursor((0, 0)))

    action.execute(editor, clipboard)

    assert(editor.buffer.content == "California here we come")
  }
}
