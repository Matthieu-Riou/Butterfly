package io.atal.butterfly

import org.scalatest._
import Matchers._

/** EditorManager unit tests
  */
class EditorManagerTest extends FlatSpec {

  "The EditorManager accessor and mutator" should "be as expected" in {
    val editorManager = new EditorManager

    assert(editorManager.editors == List())
    assert(editorManager.currentEditor == None)
  }

  "The EditorManager openEditor method" should "add a new editor and move the current editor to it" in {
    val editorManager = new EditorManager

    editorManager.editors should have length 0

    editorManager.openEditor

    editorManager.editors should have length 1
    assert(editorManager.currentEditor == Some(editorManager.editors.head))
  }

  "The EditorManager closeEditor method" should "remove the editor and potentially move the current editor" in {
    val editorManager = new EditorManager

    editorManager.openEditor
    editorManager.openEditor

    editorManager.editors should have length 2
    assert(editorManager.currentEditor == Some(editorManager.editors.head))

    var old_editors = editorManager.editors

    editorManager.closeEditor(editorManager.editors.head)
    assert(editorManager.editors == old_editors.tail)
    assert(editorManager.currentEditor == Some(editorManager.editors.head))

    editorManager.openEditor

    editorManager.editors should have length 2
    assert(editorManager.currentEditor == Some(editorManager.editors.head))

    old_editors = editorManager.editors

    editorManager.closeEditor(editorManager.editors.tail.head)
    assert(editorManager.editors == old_editors.head :: old_editors.tail.tail)
    assert(editorManager.currentEditor == Some(editorManager.editors.head))
  }
}
