package io.atal.butterfly

import org.scalatest._

class ButterflyTest extends FlatSpec {

  "A simple integer" should "be equals with its same value" in {
    assert(1 == 1)
  }
}
