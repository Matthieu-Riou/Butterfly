package io.atal.butterfly.action

import io.atal.butterfly.{Editor, Clipboard, Cursor}
import org.scalatest._

/** ClearSelection action unit test
  */
class ClearSelectionTest extends FlatSpec {

  "The clear selection action" should "set the editor not to selection mode" in {
    val action = new ClearSelection
    val editor = new Editor()
    val clipboard = new Clipboard()

    editor.cursors = List(new Cursor((0, 0), Some(new Cursor((0, 11)))))
    assert(editor.isSelectionMode)

    action.execute(editor, clipboard)

    assert(!editor.isSelectionMode)
  }
}
