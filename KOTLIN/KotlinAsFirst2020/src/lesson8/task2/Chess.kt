@file:Suppress("UNUSED_PARAMETER")

package lesson8.task2

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

/**
 * Клетка шахматной доски. Шахматная доска квадратная и имеет 8 х 8 клеток.
 * Поэтому, обе координаты клетки (горизонталь row, вертикаль column) могут находиться в пределах от 1 до 8.
 * Горизонтали нумеруются снизу вверх, вертикали слева направо.
 */
data class Square(val column: Int, val row: Int) {
    /**
     * Пример ***************************** -1 *************************************
     *
     * Возвращает true, если клетка находится в пределах доски
     */
    fun inside(): Boolean = column in 1..8 && row in 1..8

    fun plus(): Int = column + row
    fun minus(): Int = column - row

    /**
     * Простая  ******************************** 01 *****************************************
     *
     * Возвращает строковую нотацию для клетки.
     * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
     * Для клетки не в пределах доски вернуть пустую строку
     */
    // Ok 20022020 13:17  ( здесь преоброзование чисел в буквы годится для 16 ричного преобразования)
    fun notation(): String = if (inside()) "${(96 + column).toChar()}$row" else ""
}

/**
 * Простая ******************************** 02 *****************************************
 *
 * Создаёт клетку по строковой нотации.
 * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
 * Если нотация некорректна, бросить IllegalArgumentException
 */
// ок на этом тестовом примере
fun square(notation: String): Square =
    Square(
        notation[0].minus(48).toString().toInt(),
        Character.getNumericValue(notation[1])
    )

// Ok 20022020 13:52 ( Character.getNumericValue - Java но можно и toString().toInt())
fun square1(notation: String): Square =
    Square(
        (notation[0].toInt() - 48).toChar().toString().toInt(),
        Character.getNumericValue(notation[1])
    )

/**
 * Простая ******************************** 03 *****************************************
 *
 * Определить число ходов, за которое шахматная ладья пройдёт из клетки start в клетку end.
 * Шахматная ладья может за один ход переместиться на любую другую клетку
 * по вертикали или горизонтали.
 * Ниже точками выделены возможные ходы ладьи, а крестиками -- невозможные:
 *
 * xx.xxххх
 * xх.хxххх
 * ..Л.....
 * xх.хxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: rookMoveNumber(Square(3, 1), Square(6, 3)) = 2
 * Ладья может пройти через клетку (3, 3) или через клетку (6, 1) к клетке (6, 3).
 */
// Ok 20022020 14:23
fun rookMoveNumber(start: Square, end: Square): Int = when {
    !start.inside() || !end.inside() -> throw IllegalArgumentException()
    start == end -> 0
    start.column == end.column || start.row == end.row -> 1
    else -> 2
}

/**
 * Средняя ******************************** 04 *****************************************
 *
 * Вернуть список из клеток, по которым шахматная ладья может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов ладьи см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: rookTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможен ещё один вариант)
 *          rookTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(3, 3), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          rookTrajectory(Square(3, 5), Square(8, 5)) = listOf(Square(3, 5), Square(8, 5))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
//Ok 20022020 14:37 не хорошо наверно надо по другому ( ответ предполагает только один путь)
fun rookTrajectory(start: Square, end: Square): List<Square> = when {
    !start.inside() || !end.inside() -> throw IllegalArgumentException()
    start == end -> listOf(start)
    start.column == end.column || start.row == end.row -> listOf(start, end)
    else -> listOf(start, Square(start.column, end.row), end)
}

/**
 * Простая ******************************** 05 *****************************************
 *
 * Определить число ходов, за которое шахматный слон пройдёт из клетки start в клетку end.
 * Шахматный слон может за один ход переместиться на любую другую клетку по диагонали.
 * Ниже точками выделены возможные ходы слона, а крестиками -- невозможные:
 *
 * .xxx.ххх
 * x.x.xххх
 * xxСxxxxx
 * x.x.xххх
 * .xxx.ххх
 * xxxxx.хх
 * xxxxxх.х
 * xxxxxхх.
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если клетка end недостижима для слона, вернуть -1.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Примеры: bishopMoveNumber(Square(3, 1), Square(6, 3)) = -1; bishopMoveNumber(Square(3, 1), Square(3, 7)) = 2.
 * Слон может пройти через клетку (6, 4) к клетке (3, 7).
 */
