@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import kotlin.math.sqrt

// Является ли число простым:
fun isPrime(n: Int) = n >= 2 && (2..n/2).all { n % it != 0 }
/*Функция высшего порядка all в данном примере вызывается для получателя-интервала: 2..n/2.
Применима она и для списка, как и для любого другого объекта, элементы которого можно перебрать с помощью for.
Функция all имеет логический результат и возвращает true, если функция-аргумент возвращает true для ВСЕХ элементов списка.
Тип параметра функции-аргумента совпадает с типом элементов списка, тип результата — Boolean
*/
fun isNotPrime(n: Int) = n < 2 || (2..n/2).any { n % it == 0 }
//Функция высшего порядка any возвращает true, если функция-аргумент возвращает true ХОТЯ БЫ для одного элемента списка.

// кваадраты чисел в списке:
fun squares_(list: List<Int>) = list.map { it * it }



/**
 * Пример ***************************** -1 *************************************
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
    when {
        y < 0 -> listOf()
        y == 0.0 -> listOf(0.0)
        else -> {
            val root = sqrt(y)
            // Результат!
            listOf(-root, root)
        }
    }

/**
 * Пример ***************************** -1 *************************************
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример ***************************** -1 *************************************
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример ***************************** -1 *************************************
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример ***************************** -1 *************************************
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример ***************************** -1 *************************************
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример ***************************** -1 *************************************
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример ***************************** -1 *************************************
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая ***************************** 01 *************************************
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
//fun abs(v: List<Double>): Double = sqrt(v.foldRight(0.0){ total, next -> total * total + next })
fun abs(v: List<Double>): Double = sqrt(v.sumByDouble { it * it })

/**
 * Простая ***************************** 02 *************************************
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double = if (list.isEmpty()) 0.0 else list.average()

/**
 * Средняя ***************************** 03 *************************************
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val sred = list.average()
    for (i in 0 until list.size)
        list[i] = list[i] - sred
    return list
}

/**
 * Средняя ***************************** 04 *************************************
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.
 */
fun times(a: List<Int>, b: List<Int>): Int
{
    var C = 0
    for (i in 0 until a.size) C += a[i] * b[i]
    return C
}
//??????   = a.zip(b).reduce(0.00) {total , next (a , b) -> total +a*b }
// Выставлен вопрос 2 февраля 2020
//Не работает.
//Есть кто решил эту задачку красиво в одну строчку?
//Подскажите пожалуйста.
/*
Marat Akhin  13 days ago
Для начала посмотрите, в чем разница между reduce и fold
Потом почитайте про то, какой синтаксис правильный при destructuring declaration параметра у лямбды
И должно заработать :smiley:

Asdzendo  3 hours ago
Спасибо, разобрался
 */

/**
 * Средняя ***************************** 05 *************************************
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun polynom(p: List<Int>, x: Int): Int {
    var C = 0
    var xx = 1
    for (i in 0 until p.size) {
        C = C + p[i] * xx
        xx = xx * x
    }
    return C
}

/**
 * Средняя ***************************** 06 *************************************
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
// fun accumulate(list: MutableList<Int>): MutableList<Int> = TODO()
fun accumulate(list: MutableList<Int>): MutableList<Int> {
    for (i in 1 until list.size) list[i] += list[i - 1]
    return list
}

/**
 * Средняя ***************************** 07 *************************************
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    val rezultn: MutableList<Int> = mutableListOf()
    var rez = 1
    var rezRez = n
    while (rez < n) {
        for (i in 2..n) {
            if (rezRez % i == 0) {
                rez = rez * i
                rezRez = rezRez / i
                rezultn.add(i)
                break
            }
        }
    }
    return rezultn
}

/**
 * Сложная ***************************** 08 *************************************
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")

/**
 * Средняя ***************************** 09 *************************************
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    val rez: MutableList<Int> = mutableListOf()
    var rezz = n
    while (rezz != 0) {
        rez.add(rezz % base)
        rezz = rezz / base
    }
    return rez.reversed()
}

/**
 * Сложная ***************************** 10 *************************************
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, n.toString(base) и подобные), запрещается.
 */
