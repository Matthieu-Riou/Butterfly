package io.atal.butterfly.action

import io.atal.butterfly.{Editor, Clipboard, Cursor}
import org.scalatest._

/** Write action unit test
  */
class WriteTest extends FlatSpec {

  "The write action" should "write the given text to the editor" in {
    val action = new Write("California ")
    val editor = new Editor()
    val clipboard = new Clipboard()

    editor.buffer.content = "here we come"
    editor.cursors = List(new Cursor((0, 0)))

    action.execute(editor, clipboard)

    assert(editor.buffer.content == "California here we come")
  }
}
