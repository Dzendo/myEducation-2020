package secondaryConstructorsExercise2

import atomictest.eq

data class SpaceShip(val name: String) {
    // TODO add secondary constructor
}

fun main() {
    SpaceShip("SuperhighspeedShip") eq
      "SpaceShip(name=SuperhighspeedShip)"
//    SpaceShip("MClass", 29321) eq
//      "SpaceShip(name=MClass-29321)"
}