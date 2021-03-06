package io.atal.butterfly

import org.scalatest._
import Matchers._

/** Buffer unit test
  */
class BufferTest extends FlatSpec {

  "The Buffer's content accessor and mutator" should "be as expected" in {
    val buffer = new Buffer("#begin")

    assert(buffer.content == "#begin")

    val expected = "#buffer"
    buffer.content = expected

    assert(buffer.content == expected)
  }

  "The Buffer lines method" should "return the buffer content in an array" in {
    val buffer = new Buffer("#begin\n#lessbegin\n#end")

    var expected = Array("#begin", "#lessbegin", "#end")
    var lines = buffer.lines

    lines should equal (expected)
  }

  "The Buffer select method" should "return the substring selected" in {
    val buffer = new Buffer("Hello world!")

    var expected = "world"
    var selected = buffer.select(6, 11)

    assert(expected == selected)
  }

  "The Buffer select method with two dimensions position" should "return the substring selected" in {
    val buffer = new Buffer("Hello world!")

    var expected = "world"
    var selected = buffer.select((0, 6), (0, 11))

    assert(expected == selected)

    buffer.content = "I am a tribe\nI love chair."

    expected = "a tribe\nI love chair"
    selected = buffer.select((0, 5), (1, 12))

    assert(expected == selected)
  }

  "The Buffer insert method" should "insert the wanted string in the content at the wanted position" in {
    val buffer = new Buffer("Hello world!")

    buffer.insert("king of the ", 6)

    assert(buffer.content == "Hello king of the world!")
  }

  "The Buffer insert method with a two dimensions position" should "insert the string at the wanted position" in {
    val buffer = new Buffer("Hello world!")

    buffer.insert("king of the ", (0, 6))

    assert(buffer.content == "Hello king of the world!")

    buffer.content = "I am a tribe\nYou are a tribe\nWe a tribe"
    var expected = "I am a tribe\nYou are a tribe\nWe are a tribe"

    buffer.insert(" are", (2, 2))

    assert(buffer.content == expected)

    expected = "I am a tribe\nYou are a tribe potatoes\nWe are a tribe"
    buffer.insert(" potatoes", (1, 15))

    assert(buffer.content == expected)

    expected = "I am a tribe with sugar\nYou are a tribe potatoes\nWe are a tribe"
    buffer.insert(" with sugar", (0, 12))

    assert(buffer.content == expected)
  }

  "The Buffer remove method" should "remove the substring defined by positions from the content" in {
    val buffer = new Buffer("Hello world!")

    buffer.remove(5, 11)

    assert(buffer.content == "Hello!")

    buffer.remove(0, 1)

    assert(buffer.content == "ello!")
  }

  "The Buffer remove method with two dimensions position" should "remove the substring defined by the positions" in {
    val buffer = new Buffer("Hello world!")

    buffer.remove((0, 5), (0, 11))

    assert(buffer.content == "Hello!")

    buffer.remove((0, 0), (0,1))

    assert(buffer.content == "ello!")

    buffer.content = "I am a tribe\nNothing else.\nchair"

    buffer.remove((0, 7), (2, 0))

    assert(buffer.content == "I am a chair")
  }

  "The last event" should "undo and redo properly" in {
    val buffer = new Buffer("Hello world!")

    buffer.insert("king of the ", 6)

    assert(buffer.content == "Hello king of the world!")

    buffer.undo()

    assert(buffer.content == "Hello world!")

    buffer.redo()

    assert(buffer.content == "Hello king of the world!")

    buffer.remove(6, 18)

    assert(buffer.content == "Hello world!")

    buffer.undo()

    assert(buffer.content == "Hello king of the world!")

    buffer.redo()

    assert(buffer.content == "Hello world!")
  }

  "The convert to linear position" should "correctly convert" in {
    val buffer = new Buffer("Hello world!\nThis is the night\nOf the chair.")

    var linear1 = buffer.convertToLinearPosition((1, 5))
    var linear2 = buffer.convertToLinearPosition((2, 2))
    var linear3 = buffer.convertToLinearPosition((0, 2))

    assert(linear1 == 18)
    assert(linear2 == 33)
    assert(linear3 == 2)
  }
}
