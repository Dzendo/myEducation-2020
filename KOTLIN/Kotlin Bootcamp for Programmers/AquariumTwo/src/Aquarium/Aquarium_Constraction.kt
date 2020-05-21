package Aquarium

//  public Все объявления публичны -- видны всем и везде, даже внутренние класса
//  private для val var fun set get - только внутри файла
//  protected - Могут видеть подклассы
//  internal видно где угодно в том же модуле
// модуль - набор файлов котлин скомпилированных вместе

//

class Aquarium_Constraction (var lenght: Int = 100, var width: Int = 20, var height: Int = 40) {
    // Если дать параметрам var то нижнее не нужно
    // Это более правильно в котлине
   // var width: Int = width      // 20
   // var height: Int = height    // 40
   // var lenght: Int = lenght    // 100

   // fun volume() = width * height * lenght /1000  // лучше считать что это функция или свойство класса

/*    val volume :Int                               // переменная с переопределенным Get
        get() {
         return  width * height * lenght /1000
        }
*/
    var volume: Int                                  // переменная с переопределенным Get в одну строку
            get () = width * height * lenght /1000
            set(value) {height = (value * 1000) / (width * lenght)}   // можно сделать private
    var water = volume * 0.9  // будет double
    // Вторичный конструктор this() - вызов первичного без параметров т.к. там они есть по умолчанию
    constructor(numberOfFish: Int): this() {
        val water = numberOfFish * 2000 // cm3
        val tank = water + water * 0.1
        height = (tank / (lenght * width)).toInt()
        println (" constructor: water = $water  tank = $tank ")

        println(" Constractor Length: ${lenght}  " +
                "Width: ${width}  " +
                "Height: ${height}  cm ")
    }
}