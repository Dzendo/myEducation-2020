package Book

const val MAX_NUMBER_BOOKS = 20
object Constants {
    const val BASE_URL = "http://www.turtlecare.net/"
}

class BookC ( val title:String, val author:String){
    companion object {
        val BASE_URL = "http://www.turtlecare.net/"
    }

    fun canBorrow(hasBooks: Int): Boolean {
        return (hasBooks < MAX_NUMBER_BOOKS)
    }
    fun printUrl() {
        println(Constants.BASE_URL + title + ".html")
    }

}