fun convertToString(n: Int, base: Int): String = n.toString(base)  // работает но запрещено
/*{
   var rez = ""
    val list = convert(n, base)
    for (i in list.indices) {
        val li = list[i]

        when (list[i]) {
            in 0..9 -> rez = rez + li
            else -> rez = rez + li.toInt()

        }

    }
    return rez
}*/

/**
 * Средняя ***************************** 11 *************************************
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var baset = 1
    var basetold = 1
    var t = -1
    return digits.reversed()
        .sumBy { basetold = baset; baset *= base; it * basetold } // работает
        //  .sumBy { t++ ; it * base.toDouble().pow(t).toInt() } // работает
    //    .reduceIndexed { index, R, T -> R + T * base.toDouble().pow(index).toInt() }  // Работает
     //  .foldIndexed(0) { index, R, T -> R + T * base.toDouble().pow(index).toInt() }  // Работает

}


/**
 * Сложная ***************************** 12 *************************************
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, str.toInt(base)), запрещается.
 */
fun decimalFromString(str: String, base: Int): Int = str.toInt(base)   // работает но запрещено

/**
 * Сложная ***************************** 13 *************************************
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String = ArabToRomanDO(n)
// RoManiac.convert(n)   // работает неверно

//  https://fooobar.com/questions/133147/converting-integers-to-roman-numerals-java
fun ArabToRoman(arabIN: Int): String {
    var arab = arabIN
    if (arab < 1 || arab > 3999) return "Invalid Roman Number Value"
    var s = ""
    while (arab >= 1000) { s += "M";  arab -= 1000 }
    while (arab >= 900)  { s += "CM"; arab -= 900  }
    while (arab >= 500)  { s += "D";  arab -= 500  }
    while (arab >= 400)  { s += "CD"; arab -= 400  }
    while (arab >= 100)  { s += "C";  arab -= 100  }
    while (arab >= 90)   { s += "XC"; arab -= 90   }
    while (arab >= 50)   { s += "L";  arab -= 50   }
    while (arab >= 40)   { s += "XL"; arab -= 40   }
    while (arab >= 10)   { s += "X";  arab -= 10   }
    while (arab >= 9)    { s += "IX"; arab -= 9    }
    while (arab >= 5)    { s += "V";  arab -= 5    }
    while (arab >= 4)    { s += "IV"; arab -= 4    }
    while (arab >= 1)    { s += "I";  arab -= 1    }
    return s
}
fun ArabToRomanDO(arab: Int): String {  // Короче от ДО
    var arab = arab
    var roman = ""
    val Arab = listOf(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)
    val Roman = listOf("M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I")
    for (i in 0..12) {
         while (arab >= Arab[i]) {
             roman += Roman[i]
             arab -= Arab[i]
         }
    }
    return roman
}
/**
 * Очень сложная ***************************** 14 *************************************
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String = ConvertNumberInWords.convertRu(n.toLong())
// http://www.cyberforum.ru/java-j2se/thread2185481.html на java все работает на массивах и StringBuiler
object ConvertNumberInWords {
    //Define digits
    private val dg1_9 = arrayOf(
        arrayOf(
            "одна",
            "две",
            "три",
            "четыре",
            "пять",
            "шесть",
            "семь",
            "восемь",
            "девять"
        ), arrayOf("один", "два")
    ) //dg[0] - female, dg[1] - male
    private val dg10_19 = arrayOf(
        "десять",
        "одиннадцать",
        "двенадцать",
        "тринадцать",
        "четырнадцать",
        "пятнадцать",
        "шестнадцать",
        "семнадцать",
        "восемнадцать",
        "девятнадцать"
    )
    private val dg20_90 = arrayOf(
        "двадцать",
        "тридцать",
        "сорок",
        "пятьдесят",
        "шестьдесят",
        "семьдесят",
        "восемьдесят",
        "девяносто"
    )
    private val dg100 = arrayOf(
        "сто",
        "двести",
        "триста",
        "четыреста",
        "пятьсот",
        "шестьсот",
        "семьсот",
        "восемьсот",
        "девятьсот"
    )
    private val dg1000 = arrayOf("тысяча", "тысячи", "тысяч")
    private val dgM = arrayOf("миллион", "миллиона", "миллионов")
    private val dgG = arrayOf("миллиард", "миллиарда", "миллиардов")
    private val dgT = arrayOf("триллион", "триллиона", "триллионов")
    //gender 0-female,1-male
    private const val FEMALE: Byte = 0
    private const val MALE: Byte = 1
    private const val TEN = 10
    private const val HND = 100
    private const val TH = 1000
    private const val T = 1E12.toLong()
    //attribute SINGULAR and PLURAL
    private const val SINGULAR: Byte = 0
    private const val PLURAL1: Byte = 1
    private const val PLURAL2: Byte = 2
    //Function get  numbers in words in range from 0 to 999
    private fun getNumInWordsNoMore999(num: Short, gender: Byte): String {
        val res = StringBuilder(100)
        //get words for numbers from 0 to 19
        if (num.toInt() == 0) return "ноль"
        val h = num / HND //hundreds
        val nn = num % HND
        val d = nn / TEN //decimals
        val o = nn % TEN //one
        when (h) {
            0 -> { }
            else -> res.append(" ").append(dg100[h - 1])
        }
        when (d) {
            0 -> { }
            1 -> { res.append(" ").append(dg10_19[o])
                return res.toString().trim { it <= ' ' }
            }
            else -> res.append(" ").append(dg20_90[d - 2])
        }
        when (o) {
            0 -> {  }
            1 -> res.append(" ").append(dg1_9[gender.toInt()][0])
            2 -> res.append(" ").append(dg1_9[gender.toInt()][1])
            else -> res.append(" ").append(dg1_9[0][o - 1])
        }
        return res.toString().trim { it <= ' ' }
    }

    //get attribute plural2(миллионов,тысяч,миллиардов итд),plural1(миллиона,миллиарда) и SINGULAR(миллион,миллиард)
    private fun getAttributeNumber(num: Short): Byte {
        val str_num = num.toString()
        val last_number = str_num.substring(str_num.length - 1).toByte()
        var middle: Byte = 0
        if (str_num.length > 1) middle = str_num.substring(str_num.length - 2, str_num.length - 1).toByte()
        if (middle.toInt() == 1) return PLURAL2
        if (last_number.toInt() == 1) return SINGULAR
        return if (last_number in 2..4) PLURAL1 else PLURAL2
    }

    //decompose numbers to XXX XXX XXX 222 миллиона 222 тысячи 222
    private fun getTBMTH(num: Long): ShortArray {
        var num1 = num
        var number = T
        val arr = ShortArray(5)
        for (i in 4 downTo 0) {
            val n_in_digital = (num1 / number).toShort()
            arr[i] = n_in_digital
            num1 -= n_in_digital * number
            number /= TH
        }
        return arr
    }

    // collect XXX
    fun convertRu(num: Long): String {
        val res = StringBuilder(1000)
        if (num == 0L) return "ноль"
        var num_positive = num
        if (num < 0) {
            num_positive = -num
            res.append("минус ")
        }
        val arr = getTBMTH(num_positive)
        for (i in 4 downTo 0) {
            if (arr[i] > 0) {
                val attr_num = getAttributeNumber(arr[i]).toShort()
                when (i) {
                    0 -> res.append(getNumInWordsNoMore999(arr[0], MALE)) // add XXX
                    1 -> res.append(getNumInWordsNoMore999(arr[i], FEMALE))
                        .append(" ").append(dg1000[attr_num.toInt()]) //XXX+тысяча (female)
                    2 -> res.append(getNumInWordsNoMore999(arr[i], MALE))
                        .append(" ").append(dgM[attr_num.toInt()]) //XXX+миллион
                    3 -> res.append(getNumInWordsNoMore999(arr[i], MALE))
                        .append(" ").append(dgG[attr_num.toInt()]) //XXX+миллиард
                    4 -> res.append(getNumInWordsNoMore999(arr[i], MALE))           //add XXX (1000 000 000 000)
                        .append(" ").append(dgT[attr_num.toInt()]) //XXX+триллион
                }
                res.append(" ")
            }
        }
        return res.toString().trim { it <= ' ' }
    }
}


