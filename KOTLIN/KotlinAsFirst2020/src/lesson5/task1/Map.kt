@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

import lesson2.task1.gradeNotation

/**
 * Пример ***************************** -1 *************************************
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
    shoppingList: List<String>,
    costs: Map<String, Double>
): Double =
    shoppingList.sumByDouble { if (costs.containsKey(it)) costs.getValue(it) else 0.0 }  //ДО

/*{
    var totalCost = 0.0
    totalCost =

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}*/

/**
 * Пример ***************************** -1 *************************************
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
    phoneBook: MutableMap<String, String>,
    countryCode: String
) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример ***************************** -1 *************************************
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(text: List<String>, vararg fillerWords: String): List<String> =
    text.filter { it !in fillerWords }  // лекция 6 - vararg уже массив - список не обязательно
// text.filter { it !in setOf(*fillerWords) }  // ДО
/*{
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}*/

/**
 * Пример ***************************** -1 *************************************
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> = text.toMutableSet()     // короче от ДО
/*{
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}*/


/**
 * Простая ***************************** 01 *************************************
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val mapOut = mutableMapOf<Int, MutableList<String>>()
    grades.forEach {
        if (!mapOut.containsKey(it.value)) mapOut[it.value] = mutableListOf()
        mapOut.getValue(it.value).add(it.key)
    }
    return mapOut
}

/**
 * Простая ***************************** 02 *************************************
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean =
    a.all { b.containsKey(it.key) && it.value == b[it.key] }

/* { a.forEach { if (!b.containsKey(it.key)) return false
                if (it.value != b[it.key])  return false }  } */


/*{
    for (it in a) {
        if (!b.containsKey(it.key)) return false
        if (it.value != b[it.key])  return false
    }
    return true
}*/

/**
 * Простая ***************************** 03 *************************************
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>): Unit =
    a.keys.filter { b.keys.contains(it) && b.getValue(it) == a.getValue(it) }.forEach { a.remove(it) }

// {    val d: List<String> = a.keys.filter { b.keys.contains(it) && b.getValue(it) == a.getValue(it) }
//          d.forEach { a.remove(it) }   }


/**
 * Простая ***************************** 04 *************************************
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках.
 * В выходном списке не должно быть повторяюихся элементов,
 * т. е. whoAreInBoth(listOf("Марат", "Семён, "Марат"), listOf("Марат", "Марат")) == listOf("Марат")
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> = a.toSet().filter { b.contains(it) }

/**
 * Средняя ***************************** 05 *************************************
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(
    mapA: Map<String, String>,
    mapB: Map<String, String>
): Map<String, String> = (mapA + mapB).map { (key, value) ->
    if (mapA[key] ?: mapB[key] == mapB[key] ?: mapA[key]) key to value else key to "${mapA[key]}, ${mapB[key]}"
}.toMap()
//if (mapA[key] ?: mapB[key] == mapB[key] ?: mapA[key]) key to value else key to "${mapA[key]}, ${mapB[key]}"}.toMap()
/*        println(); println(mapA); println(mapB)   // Работает но дуболомно; чуть лучше
  val mapC: MutableMap<String, String> = (mapA + mapB).toMutableMap()
      for ((key) in mapC) {
        if (mapA[key] ?: continue != mapB[key] ?: continue) mapC[key] = "${mapA[key]}, ${mapB[key]}"
    }
    println(mapC)
    return mapC
}*/

/**
 * Средняя ***************************** 06 *************************************
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> // =
/*{
    var rez = stockPrices.toMap() // .forEach() { S:String , D:Double ->  Pair(S , D) }
    for (it in rez) {
        val rey = it.key
        var rez1 = stockPrices.map { {} }
    }


}*/
/*    stockPrices.map { it.first }.toSet().map {var sum = 0.0 ; var n = 0 ;
        for (jt in stockPrices) { if (jt.first == it) { sum += jt.second;  n++} ;
            it.put(it, sum / n) } }.toMutableMap()
*/
//   Рабочий вариант а Высшими функциями не могу - голова
{
    val rez: MutableMap<String, Double> = mutableMapOf()

    val keyss = stockPrices.map { it.first }.toSet()
    for (it in keyss) {
        var sum = 0.0
        var n = 0
        for (jt in stockPrices) {
            if (jt.first == it) {
                sum += jt.second
                n++
            }
        }
        rez.put(it, sum / n)
    }
    return rez
}


