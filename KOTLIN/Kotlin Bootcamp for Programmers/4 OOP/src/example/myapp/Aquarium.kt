package example.myapp

import kotlin.math.PI

// Шаг 2. Создайте класс со свойствами
// class Aquarium {
/*class Aquarium(length: Int = 100, width: Int = 20, height: Int = 40) {
    var width: Int = 20
    var height: Int = 40
    var length: Int = 100
    //Под капотом Kotlin автоматически создает методы получения и установки для свойств,
    // которые вы определили в Aquariumклассе, так что вы можете получить прямой доступ к свойствам,
    // например myAquarium.length,.
*/
// Обратите внимание, что вам не пришлось перегружать конструктор
// и писать разные версии для каждого из этих случаев (плюс еще несколько для других комбинаций).
// Kotlin создает то, что нужно из значений по умолчанию и именованных параметров.
// Шаг 1: Сделайте класс Аквариума открытым open
open class Aquarium(var length: Int = 100, var width: Int = 20, open var height: Int = 40) {
    open val shape = "rectangle"
    open var water: Double = 0.0
        get() = volume * 0.9
    // Шаг 4: добавление нового свойства getter
    open var volume: Int
         get() = width * height * length / 1000  // 1000 cm^3 = 1 l
         set(value) {
                height = (value * 1000) / (width * length)
         }
// Если вашему конструктору требуется больше кода инициализации, его можно поместить в один или несколько init блоков
    init {
        println("init 1 : aquarium initializing")
    }
    init {
        // 1 liter = 1000 cm^3
        println("Init 2: Volume: ${width * length * height / 1000} l")
    }
    // добавьте вторичный конструктор, который принимает количество рыбы в качестве аргумента
    constructor(numberOfFish: Int) : this() {
        // Создайте val свойство резервуара для рассчитанного объема аквариума в литрах в зависимости от количества рыб.
        // Предположим, что 2 литра (2000 см ^ 3) воды на рыбу плюс немного больше места, чтобы вода не пролилась.
        // 2,000 cm^3 per fish + extra room so water doesn't spill
        val tank = numberOfFish * 2000 * 1.1
        // calculate the height needed
        height = (tank / (length * width)).toInt()
    }
// Шаг 4: Добавить метод
    fun printSize() {
        println("Width: $width cm " +
                "Length: $length cm " +
                "Height: $height cm ")
    // 1 l = 1000 cm^3
    println("getter Volume: $volume l")

    println(shape)
    println("Width: $width cm " +
            "Length: $length cm " +
            "Height: $height cm ")
    // 1 l = 1000 cm^3
    println("Volume: $volume l Water: $water l (${water/volume*100.0}% full)")

    }
}

// Шаг 2: Создать подкласс
// Примечание. Подклассы должны явно указывать свои параметры конструктора.
class TowerTank (override var height: Int, var diameter: Int): Aquarium(height = height, width = diameter, length = diameter) {
    override var volume: Int
        // ellipse area = π * r1 * r2
        get() = (width/2 * length/2 * height / 1000 * PI).toInt()
        set(value) {
            height = ((value * 1000 / PI) / (width/2 * length/2)).toInt()
        }

    override var water = volume * 0.8
    override val shape = "cylinder"
}