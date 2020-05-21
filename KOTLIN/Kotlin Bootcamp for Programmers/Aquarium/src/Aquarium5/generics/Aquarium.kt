package Aquarium5.generics
// Вложенный пакет позволяет нам переопределять вещи
// используя те же имена без конфликтов
fun main(){
    genericExample()
}
// создадим иерархию классов:
open class WaterSupply(var  needsProcessed: Boolean)

class TapWater : WaterSupply( true) {
    fun addChemicalCleaners() {
        needsProcessed = false
    }
}

class FishStoreWater : WaterSupply( false)

class  LakeWater : WaterSupply(true) {
    fun filter() {
        needsProcessed = false
    }
}
// Теперь сделаем generic Аквариум:

// это побочное явление что тип Т любой
// чтобы убрать нулевой тип убираем знак ? у объявления class Any? и затем вообще ограничиваем Т
//class Aquarium <T: Any? >(val waterSupply: T)
//class Aquarium <T: Any? >(val waterSupply: T)
class Aquarium <T: WaterSupply >(val waterSupply: T){   // ограничиваем Т насколько нам надо

    fun addWater(){                   // проверка на правильность переданного Т при вызове класса - встроенная
     check(!waterSupply.needsProcessed) { "water supply needs processed"}
 }
}

fun genericExample(){
    val aquarium = Aquarium(TapWater())
    aquarium.waterSupply.addChemicalCleaners()
    println(aquarium.waterSupply)

// попробуем еще один пример: передадим строку в аквариум
//    val aquarium2 = Aquarium("string")  //щшибка после того как ограничили T только WaterSupply
    println(aquarium.waterSupply)   // вывод: Aquarium5.generics.TapWater@6e8cf4c6 - не имеет смысла хотя можно

    // Можно вообще передать null на место Any?
//    val aquarium3 = Aquarium(null)   // Ошибка если Any без ?
 //   println(aquarium.waterSupply)   // вывод: Aquarium5.generics.TapWater@6e8cf4c6 - не имеет смысла хотя можно

    val aquarium4 = Aquarium(LakeWater())
    aquarium4.waterSupply.filter()
    aquarium4.addWater()
    println(aquarium4.waterSupply)
    // Но сделать аквариум из string более невозможно из-за ограничений Т
    // И ЭТО ОСНОВЫ ДЖЕНЕРИКА
}
// Есть еще много Аспектов дженериков , но это самая важная концепция

// Как объявить универсальный класс с верхней границей и использовать его далее: сл лекция
