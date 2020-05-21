// Upcasting/UpcastExercise2.kt
package upcastingExercise2

interface Apple {
  fun consume(): String
}

class GrannySmith

class Gala

class Fuji

class Braeburn

fun main() {
  val apples = listOf(
    GrannySmith(),
    Gala(),
    Fuji(),
    Braeburn()
  )
  apples.forEach {
//    println(it.consume())
  }
}