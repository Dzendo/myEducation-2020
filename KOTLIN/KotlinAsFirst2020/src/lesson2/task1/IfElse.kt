@file:Suppress("UNUSED_PARAMETER")

package lesson2.task1

import lesson1.task1.discriminant
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sqrt

/**
 * Пример ***************************** -1 *************************************
 *
 * Найти число корней квадратного уравнения ax^2 + bx + c = 0
 */
fun quadraticRootNumber(a: Double, b: Double, c: Double): Int {
    val discriminant = discriminant(a, b, c)
    return when {
        discriminant > 0.0 -> 2
        discriminant == 0.0 -> 1
        else -> 0
    }
}

/**
 * Пример ***************************** -1 *************************************
 *
 * Получить строковую нотацию для оценки по пятибалльной системе
 */
fun gradeNotation(grade: Int): String = when (grade) {
    5 -> "отлично"
    4 -> "хорошо"
    3 -> "удовлетворительно"
    2 -> "неудовлетворительно"
    else -> "несуществующая оценка $grade"
}

/**
 * Пример ***************************** -1 *************************************
 *
 * Найти наименьший корень биквадратного уравнения ax^4 + bx^2 + c = 0
 */
fun minBiRoot(a: Double, b: Double, c: Double): Double {
    // 1: в главной ветке if выполняется НЕСКОЛЬКО операторов
    if (a == 0.0) {
        if (b == 0.0) return Double.NaN // ... и ничего больше не делать
        val bc = -c / b
        if (bc < 0.0) return Double.NaN // ... и ничего больше не делать
        return -sqrt(bc)
        // Дальше функция при a == 0.0 не идёт
    }
    val d = discriminant(a, b, c)   // 2
    if (d < 0.0) return Double.NaN  // 3
    // 4
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    val y3 = max(y1, y2)       // 5
    if (y3 < 0.0) return Double.NaN // 6
    return -sqrt(y3)           // 7
}

/**
 * Простая ***************************** 01 *************************************
 *
 * Мой возраст. Для заданного 0 < n < 200, рассматриваемого как возраст человека,
 * вернуть строку вида: «21 год», «32 года», «12 лет».
 */

// Ok
fun ageDescription(age: Int): String =
    when {
        age % 100 in 11..14 -> "$age лет"
        age % 10 == 1 -> "$age год"
        age % 10 in 2..4 -> "$age года"
        else -> "$age лет"
    }

// как-то можно короче !!! потом
fun ageDescription2(age: Int): String =
    when {
        age % 100 in 11..14 -> "$age лет"
        age.toString().reversed()[0] == '1' -> "$age год"
        age.toString().reversed()[0] in '2'..'4' -> "$age года"
        else -> "$age лет"
    }
// как-то можно короче !!! потом
fun ageDescription1(age: Int): String =
    if (age % 100 in 11..14) "$age лет"
    else if (age % 10 == 1) "$age год"
    else if (age % 10 in 2..4) "$age года"
    else "$age лет"

/**
 * Простая ***************************** 02 *************************************
 *
 * Путник двигался t1 часов со скоростью v1 км/час, затем t2 часов — со скоростью v2 км/час
 * и t3 часов — со скоростью v3 км/час.
 * Определить, за какое время он одолел первую половину пути?
 */
//  работает, короче и функцию не могу придумать выставить
fun timeForHalfWay(
    t1: Double, v1: Double,
    t2: Double, v2: Double,
    t3: Double, v3: Double
): Double = when {
    (ss(t1, v1) + ss(t2, v2) + ss(t3, v3)) / 2.0 <= ss(t1, v1) -> (ss(t1, v1) + ss(t2, v2) + ss(t3, v3)) / 2.0 / v1
    (ss(t1, v1) + ss(t2, v2) + ss(t3, v3)) / 2.0 <= ss(t1, v1) + ss(t2, v2)
        -> t1 + ((ss(t1, v1) + ss(t2, v2) + ss(t3, v3)) / 2.0 - ss(t1, v1)) / v2
    else -> t1 + t2 + ((ss(t1, v1) + ss(t2, v2) + ss(t3, v3)) / 2.0 - ss(t1, v1) - ss(t2, v2)) / v3
}
fun ss(t1: Double, v1: Double): Double = t1 * v1

//  работает наиболее понятна НО не функция
fun timeForHalfWay3(
    t1: Double, v1: Double,
    t2: Double, v2: Double,
    t3: Double, v3: Double
): Double {
    val s1 = t1 * v1
    val s2 = t2 * v2
    val s3 = t3 * v3
    val hs = (s1 + s2 + s3) / 2.0
    return when {
        (hs <= s1) -> hs / v1
        (hs <= s1 + s2) -> t1 + (hs - s1) / v2
        else -> t1 + t2 + (hs - s1 - s2) / v3
    }
}

//  работает
fun timeForHalfWay2(
    t1: Double, v1: Double,
    t2: Double, v2: Double,
    t3: Double, v3: Double
): Double {
    val s1 = t1 * v1
    val s2 = t2 * v2
    val s3 = t3 * v3
    val hs = (s1 + s2 + s3) / 2.0
    if (hs <= s1) return hs / v1
    if (hs <= s1 + s2) return t1 + (hs - s1) / v2
    return t1 + t2 + (hs - s1 - s2) / v3
}