// Ok 20022020 17:00   Обратная диагональ - дополнительная к 8
fun bishopMoveNumber(start: Square, end: Square): Int = when {
    !start.inside() || !end.inside() -> throw IllegalArgumentException()
    start == end -> 0
    (start.row + start.column) % 2 + (end.row + end.column) % 2 == 1 -> -1
    (8 - start.row) + start.column == (8 - end.row) + end.column
            || start.row + start.column == end.row + end.column -> 1
    else -> 2
}

/**
 * Сложная ******************************** 06 *****************************************
 *
 * Вернуть список из клеток, по которым шахматный слон может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов слона см. предыдущую задачу.
 *
 * Если клетка end недостижима для слона, вернуть пустой список.
 *
 * Если клетка достижима:
 * - список всегда включает в себя клетку start
 * - клетка end включается, если она не совпадает со start.
 * - между ними должны находиться промежуточные клетки, по порядку от start до end.
 *
 * Примеры: bishopTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          bishopTrajectory(Square(3, 1), Square(3, 7)) = listOf(Square(3, 1), Square(6, 4), Square(3, 7))
 *          bishopTrajectory(Square(1, 3), Square(6, 8)) = listOf(Square(1, 3), Square(6, 8))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
// OK 20022020 20:00 работает полностью.ввел в Square
//    fun plus(): Int = column + row
//    fun minus(): Int = column - row
fun bishopTrajectory(start: Square, end: Square): List<Square> = when {
    !start.inside() || !end.inside() -> throw IllegalArgumentException()
    start == end                                    -> arrayListOf(start)       // start и end совпадают
    (start.plus()) % 2 + (end.plus()) % 2 == 1      -> emptyList()              // Так слон не ходит - с черного на белое
    start.plus() == end.plus()                      -> arrayListOf(start, end)  // На одной диагонали верх-лево --> низ-право
    start.minus() == end.minus()                    -> arrayListOf(start, end)  // На одной диагонали верх-раво --> низ-лево
    else                                            -> {
        val col_1 = (start.minus() + end.plus()) / 2
        val col_2 = (start.plus() + end.minus()) / 2
        val col_0 = if (col_2 in 1..8) col_2 else col_1
        val row_1 = (start.plus() - end.minus()) / 2
        val row_2 = (start.plus() + end.minus()) / 2
        val row_0 = if (row_1 in 1..8) row_1 else row_2
                                                        arrayListOf(start, Square(col_0, row_0), end)
    }
}


// OK 20022020 20:00 работает полностью. до конца уверенности нет - формулы подбором
fun bishopTrajectory1(start: Square, end: Square): List<Square> = when {
    !start.inside() || !end.inside() -> throw IllegalArgumentException()
    start == end -> arrayListOf(start)
    (start.row + start.column) % 2 + (end.row + end.column) % 2 == 1 -> emptyList()
    start.row + start.column == end.row + end.column -> arrayListOf(start, end)
    (8 - start.row) + start.column == (8 - end.row) + end.column -> arrayListOf(start, end)
    else -> {
        val col_1 = (start.column - start.row + (end.column + end.row)) / 2
        val col_2 = (start.column + start.row + (end.column - end.row)) / 2
        val col = if (col_2 in 1..8) col_2 else col_1
        val row_1 = (start.column + start.row - (end.column - end.row)) / 2
        val row_2 = (start.column + start.row + (end.column - end.row)) / 2
        val row = if (row_1 in 1..8) row_1 else row_2
        arrayListOf(start, Square(col, row), end)
    }
}

/**
 * Средняя ******************************** 07 *****************************************
 *
 * Определить число ходов, за которое шахматный король пройдёт из клетки start в клетку end.
 * Шахматный король одним ходом может переместиться из клетки, в которой стоит,
 * на любую соседнюю по вертикали, горизонтали или диагонали.
 * Ниже точками выделены возможные ходы короля, а крестиками -- невозможные:
 *
 * xxxxx
 * x...x
 * x.K.x
 * x...x
 * xxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: kingMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Король может последовательно пройти через клетки (4, 2) и (5, 2) к клетке (6, 3).
 */
