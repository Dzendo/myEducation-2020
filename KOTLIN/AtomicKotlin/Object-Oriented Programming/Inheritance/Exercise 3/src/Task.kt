package inheritanceExercise3

import atomictest.eq

open class Game {
  // TODO
}

open class BoardGame: Game() {
  // TODO
}

class Chess: BoardGame() {
  // TODO
}

fun main() {
  val chess = Chess()
//  chess.initOrder eq listOf("???")
}