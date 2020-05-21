import java.util.*

fun main(args: Array<String>) {
    println("Hello, world!")
    // Run > Edit Conf > Prog Arguments > Kotlin!!!
    println("Hello, ${args[0]}")
    // Hello, world!
    // Hello, Kotlin!!!

    // Will assign kotlin.Unit
    val isUnit = println("This is an expression")
    println(isUnit)

    // This is an expression
    // kotlin.Unit

    val temperature = 10
    val isHot = if (temperature > 50) true else false
    println(isHot)
    // false

    val temperature1 = 10
    val message = "The water temperature is ${ if (temperature1 > 50) "too warm" else "OK" }."
    println(message)
    // The water temperature is OK.

    feedTheFish1()
    feedTheFish2()
    // Today is Tuesday and the fish eat pellets

    swim()   // uses default speed
    swim("slow")   // positional argument
    swim(speed="turtle-like")   // named parameter

    feedTheFish()
    val decorations = listOf ("rock", "pagoda", "plastic plant", "alligator", "flowerpot")
    println( decorations.filter {it[0] == 'p'})
    // eager, creates a new list
    val eager = decorations.filter { it [0] == 'p' }
    println("eager: $eager")
    // lazy, will wait until asked to evaluate
    val filtered = decorations.asSequence().filter { it[0] == 'p' }
    println("filtered: $filtered")
    // force evaluation of the lazy list
    val newList = filtered.toList()
    println("new list: $newList")
    val lazyMap = decorations.asSequence().map {
        println("access: $it")
        it
    }

    println("lazy: $lazyMap")
    println("-----")
    println("first: ${lazyMap.first()}")
    println("-----")
    println("all: ${lazyMap.toList()}")

    val lazyMap2 = decorations.asSequence().filter {it[0] == 'p'}.map {
        println("access: $it")
        it
    }
    println("-----")
    println("filtered: ${lazyMap2.toList()}")
}
// Запустите вашу программу и наблюдайте за дополнительным выводом.
// Как и при получении первого элемента, внутренний println() вызывается только для элементов,
// к которым осуществляется доступ.
// [pagoda, plastic plant]
//eager: [pagoda, plastic plant]
//filtered: kotlin.sequences.FilteringSequence@4b85612c
//new list: [pagoda, plastic plant]
//lazy: kotlin.sequences.TransformingSequence@41906a77
//-----
//access: rock
//first: rock
//-----
//access: rock
//access: pagoda
//access: plastic plant
//access: alligator
//access: flowerpot
//all: [rock, pagoda, plastic plant, alligator, flowerpot]
//-----
//access: pagoda
//access: plastic plant
//filtered: [pagoda, plastic plant]
//
//Process finished with exit code 0



fun feedTheFish1() {
    val day = randomDay()
    val food = "pellets"
    println ("Today is $day and the fish eat $food")
}


fun randomDay() : String {
    val week = arrayOf ("Monday", "Tuesday", "Wednesday", "Thursday",
        "Friday", "Saturday", "Sunday")
    return week[Random().nextInt(week.size)]
}

// Примечание. Циклы являются исключениями для «все имеет значение».
// Не существует разумного значения для for циклов или while циклов, поэтому они не имеют значений.
// Если вы попытаетесь присвоить значение цикла чему-либо, компилятор выдаст ошибку.

fun fishFood1 (day : String) : String {
    //var food = ""
    val food: String
    when (day) {
        "Monday" -> food = "flakes"
        "Tuesday" -> food = "pellets"
        "Wednesday" -> food = "redworms"
        "Thursday" -> food = "granules"
        "Friday" -> food = "mosquitoes"
        "Saturday" -> food = "lettuce"
        "Sunday" -> food = "plankton"
        else -> food = "nothing"
    }
    return food
}

fun fishFood2 (day : String) : String {
    //var food = ""
    return when (day) {
        "Monday" ->  "flakes"
        "Tuesday" ->  "pellets"
        "Wednesday" ->  "redworms"
        "Thursday" ->  "granules"
        "Friday" -> "mosquitoes"
        "Saturday" ->  "lettuce"
        "Sunday" ->  "plankton"
        else ->  "nothing"
    }

}

