package example.myapp.decor

fun main() {
    makeDecorations()
    makeDecorations2()
}

fun makeDecorations() {
    val decoration1 = Decoration("granite")
    println(decoration1)
    val decoration2 = Decoration("slate")
    println(decoration2)
    val decoration3 = Decoration("slate")
    println(decoration3)
    println (decoration1.equals(decoration2))
    println (decoration3.equals(decoration2))
    // Примечание: вы могли бы ==проверить, есть ли d1 == d2и d3 == d2.
    // В Kotlin использование ==объектов класса данных аналогично использованию equals()(структурное равенство).
    // Если вам нужно проверить, ссылаются ли две переменные на один и тот же объект (ссылочное равенство),
    // используйте ===оператор. Узнайте больше о равенстве в Kotlin в документации по языку.
}

data class Decoration (val rocks: String) {

}

// Here is a data class with 3 properties.
data class Decoration2(val rocks: String, val wood: String, val diver: String){
}
fun makeDecorations2() {
    val d5 = Decoration2("crystal", "wood", "diver")
    println(d5)

// Assign all properties to variables.
    val rock2 = d5.rocks
    val wood2 = d5.wood
    val diver2 = d5.diver
    // val (rock, _, diver) = d5
    // Шаг 2. Используйте деструктуризацию
    val (rock, wood, diver) = d5
    println(rock)
    println(wood)
    println(diver)
}