package io.atal.butterfly.action

import io.atal.butterfly.{Editor, Clipboard}
import org.scalatest._

/** EraseAll action unit test
  */
class EraseAllTest extends FlatSpec {

  "The erase all action" should "remove all" in {
    val action = new EraseAll
    val editor = new Editor()
    val clipboard = new Clipboard()

    editor.buffer.content = "California here we come, right back where we started from"

    action.execute(editor, clipboard)

    assert(editor.buffer.content == "")
  }
}
