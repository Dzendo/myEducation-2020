@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1



import java.text.DateFormat
import java.text.ParseException
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*
import java.util.Locale




/**
 * Пример ***************************** -1 *************************************
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример ***************************** -1 *************************************
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример ***************************** -1 *************************************
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод ***************************** -1 *************************************
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя ***************************** 01 *************************************
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String //= TODO()
{  // сделано два варианта 07,02,2020 оба не идеальные - нет Regexp и слабо с датой
    println(str)
    // Можно искать впрямую и when ... Сделано в самом низу
    // Можно создать List("января', ...   и затем индекс в string по формату %2d
    // Можно создать Map("января' to "01", ... и вставать на ключ сделано здесь - работает
    //

    // https://developer.android.com/reference/kotlin/java/text/SimpleDateFormat?hl=ru
    // Еще вариант по итогам разбора 2-ой задачи через  SimpleDateFormat:
    println(str)

    var df: DateFormat = SimpleDateFormat("dd MMMM yyyy")  // разобрать дату parse
    df.isLenient = false   // true дает мягкий разбор поэтому и сдвигает 29 февраля на 28 - не надо
    val pos = ParsePosition(0)  // начальная точка рабора; возврат - конечная или 0 ;errorIndex = -1 норма
    val dff = df.parse(str, pos)
    if (pos.index < str.length) return ""   // входная дата ошибочна

    df = SimpleDateFormat("dd.MM.yyyy")  // отформатировать разобранную format
    return df.format(dff)

    // Этот вариант работает
    val russMonth = mapOf<String, Int>(
        "января" to 1, "февраля" to 2, "марта" to 3, "апреля" to 4,
        "мая" to 5, "июня" to 6, "июля" to 7, "августа" to 8, "сентября" to 9,
        "октября" to 10, "ноября" to 11, "декабря" to 12
    )
    val listdata = str.split(" ").toMutableList()
    if (listdata.size < 2) return ""

    listdata[1] = russMonth[listdata[1]]?.toString() ?: return ""
    var rett = String.format("%02d.%02d.%04d", *listdata.map { it.toInt() }.toTypedArray())

    try {
        val df: DateFormat = SimpleDateFormat("dd.MM.yyyy")
        df.isLenient = false
        df.parse(rett)
    } catch (e: ParseException) {
        rett = ""
    }

    println(rett)
    return rett

    /* не знаю как решается Regexp
    val monthRus = "января|февраля|марта|апреля|мая|июня|июля|августа|сентября|октября|ноября|декабря"
    val regex = Regex("января|февраля|марта|апреля|мая|июня|июля|августа|сентября|октября|ноября|декабря")
    val reg = regex.replace(str, "-1")

    println("reg -->$reg")
    //val listint = intArrayOf(listdata)
    if (listdata.size < 2) return ""
return reg
*/

    // Можно искать впрямую и when ...
    listdata[1] = when (listdata[1]) {
        "января" -> "01"
        "февраля" -> "02"
        "марта" -> "03"
        "апреля" -> "04"
        "мая" -> "05"
        "июня" -> "06"
        "июля" -> "07"
        "августа" -> "08"
        "сентября" -> "09"
        "октября" -> "10"
        "ноября" -> "11"
        "декабря" -> "12"
        else -> return ""
    }
    //listdata[1] = monthInt
    //val dataInt = "${listdata[0]}.$monthInt.${listdata[2]}"
    // val dataInt = listdata.joinToString(".")
    //val dataInt = String.format("%02d.%02d.%04d", listdata[0].toInt(), listdata[1].toInt(), listdata[2].toInt())
    val listInt = listdata.map { it.toInt() }
    var ret = String.format("%02d.%02d.%04d", listInt[0], listInt[1], listInt[2])
    //var ret = String.format("%02d.%02d.%04d", listInt)
    var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    // var dateCheck = LocalDate.parse(ret, formatter)  // дает исключение на неверную дату - ловить

    try {
        val df: DateFormat = SimpleDateFormat("dd.MM.yyyy")
        df.isLenient = false
        df.parse(ret)

    } catch (e: ParseException) {
        ret = ""
    }

    println(" получается --> $ret-->$ ret1 ")
    return ret

