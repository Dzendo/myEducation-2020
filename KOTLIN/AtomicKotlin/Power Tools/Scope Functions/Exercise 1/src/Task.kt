package scopeFunctionsExercise1

class X {
  var first = 0
  var second = 0
  var third = 0
}

fun example1(x: X): String {
  x.let {
    it.first = 1
    it.second = 2
    it.third = 3
  }
  return "let"
}

class Y {
  fun start() {}
  fun finish() {}
}

fun example2(y: Y?): String {
  y?.let {
    with(it) {
      start()
      finish()
    }
  }
  return "let & with"
}

class Z {
  fun init() {}
}

fun example3(z: Z): String {
  val result = with(z) {
    init()
    this
  }
  return "with"
}

fun main() {
  example1(X())
  example2(Y())
  example3(Z())
}