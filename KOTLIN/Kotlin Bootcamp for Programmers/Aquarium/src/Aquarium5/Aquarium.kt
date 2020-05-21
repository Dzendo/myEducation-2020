package Aquarium5

fun main() {
    val fish = Fish()
}
internal fun buildAquarium()= Aquarium()

class Aquarium (val width: Int = 0, var height: Int = 40, val length: Int = 100) {

    var volume
    get() = width * height * length /1_000
    set(value) { height = (value*1000) / (width * length)}
}