// Дурдом а попроще нельзя ????
fun timeForHalfWay1(
    t1: Double, v1: Double,
    t2: Double, v2: Double,
    t3: Double, v3: Double
): Double = if ((t1 * v1 + t2 * v2 + t3 * v3) / 2 <= t1 * v1) (t1 * v1 + t2 * v2 + t3 * v3) / 2 / v1
else if ((t1 * v1 + t2 * v2 + t3 * v3) / 2 <= t1 * v1 + t2 * v2) t1 + ((t1 * v1 + t2 * v2 + t3 * v3) / 2 - t1 * v1) / v2
else t1 + t2 + ((t1 * v1 + t2 * v2 + t3 * v3) / 2 - t1 * v1 - t2 * v2) / v3


/*   run {

}*/

/**
 * Простая ***************************** 03 *************************************
 *
 * Нa шахматной доске стоят черный король и две белые ладьи (ладья бьет по горизонтали и вертикали).
 * Определить, не находится ли король под боем, а если есть угроза, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от первой ладьи, 2, если только от второй ладьи,
 * и 3, если угроза от обеих ладей.
 * Считать, что ладьи не могут загораживать друг друга
 */
// Не супер
fun whichRookThreatens(
    kingX: Int, kingY: Int,
    rookX1: Int, rookY1: Int,
    rookX2: Int, rookY2: Int
): Int = when {
    (kingX == rookX1 || kingY == rookY1)
            && (kingX == rookX2 || kingY == rookY2) -> 3
    kingX == rookX1 || kingY == rookY1 -> 1
    kingX == rookX2 || kingY == rookY2 -> 2
    else -> 0
}

// дурдом длинно
fun whichRookThreatens1(
    kingX: Int, kingY: Int,
    rookX1: Int, rookY1: Int,
    rookX2: Int, rookY2: Int
): Int = if (((kingX == rookX1) || (kingY == rookY1)) && ((kingX == rookX2) || (kingY == rookY2))) 3
else if ((kingX == rookX1) || (kingY == rookY1)) 1
else if ((kingX == rookX2) || (kingY == rookY2)) 2
else 0

/**
 * Простая ***************************** 04 *************************************
 *
 * На шахматной доске стоят черный король и белые ладья и слон
 * (ладья бьет по горизонтали и вертикали, слон — по диагоналям).
 * Проверить, есть ли угроза королю и если есть, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от ладьи, 2, если только от слона,
 * и 3, если угроза есть и от ладьи и от слона.
 * Считать, что ладья и слон не могут загораживать друг друга.
 */
// 3 - Не работает эксп попробовать вх парам в списки загнать

// Ок
fun rookOrBishopThreatens(
    kingX: Int, kingY: Int,
    rookX: Int, rookY: Int,
    bishopX: Int, bishopY: Int
): Int = when {
    (kingX == rookX || kingY == rookY) && abs(kingX - bishopX) == abs(kingY - bishopY) -> 3
    kingX == rookX || kingY == rookY -> 1
    abs(kingX - bishopX) == abs(kingY - bishopY) -> 2
    else -> 0
}

fun rookOrBishopThreatens1(
    kingX: Int, kingY: Int,
    rookX: Int, rookY: Int,
    bishopX: Int, bishopY: Int
): Int = if (((kingX == rookX) || (kingY == rookY)) && (abs(kingX - bishopX) == abs(kingY - bishopY))) 3
else if ((kingX == rookX) || (kingY == rookY)) 1
else if (abs(kingX - bishopX) == abs(kingY - bishopY)) 2
else 0

/**
 * Простая ***************************** 05 *************************************
 *
 * Треугольник задан длинами своих сторон a, b, c.
 * Проверить, является ли данный треугольник остроугольным (вернуть 0),
 * прямоугольным (вернуть 1) или тупоугольным (вернуть 2).
 * Если такой треугольник не существует, вернуть -1.
 */
//Ок
fun triangleKind(a: Double, b: Double, c: Double): Int =
    when {
        a > b + c || b > a + c || c > a + b -> -1    // это не треугольник
        (c * c < a * a + b * b) && (a * a < b * b + c * c) && (b * b < a * a + c * c) -> 0  // остро
        c * c > a * a + b * b || a * a > b * b + c * c || b * b > a * a + c * c -> 2  // тупо
        else -> 1  // прямо
    }

fun triangleKind1(a: Double, b: Double, c: Double): Int =
    if ((a > b + c) || (b > a + c) || (c > a + b)) -1
    else if ((c * c < a * a + b * b) && (a * a < b * b + c * c) && (b * b < a * a + c * c)) 0
    else if ((c * c > a * a + b * b) || (a * a > b * b + c * c) || (b * b > a * a + c * c)) 2
    else 1
//Есть идеи по красивее? Slacke 16.02.2020
/**
 *  Средняя ***************************** 06 *************************************
 *
 * Даны четыре точки на одной прямой: A, B, C и D.
 * Координаты точек a, b, c, d соответственно, b >= a, d >= c.
 * Найти длину пересечения отрезков AB и CD.
 * Если пересечения нет, вернуть -1.
 */
// ok
fun segmentLength(a: Int, b: Int, c: Int, d: Int): Int = when {
    c > b || d < a -> -1
    c > a && d < b -> abs(d - c)
    d > b && c < a -> abs(a - b)
    d > b -> abs(b - c)
    c < a -> abs(d - a)
    else -> 0
}

fun segmentLength1(a: Int, b: Int, c: Int, d: Int): Int =
    if ((c > b) || (d < a)) -1
    else if ((c > a) && (d < b)) abs(d - c)
    else if ((d > b) && (c < a)) abs(a - b)
    else if (d > b) abs(b - c)
    else if (c < a) abs(d - a )
    else 0
