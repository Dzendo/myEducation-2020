package Aquarium
// Каждый раз когда нужен класс создаваемый только один раз - используем object
object ModyDickWhale {
    val author = "Herman Melville"
    fun jump(){
        // .....
    }
}

// Перечисления в котлине
enum class Color(val rgb:Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)
}

sealed class Seal
class SeaLion: Seal()
class Walrus: Seal()

// Все складывать ТОЛЬКО в этот файл

fun mathSeals(seal: Seal): String {
    return when(seal) {
        is Walrus -> "walrus"
        is SeaLion -> "sea Lion"
    }
}