fun printSome(item:String, vararg word:String) {
    print("$item:")
    word.forEach { el -> print("$el ") }
    println()
}

fun main() {

    val items = arrayOf(5,4,8,15,7)
    val items_list = listOf(5,4,8,15,7)
    items.forEach { el -> println(el) }
    items.forEachIndexed { index,el -> println(" $index  $el") }

    items_list.indexOf(15)

    items_list.forEach { el -> println(el) }
    items_list.forEachIndexed { index,el -> println(" $index  $el") }

    var user= mapOf("name" to "Vasa", "age" to "12")
    user.forEach {key, value -> println("$key  ->  $value")}
    // Any - любой тип можно везде
    val names = arrayOf("Bob", "John", "Alex")
    printSome("Hi","Some","htllo")
    printSome("Hii",*names)

}