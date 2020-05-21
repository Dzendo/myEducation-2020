package nonNullAssertionsExercise2

import atomictest.eq

fun sumFirstAndLast(list: List<Int>): Int =
  TODO()

fun main() {
  sumFirstAndLast(listOf()) eq 0
  sumFirstAndLast(listOf(1)) eq 1
  sumFirstAndLast(listOf(1, 2)) eq 3
  sumFirstAndLast(listOf(1, 2, 3)) eq 4
}