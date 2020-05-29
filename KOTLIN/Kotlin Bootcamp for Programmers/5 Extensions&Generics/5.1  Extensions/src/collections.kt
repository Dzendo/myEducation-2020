

fun main() {
    val list = listOf(1, 5, 3, 4)
    println(list.sum())
    //13

    val list2e = listOf("a", "bbb", "cc")
   // println(list2e.sum())
// error: none of the following functions can be called with the arguments supplied:

    val list2 = listOf("a", "bbb", "cc")
    println(list2.sumBy { it.length })
    //6

    //list2.
    // Примечание. Чтобы увидеть функциональность класса, создайте объект в IntelliJ IDEA,
    // добавьте точку после имени, а затем посмотрите на список автозаполнения во всплывающей подсказке.
    // Это работает для любого объекта.

// Выберите listIterator() из списка, затем просмотрите список с for оператором
// и распечатайте все элементы, разделенные пробелами.
    val list3 = listOf("a", "bbb", "cc")
    for (s in list3.listIterator()) {
        println("$s ")
    }
    // a bbb cc

// Шаг 2: Попробуйте хеш-карты
    // Создайте хэш-карту, которая соответствует симптомам, ключам и болезням рыб, значениям.
    val cures = hashMapOf("white spots" to "Ich", "red sores" to "hole disease")
    // Затем вы можете получить значение заболевания на основе ключа симптома,
    // используя get() или даже более короткие квадратные скобки [].
    println(cures.get("white spots"))
    //⇒ Ich
    println(cures["red sores"])
    //⇒ болезнь отверстия
// Попробуйте указать симптом, которого нет на карте.
    println(cures["scale loss"])
    //⇒ ноль

// Попробуйте найти ключ, который не соответствует, используя getOrDefault().
    println(cures.getOrDefault("bloating", "sorry, I don't know"))
    // sorry, I don't know
// Измените свой код, чтобы использовать getOrElse() вместо getOrDefault().
    println(cures.getOrElse("bloating") {"No cure for this"})
    // No cure for this - нет лекарства от этого

// Создайте карту инвентаря, которую можно изменить, сопоставив строку оборудования с количеством предметов.
// Создайте его с сеткой из рыбы, затем добавьте 3 аквариума в инвентарь put()и удалите сеть из рыбы remove().
    val inventory = mutableMapOf("fish net" to 1)
    inventory.put("tank scrubber", 3)
    println(inventory.toString())
    inventory.remove("fish net")
    println(inventory.toString())
    // {fish net=1, tank scrubber=3}
    // {tank scrubber=3}
    // {сетка для рыбы = 1, очиститель бака = 3} {очиститель бака = 3}



}