// ok 20022020 22:00 работает не очень тупо с лишней печатью
fun kingMoveNumber(start: Square, end: Square): Int //=
{
    var steps = 0
    var step = start
    while (step != end) {
        step = Square(step.column + (end.column - step.column).sign, step.row + (end.row - step.row).sign)
        steps++
    }
    return steps
}

// ok 20022020 22:00 работает не очень тупо с лишней печатью
fun kingMoveNumber1(start: Square, end: Square): Int //= TODO()
{
    println((end.column - start.column))
    println((end.row - start.row))
   // val start = square("b4")
   // val end = square("g6")
    var steps = 0
    var step = start
    while (step != end) {
        var step_col = (end.column - step.column)
        if (step_col != 0)  step_col = step_col / abs(step_col)

        var step_row = (end.row - step.row)
        if (step_row != 0)  step_row = step_row / abs(step_row)
        step = Square(step.column + step_col, step.row + step_row)
        steps++
        println("steps= $steps, step=$step step_col= $step_col step_row= $step_row"  )
        if (steps > 22) break
    }
    println("приехали за $steps Шагов")
    return steps
}

/**
 * Сложная ******************************** 08 *****************************************
 *
 * Вернуть список из клеток, по которым шахматный король может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов короля см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: kingTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможны другие варианты)
 *          kingTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(4, 2), Square(5, 2), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          kingTrajectory(Square(3, 5), Square(6, 2)) = listOf(Square(3, 5), Square(4, 4), Square(5, 3), Square(6, 2))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun kingTrajectory(start: Square, end: Square): List<Square> //= TODO()
{
    val steps: MutableList<Square> = mutableListOf(start)
    var step = start
    while (step != end) {
        step = Square(step.column + (end.column - step.column).sign, step.row + (end.row - step.row).sign)
        steps.add(step)
    }
    return steps
}
/**
 * Сложная ******************************** 09 *****************************************
 *
 * Определить число ходов, за которое шахматный конь пройдёт из клетки start в клетку end.
 * Шахматный конь одним ходом вначале передвигается ровно на 2 клетки по горизонтали или вертикали,
 * а затем ещё на 1 клетку под прямым углом, образуя букву "Г".
 * Ниже точками выделены возможные ходы коня, а крестиками -- невозможные:
 *
 * .xxx.xxx
 * xxKxxxxx
 * .xxx.xxx
 * x.x.xxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: knightMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Конь может последовательно пройти через клетки (5, 2) и (4, 4) к клетке (6, 3).
 */
fun knightMoveNumber(start: Square, end: Square): Int = TODO()

/**
 * Очень сложная ******************************** 10 *****************************************
 *
 * Вернуть список из клеток, по которым шахматный конь может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов коня см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры:
 *
 * knightTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 * здесь возможны другие варианты)
 * knightTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(5, 2), Square(4, 4), Square(6, 3))
 * (здесь возможен единственный вариант)
 * knightTrajectory(Square(3, 5), Square(5, 6)) = listOf(Square(3, 5), Square(5, 6))
 * (здесь опять возможны другие варианты)
 * knightTrajectory(Square(7, 7), Square(8, 8)) =
 *     listOf(Square(7, 7), Square(5, 8), Square(4, 6), Square(6, 7), Square(8, 8))
 *
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun knightTrajectory(start: Square, end: Square): List<Square> = TODO()

