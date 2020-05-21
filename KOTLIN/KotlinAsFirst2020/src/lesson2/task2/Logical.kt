@file:Suppress("UNUSED_PARAMETER")

package lesson2.task2

import lesson1.task1.sqr
import java.time.LocalDate
import java.time.YearMonth
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

/**
 * Пример ***************************** -1 *************************************
 *
 * Лежит ли точка (x, y) внутри окружности с центром в (x0, y0) и радиусом r?
 */
fun pointInsideCircle(x: Double, y: Double, x0: Double, y0: Double, r: Double) =
    sqr(x - x0) + sqr(y - y0) <= sqr(r)

/**
 * Простая ***************************** 01 *************************************
 *
 * Четырехзначное число назовем счастливым, если сумма первых двух ее цифр равна сумме двух последних.
 * Определить, счастливое ли заданное число, вернуть true, если это так.
 */
// Ok
fun isNumberHappy(number: Int): Boolean =
    number.toString().let{ it[0].toInt() + it[1].toInt() == it[it.lastIndex].toInt() + it[it.lastIndex - 1].toInt()}

fun isNumberHappy2(number: Int): Boolean =
    number.toString()[0] + number.toString()[1].toInt() == number.toString().reversed()[0] + number.toString().reversed()[1].toInt()

fun isNumberHappy1(number: Int): Boolean =
    number % 10 + (number % 100 - number % 10) / 10 == (number % 1000 - number % 100) / 100 + (number % 10000 - number % 1000) / 1000

/**
 * Простая ***************************** 02 *************************************
 *
 * На шахматной доске стоят два ферзя (ферзь бьет по вертикали, горизонтали и диагоналям).
 * Определить, угрожают ли они друг другу. Вернуть true, если угрожают.
 * Считать, что ферзи не могут загораживать друг друга.
 */
//Ok
fun queenThreatens(x1: Int, y1: Int, x2: Int, y2: Int): Boolean =
    x1 == x2 || y1 == y2 || abs(x1 - x2) == abs(y2 - y1)


/**
 * Простая ***************************** 03 *************************************
 *
 * Дан номер месяца (от 1 до 12 включительно) и год (положительный).
 * Вернуть число дней в этом месяце этого года по григорианскому календарю.
 * Специальный Календарь КОТЛИНА не нашел - этот JAVA
 */
//Ok
fun daysInMonth(month: Int, year: Int): Int = YearMonth.of(year, month).lengthOfMonth()
//  Специальный Календарь КОТЛИНА не нашел - этот JAVA
fun daysInMonth1(month: Int, year: Int): Int = LocalDate.of(year, month, 1).lengthOfMonth()
//Пришлось воспользоваться Java, знает ли кто-нибудь календарь Kotlin спросила в Slacke 16.02.2020
/**
 * Средняя ***************************** 04 *************************************
 *
 * Проверить, лежит ли окружность с центром в (x1, y1) и радиусом r1 целиком внутри
 * окружности с центром в (x2, y2) и радиусом r2.
 * Вернуть true, если утверждение верно
 */
// неверно: именно 1 во второй а не одна в другой
fun circleInside2(
    x1: Double, y1: Double, r1: Double,
    x2: Double, y2: Double, r2: Double
): Boolean = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) <= (r2 - r1) * (r2 - r1)
// Правильно:
fun circleInside(
    x1: Double, y1: Double, r1: Double,
    x2: Double, y2: Double, r2: Double
): Boolean = sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)) <= (r2 - r1)

/**
 * Средняя ***************************** 05 *************************************
 *
 * Определить, пройдет ли кирпич со сторонами а, b, c сквозь прямоугольное отверстие в стене со сторонами r и s.
 * Стороны отверстия должны быть параллельны граням кирпича.
 * Считать, что совпадения длин сторон достаточно для прохождения кирпича, т.е., например,
 * кирпич 4 х 4 х 4 пройдёт через отверстие 4 х 4.
 * Вернуть true, если кирпич пройдёт
 */
//Ok - применил let
fun brickPasses(a: Int, b: Int, c: Int, r: Int, s: Int): Boolean =
    listOf(a, b, c).sorted().let{ it[0] <= min(r, s) && it[1] <= max(r, s)}
// ok Интересно вх параметры в лист
fun brickPasses1(a: Int, b: Int, c: Int, r: Int, s: Int): Boolean =
    listOf(a, b, c).sorted()[0] <= min(r, s) && listOf(a, b, c).sorted()[1] <= max(r, s)
