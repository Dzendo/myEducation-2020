package Aquarium.Decoration

fun main() {
    makeDecoration()
}

fun makeDecoration() {
    val d1 = Decorations("granite")
    println(d1)  // когда печатает dataclass сам преобразует в данные из указателя
    val d2 = Decorations("slate")
    println(d2)
    val d3 = Decorations("slate")
    println(d3)

    println(d1.equals(d2))
    println(d2.equals(d3))

    val d4 = d3.copy()
    println(d3)
    println(d4)

    val d5 = Decorations2("crystal","wood","diver")
    println(d5)

    // такое объявление называется процессом декомпозиции:
    val (rock,wood,diver)= d5
    // порядок должен быть жестко как в объявлении дата класса
    println(rock)
    println(wood)
    println(diver)

}
data class Decorations(val rocks: String) {}

data class Decorations2(val rocks: String, val wood: String, val diver: String) {}
