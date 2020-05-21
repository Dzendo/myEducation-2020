package lambdaWithReceiverExercise1

import atomictest.eq

fun drawSquare(width: Int) = buildString {
  TODO()
}.trim()

fun main() {
  drawSquare(3) eq
    """|***
       |***
       |***""".trimMargin()
}