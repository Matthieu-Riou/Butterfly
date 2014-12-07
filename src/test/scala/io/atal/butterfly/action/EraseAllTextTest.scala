package io.atal.butterfly.action

import io.atal.butterfly.{Editor, Clipboard}
import org.scalatest._

/** EraseAllText action unit test
  */
class EraseAllTextTest extends FlatSpec {

  "The erase all text action" should "remove all occurences of the given text" in {
    val action = new EraseAllText("we ")
    val editor = new Editor()
    val clipboard = new Clipboard()

    editor.buffer.content = "California here we come, right back where we started from"

    action.execute(editor, clipboard)

    assert(editor.buffer.content == "California here come, right back where started from")
  }
}
