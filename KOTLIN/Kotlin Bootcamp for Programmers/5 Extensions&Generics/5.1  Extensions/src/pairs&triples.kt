

fun main() {
/*
Предположим, у вас есть List рыба и функция isFreshWater()для проверки, является ли рыба пресноводной или морской рыбой.
List.partition() возвращает два списка, один с элементами, в которых находится условие true,
а другой для элементов, в которых есть условие false.
 */
    val fish = arrayListOf<String>("a","b","c")
    val twoLists = fish.partition { isFreshWater(it) }
    println("freshwater: ${twoLists.first}")
    println("saltwater: ${twoLists.second}")

    // Шаг 1: Сделайте несколько пар и троек
    //Создайте пару, связав элемент оборудования с тем, для чего он используется, а затем напечатайте значения.
    // Вы можете создать пару, создав выражение, связывающее два значения,
    // например две строки, с ключевым словом to, а затем используя .first или .second для ссылки на каждое значение.
    val equipment = "рыболовная сеть" to "ловля рыбы"
    println("${equipment.first} используется для ${equipment.second}")
    //fish net used for catching fish
    // рыболовная сеть используется для ловля рыбы

    // Создайте тройку и распечатайте ее с помощью toString(), затем преобразуйте в список с помощью toList().
    // Вы создаете тройку, используя Triple()3 значения.
    // Используйте .first, .second и .third обратиться к каждому значению.
    val numbers = Triple(6, 9, 42)
    println(numbers.toString())
    println(numbers.toList())
    // (6, 9, 42)
    // [6, 9, 42]
// В приведенных выше примерах используется один и тот же тип для всех частей пары или тройки, но это не обязательно.
// Части могут быть, например, строкой, числом или списком - даже другой парой или тройкой.

    // 4. Создайте пару, где первая часть пары сама является парой.
    val equipment2 = ("рыболовная сеть" to "ловля рыбы") to "оборудование"
    println("${equipment2.first} это ${equipment2.second}\n")
    println(equipment2.first.second)
    // (рыболовная сеть, ловля рыбы) это оборудование
    // ловля рыбы

    // Step 2: Destructure some pairs and triples
    // Уничтожьте пару и распечатайте значения.
    val equipment3 = "рыболовная сеть" to "ловля рыбы"
    val (tool, use) = equipment3
    println("$tool используется для $use")
    // рыболовная сеть используется для ловля рыбы

    // Уничтожьте тройку и напечатайте значения.
    val numbers1 = Triple(6, 9, 42)
    val (n1, n2, n3) = numbers1
    println("$n1 $n2 $n3")
    // 6 9 42

}
fun isFreshWater(name:String): Boolean = when (name) {
    "a" -> true
    "b" -> false
    "c" -> true
    else -> false
}

class Extensions {
}