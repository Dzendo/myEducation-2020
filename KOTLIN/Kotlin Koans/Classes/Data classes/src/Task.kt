data class Person(val name: String, val age: Int)
fun getPeople() = listOf(Person("Alice", 29), Person("Bob", 31))
fun comparePeople() = Person("Alice", 29) == Person("Alice", 29)

