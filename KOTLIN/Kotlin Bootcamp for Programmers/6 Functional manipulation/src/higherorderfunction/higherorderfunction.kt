package higherorderfunction


// 5. Task: Write a higher-order function

// Шаг 1: Создайте новый класс

data class Fish (var name: String)

fun fishExamples() {
    val fish = Fish("splashy")  // all lowercase
    // Шаг 2: Используйте функцию высшего порядка
    // with() на самом деле это функция высшего порядка,
    // и в lambda вы указываете, что делать с предоставленным объектом.
    with (fish.name) {
        println(capitalize())
        // Splashy
    }
    // fish.name является аргументом имени и println(capitalize()) является блочной функцией.
    myWith (fish.name) {
        println(capitalize())
        // Splashy
    }

    // Шаг 4: Изучите больше встроенных расширений
    // Вот некоторые из других вы могли бы найти под рукой: run(), apply()и let().
    val f = fish.run {
        name
    }
    println(f) // splashy

    val fish2 = Fish(name = "splashy").apply {
        name = "sharky"
    }
    println(fish2.name)  // sharky

    println(fish.let { it.name.capitalize()}
            .let{it + "fish"}
            .let{it.length}
            .let{it + 31})
    // 42

    println(fish)
    // Fish(name=splashy)
}
// ⇒ Splashy
//Шаг 3: Создайте функцию высшего порядка
// Внутри myWith(), block()теперь является функцией расширения String.
// Расширяемый класс часто называют объектом-получателем .
// Так name же и объект-получатель в этом случае.
fun myWith(name: String, block: String.() -> Unit) {
    // применять принятый в функции block(), к объекту приемника name.
    name.block()
}

fun main () {
    fishExamples()
}