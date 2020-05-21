import java.util.*

fun main(){
    println("Hello !")
    feedTheFish()

    var  bubbles = 0
    while (bubbles<50){
        bubbles++
    }
    // REPEAT (функция из стандартной библиотеки)
    repeat(2){
    println("A fish is $it") }
    // там в библиотеке есть еще куча функций

    eagerExample()
    spicesFilter()
    val simpleSpice = SimpleSpice()
    println("Class ${simpleSpice.name} ${simpleSpice.heat}")

}
// Поддержка Лямбд:
// ЛЯМБДА - это выражение, которое делает функцию см в REPL:
// { println("Hello")}()  // В котлине ЭТО называется Лямбами - окружены {}()
//  Ответ: Hello            // Это неименованная но ее можно вызвать
// на других языках это анонимные функции или функциональные литералы или аналогичные имена
// val swim = {println("swim \n")}   // лямбда без аргумента
//swim()
//swim
//
//var dirty = 20
//val waterFilter = {dirty:Int -> dirty/2}    // Лямбда с аргументом
//
//waterFilter(dirty)
//res6: kotlin.Int = 10

// Это обычная именованная функция:
fun swim(){
    // swim
}


// примеры фильтров
fun spicesFilter() {
val spices = listOf("curry", "pepper", "cayenne", "ginger", "red curry", "green curry", "red pepper" )
println(spices.filter { it.contains("curry") }.sortedBy { it.length })
//res0: kotlin.collections.List<kotlin.String> = [curry, red curry, green curry]

    println(spices.filter{it.startsWith('c')}.filter{it.endsWith('e')})
//res1: kotlin.collections.List<kotlin.String> = [cayenne]

    println(spices.filter{it.startsWith('c')}.filter{it.endsWith('e')})
    //incomplete code

        println(spices.filter { it.startsWith('c') && it.endsWith('e') })
    //res3: kotlin.collections.List<kotlin.String> = [cayenne]

   // Filtering the first 3 items by 'c'
    //incomplete code

                println(spices.take(3).filter { it.startsWith('c') })
    //res5: kotlin.collections.List<kotlin.String> = [curry, cayenne]
}

fun eagerExample(){
    val decorations = listOf ("rock", "pagoda", "plastic plants", "alligator", "flowerpot")
    val eager = decorations.filter { it[0] =='p' }
    println(eager)

    // apply filter lazily ЛЕНИВЫЕ
    val filtered = decorations.asSequence().filter { it[0] =='p' }
    println(filtered)
    println(filtered.toList())  // превратить обратно в лист

    // apply MAP lazily
    val lazyMap = decorations.asSequence().map {
        println("map: $it")
    }
    println(lazyMap)
    println("first: ${lazyMap.first()}")
    println("all: ${lazyMap.toList()}")
}

fun getDirtySensorReading() = 20

fun shouldChangeWater(
    day:String,
    temperature: Int = 22,
    dirty: Int = getDirtySensorReading()) :Boolean {

    fun isTooHot(temperature:Int)= temperature>30
    fun isDirty(dirty: Int)= dirty >30
    fun isSunday(day:String)= day == "Sunday"

    return when {
        isTooHot(temperature) -> true
        isDirty(dirty) -> true
        isSunday(day) -> true
        else -> false
    }
}

// Поддержка Лямбд:
// ЛЯМБДА - это выражение, которое делает функцию
var dirty = 20
val waterFilter: (Int) -> Int = {dirty -> dirty/2}    // Лямбда с аргументом
fun feedFish(dirty: Int) = dirty + 10
// Настоящая сила лямбды возникает когда создаем функции высшего порядка:
// Это когда лямбда принимает лямбду в качестве аргумента:

fun updateDirty(dirty:Int, operation: (Int) -> Int): Int {
    return operation(dirty)
}
// operation имеет тип функции и является последним параметром
fun dirtyProcessor(){
    dirty = updateDirty(dirty,waterFilter)
    dirty = updateDirty(dirty, ::feedFish)  // разбор
    // Этим даем котлину понять что не вызываем функцию feedFish а передаем ее ссылку
    // когда рбъединяем функции высшего порядка с лямбдами
    // Эна этот раз разбираем лямбду в качестве аргумента для операции с параметрами:
    dirty = updateDirty(dirty) {dirty ->
        dirty + 50
    }
    // здесь лямбда является аргументом для функции,
    // но т.к. мы разбираем его как последний параметр
    // нам не нужно помещать его в скобки функции
    // Используя этот синтаксис мы можем определить
    // функции, которые выглядят как встроенные в язык
    // Уже использовали Например:
    // list.filter {
    //  it==2                   // берет лямбду и использует ее для фильтрации списка
    //  }
    //  [2]
    // repeat(2){ лямбда }   - Просто функция из библиотеки, которая берет кол-во повторений и повторяет Лямбду

}



fun feedTheFish(){
    val day = randomDay()
    val food = fishFood(day)
    println("Сегодня $day и рыба ест $food")
    shouldChangeWater(day,20,50)
    shouldChangeWater(day)
    shouldChangeWater(day,dirty = 50)

    if (shouldChangeWater(day)) println ("Change water today")

    // call dirty processor lambda
    dirtyProcessor()
}


fun fishFood(day: String): String {
    var food = "fasting"
    return when (day){
        "Monday" -> "flakes"
        "Tuesday" -> "palets"
        "Wednesday" -> "redworms"
        "Thursday" -> "granules"
        "Friday" -> "mosquitoes"
        "Saturday" -> "lettuce"
        "Sunday" -> "plankton"
        else -> "Hungry"
    }
}

fun randomDay() : String {
    val week = listOf ("Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday")
 return week[Random().nextInt(7)]
}