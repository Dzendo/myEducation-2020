package theWithFunctionExercise1

import atomictest.eq

fun buildString(action: StringBuilder.() -> Unit): String =
  with(StringBuilder()) {
    TODO()
  }

fun main() {
  buildString {
    append("Alphabet: ")
    for (ch in 'a'..'z') {
      append(ch)
    }
  } eq "Alphabet: abcdefghijklmnopqrstuvwxyz"
}