import java.util.*

const val MAX_NUMBER_BOOKS = 20

object Constants {
    const val BASE_URL = "http://www.turtlecare.net/"
}

class Book (val title: String, val author: String, val year: Int, var pages:Int) {

    fun getTitleAuthor(): Pair<String, String> {
        return (title to author)
    }

    fun getTitleAuthorYear(): Triple<String, String, Int> {
        return Triple(title, author, year)
    }

    fun canBorrow(hasBooks: Int): Boolean {
        return (hasBooks >= MAX_NUMBER_BOOKS)
    }

    fun printUrl() {
        println(Constants.BASE_URL + title + ".html")
    }

    companion object {
        val BOOK_URL = "http://www.turtlecare.net/"
    }
}

class Puppy() {
    fun playWithBook(book: Book) {
        book.tornPages(Random().nextInt(12))
    }
}

fun Book.weight() : Double { return (pages * 1.5) }

fun Book.tornPages(torn: Int) = if (pages >= torn) pages -= torn else pages = 0

fun main(args: Array<String>) {

    val book1 = Book("Romeon and Juliet", "William Shakespeare",1597, 768)

    val bookTitleAuthor = book1.getTitleAuthor()
    val bookTitleAuthorYear = book1.getTitleAuthorYear()

    println("Here is your book ${bookTitleAuthor.first} by ${bookTitleAuthor.second}")

    println("Here is your book ${bookTitleAuthorYear.first} " +
            "by ${bookTitleAuthorYear.second} written in ${bookTitleAuthorYear.third}")

    val allBooks = mutableSetOf("Romeon and Juliet", "Much Ado About Nothing", "Hamlet")
    val shakespeare = hashMapOf<Set<String>, String>(allBooks to "William Shakespeare")

    println(shakespeare.any {it.key.contains("Hamlet")})

    var moreBooks = hashMapOf<String, String>("Wilhem Tell" to "Schiller")
    moreBooks.getOrPut("Jungle Book") {"Kiplin"}
    moreBooks.getOrPut("Hammlet") {"Shakespeare"}
    println(moreBooks)

    book1.printUrl()

    // Create a puppy and play

    val puppy = Puppy ()
    val book = Book("Oliver Twist", "charles dickens", 1846, 347)

    while (book.pages > 0) {
        puppy.playWithBook(book)
        println("${book.pages} left in ${book.title}")
    }
    println("Sad puppy, no more pages in ${book.title}. ")


}


// listOf.reverse
// mutableList
// add, remove, contains
// subList, sum, sumBy
// hashMapOf, mutableMapOf
// getOrDefault,getOrElse


