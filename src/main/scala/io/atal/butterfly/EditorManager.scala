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
  def openEditor: Unit = {
    _editors = new Editor() :: _editors
    _currentEditor = Some(_editors.head)
  }
  
  /** Close an Editor
    *
    * @param editor The editor to close
    * @TODO Save the editor
    */
  def closeEditor(editor: Editor): Unit = {
    _editors = _editors.diff(List(editor))
    if(_currentEditor == Some(editor)) {
      _currentEditor = Some(_editors.head)
    }
  }
  
  /** Write text into the current Editor
    *
    * Call the write method of Editor
    * @param text The text to insert
    */
  def write(text: String): Unit = _currentEditor match {
    case Some(editor) => editor.write(text)
    case None => Unit
  }
  
  /** Erase text from the current Editor
    *
    * Call the erase method of Editor
    */
  def erase: Unit = _currentEditor match {
    case Some(editor) => editor.erase
    case None => Unit
  }

  /** Get contents from the selections of the current Editor
    *
    * Call the getSelectionContent method from Editor
    * @return String The text from the selection (or an empty string if there is no current editor)
    */
  def getSelectionContent: String = _currentEditor match {
    case Some(editor) => editor.getSelectionContent
    case None => ""
  }
  
}
