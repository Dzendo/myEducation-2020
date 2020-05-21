@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1


import java.io.File


/**
 * Пример ***************************** -1 *************************************
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile_2018(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    // не создавали bufferedReader file.readLines().
    // Она создаёт bufferedReader внутри себя и обращается к его функции readLines()
    // После чтения последней строки файл закрывается
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    var currentLineLength = 0
    fun append(word: String) {
        if (currentLineLength > 0) {
            if (word.length + currentLineLength >= lineLength) {
                writer.newLine()
                currentLineLength = 0
            } else {
                writer.write(" ")
                currentLineLength++
            }
        }
        writer.write(word)
        currentLineLength += word.length
    }
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {   // если очередная входная строка пустая
            writer.newLine()    // переписать ее в вых файл ничего не изменяя
            if (currentLineLength > 0) {    // а если при этом и не все записано то записать
                writer.newLine()            // сначала хвост, потом пустую?? - все равно это только перевод каретки 2 раза
                currentLineLength =
                    0    // и обнулить чтобы следующую набирать  // вроде хвост уже записан и только перевод каретки
            }
            continue
        }
        for (word in line.split(Regex("\\s+"))) { // разрезали пословно входую строку (непустую)
            append(word)   // и слово за словом ее добавляем в выход пока вх строка не закончится
        }                   // а коретку переводят сами слова при добавлении когда им надо
    }
    writer.close()
}

/**
 * Средняя ***************************** 01 *************************************
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 *(?=...)
Положительная проверка.
Соответствует случаю, когда содержащееся выражение, представленное здесь как ...,соответствует текущей позиции.
Но, после того как содержащееся выражение было опробовано, сравнивающий движок не продвигается далее;
остаток шаблона сравнивается далее справа от того места, где начинается утверждение.

 */
// пока окончательно правильно работает - можно выставлять 12,02,2020 17:00
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> = substrings.map { sub -> sub to
            File(inputName).readLines().sumBy { line ->
            "(?=\\Q$sub\\E)".toRegex(RegexOption.IGNORE_CASE).findAll(line).count() } }.toMap()
// правильно работает : ?= для поиска перекрытий; . -> \\Q.\\E для экранирования точки и др символов
// https://coderoad.ru/8899905/Подсчет-количества-вхождений-данной-подстроки-в-строку#48889434

fun countSubstrings2(inputName: String, substrings: List<String>): Map<String, Int> =
    // неправильно работает
    substrings.map { sub ->
        sub to File(inputName).readLines().sumBy { line ->
            line.count {line.contains(sub,true)}}
    }.toMap()

fun countSubstrings1(inputName: String, substrings: List<String>): Map<String, Int> //= TODO()
{    // это работает правильно но тупо 11,02,2020 19:15
    println(substrings)
    val rezMap: MutableMap<String, Int> = mutableMapOf()  //   substrings.map { it to 0}.toMap().toMutableMap()
    for (line in File(inputName).readLines()) {
        println(line)
        for (sub in substrings) {
            var coun = rezMap[sub] ?: 0
            line.toLowerCase().forEachIndexed { ll, c ->
                if (line.substring(ll).toLowerCase().startsWith(sub.toLowerCase())) coun++
            }
            rezMap.put(sub, coun)
        }
    }
    println(rezMap)
    return rezMap
}


