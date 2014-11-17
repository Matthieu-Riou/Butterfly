package io.atal.butterfly

import org.scalatest._
import Matchers._

/** History unit test
  */
class HistoryTest extends FlatSpec {

  "The History accessor and mutator" should "be as expected" in {
    val buf = new Buffer("")
    val history = new History(buf)

    assert(history.buffer == buf)
  }

  "The History newInsertion and newDeletion methods" should "add the event to the history" in {
    val buffer = new Buffer("Hello")
    val history = new History(buffer)

    history._historyBefore should have length 0

    history.newInsertion(" world!", 5)
    buffer.insert(" world!", 5)

    history._historyBefore should have length 1

    history.newDeletion(5, 11)
    buffer.remove(5, 11)

    history._historyBefore should have length 2
  }

  "The History newInsertion and newDeletion methods" should "clear the after history" in {
    val buffer = new Buffer("Hello")
    val history = new History(buffer)

    history._historyAfter should have length 0

    history.newInsertion(" world!", 5)
    buffer.insert(" world!", 5)

    history._historyAfter should have length 0

    history.undo()

    history._historyAfter should have length 1

    history.newInsertion(" woooorld!", 5)
    buffer.insert(" woooorld!", 5)

    history._historyAfter should have length 0

    history.undo()

    history._historyAfter should have length 1

    history.newDeletion(2, 3)
    buffer.remove(2, 3)

    history._historyAfter should have length 0
  }

  "The History undo method" should "undo the last event" in {
    val buffer = new Buffer("Hello")
    val history = new History(buffer)

    assert(history.buffer.content == "Hello")

    history.newInsertion(" world!", 5)
    buffer.insert(" world!", 5)

    assert(history.buffer.content == "Hello world!")

    history.undo()

    assert(history.buffer.content == "Hello")

    history.newDeletion(2,3)
    buffer.remove(2,3)

    assert(history.buffer.content == "Helo")

    history.undo()

    assert(history.buffer.content == "Hello")
  }

  "The History redo method" should "redo the last undo event" in {
    val buffer = new Buffer("Hello")
    val history = new History(buffer)

    assert(history.buffer.content == "Hello")

    history.newInsertion(" world!", 5)
    buffer.insert(" world!", 5)

    assert(history.buffer.content == "Hello world!")

    history.undo()

    assert(history.buffer.content == "Hello")

    history.redo()

    assert(history.buffer.content == "Hello world!")

    history.newDeletion(2,3)
    buffer.remove(2,3)

    assert(history.buffer.content == "Helo world!")

    history.undo()

    assert(history.buffer.content == "Hello world!")

    history.redo()

    assert(history.buffer.content == "Helo world!")
  }
}
