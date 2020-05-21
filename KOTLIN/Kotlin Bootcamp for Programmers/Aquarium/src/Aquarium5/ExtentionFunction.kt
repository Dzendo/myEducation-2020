package Aquarium5

// Добавляет функцию расщирения(через точку) к существующему классу
// без доступа к его исходному коду  в удобном синтаксисе
// Например можно объявить их в extentions.kt как часть пакета
// Расширения фактически не меняют классы которые они расширяют
// после того как объявлено расширение - оно доступно, как если бы было объявлено в классе
// ЭТО КРУТО
// Можно расширять классы к которым нет доступа


fun String.hasSpaces(): Boolean {
    val found:Char? = this.find { it == ' '}
    return found != null

    fun String.hasSpaces_short() =  find { it == ' '} !=null

}
fun extentionsExample(){
    "Does it have spad"
}
// можно использовать для отделения основного API от вспомогательных методов в собст классах

open class AquariumPlants(val color: String,private val size: Int)
class GreenLeafyPlant(size:Int) : AquariumPlants("Green", size)

fun AquariumPlants.isRed() = color == "Red"
// ясно показываем что это просто помощник
// Определяются ВНЕ класса который расщиряют ==> не получают доступ к закрытым переменным:
// fun AquariumPlants.isBIG() = size > 50  // Ошибка размер является private

fun AquariumPlants.print() = println("AquariumPlant")
fun GreenLeafyPlant.print() = println("GreenLeafyPlant")

fun staticExample() {
    val plant = GreenLeafyPlant(50)
    plant.print() // GreenLeafyPlant

    val aquariumPlant : AquariumPlants = plant
    aquariumPlant.print()  // AquariumPlant
}

val AquariumPlants.isGreen: Boolean
       get()= color == "Green"

// теперь можно использовать как обычное свойство:
fun prorertyEzample() {
    val plant = AquariumPlants("green", 50)
    plant.isGreen  // true
}

// Можно сделать класс расширить на null^
fun AquariumPlants?.pull(){
    this?.apply {
        println("removing $this")
    }
}
fun nullableExample(){
    val plant: AquariumPlants? = null
    plant.pull()  // OK
}
// Функции расширения действительно мощные
// и большая часть станд библиотеки Котлин реализована
// как функция расширения

// Надо освоить и будешь все время дополнять свои классы


fun main(){}