package Kotlin

class Repository7
// keeping the constructor private to enforce the usage of getInstance
private constructor() {

    //private var users: MutableList<User>? = null
    private val users = mutableListOf<User>()

    val formattedUserNames: List<String>
        get() {
            val userNames = ArrayList<String>(users.size)
            for ((firstName, lastName) in users) {
                val name: String?

                if (lastName != null) {
                    if (firstName != null) {
                        name = "$firstName $lastName"
                    } else {
                        name = lastName
                    }
                } else if (firstName != null) {
                    name = firstName
                } else {
                    name = "Unknown"
                }
                userNames.add(name)
            }
            return userNames
        }

    init {

        val user1 = User("Jane", "")
        val user2 = User("John", null)
        val user3 = User("Anne", "Doe")

      //  users = ArrayList()
        users.add(user1)
        users.add(user2)
        users.add(user3)
    }

    fun getUsers(): List<User>? {
        return users
    }

    companion object {

        private var INSTANCE: Repository7? = null

        val instance: Repository7
            get() {
                if (INSTANCE == null) {
                    synchronized(Repository7::class.java) {
                        if (INSTANCE == null) {
                            INSTANCE = Repository7()
                        }
                    }
                }
                return INSTANCE!!
            }
    }
}