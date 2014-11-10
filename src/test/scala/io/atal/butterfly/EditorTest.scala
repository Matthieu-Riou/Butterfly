package io.atal.butterfly

import org.scalatest._
import Matchers._

/** Clipboard unit test
  */
class EditorTest extends FlatSpec {

  "The Editor's buffers' accessor" should "be as expected" in {
    val editor = new Editor

    editor.buffers should equal (Array())
  }

  "The Editor's add buffer method" should "add the buffer" in {
    val editor = new Editor
    val buffer1 = new Buffer("#buffer")
    val buffer2 = new Buffer("#not")

    editor.addBuffer(buffer1)
    editor.addBuffer(buffer2)

    editor.buffers should have length (2)

    editor.removeBuffer(buffer1)
    editor.buffers should have length (1)
  }
}
