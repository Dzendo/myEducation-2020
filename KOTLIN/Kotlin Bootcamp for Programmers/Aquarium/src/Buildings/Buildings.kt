package Buildings

fun main() {
    Building(Wood()).build()
    isSmallBuilding(Building(Brick()))
}

open class BaseBuildingMaterial {
    open val numberNeeded = 1
}

class Wood : BaseBuildingMaterial() {
    override val numberNeeded: Int = 4
}
class Brick : BaseBuildingMaterial() {
    override val numberNeeded: Int = 8
}
class Building <out T: BaseBuildingMaterial>(val buildingMaterial: T) {
    val baseMaterialNeeded = 100
    val actualMaterialNeeded = buildingMaterial.numberNeeded * baseMaterialNeeded

    fun build() {
        println(" $actualMaterialNeeded ${buildingMaterial::class.simpleName} required")
    }
}
fun <T : BaseBuildingMaterial> isSmallBuilding(building: Building<T>) {
    if (building.actualMaterialNeeded < 500) println("Small building")
    else println("large building")
}