/**
 * Средняя ***************************** 07 *************************************
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? =
    stuff.filter { it.value.first == kind }.minBy { it.value.second }?.key
//  Работает в ФВП
/*  Этот вариант работает правильно
    var minCena = 32000.0
    var key: String? = null
    for (it in stuff){
        if ((it.value.first == kind) && (it.value.second < minCena))  {minCena = it.value.second ; key = it.key }
        }
    return key
}*/

/**
 * Средняя ***************************** 08 *************************************
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean =
    word.all { chars.contains(it) }
/*{  // Работает правильно
    for (a in word ) if (!chars.contains(a)) return false
    return true
}*/
/**
 * Средняя ***************************** 09 *************************************
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> =
    list.map { lt -> (lt to list.count { it == lt }) }.filter { (_, value) -> value > 1 }.toMap()

/*{  // доделал 04,02,2020 4:15

    //val llist = list.map { llist -> if (list.count{ it == llist} > 1) (llist to list.count{ it == llist }) else (llist to 1) }
   // val llist1 = list.map { llist -> (llist to list.count{ it == llist }) }
   /* val rez = mutableMapOf<String, Int>()
    for (itList in list) {
        val count = list.count{ it == itList}
        if (count > 1) rez.put(itList, count)
    }
    return rez*/
    return llist.toMap()
}*/

/**
 * Средняя ***************************** 10 *************************************
 *
 * Для заданного списка слов определить, содержит ли он анаграммы
 * (два слова являются анаграммами, если одно можно составить из второго)
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun hasAnagrams(words: List<String>): Boolean =   // OK 04022020 5:30
    words.any { word ->
        words
            .filter { it.toLowerCase() != word.toLowerCase() }
            .map { it.toLowerCase().toList().sorted() }
            .contains(word.toLowerCase().toList().sorted())
    }

//word -> words.toList().toList().contains(word.toList())}
/*{  var  z = words.any { words.map {it.toLowerCase().toList().sorted()}.contains(it.toLowerCase().toList().sorted())}
        //word -> words.toList().toList().contains(word.toList())}
    var a = words.map {it.toLowerCase().toList().sorted()}
    // работает 04042020 4:40
    for (word in words){
        for (word2 in words.filter { it.toLowerCase() != word.toLowerCase() }){
           // if (word==word2) continue
    println(word.toLowerCase().toList().sorted() + "-->" + word2.toLowerCase().toList().sorted())
            if (word.toLowerCase().toList().sorted() == word2.toLowerCase().toList().sorted()) return true
        }
    }
    return false
}*/

/**
 * Сложная ***************************** 11 *************************************
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * T/e видимо сделать карту друзей до 2 колена
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta")
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat")
 *        )
 */
fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> //= TODO()
{
    var all = friends.keys + friends.values.flatMap { it }
    println(all)

    for (each in all) {
        val friendsEach = mutableSetOf<String>()
        while (1 > 0) {
            friendsEach += friends.getValue(each)
            println(friendsEach)
            val z = friends.getValue(each)
            println(z)
        }
    }
    return friends
}

/**
 * Сложная ***************************** 12 *************************************
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int>  //=
{ //
    for (i in list.indices)
        for (j in i + 1 until list.size)
            if (list[i] + list[j] == number) return Pair(i, j)
    return Pair(-1, -1)

}

/**
 * Очень сложная ***************************** 13 *************************************
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Перед решением этой задачи лучше прочитать статью Википедии "Динамическое программирование".
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> = TODO()
