package io.atal.butterfly

import org.scalatest._

class ButterflyTest extends FlatSpec {

  "A simple integer" should "be equals with it same value" in {
    assert(1 === 1)
  }
}
