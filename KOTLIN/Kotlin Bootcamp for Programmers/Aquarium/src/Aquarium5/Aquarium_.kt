package Aquarium5

fun main(){
    val testList: List<Int> = listOf(11,12,13,14,15,16,17,18,19,20)
    println(reserveList(testList))
    // [20, 19, 18, 17, 16, 15, 14, 13, 12, 11]
    println(testList.reversed())

// В КОТЛИН есть List (типа val) и MutableList (типа var)

    val symptons = mutableListOf("white spots", "red spots", "not eating", "belly up")
    symptons.add("white fungus")
    symptons.remove("white fungus")
    symptons.contains("red") // false
    symptons.contains("red spots")  // true

    println(symptons.subList(4,symptons.size)) // [belly up]
    listOf(1, 3, 5).sum() // 9
    listOf("a", "b", "cc").sumBy { it.length } //4
    // Есть еще масса: надо набрать symptons. и листать выбирать


   // Картирование очень полезная вещь: можно сопоставить все что угодно:
    val cures:Map<String,String> = mapOf("white spots" to "Inch", "red sores" to "hole disease")
    // карта соответствует симптомам
    println(cures.get("white spots"))
    println(cures["white spots"])
    // выставить умолчание:
    println(cures.getOrDefault("bloating", "sorry I dont know"))

    cures.getOrElse("bloating") {" No cure for this"}

    // В КОТЛИН есть Map (типа val) и MutableMap (типа var)
    // неизменяемые коллекции полезны в многопоточной среде

    val inventory = mutableMapOf("fish net" to 1)
    inventory.put("tank scrubber",3)
    inventory.remove("fish net")


}

// стандартный код обычно чтобы поменять местами:

fun reserveList(list:List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for(i in 0..list.size-1) {
        result.add(list[list.size-i-1])
    }
    return result
}