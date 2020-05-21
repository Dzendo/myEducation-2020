package Aquarium5

const val rock = 3  // определяется во время компиляции
val number = 5      // определяется во время выпполения
const val num = 5
fun complexFuncionCall() {}
val result = complexFuncionCall()
// const val result2 = complexFuncionCall() нельзя
// const val работает тоько на верхнем уровне и в классах

const val Constant = "top-level constant"

object Constants {
    const val Constant2 = "object constant"
}

val foo = Constants.Constant2

class MyClass {
    companion object {
        const val CONSTANT3 = "constant inside companion"
        // эти сконстанты будут инициализироваться при создании класса
    }
    // обычнве константы инициализируются лениво
}