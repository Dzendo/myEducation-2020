package Spice

fun main(){
    delegate()

    val spiceCabinet = listOf(SpiceContainer(Curry("Yellow Curry", "mild")),
        SpiceContainer(Curry("Red Curry", "medium")),
        SpiceContainer(Curry("Green Curry", "spicy")))

    for(element in spiceCabinet) println(element.label)
}
fun delegate(){
    val spice = Curry("name-try","spiciness-try")
    println ("Spice has color ${spice.color}")
 //   spice.eat()
}

sealed abstract class Spice(val name: String, val spiciness: String = "mild", color: SpiceColor) : SpiceColor by color {
    abstract fun prepareSpice()
}

class Curry(name: String, spiciness: String,
            color: SpiceColor = YellowSpiceColor) : Spice(name, spiciness, color), Grinder {
    override fun grind() {
    }

    override fun prepareSpice() {
        grind()
    }
}

interface Grinder {
    fun grind()
}

interface SpiceColor {
    val color: Color
}

object YellowSpiceColor : SpiceColor {
    override val color = Color.Yellow
}

data class SpiceContainer(var spice: Spice) {
    val label = spice.name
}

enum class Color(val rgb: Int) {
    RED(0xFF0000), GREEN(0x00FF00), BLUE(0x0000FF), Yellow(0xFFFF00);
}