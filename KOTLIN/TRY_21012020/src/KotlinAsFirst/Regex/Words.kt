package KotlinAsFirst.Regex

import java.util.*


object Words {
 // https://ru.stackoverflow.com/questions/655389/%D0%98%D1%81%D0%BF%D0%BE%D0%BB%D1%8C%D0%B7%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5-treemap-%D0%B4%D0%BB%D1%8F-%D0%BF%D0%BE%D0%B4%D1%81%D1%87%D0%B5%D1%82%D0%B0-%D0%BA%D0%BE%D0%BB%D0%B8%D1%87%D0%B5%D1%81%D1%82%D0%B2%D0%B0-%D0%B2%D1%85%D0%BE%D0%B6%D0%B4%D0%B5%D0%BD%D0%B8%D0%B9-%D1%81%D0%BB%D0%BE%D0%B2
    fun main(args: Array<String>) {
        var randomNumber: Int
        val cons = System.console()
       // val list0: MutableMap<Int, Word> = TreeMap()
        val list: MutableMap<Any, String> = TreeMap()
        val generator = Random()
        val myText = cons.readLine()
        for (word in myText.split(" ".toRegex()).toTypedArray()) {
            randomNumber = generator.nextInt(100001)
          //  list0[list0.getOrDefault(0, word) + 1] = word
            list[list.getOrDefault(0, word) + 1] = word
          /*  val oldCount: Int = wordToCount.getOrDefault(word, 0)
            val newCount = oldCount + 1
            wordToCount.put(word, newCount)
          */
        }
    }

    internal class Word(count: Int, word: String) {
        var count = 0
        var word: String

        init {
            this.count = count
            this.word = word
        }
    }
}