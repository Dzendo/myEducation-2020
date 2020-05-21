

class SimpleSpice {
    val name = "curry"
    val spiciness = "mild"
    val heat: Int
        get() {return 5 }

    val spices1 = listOf(
        Spice("curry", "mild"),
        Spice("pepper", "medium"),
        Spice("cayenne", "spicy"),
        Spice("ginger", "mild"),
        Spice("red curry", "medium"),
        Spice("green curry", "mild"),
        Spice("hot pepper", "extremely spicy")
    )
    val spice = Spice("cayenne", spiciness = "spicy")



    val spicelist = spices1.filter {it.heat < 5}

    init {
        println("Class spice ${spice.name} ${spice.heat}")
        println("Class spice ${spicelist.toString()} ")  //${spicelist.name} ${spicelist.heat}")
        for (sp in spicelist) {
            println("Class spice ${sp.name} ${sp.heat}")
        }
    }
}

fun makeSalt() = Spice("Salt")

 // val spices = listOf("curry", "pepper", "cayenne", "ginger", "red curry", "green curry", "red pepper" )

class Spice (val name: String, val spiciness: String = "mild") {
    val heat: Int
        get() {
            return when (spiciness) {
                "mild" -> 1
                "medium" -> 3
                "spicy" -> 5
                "very spicy" -> 7
                "extremely spicy" -> 10
                else -> 0
            }
        }
}