/*    // Можно искать впрямую и when ... - работает правильно
    listdata[1] = when (listdata[1]) {
        "января" -> "01"
        "февраля" -> "02"
        "марта" -> "03"
        "апреля" -> "04"
        "мая" -> "05"
        "июня" -> "06"
        "июля" -> "07"
        "августа" -> "08"
        "сентября" -> "09"
        "октября" -> "10"
        "ноября" -> "11"
        "декабря" -> "12"
        else -> return ""
    }
    //listdata[1] = monthInt
    //val dataInt = "${listdata[0]}.$monthInt.${listdata[2]}"
    // val dataInt = listdata.joinToString(".")
    //val dataInt = String.format("%02d.%02d.%04d", listdata[0].toInt(), listdata[1].toInt(), listdata[2].toInt())
    val listInt = listdata.map{ it.toInt() }
    var ret = String.format("%02d.%02d.%04d", listInt[0], listInt[1], listInt[2])
    //var ret = String.format("%02d.%02d.%04d", listInt)
    var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    // var dateCheck = LocalDate.parse(ret, formatter)  // дает исключение на неверную дату - ловить

    try {
        val df: DateFormat = SimpleDateFormat("dd.MM.yyyy")
        df.isLenient = false
        df.parse(ret)

    } catch (e: ParseException) {
        ret = ""
    }

    println(" получается --> $ret-->$ ret1 ")
    return ret
*/

    var dateIn = LocalDate.parse(str)

    val c = Calendar.getInstance()
    var year: Int = 0
    var month: Int = 0
    var dayOfMonth: Int = 0

    var date = LocalDate.parse("2018-12-12")

    c.set(year, month, dayOfMonth)
    val millisecondsInADay = 24 * 60 * 60 * 1000L
    val timeInMillis = c.timeInMillis + millisecondsInADay
    val result = Calendar.getInstance()
    result.timeInMillis = timeInMillis
    return ""
    //MyDate(result.get(Calendar.YEAR), result.get(Calendar.MONTH), result.get(Calendar.DATE))

}

/**
 * Средняя ***************************** 02 *************************************
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String  //= TODO()
{
    println(digital)

    val df_input: DateFormat = SimpleDateFormat("dd.MM.yyyy")  // разобрать дату parse
    val df_output: DateFormat = SimpleDateFormat("d MMMM yyyy")  // отформатировать разобранную format: d - подавить 0
    df_input.isLenient = false   // true дает мягкий разбор поэтому и сдвигает 29 февраля на 28 - не надо
    val pos = ParsePosition(0)  // начальная точка рабора; возврат - конечеая или 0 ;errorIndex = -1
    val dff = df_input.parse(digital, pos)
    println(" pos = ${pos.index} error = ${pos.errorIndex}")
    if (pos.index < digital.length) return ""

    val rez_ru_ru = df_output.format(dff)
    println(rez_ru_ru)
    return rez_ru_ru


    // работает но доделать 03 и 29 февраля
    val dateString = digital //"2015-05-15"
    var rez_ru: String = ""
    try {

    } catch (e: ParseException) {
        return ""
    }
    try {

        val dd = digital
        val df: DateFormat = SimpleDateFormat("dd.MM.yyyy")
        df.isLenient = false
        df.parse(dd)

        val date_ru = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        rez_ru = date_ru.format(DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru")))
    } catch (e: DateTimeParseException) {
        return ""
    } catch (e: ParseException) {
        return ""
    }


    println(rez_ru)
    return rez_ru

    // надо бы дочитать SimpleDateFormat 29 февраля сдвигает
    val parts_new = digital.split(".")
    if (parts_new.size != 3) return ""
    if (parts_new.any { it.toInt() == 0 }) return ""

    val oldDateString = digital //"2015-05-15"
    val oldDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val newDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    oldDateFormat.isLenient = false
    newDateFormat.isLenient = false
    val date = oldDateFormat.parse(oldDateString)
    val result = newDateFormat.format(date)
    println(result)
    return result


// это работает но не смог найти готовый объект для этого перевода
    val russMonth = listOf<String>(
        "января",
        "февраля",
        "марта",
        "апреля",
        "мая",
        "июня",
        "июля",
        "августа",
        "сентября",
        "октября",
        "ноября",
        "декабря"
    )
    try {
        val df: DateFormat = SimpleDateFormat("dd.MM.yyyy")
        df.isLenient = false
        df.parse(digital)
    } catch (e: ParseException) {
        return ""
    }
    val parts = digital.split(".")
    if (parts.size != 3) return ""
    val rett = String.format("%d %s %d", parts[0].toInt(), russMonth[parts[1].toInt() - 1], parts[2].toInt())
    println(rett)
    return rett
}

/**
 * Средняя ***************************** 03 *************************************
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String //= TODO()
{  // работает верно 10,02,2020
    println(phone)

    val phone1 = phone.replace("[ ()-]".toRegex(), "")
    if (!phone1.matches("^[+\\d]\\d*$".toRegex())) return ""
    println(phone1)
    return phone1
}


/**
 * Средняя ***************************** 04 *************************************
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int //= TODO()
{
    // работает правильно: но грустно
    println(jumps)
    val sjump = jumps.replace("  ", " ").split(" ")
    var max = -1
    for1@ for (rez in sjump)
        when (rez) {
            "-" -> continue@for1
            "%" -> continue@for1
            else -> try {
                if (rez.toInt() > max) max = rez.toInt()
                continue@for1
            } catch (e: NumberFormatException) {
                return -1
            }
        }
    println(max)
    return max
}

/**
 * Сложная ***************************** 05 *************************************
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int //= TODO()
{
    // Решена работает правильно 10,02,2020 но хорошо бы Regex
    println(jumps)
    var max1 = -1
    val sjump1 = jumps.trim().replace("  ", " ").split(" ")
    //println(sjump1)
    for (jj in sjump1.indices step 2) {
        if (jj + 1 > sjump1.size - 1) return -1
        if (!sjump1[jj + 1].contains("+")) continue
        try {
            if (sjump1[jj].toInt() > max1) max1 = sjump1[jj].toInt()
        }catch (e:NumberFormatException) {return -1}
    }
    println(max1)
    return max1

    // решена но просто неправильно - другая задача:
    val jump1 = jumps.replace("[+%-]".toRegex(), "")
    if (!jump1.matches("^[ |\\d]*$".toRegex())) return -1
    println(jump1)
    val sjump = jump1.trim().replace("  ", " ").split(" ")
    println(sjump)
    var max = -1
    for1@ for (rez in sjump)
        try {
            if (rez.toInt() > max) max = rez.toInt()
            continue@for1
        } catch (e: NumberFormatException) {
            return -1
        }

    println(max)
    return max
}

/**
 * Сложная ***************************** 06 *************************************
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */


