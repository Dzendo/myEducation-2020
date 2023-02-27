Краткие сведения
Функции определяются с funпомощью ключевого слова и содержат повторно используемые фрагменты кода.
Функции помогают упростить обслуживание больших программ и предотвращают ненужное повторение кода.
Функции могут возвращать значение, которое вы можете сохранить в переменной для последующего использования.
Функции могут принимать параметры, которые являются переменными, доступными внутри тела функции.
Аргументы - это значения, которые вы передаете при вызове функции
Вы можете называть аргументы при вызове функции. При использовании именованных аргументов вы можете изменить порядок аргументов, не влияя на результат.
Вы можете указать аргумент по умолчанию, который позволяет опускать аргумент при вызове функции.

fun main() {
    birthdayGreeting()
}

fun birthdayGreeting() {
    println("Happy Birthday, Rover!")
    println("You are now 5 years old!")
}
Happy Birthday, Rover!
You are now 5 years old!

 по умолчанию используется возвращаемый тип Unit. Unitозначает, что функция не возвращает значение
Для функций, которые ничего не возвращают или возвращаютUnit, вам не нужен оператор return .

fun birthdayGreeting(): String {
    val nameGreeting = "Happy Birthday, Rover!"
    val ageGreeting = "You are now 5 years old!"
    return "$nameGreeting\n$ageGreeting"
}

fun main() {
    val greeting = birthdayGreeting()
    println(greeting)
}
Happy Birthday, Rover!
You are now 5 years old!


fun birthdayGreeting(name: String): String {
    val nameGreeting = "Happy Birthday, Rover!"
    val ageGreeting = "You are now 5 years old!"
    return "$nameGreeting\n$ageGreeting"
}

fun main() {
      println(birthdayGreeting("Rover"))
    println(birthdayGreeting("Rover"))
println(birthdayGreeting("Rex"))
}

Предупреждение:
 В отличие от некоторых языков, таких как Java, где функция может изменять значение, переданное в параметр,
 параметры в Kotlin являются неизменяемыми.
 Вы не можете переназначить значение параметра из тела функции.

fun main() {
    println(birthdayGreeting("Rover", 5))
    println(birthdayGreeting("Rex", 2))
}
fun birthdayGreeting(name: String, age: Int): String {
    val nameGreeting = "Happy Birthday, $name!"
    val ageGreeting = "You are now $age years old!"
    return "$nameGreeting\n$ageGreeting"
}
Happy Birthday, Rover!
You are now 5 years old!
Happy Birthday, Rex!
You are now 2 years old!

6. Именованные аргументы
println(birthdayGreeting(name = "Rex", age = 2))

fun main() {
    println(birthdayGreeting(age = 2, name = "Rex"))
    println(birthdayGreeting("Rover", 5))
    println(birthdayGreeting(name = "Rex", age = 2))
}
fun birthdayGreeting(name: String, age: Int): String {
    val nameGreeting = "Happy Birthday, $name!"
    val ageGreeting = "You are now $age years old!"
    return "$nameGreeting\n$ageGreeting"
}
Happy Birthday, Rex!
You are now 2 years old!
Happy Birthday, Rover!
You are now 5 years old!
Happy Birthday, Rex!
You are now 2 years old!

7. Аргументы по умолчанию
fun birthdayGreeting(name: String = "Rover", age: Int): String {
    return "Happy Birthday, $name! You are now $age years old!"
}
println(birthdayGreeting(age = 5))
println(birthdayGreeting("Rex", 2))