/**
 * Средняя ***************************** 02 *************************************
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    for (line in File(inputName).readLines()) {
        var lineout = line
        val mathrezult ="[ЖЧШЩ][ЫЮЯ]".toRegex(RegexOption.IGNORE_CASE).findAll(line)
        mathrezult.forEach { f -> lineout = lineout.replaceRange(f.range,"${f.value[0]}${bukva(f.value[1])}") }

        writer.write(lineout)
        writer.newLine()
    }
   //writer.write("?")
    writer.close()
}
fun bukva( bukva: Char): Char =
    when(bukva){
        'Ы' -> 'И'
        'ы' -> 'и'
        'Я' -> 'А'
        'я' -> 'а'
        'Ю' -> 'У'
        'ю' -> 'у'
        else -> '@'
    }

/**
 * Средняя ***************************** 03 *************************************
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
// работает правильно и совпадает с ответом 12,02,2020 23:00
fun centerFile(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val reader = File(inputName).readLines().map { it.trim() }
    val maxLen = reader.maxBy { it.length }?.length ?: 0
    for (line in reader) {
        writer.write(line.padStart((maxLen + line.length) / 2, ' '))
        writer.newLine()
    }
    writer.close()
}

// работает правильно и совпадает с ответом 12,02,2020 23:00
fun centerFile1(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val reader = File(inputName).readLines()
    val maxLen = reader.maxBy { it.trim().length }?.trim()?.length ?: 0
    for (line in reader) {
        val linelen = line.trim().length
        val lineoutlen = linelen + (maxLen - linelen) / 2
        println (" maxlen = $maxLen linelen = $linelen $lineoutlen")
        val lineoutput: String = line.trim().padStart(lineoutlen, ' ')
        //val lineoutput2 = lineoutput1//.padEnd(maxLen, ' ')
        println(lineoutput)
        writer.write(lineoutput)
        writer.newLine()
    }
    writer.close()
}
/**
 * Сложная ***************************** 04 *************************************
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
// Работает  правильно  13,02,2020 1:47


fun alignFileByWidth(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    // Входной текст через один пробел:
    val reader = File(inputName).readLines().map { it.trim().replace("\\s+".toRegex()," ") }
    val maxLen = reader.maxBy { it.length }?.length ?: 0   // длинна максимальной строки

    for (line in reader) { // строку обрабатываю и в выходной файл ее
        val kolProb = line.sumBy { if (it == ' ') 1 else 0 }        // кол-во пробелов в строке
        val dopProb = (maxLen - line.length)                        // Сколько пробелов добавлять
        val celProb = if (kolProb == 0) 0 else dopProb / kolProb    // Сколько добавить к КАЖДОМУ пробелу
        var ostProb = if (kolProb == 0) 0 else dopProb % kolProb    // Остаток пробелов к распределению по 1
        
        writer.write( line.split(' ')  // разрезаю в List для удобства добавления
        .map{var i1 = 1; if (ostProb <= 0) i1 = 0 else ostProb--; it.padEnd(it.length + celProb + i1)}
        .joinToString (" ").trim())     // склеиваю обратно с добавленными пробелами
        // записываю готовую строку в вых файл (trim т.к. добавил ненужные пробелы к последней строке)
        writer.newLine()
    }
    //writer.write("?")  // что-бы можно было сверить результат
    writer.close()
}

// Работает правильно 13,02,2020 1:27
fun alignFileByWidth1(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val reader = File(inputName).readLines().map { it.trim() }.map {
        while (it.contains("  ")) it.replace("  ", " ")
         it  }
    val maxLen = reader.maxBy { it.length }?.length ?: 0

    for (line in reader) {
        println(line)
        val kolProb = line.sumBy { if (it == ' ') 1 else 0 }
        val dopProb = (maxLen-line.length)
        val celProb = if (kolProb == 0) 0 else dopProb / kolProb
        var ostProb = if (kolProb == 0) 0 else dopProb % kolProb
        val linesplit = line.split(' ')
        println (" maxlen = $maxLen linelen = ${line.length} kolprob= $kolProb dopProb= $dopProb celProb= $celProb ostprob= $ostProb")
        val linesplitProb = linesplit.map{var i1 = 1; if (ostProb<=0) i1=0 else ostProb--; it.padEnd(it.length+ celProb + i1)}
        val lineout = linesplitProb.joinToString (" ").trim()
        println(lineout)
        writer.write(lineout)
        writer.newLine()
    }
    //writer.write("?")
    writer.close()
}

/**
 * Средняя ***************************** 05 *************************************
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */

// 5 вариант  работает правильно  через GroupBy 14,02,2020 19:00
// Работает быстро и правильно
fun top20Words(inputName: String): Map<String, Int> =

 """[a-zа-яё]+""".toRegex().findAll(File(inputName).readText().toLowerCase()).map { it.value }
        .groupBy { it }.map{ it.key to it.value.count() }
        .sortedByDescending { (_, k) -> k }.take(20).toMap()

// 4 вариант  работает правильно - то 104 - 100 (то-то) пришлось добавить ?=
// Работает безумно долго но правильно
fun top20Words4(inputName: String): Map<String, Int> =
    """[a-zа-яё]+""".toRegex().findAll(File(inputName).readText().toLowerCase()).map{ it.value }.toSet()
        .map { println(it); it to "(?=(^|[^a-zа-яё])$it([^a-zа-яё]|$))".toRegex().findAll(File(inputName).readText().toLowerCase()).count()}
        .sortedByDescending { (slovo,kol) -> kol }.take(20).toMap()

