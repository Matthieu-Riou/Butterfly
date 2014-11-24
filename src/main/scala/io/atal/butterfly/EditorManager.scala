package io.atal.butterfly

/** A manager that allow to use multiple editor
  * Call function of Editor, and manage the focus upon editors
  * Manage the clipboard
  */
class EditorManager {
  var _editors: List[Editor] = List()
  var _clipboard: Clipboard = new Clipboard()
  var _currentEditor: Option[Editor] = None
  
  def editors: List[Editor] = _editors

  def clipboard: Clipboard = _clipboard
  
  def currentEditor: Option[Editor] = _currentEditor
  
  def currentEditor_=(editor: Editor) = _currentEditor = Some(editor)
  
  /** Open a new Editor
    *
    * @TODO Add a param files to open a file text
    */
  def openEditor: Unit = new Editor() :: editors
  
  /** Close an Editor
    *
    * @param editor The editor to close
    * @TODO Save the editor
    */
  def closeEditor(editor: Editor): Unit = editors == editors.diff(List(editor))

}
