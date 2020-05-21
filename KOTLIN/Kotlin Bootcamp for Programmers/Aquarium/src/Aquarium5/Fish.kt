package Aquarium5

class Fish(val friedly: Boolean = true, volumeNeeded: Int) {
    val size: Int

    init{
        println("first init block")
    }
    constructor(): this(true,9){
        println("running sec constraction")
    }
    init {
        if (friedly) {
            size = volumeNeeded
        } else {
            size = volumeNeeded *2
        }
    }
    init {
        println("constracted fish of size $volumeNeeded final size ${this.size}")
    }
    init {
        println("last init block")
    }
}

fun fishexample() {
    val fish = Fish(true,20)
    println("Is the fish frendly?  ${fish.friedly} It needs volume ${fish.size}")
}