// третий вариант  работает правильно -
// чищенный вариант 2
fun top20Words3(inputName: String): Map<String, Int> //= TODO()
{
    val mapOut: MutableMap<String, Int> = mutableMapOf()
    """[a-zа-яё]+""".toRegex().findAll(File(inputName).readText().toLowerCase())
        .forEach { math -> mapOut[math.value] = mapOut.getOrDefault(math.value, 0) + 1 }
    return mapOut.toList().sortedByDescending { (slovo,kol) -> kol }.take(20).toMap()
}

// второй вариант  работает правильно -
// переделал из убирания знаков  в чтение слов -- Убрать римские цифры тогда не надо; ;
// Уменьшил количество МАР ;
// Не надо Найти сворачивание Списка в Карту
// По-хорошнму надо переходить на потоковое чтение - вариант 3
fun top20Words2(inputName: String): Map<String, Int> //= TODO()
{
    var mapOut: MutableMap<String, Int> = mutableMapOf()
    val readStr = File(inputName).readLines().joinToString().toLowerCase()
    val Rezult = """[a-zа-яё]+""".toRegex(RegexOption.IGNORE_CASE).findAll(readStr)
    for (math in Rezult)  mapOut[math.value] = mapOut.getOrDefault(math.value, 0) + 1
    return mapOut.toList().sortedByDescending { (slovo,kol) -> kol }.take(20).toMap()
}

// первый вариант работает правильно - гавно полное не показывать 18,02,2020 22:00
fun top20Words1(inputName: String): Map<String, Int> //= TODO()
{
    var mapOut: MutableMap<String, Int> = mutableMapOf()

    var reader = File(inputName).readLines()
        reader = reader.map { it.replace("[IVX]".toRegex()," ") }  // нехорошо
       // reader = reader.map { it.replace("m/^m*(d?c{0,3}|c[dm])(1?x{0,3}|x[1c])(v?i{0,3}|i[vx])$/i".toRegex(),"") }
        reader = reader.map { it.trim().toLowerCase() }
        reader = reader.map { it.replace("[«»:*,;.(\\-)!?0-9—]".toRegex()," ") }
        reader = reader.map { it.trim().replace("\\s+".toRegex()," ") }
    val listAll: MutableList<String> = arrayListOf()
    for (line in reader) {
        if (line.isEmpty()) continue
        listAll.addAll(line.trim().split(" "))
       // println(line)
    }

    //println(listAll)
   for (slovo in listAll) {
       val kol = mapOut.getOrDefault(slovo, 0) + 1
       mapOut.put(slovo, kol)
   }
    // https://stackoverflow.com/questions/45375988/how-to-sort-linkedhashmap-by-values-in-kotlin

    val mapOut1: Map<String, Int> = mapOut.toList().sortedBy { (slovo,kol) -> -kol }.toMap()
    val mapOut3: MutableMap<String, Int> = mutableMapOf()
    var j = 0
    for (i in mapOut1) {
        mapOut3.put(i.key, i.value)
        j++
        if (j>=20) break
    }
    println(mapOut3)

    return mapOut3
}
/**
 * Средняя ***************************** 06 *************************************
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
// Первый вариант работает правильно 14,02,2020 23:00
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val dict = dictionary.map { (C,S) -> C.toLowerCase() to S.toLowerCase()}.toMap()
    val writer = File(outputName).bufferedWriter()
    val reader = File(inputName).readLines()

    for (line in reader) {
        println(line)
        var lineOut = buildString {
            for (bukva in line)
                append(dict.getOrDefault(bukva.toLowerCase(), bukva.toString().toLowerCase()))
        }
        if (line[0].isUpperCase()) lineOut = lineOut.capitalize()
        writer.write(lineOut)
        writer.newLine()
        println(lineOut)
    }
    writer.close()
}

/**
 * Средняя ***************************** 07 *************************************
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
// Первый вариант работает правильно 14,02,2020 23:00
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val reader = File(inputName).readLines()
    val readerLenth = reader.maxBy { it.length }?.length ?: 0
    val writerOut = reader.filter { it.length == readerLenth &&
        it.toLowerCase().toSet().count() == readerLenth }.joinToString(", ")
            writer.write(writerOut)
            writer.newLine()
            writer.close()
}

/**
 * Сложная ***************************** 08 *************************************
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сложная ***************************** 09 *************************************
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Фрукты
<ol>
<li>Бананы</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная ***************************** 10 *************************************
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя ***************************** 11 *************************************
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {

}


/**
 * Сложная ***************************** 12 *************************************
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}

