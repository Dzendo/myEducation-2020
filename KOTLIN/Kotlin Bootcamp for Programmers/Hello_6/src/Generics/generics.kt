package Generics


open class BaseBuildingMaterial() {
    open val numberNeeded = 1
}

class Wood : BaseBuildingMaterial() {
    override val numberNeeded = 4
}

class Brick : BaseBuildingMaterial() {
    override val numberNeeded = 8
}


class Building<out T: BaseBuildingMaterial>(val buildingMaterial: T) {

    val baseMaterialsNeeded = 100
    val actualMaterialsNeeded = buildingMaterial.numberNeeded * baseMaterialsNeeded

    fun build() {
        println(" $actualMaterialsNeeded ${buildingMaterial::class.simpleName} required")
    }
}

fun <T : BaseBuildingMaterial> isSmallBuilding( building: Building<T>){
    if (building.actualMaterialsNeeded < 500) println("Small building")
    else println("large building")
}


fun main(args: Array<String>) {
    Building(Wood()).build()

    isSmallBuilding(Building(Brick()))

}


/*
* Type hierarchies:
* -----------------
* We start by creating a type hierarchy.
* Each type has shared properties, but they usually have different values.
*
* Generic class
* -------------
* We can create a class that takes as an argument any of the sub-types of the hierarchy.
* Inside the class, we perform actions depending on the sub-type's properties.
*/