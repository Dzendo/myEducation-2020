package nonNullAssertionsExercise3

import atomictest.eq
import kotlin.math.absoluteValue

fun absValueMaxEqualsMin(list: List<Int>): Boolean {
  TODO()
}

fun main() {
  absValueMaxEqualsMin(listOf(-10, 1, 10)) eq true
  absValueMaxEqualsMin(listOf(10, 10)) eq true
  absValueMaxEqualsMin(listOf(-10, 1)) eq false
}