fun plusMinus(expression: String): Int //= TODO()
{

    /* Как-то можно через scripts - не разобрался
    https://vike.io/ru/449937/

    val engine = ScriptEngineManager().getEngineByExtension("kts")!!
    val res = engine.eval("3 + 2 - 1")
    println(res)
    return 5
    val res1 =  engine.eval("val x = 3")
    //Assert.assertNull(res1)
    val res2 =  engine.eval("x + 2")
    //Assert.assertEquals(5, res2)
*/
    // работает 10,02,2020 правильно но уродство полное
    println(expression)
    val listExp = expression.trim().replace("  ", " ").split(" ")
        .map { it.trim() }
    val e = IllegalArgumentException("exeption")
    if (listExp[0].startsWith("+")) throw e
    var rezult = listExp[0].toInt()
    for (i in 1..listExp.size-1 step 2) {
        if (i+1 > listExp.size-1)  throw e
        if (listExp[i+1].startsWith("+")) throw e
        if (listExp[i+1].startsWith("-")) throw e
        rezult = when (listExp[i].trim()) {
            "+" -> rezult + listExp[i+1].toInt()
            "-" -> rezult - listExp[i+1].toInt()
            else -> throw e
        }
    }
    println(rezult)
    return rezult
}
/**
 * Сложная ***************************** 07 *************************************
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int //= TODO()
{  // работает верно 10,02,2020
    val listStr = str.trim().split(" ")
    for (i in 0..listStr.size - 2){
        if (listStr[i].toLowerCase() != listStr[i + 1].toLowerCase()) continue
            val a = str.indexOf(listStr[i] + " " + listStr[i + 1])
            println(a)
            return a
    }
    return -1
}
/**
 * Сложная ***************************** 08 *************************************
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String = TODO()

/**
 * Сложная ***************************** 09 *************************************
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int = TODO()

/**
 * Очень сложная ***************************** 10 *************************************
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> = TODO()
