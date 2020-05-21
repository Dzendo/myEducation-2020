package Book

import java.util.*


fun main(){
    val one = Book_5()
    val (t,a,y) = one.TripleTAY()
    println ( " Here is your book $t. written by $a in $y.")

    val puppy = Puppy()
    val book = Book("Oliver Twist", "Charles Dickens", 1837, 540)

    while (book.pages > 0) {
        puppy.playWithBook(book)
        println("${book.pages} left in ${book.title}")
    }
    println("Sad puppy, no more pages in ${book.title}. ")
}



open class Book ( val title:String, author:String,  var pages: Int = 0, private var currentPage:Int){
  //  var pages: Int = 0
   //private var currentPage = 1

    open fun readPage() { currentPage++ }

}
class eBook(title: String, author: String, var format: String = "text") : Book(title, author,100,10)  {

    private var wordsRead = 0

    override fun readPage()  {
        wordsRead = wordsRead + 250
           }
}

class Book_5 (val title: String = "Пикник на обочине", val author: String = "Стругацкие", val year : Int = 1980){

    fun PairTA() = title to author
    fun TripleTAY() = Triple (title, author, year )
}

fun Book.weight() : Double { return (pages * 1.5) }
fun Book.tornPages(torn: Int) = if (pages >= torn) pages -= torn else pages = 0
class Puppy() {
    fun playWithBook(book: Book) {
        book.tornPages(Random().nextInt(12))
    }
}


