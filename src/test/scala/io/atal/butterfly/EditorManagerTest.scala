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
  
  "The EditorManager write method" should "write the given text int the current editor at all cursors position" in {
    val editorManager = new EditorManager
    
    editorManager.openEditor
    
    val editor = editorManager.currentEditor.get
    val cursor = new Cursor((1, 0))

    editor.buffer.content = "Wow\nWow"

    editor.addCursor(cursor)

    // There are two cursors, lying at (0, 0) and (1, 0)
    editorManager.write("So ")

    assert(editor.buffer.content == "So Wow\nSo Wow")
  }

  "The Editor erase method" should "erase the character at all cursors position" in {
    val editorManager = new EditorManager
    
    editorManager.openEditor
    
    val editor = editorManager.currentEditor.get
    val cursor = new Cursor((1, 2))

    editor.buffer.content = "Wow\nSon"

    editor.addCursor(cursor)

    // Two cursors, lying at (0, 0) and (1, 2)
    editorManager.erase

    assert(editor.buffer.content == "Wow\nSn")
    assert(editor.cursors(0).position == (1, 1))
    assert(editor.cursors(1).position == (0, 0))

    // Two cursors, lying at (0, 0) and (1, 1)
    editorManager.erase

    assert(editor.buffer.content == "Wow\nn")
    assert(editor.cursors(0).position == (1, 0))
    assert(editor.cursors(1).position == (0, 0))

    // Two cursors, lying at (0, 0) and (1, 0)
    editorManager.erase

    assert(editor.buffer.content == "Wown")
    assert(editor.cursors(0).position == (0, 3))
    assert(editor.cursors(1).position == (0, 0))
  }

  "The Editor erase method" should "erase the selections if there are' content" in {
    val editorManager = new EditorManager
    
    editorManager.openEditor
    
    val editor = editorManager.currentEditor.get
    
    editor.cursors.head.position = (0, 4)
    val cursor2 = new Cursor(1, 4)
    val cursor3 = new Cursor(2, 3)
    val cursor4 = new Cursor(3, 16)

    editor.buffer.content = "Wow Please\nSon Please\nNo Please\nDon't chair me. Please."

    editor.addCursor(cursor2)
    editor.addCursor(cursor3)
    editor.addCursor(cursor4)

    // Selections are on every "Please"
    editor.moveSelection(6)

    editorManager.erase

    var expected = "Wow \nSon \nNo \nDon't chair me. ."
    assert(editor.buffer.content == expected)

    assert(editor.isSelectionMode == false)
  }

}
