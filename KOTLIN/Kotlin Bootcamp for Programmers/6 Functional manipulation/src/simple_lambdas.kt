
// 4. Task: Create simple lambdas

// Шаг 1: Создайте простую лямбду
val waterFilter = { dirty: Int -> dirty / 2 }

// Шаг 2: Создание фильтра лямбда
data class Fish(val name: String)
val myFish = listOf(Fish("Flipper"), Fish("Moby Dick"), Fish("Dory"))


fun main() {
    println(waterFilter(30))
    // 15
    // res1: kotlin.Int = 15 (REPL)

    // В лямбда-выражении it ссылается на текущий элемент списка,
    // и фильтр применяется к каждому элементу списка по очереди.
    println(myFish.filter { it.name.contains("i")})
    // [`higher-order_function`.Fish(name=Flipper), `higher-order_function`.Fish(name=Moby Dick)]
    // res6: kotlin.collections.List<Line_4.`higher-order_function`.Fish> = [`higher-order_function`.Fish(name=Flipper), `higher-order_function`.Fish(name=Moby Dick)] (REPL)

    // Примените joinString()к результату, используя ", "в качестве разделителя.
    println(myFish.filter { it.name.contains("i")}.joinToString(", ") { it.name })
    // res7: kotlin.String = Flipper, Moby Dick (REPL)
    // Flipper, Moby Dick
    // joinToString() Функция создает строку путем объединения отфильтрованных имен, разделенных строки, заданной.
    // Это одна из многих полезных функций, встроенных в стандартную библиотеку Kotlin.
}
