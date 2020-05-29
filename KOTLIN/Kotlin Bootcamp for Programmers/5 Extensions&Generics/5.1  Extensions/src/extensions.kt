

fun main() {

    // Шаг 1: Написать функцию расширения
    //  напишите простую функцию расширения, hasSpaces()чтобы проверить, содержит ли строка пробелы.
    //  Имя функции имеет префикс класса, с которым она работает.
    //  Внутри функции this ссылается на объект, к которому она вызывается, и it ссылается на итератор в find() вызове.
    fun String.hasSpaces(): Boolean {
        val found = this.find { it == ' ' }
        return found != null
    }
    println("Does it have spaces?".hasSpaces())
    // true

    // Вы можете упростить hasSpaces()функцию.
    fun String.hasSpace() = find { it == ' ' } != null
    println("Does it have spaces?".hasSpace())
    // true

    val plant = GreenLeafyPlant(size = 10)
    plant.print()
    println("\n")
    val aquariumPlant: AquariumPlantC = plant
    aquariumPlant.print()  // what will it print?
    println(aquariumPlant.isGreen)
    //true   res14: kotlin.Boolean = true

    // Step 4: Know about nullable receivers
    val plant4: AquariumPlantC? = null
    plant4.pull()
    // В этом случае нет выхода при запуске программы.
    // Потому что plant это null, внутренний println() не называется.
}

class AquariumPlantA(val color: String, private val size: Int)
// Шаг 2. Изучите ограничения расширений
fun AquariumPlantA.isRed() = color == "red"    // OK
//fun AquariumPlantA.isBig() = size > 50         // gives error - private


// Изучите приведенный ниже код и выясните, что он будет печатать.
open class AquariumPlantC(val color: String, private val size: Int)

class GreenLeafyPlant(size: Int) : AquariumPlantC("green", size)

fun AquariumPlantC.print() = println("AquariumPlantC")
fun GreenLeafyPlant.print() = println("GreenLeafyPlant")

// GreenLeafyPlant
//AquariumPlant

val AquariumPlantC.isGreen: Boolean
    get() = color == "green"

//Step 4: Know about nullable receivers
fun AquariumPlantC?.pull() {
    this?.apply {
        println("removing $this")
    }
}
