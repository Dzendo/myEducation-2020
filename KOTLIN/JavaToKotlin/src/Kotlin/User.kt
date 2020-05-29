package Kotlin

//class User(var firstName: String, var lastName: String)
data class User(var firstName: String? = null, var lastName: String?  = null)

val user1 = User("Jane", "Doe")
val user2 = User("Jane", "Doe")
val structurallyEqual = user1 == user2 // true
val referentiallyEqual = user1 === user2 // false

// usage
val jane1 = User ("Jane") // same as User("Jane", null)
val joe1 = User ("John", "Doe")
val john1 = User (firstName = "John", lastName = "Doe")

// usage
val jane2 = User (lastName = "Doe") // same as User(null, "Doe")
val john2 = User ("John", "Doe")

// Если ваша функция имеет несколько параметров,
// рассмотрите возможность использования именованных аргументов,
// поскольку они делают код более читабельным.