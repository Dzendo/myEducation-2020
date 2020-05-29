


object Constants {
    const val CONSTANT2 = "object constant"
}
val foo = Constants.CONSTANT2

const val rocks = 3


fun main() {
    val value1 = complexFunctionCall() // OK
   /* const */ val CONSTANT1 = complexFunctionCall() // NOT ok
}
fun complexFunctionCall() = 0

class MyClass {
    companion object {
        const val CONSTANT3 = "constant in companion"
    }
}

