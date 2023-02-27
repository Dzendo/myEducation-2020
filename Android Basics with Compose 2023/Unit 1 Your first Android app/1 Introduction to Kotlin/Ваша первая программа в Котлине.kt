https://developer.android.com/training/kotlinplayground

fun main() {
    println("Hello, world!")
}

верблюжьем регистре
calculateTip
displayErrorMessage
takePhoto

fun main() {
    println("Hello, Android!")
}
Hello, Android!

fun main() {
    println("Hello, DzenDO!")
    println("Hello, Android!")
}

Имена функций должны быть в верблюжьем регистре и должны быть глаголами или глагольными фразами.
Каждое утверждение должно быть в отдельной строке.
Открывающая фигурная скобка должна появиться в конце строки, где начинается функция.
Перед открывающей фигурной скобкой должен быть пробел.
Текст функции должен иметь отступ в 4 пробела.
     Не используйте символ табуляции для отступа вашего кода, введите через 4 пробела.
Закрывающая фигурная скобка находится на отдельной строке после последней строки кода в теле функции. Закрывающая фигурная скобка должна совпадать с fun ключевым словом в начале функции.

Все исходные файлы должны быть закодированы как UTF-8.
.ktФайл содержит следующее, в порядке:
Заголовок об авторских правах и / или лицензии (необязательно)
Аннотации на уровне файлов
Инструкция по пакету
Импорт инструкций
Объявления верхнего уровня
Ровно одна пустая строка разделяет каждый из этих разделов.

fun main() {
    println("1")
    println("2")
    println("3")
}

fun main() {
    println("I'm")
    println("learning")
    println("Kotlin!")
}

fun main() {
      println("Monday")
    println("Tuesday")
    println("Wednesday")
    println("Thursday")
    println("Friday")
}

fun main() {
    println("Tomorrow is rainy")
}

fun main() {
    println("There is a chance of snow")
}

fun main() {
    println("Cloudy"); println("Partly Cloudy"); println("Windy")
}

fun main() {
    println("How's the weather today?")
}

**********************************************
10. Решения
Вывод программы:

1
2
3
Код в вашей программе должен выглядеть следующим образом:

fun main() {
    println("I'm")
    println("learning")
    println("Kotlin!")
}
Это правильный код для программы:

fun main() {
    println("Monday")
    println("Tuesday")
    println("Wednesday")
    println("Thursday")
    println("Friday")
}
Закрывающая фигурная скобка, которая указывает конец тела функции для mainфункции, отсутствует в третьей строке программы.
Правильный код:


fun main() {
    println("Tomorrow is rainy")
}
Вывод:


Tomorrow is rainy
При запуске программы вы увидите Unresolved reference: printLineсообщение об ошибке. Это потомуprintLine(), что это не распознанная функция в Kotlin. Вы также можете увидеть часть кода, вызывающую ошибку, выделенную красным цветом на игровой площадке Kotlin. Измените имя функции printlnна, чтобы напечатать строку текста на выходе, что исправляет ошибку.
Правильный код:


fun main() {
    println("There is a chance of snow")
}
Вывод:


There is a chance of snow
При запуске программы вы увидите Unresolved reference: printlnсообщение об ошибке. В этом сообщении прямо не говорится, как устранить проблему. Иногда это может произойти при устранении неполадок ошибки, и это требует более глубокого изучения кода для устранения неожиданного поведения.
При ближайшем рассмотрении второй println()вызов функции в коде окрашен в красный цвет, что сигнализирует о том, в чем проблема. Kotlin ожидает только одного оператора в каждой строке. В этом случае вы можете переместить вызовы второй и третьей println()функций в отдельные новые строки, чтобы решить проблему.

Правильный код:


fun main() {
    println("Cloudy")
    println("Partly Cloudy")
    println("Windy")
}
Вывод:


Cloudy
Partly Cloudy
Windy
Если вы запустите программу, вы увидите сообщение об ошибке: Function 'main' must have a body. Тело функции должно быть заключено в открывающую фигурную скобку и закрывающую фигурную скобку { }, а не в открывающие и закрывающие круглые скобки ( ).
Правильный код:


fun main() {
    println("How's the weather today?")
}
Вывод:


How's the weather today














