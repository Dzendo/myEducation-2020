package Aquarium

import kotlin.math.PI

// Все классы наследуются от :Any() - это необяз т.к. по умолч
// чтобы наслед надо делать класс OPEN

open class Aquarium (var lenght: Int = 100, var width: Int = 20, var height: Int = 40) {

    open var volume: Int                                  // переменная с переопределенным Get в одну строку
            get () = width * height * lenght /1000
            set(value) {height = (value * 1000) / (width * lenght)}   // можно сделать private
    open var water = volume * 0.9  // будет double

    // Вторичный конструктор this() - вызов первичного без параметров т.к. там они есть по умолчанию
    constructor(numberOfFish: Int): this() {
        val water = numberOfFish * 2000 // cm3
        val tank = water + water * 0.1
        height = (tank / (lenght * width)).toInt()

    }
}

class TowerTank(): Aquarium() {

    override var water = volume *0.8

    override var volume: Int
        get() = (width * height * lenght / 1000 * PI).toInt()
        set(value) {
            height = (value * 1000) / (width * lenght)}
}