fun fishFood3 (day : String) : String {
    return when (day) {
        "Monday" -> "flakes"
        "Wednesday" -> "redworms"
        "Thursday" -> "granules"
        "Friday" -> "mosquitoes"
        "Sunday" -> "plankton"
        else -> "nothing"
    }
}

fun feedTheFish2() {
    val day = randomDay()
    val food1 = fishFood1(day)
    println ("1 Today is $day and the fish eat $food1")
    val food2 = fishFood2(day)
    println ("2 Today is $day and the fish eat $food2")
    val food3 = fishFood2(day)
    println ("3 Today is $day and the fish eat $food3")
    val food = fishFood2(day)
    println ("Today is $day and the fish eat $food")
}


fun fishFood (day : String) : String =
         when (day) {
        "Monday" -> "flakes"
        "Wednesday" -> "redworms"
        "Thursday" -> "granules"
        "Friday" -> "mosquitoes"
        "Sunday" -> "plankton"
        else -> "nothing"
    }
// Hello, world!
//Hello, Kotlin!!!
//This is an expression
//kotlin.Unit
//false
//The water temperature is OK.
//Today is Thursday and the fish eat pellets
//1 Today is Tuesday and the fish eat pellets
//2 Today is Tuesday and the fish eat pellets
//3 Today is Tuesday and the fish eat pellets
//Today is Tuesday and the fish eat pellets
//
//Process finished with exit code 0

fun swim(speed: String = "fast") {
    println("swimming $speed")
}

fun shouldChangeWater (day: String, temperature: Int = 22, dirty: Int = 20): Boolean {
    return when {
        temperature > 30 -> true
        dirty > 30 -> true
        day == "Sunday" ->  true
        else -> false
    }
}

fun feedTheFish() {
    val day = randomDay()
    val food = fishFood(day)
    println ("Today is $day and the fish eat $food")
    println("Change water: ${shouldChangeWater(day)}")
    println("1 Change water: ${shouldChangeWater1(day)}")
}

fun isTooHot(temperature: Int) = temperature > 30

fun isDirty(dirty: Int) = dirty > 30

fun isSunday(day: String) = day == "Sunday"

fun shouldChangeWater1 (day: String, temperature: Int = 22, dirty: Int = 20): Boolean {
    return when {
        isTooHot(temperature) -> true
        isDirty(dirty) -> true
        isSunday(day) -> true
        else  -> false
    }
}

//fun shouldChangeWater (day: String, temperature: Int = 22, dirty: Int = getDirtySensorReading()): Boolean {
// Примечание. Функция, используемая в качестве значения по умолчанию, оценивается во время выполнения,
// поэтому не помещайте в функцию дорогостоящую операцию, такую ​​как чтение файла или выделение большого объема памяти.
// Операция выполняется каждый раз, когда вызывается ваша функция, что может замедлить вашу программу.

// 7. Задача: начать работу с лямбдами и функциями высшего порядка
/*
Шаг 1: Узнайте о лямбдах
var dirtyLevel = 20
 val waterFilter = { dirty : Int -> dirty / 2}
 println(waterFilter(dirtyLevel))
10
val waterFilter: (Int) -> Int = { dirty -> dirty / 2 }

Шаг 2: Создайте функцию высшего порядка
fun updateDirty(dirty: Int, operation: (Int) -> Int): Int {
     return operation(dirty)
 }

val waterFilter: (Int) -> Int = { dirty -> dirty / 2 }
 println(updateDirty(30, waterFilter))
15
fun increaseDirty( start: Int ) = start + 1

 println(updateDirty(15, ::increaseDirty))
16
Примечание: Kotlin предпочитает, чтобы любой параметр, который принимает функцию, был последним параметром.
При работе с функциями более высокого порядка Kotlin имеет специальный синтаксис,
называемый синтаксисом вызова последнего параметра , который позволяет сделать код еще более лаконичным.
В этом случае вы можете передать лямбду для параметра функции, но вам не нужно ставить лямбда в скобках.

var dirtyLevel = 19;
 dirtyLevel = updateDirty(dirtyLevel) { dirtyLevel -> dirtyLevel + 23}
 println(dirtyLevel)
42

 */