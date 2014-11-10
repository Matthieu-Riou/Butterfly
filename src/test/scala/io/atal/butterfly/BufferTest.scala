package io.atal.butterfly

import org.scalatest._

/** Buffer unit test
  */
class BufferTest extends FlatSpec {

  "The Buffer accessor and mutator" should "be as expected" in {
    val buffer = new Buffer("#begin")

    assert(buffer.content == "#begin")

    val expected = "#buffer"
    buffer.content = expected

    assert(buffer.content == expected)
  }

  "The Buffer toString method" should "return the content" in {
    val buffer = new Buffer("#begin")

    assert(buffer.toString() == buffer.content)
  }
}
