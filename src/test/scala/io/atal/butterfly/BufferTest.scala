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

  "The Buffer select method" should "return the substring selected" in {
    val buffer = new Buffer("Hello world!")

    assert(buffer.select(6, 11).equals("world"))
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
}
