

class MyList<T> {
    fun get(pos: Int): T {
        TODO("implement")
    }
    fun addItem(item: T) {}
}
/*
Чтобы оставить пример без помех, создайте новый пакет под src и вызовите его generics.
В универсальном пакете создайте новый Aquarium.ktфайл.
Это позволяет вам переопределять вещи, используя одни и те же имена без конфликтов,
поэтому остальная часть кода для этой кодовой метки попадает в этот файл.
Составьте иерархию типов типов водоснабжения.
Начните с создания WaterSupply в open класс, так что он может быть подклассы.
Добавьте логический var параметр needsProcessing.
Это автоматически создает изменяемое свойство, а также метод получения и установки.
Сделайте подкласс, TapWater который проходит WaterSupply, и передать true для needsProcessing,
так как водопроводная вода содержит добавки , которые вредны для рыб.
В TapWater определим функцию с именем, addChemicalCleaners() что наборы needsProcessing для false после очистки воды.
needsProcessingСвойство может быть установлено от TapWater, потому что это public по умолчанию и доступен для подклассов.
Вот законченный код.
 */
open class WaterSupply(var needsProcessing: Boolean)

class TapWater : WaterSupply(true) {
    fun addChemicalCleaners() {
        needsProcessing = false
    }
}

/*
Создайте еще два подкласса с WaterSupply именами FishStoreWater и LakeWater.
FishStoreWater не требует обработки, но LakeWater должна быть отфильтрована filter() методом.
После фильтрации его не нужно снова обрабатывать, поэтому в filter() настройках needsProcessing = false.
 */
class FishStoreWater : WaterSupply(false)

class LakeWater : WaterSupply(true) {
    fun filter() {
        needsProcessing = false
    }
}

// Шаг 2: Создайте общий класс
class Aquarium<T>(val waterSupply: T)
class AquariumN<T: Any?>(val waterSupply: T)
class AquariumT<T: Any>(val waterSupply: T)
class AquariumW<T: WaterSupply>(val waterSupply: T)
//  только WaterSupply(или один из его подклассов) может быть передан T
// Шаг 4: Добавить больше проверки
//class AquariumWS<T: WaterSupply>(val waterSupply: T) {
class AquariumWS<out T: WaterSupply>(val waterSupply: T) {
    fun addWater() {
        check(!waterSupply.needsProcessing) { "водоснабжения должен обрабатывать сначала" }
        println("добавление воды из $waterSupply")
    }
}
// В этом случае, если needsProcessing это правда, check() сгенерирует исключение.


fun genericsExample() {
    //val aquarium = Aquarium<TapWater>(TapWater())
    val aquarium = Aquarium(TapWater())
    println("вода нуждается в обработке: ${aquarium.waterSupply.needsProcessing}")
    aquarium.waterSupply.addChemicalCleaners()
    println("вода нуждается в обработке:: ${aquarium.waterSupply.needsProcessing}")
    // вода нуждается в обработке: true
    // вода нуждается в обработке:: false
    val aquarium2 = Aquarium("string")
    println(aquarium2.waterSupply)

    val aquarium3 = Aquarium(null)
    if (aquarium3.waterSupply == null) {
        println("водоснабжение является нулевым null")
        // водоснабжение является нулевым null
    }
    val aquarium4 = AquariumWS(LakeWater())
    aquarium4.waterSupply.filter()
    aquarium4.addWater()
// Exception in thread "main" java.lang.IllegalStateException: водоснабжения должен обрабатывать сначал
    //добавление воды из LakeWater@3d494fbf

    val aquarium5 = AquariumWS(TapWater())
    addItemTo(aquarium5)
    // item added

}
fun addItemTo(aquarium: AquariumWS<WaterSupply>) = println("item added")
fun main() {
    //genericsExample()
    genericsExampleIN()
}
// Шаг 2: Определите тип
// В Aquarium.kt определите интерфейс, Cleaner который использует общий T тип, к которому он привязан WaterSupply.
// Поскольку он используется только в качестве аргумента clean(), вы можете сделать его in параметром.
interface Cleaner<in T: WaterSupply> {
    fun clean(waterSupply: T)
}
class TapWaterCleaner : Cleaner<TapWater> {
    override fun clean(waterSupply: TapWater) =   waterSupply.addChemicalCleaners()
}
class AquariumIN<out T: WaterSupply>(val waterSupply: T) {
    fun addWater(cleaner: Cleaner<T>) {
        if (waterSupply.needsProcessing) {
            cleaner.clean(waterSupply)
        }
        println("water added")
    }
}
fun genericsExampleIN() {
    val cleaner = TapWaterCleaner()
    val aquarium = AquariumIN(TapWater())
    aquarium.addWater(cleaner)

    val aquarium1 = Aquarium(TapWater())
    //isWaterClean<TapWater>(aquarium1)
    isWaterClean(aquarium1)

    val aquariumR = AquariumR(TapWater())
    println(aquariumR.hasWaterSupplyOfType<TapWater>())   // true

    val aquariumE = AquariumR(TapWater())
    println(aquariumE.waterSupply.isOfType<TapWater>())   // true

    val aquariumZ = AquariumR(TapWater())
    println(aquariumZ.hasWaterSupplyOfType<TapWater>())  // true
}
// water added

// Шаг 1: Создайте универсальную функцию T: WaterSupply - это вариант ограничения
fun <T: WaterSupply> isWaterClean(aquarium: Aquarium<T>) {
    println("aquarium water is clean: ${!aquarium.waterSupply.needsProcessing}")
}
// aquarium water is clean: false

// Шаг 2: Создайте универсальный метод с ограниченным типом
// В Aquarium классе объявите метод, hasWaterSupplyOfType() который принимает общий параметр R( Tуже используется),
// ограниченный WaterSupply, и возвращает trueif, если он waterSupply имеет тип R.
// Это похоже на функцию, которую вы объявили ранее, но внутри Aquarium класса.
class AquariumR<out T: WaterSupply>(val waterSupply: T) {
    fun addWater() {
        check(!waterSupply.needsProcessing) { "водоснабжения должен обрабатывать сначала" }
        println("добавление воды из $waterSupply")
    }
    inline fun <reified R: WaterSupply> hasWaterSupplyOfType() = waterSupply is R
}
// Чтобы выполнить is проверку, вы должны сообщить Kotlin,
// что тип является реализованным или реальным, и его можно использовать в функции.
// Для этого поместите inline перед fun ключевым словом и reified перед универсальным типом R.
// Примечание. Общие типы обычно доступны только во время компиляции и заменяются фактическими типами.
// Чтобы общий тип оставался доступным до времени выполнения, объявите функцию inline и создайте тип reified.

// Шаг 3: Сделать функции расширения
inline fun <reified T: WaterSupply> WaterSupply.isOfType() = this is T
// Чтобы использовать звездную проекцию, поставьте <*>после Aquarium.
// Перейдите hasWaterSupplyOfType()к функции расширения,
// потому что она на самом деле не является частью основного API Aquarium.
inline fun <reified R: WaterSupply> Aquarium<*>.hasWaterSupplyOfType() = waterSupply is R