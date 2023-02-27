экран сведений для каждого местоположения
Имя местоположения
Звездный рейтинг местоположения
Количество отзывов местоположения
Сохранял ли пользователь (или добавляйте в закладки) это местоположение
Адрес местоположения
Описание местоположения

Тип данных Kotlin

Какие данные он может содержать

Пример буквальных значений

String   Текст   "Add contact"  "Search"  "Sign in"
Int  Целое целое число 32 1293490 -59281
Double Десятичное число 2.0 501.0292 -31723.99999
Float Десятичное число (менее точное, чем Double). Имеет f или F в конце числа. 5.0f -1630.209f 1.2940278F
Boolean true или false. true false
  Используйте этот тип данных, когда существует только два возможных значения. Обратите внимание, что true   и false это ключевые слова в Kotlin.

Имя местоположение - это текст, поэтому его можно сохранить в переменной, тип данных которой String.
Звездный рейтинг местоположение - это десятичное число (например, 4,2 звезды), поэтому его можно сохранить как Double.
Количество отзывов location - это целое число, поэтому оно должно храниться как Int.
Является ли пользователь сохранено это местоположение имеет только два возможных значения (сохраненное или не сохраненное), поэтому оно сохраняется как Boolean, где true и false может представлять каждое из этих состояний.
Адрес местоположение - это текст, поэтому оно должно быть String.
Описание местоположение также является текстом, поэтому оно должно быть String.

1 приложение YouTube:
Имя из видео (String)
Имя канала (String)
Количество просмотров на видео (Int)
Количество лайков на видео (Int)
Количество комментариев на видео (Int)

2 Gmail:
Имя отправителя (String)
Тема из электронного письма (String)
Является ли электронное письмо помечено (Boolean)
Количество новых электронных писем в папке Входящие (Int)


fun main() {
    val count: Int = 2
    println(count)
}
Имена переменных должны соответствовать соглашению camel case, как вы узнали из имен функций.
Первое слово имени переменной полностью строчное.
Если в имени несколько слов, между словами нет пробелов,
а все остальные слова должны начинаться с заглавной буквы.

fun main() {
    val count: Int = 2
    println("You have $count unread messages.")
}

fun main() {
    val count: Int = 10
    println("You have $count unread messages.")
}


fun main() {
    val unreadCount = 5
    val readCount = 100
    println("You have ${unreadCount + readCount} total messages in your inbox.")
}

fun main() {
    val numberOfPhotos = 100
    val photosDeleted = 10
    println("$numberOfPhotos photos")
    println("$photosDeleted photos deleted")
    println("${numberOfPhotos - photosDeleted} photos left")
}

fun main() {
    var cartTotal = 0
    cartTotal = 20
    println("Total: $cartTotal")
}

fun main() {
    var cartTotal = 0
    println("Total: $cartTotal")

    cartTotal = 20
    println("Total: $cartTotal")
}

fun main() {
    var count = 10
    println("You have $count unread messages.")
    count = count + 1
    println("You have $count unread messages.")
}

count = count + 1
count++
count = count - 1
count--

fun main() {
    val trip1: Double = 3.20
    val trip2: Double = 4.10
    val trip3: Double = 1.72
    val totalTripLength: Double = trip1 + trip2 + trip3
    println("$totalTripLength miles left to destination")
}

С помощью символа + вы можете сложить две строки вместе, что называется объединение.конкатенации строк
fun main() {
    val nextMeeting = "Next meeting is:"
    val date = "January 1"
    val reminder = nextMeeting + date
    println(reminder)
}

fun main() {
    val nextMeeting = "Next meeting is: "
    val date = "January 1"
    val reminder = nextMeeting + date + " at work"
    println(reminder)
}

fun main() {
    println("Say \"hello\"")
}

fun main() {
    val notificationsEnabled: Boolean = false
    println(notificationsEnabled)
}

fun main() {
    val notificationsEnabled: Boolean = false
    println("Are notifications enabled? " + notificationsEnabled)
}

Вот несколько других правил форматирования и кодирования, которым вы должны следовать, основываясь на новых темах, которые вы изучили:

Имена переменных должны быть в верблюжьем регистре и начинаться со строчной буквы.
Краткие сведения
Переменная - это контейнер для отдельного фрагмента данных.
Вы должны сначала объявить переменную, прежде чем использовать ее.
Используйте val ключевое слово для определения переменной, доступной только для чтения, значение которой не может измениться после ее присвоения.
Используйте var ключевое слово для определения переменной, которая является изменяемой или изменяемой.
В Kotlin предпочтительнее использовать val более var когда это возможно.
Чтобы объявить переменную, начните с val var или ключевое слово. Затем укажите имя переменной, тип данных и начальное значение. Например: val count: Int = 2.
При выводе типа опустите тип данных в объявлении переменной, если указано начальное значение.
Некоторые распространенные базовые типы данных Kotlin включают: Int, String, Boolean, Float, и Double.
Используйте оператор присваивания (=) присваивать значение переменной либо во время объявления переменной, либо при обновлении переменной.
Вы можете обновлять только переменную, которая была объявлена как изменяемая переменная (с var).
Используйте оператор увеличения (++) или оператор уменьшения (--) для увеличения или уменьшения значения целочисленной переменной на 1 соответственно.
Используйте + символ для объединения строк вместе. Вы также можете объединять переменные других типов данных, таких как Int и BooleanStrings.



При указании типа данных в объявлении переменной после двоеточия должен быть пробел.
На этой диаграмме показана строка кода, которая гласит: val discount: Double = .20 Между символом двоеточия и типом данных Double есть стрелка, указывающая на пробел с надписью space .

Перед и после оператора, подобного присваиванию, должен быть пробел (=), добавление (+), вычитание (-), умножение (*), разделение (/) операторы и многое другое.
На этой диаграмме показана строка кода, которая гласит: var pet = 

На этой диаграмме показана строка кода, которая гласит: val sum = 1 + 2 Перед и после символа плюса есть стрелки, указывающие на пробел, с надписью "пробел".

При написании более сложных программ рекомендуется ограничить 100 символов в строке. Это гарантирует, что вы сможете легко прочитать весь код программы на экране вашего компьютера, без необходимости прокрутки по горизонтали при чтении кода.

// This is a comment.
height = 1 // Assume the height is 1 to start with.
/*
 * This is a very long comment that can
 * take up multiple lines.
 */
/**
 * This program displays the number of messages
 * in the user's inbox.
 */
fun main() {
    // Create a variable for the number of unread messages.
    var count = 10
    println("You have $count unread messages.")

    // Decrease the number of messages by 1.
    count--
    println("You have $count unread messages.")
}








