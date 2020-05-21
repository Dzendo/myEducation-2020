package Aquarium5.generics.function
// еще In Out Reified и проекции * Тип стирания
// Кстати все Generic типы проявляются только во время компиляции
// Во время выполнения все Generic стираются
// поэтому и добавлены Reified
fun main(){
    genericExample()
}
open class WaterSupply(var  needsProcessed: Boolean)

class TapWater : WaterSupply( true) {
    fun addChemicalCleaners() =apply { needsProcessed = false }
}

class FishStoreWater : WaterSupply( false)

class  LakeWater : WaterSupply(true) {
    fun filter() =apply { needsProcessed = false }
}

class Aquarium <out T: WaterSupply >(val waterSupply: T) {

    fun addWater() {                   // проверка на правильность переданного Т при вызове класса - встроенная
        check(!waterSupply.needsProcessed) { "water supply needs processed" }
        println("adding water from $waterSupply")
    }
}
    // inline и reifired
    //inline fun <reified R : WaterSupply> hasWaterSupplyOfType() = waterSupply is R

    inline fun <reified R : WaterSupply> Aquarium<*>.hasWaterSupplyOfType() = waterSupply is R


    // аквариум должен иметь тип OUT чтобы это можно было вызвать
    //fun isWaterClean(aquarium: Aquarium<WaterSupply>) {
// теперь говорим что isWaterCleaner - это функция с аргументом Т (ограничен подачей воды)
    fun <T : WaterSupply> isWaterClean(aquarium: Aquarium<T>) {
        println("aquarium water is clean ${aquarium.waterSupply.needsProcessed}")
    }

inline fun <reified T: WaterSupply> WaterSupply.isOfType() = this is T

fun genericExample(){
    val aquarium = Aquarium(TapWater())
    aquarium.hasWaterSupplyOfType<TapWater>()  // true
    aquarium.waterSupply.isOfType<LakeWater>() // false

    isWaterClean(aquarium)   // нам не нужно <> - компилятор выясняет это по типу аквариума
}
// Также можно использовать Generics function для методов,
// даже в классах которые имеют родителя