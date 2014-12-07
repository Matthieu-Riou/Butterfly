package io.atal.butterfly

import io.atal.butterfly.action._

/** A manager that allow to use multiple editor
  * Call function of Editor, and manage the focus upon editors
  * And Manage the clipboard
  *
  * @constructor Create a new EditorManager
  * @param view An optional view, default None
  * @param editors Editors managed by the manager
  * @param clipboard The clipboard
  * @param currentEditor The current editor (where actions will take place)
  */
class EditorManager(
    var view: Option[View] = None,
    var editors: List[Editor] = List(),
    var clipboard: Clipboard = new Clipboard(),
    var currentEditor: Option[Editor] = None
  ) {

  /** Open a new Editor
    *
    * @todo Add a param files to open a file text
    */
  def openEditor: Unit = {
    editors = new Editor(editorManager = Some(this)) :: editors
    currentEditor = Some(editors(0))
  }

  /** Close an Editor
    *
    * @param editor The editor to close
    * @todo Save the editor
    */
  def closeEditor(editor: Editor): Unit = {
    editors = editors.diff(List(editor))
    if (currentEditor == Some(editor)) {
      currentEditor = Some(editors(0))
    }
  }

  /** Get contents from the selections of the current Editor
    *
    * Call the getSelectionContent method from Editor
    * @return String The text from the selection (or an empty string if there is no current editor)
    */
  def getSelectionContent: String = currentEditor match {
    case Some(editor) => editor.getSelectionContent
    case None => ""
  }

  def content: String = currentEditor match {
    case Some(editor) => editor.buffer.content
    case None => ""
  }

  def contentByLines: Array[String] = currentEditor match {
    case Some(editor) => editor.buffer.lines
    case None => Array()
  }

  def cursors: List[Cursor] = currentEditor match {
    case Some(editor) => editor.cursors
    case None => List()
  }

  def execute(action: Action): Unit = currentEditor match {
    case Some(editor) => action.execute(editor, clipboard)
    case None => Unit
  }

  def isSelectionMode: Boolean = currentEditor match {
    case Some(editor) => editor.isSelectionMode
    case None => false
  }

  /** When an editor changed, tell the view to update
    *
    * @param editor The editor which changed
    */
  def editorChanged(editor: Editor): Unit = {
    view match {
      case Some(v) if (editor == currentEditor) => v.updateView()
      case Some(v) => Unit
      case None => Unit
    }
  }
}
