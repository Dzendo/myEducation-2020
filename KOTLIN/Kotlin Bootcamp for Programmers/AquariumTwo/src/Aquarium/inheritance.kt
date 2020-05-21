package Aquarium

interface AquariumAction {
    fun eat()
    fun jump()
    fun clean()
    fun cathFish()
    fun swim(){
        println("swim")
    }
}

interface FishActiom {
    fun eat()
}

abstract class AquariumFish1: FishAction {
    abstract val color: String
    override fun eat() = println("Yum")
}