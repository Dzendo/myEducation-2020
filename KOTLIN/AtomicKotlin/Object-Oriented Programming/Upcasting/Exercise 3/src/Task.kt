// Upcasting/UpcastExercise3.kt
package upcastingExercise3

interface Fighter {
  fun fight(): String
}

interface Swimmer {
  fun swim(): String
}

interface Flyer {
  fun fly(): String
}

open class ActionCharacter {
  fun fight(): String = "ActionCharacter fight"
}

class Hero

fun tryFight(x: Fighter) {
  TODO()
}

fun trySwim(x: Swimmer) {
  TODO()
}

fun tryFly(x: Flyer) {
  TODO()
}

fun doAction(x: ActionCharacter) {
  TODO()
}

fun main() {
/*
  val h = Hero()
  tryFight(h) // Treat it as a Fight
  trySwim(h) // Treat it as a Swim
  tryFly(h) // Treat it as a Fly
  doAction(h) // Treat it as an ActionCharacter
*/
}