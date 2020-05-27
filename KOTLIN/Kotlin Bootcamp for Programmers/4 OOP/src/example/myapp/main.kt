// Шаг 1: Создать пакет
package example.myapp
// Шаг 3: Создайте функцию main ()
fun main() {
    //buildAquarium()
    makeFish()
}
// Шаг 1. вызов абстрактный класс
fun makeFish() {
    val shark = Shark()
    val pleco = Plecostomus()

    println("Shark: ${shark.color}")
    shark.eat()
    println("Plecostomus: ${pleco.color}")
    pleco.eat()

    val sharkd = SharkD()
    val plecod = PlecostomusD()
    println("SharkD: ${sharkd.color}")
    sharkd.eat()
    println("PlecostomusD: ${plecod.color}")
    plecod.eat()

    // Шаг 4: Добавить делегирование интерфейса для FishAction
    println("BY-BY:")
    val sharkbyd = SharkByD()
    val plecobyd = PlecostomusByD()
    println("SharkByD: ${sharkbyd.color}")
    sharkbyd.eat()
    println("PlecostomusByD: ${plecobyd.color}")
    plecobyd.eat()



}

fun buildAquarium() {
    val myAquarium = Aquarium()
    // Шаг 4: вызовите printSize() метод на myAquarium
    myAquarium.printSize()
    //  добавьте код, чтобы установить высоту 60 и распечатать измененные свойства измерения
    myAquarium.height = 60
    myAquarium.printSize()

    val aquarium1 = Aquarium()
    aquarium1.printSize()
    // default height and length
    val aquarium2 = Aquarium(width = 25)
    aquarium2.printSize()
    // default width
    val aquarium3 = Aquarium(height = 35, length = 110)
    aquarium3.printSize()
    // everything custom
    val aquarium4 = Aquarium(width = 25, height = 35, length = 110)
    aquarium4.printSize()

// использование вашего нового вторичного конструктора. Распечатать размер и объем.
    val aquarium6 = Aquarium(numberOfFish = 29)
    aquarium6.printSize()
    println("Second Constuctor Volume: ${aquarium6.width * aquarium6.length * aquarium6.height / 1000} l")

    println("getter - setter Volume:")
    aquarium6.volume = 70
    aquarium6.printSize()

    val aquarium7 = Aquarium(length = 25, width = 25, height = 40)
    println("open shape:")
    aquarium7.printSize()

    val myAquarium8 = Aquarium(width = 25, length = 25, height = 40)
    myAquarium8.printSize()
    // Вызов Дочернего класса:
    println("extends:")
    val myTower = TowerTank(diameter = 25, height = 40)
    myTower.printSize()


}
