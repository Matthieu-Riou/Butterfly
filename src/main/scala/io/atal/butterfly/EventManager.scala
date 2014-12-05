package io.atal.butterfly

import me.verticale.scalamitter._

/** The event manager handles events listening and emitting
  * Extends EventEmitter from Scalamitter library
  */
object EventManager extends EventEmitter {}

/** Simple trait which wrap the EventManager singleton
  */
trait EventHandler {
  val event = EventManager
}
