@file:Suppress("UNUSED_PARAMETER")

package lesson9.task2

import lesson9.task1.Cell
import lesson9.task1.Matrix
import lesson9.task1.createMatrix
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

// Все задачи в этом файле требуют наличия реализации интерфейса "Матрица" в Matrix.kt

/**
 * Пример ***************************** -1 *************************************
 *
 * Транспонировать заданную матрицу matrix.
 * При транспонировании строки матрицы становятся столбцами и наоборот:
 *
 * 1 2 3      1 4 6 3
 * 4 5 6  ==> 2 5 5 2
 * 6 5 4      3 6 4 1
 * 3 2 1
 */
fun <E> transpose(matrix: Matrix<E>): Matrix<E> {
    if (matrix.width < 1 || matrix.height < 1) return matrix  // по-моему <=
    val result = createMatrix(height = matrix.width, width = matrix.height, e = matrix[0, 0])
    for (i in 0 until matrix.width) {
        for (j in 0 until matrix.height) {
            result[i, j] = matrix[j, i]
        }
    }
    return result
}

/**
 * Пример ***************************** -1 *************************************
 *
 * Сложить две заданные матрицы друг с другом.
 * Складывать можно только матрицы совпадающего размера -- в противном случае бросить IllegalArgumentException.
 * При сложении попарно складываются соответствующие элементы матриц
 */
operator fun Matrix<Int>.plus(other: Matrix<Int>): Matrix<Int> {
    // Создает исключение IllegalArgumentException, если значение равно false.
    require(!(width != other.width || height != other.height))
    if (width < 1 || height < 1) return this
    val result = createMatrix(height, width, this[0, 0])
    for (i in 0 until height) {
        for (j in 0 until width) {
            result[i, j] = this[i, j] + other[i, j]
        }
    }
    return result
}

/**
 * Сложная*************************** 01 ******************************************
 *
 * Заполнить матрицу заданной высоты height и ширины width
 * натуральными числами от 1 до m*n по спирали,
 * начинающейся в левом верхнем углу и закрученной по часовой стрелке.
 *
 * Пример для height = 3, width = 4:
 *  1  2  3  4
 * 10 11 12  5
 *  9  8  7  6
 */
// Ok работает правильно 25022020 16:17
fun generateSpiral(height: Int, width: Int): Matrix<Int> {
    val result = createMatrix(height, width, 0)
    var row = 0
    var column = 0
    for (nomber in 1..height * width) when {
        nomber == 1 -> result[row, column] = nomber
        column < width - 1 && result[row, column + 1] == 0 -> result[row, ++column] = nomber
        row < height - 1 && result[row + 1, column] == 0 -> result[++row, column] = nomber
        column > 0 && result[row, column - 1] == 0 -> result[row, --column] = nomber
        row > 0 && result[row - 1, column] == 0 -> result[--row, column] = nomber
    }
    return result
}

// Ok работает правильно 25022020 15:42
fun generateSpiral1(height: Int, width: Int): Matrix<Int> //= TODO()
{
    val result = createMatrix(height, width, 0)
    if (width < 1 && height < 1) return result
    println("$ height $height $ width $width")
    var step = true
    var nomber = 1
    var row = 0
    var column = 0
    result[row, column] = nomber
    while (step) {
        nomber++
        println("nomber= $nomber row = $row  column= $column result= ${result[row, column]}")
        if (column < width - 1 && result[row, column + 1] == 0) {
            result[row, column + 1] = nomber; column++; continue
        }
        if (row < height - 1 && result[row + 1, column] == 0) {
            result[row + 1, column] = nomber; row++; continue
        }
        if (column > 0 && result[row, column - 1] == 0) {
            result[row, column - 1] = nomber; column--; continue
        }
        if (row > 0 && result[row - 1, column] == 0) {
            result[row - 1, column] = nomber; row--; continue
        }

        step = false
    }
    println(result)
    return result
}

/**
 * Сложная *************************** 02 ******************************************
 *
 * Заполнить матрицу заданной высоты height и ширины width следующим образом.
 * Элементам, находящимся на периферии (по периметру матрицы), присвоить значение 1;
 * периметру оставшейся подматрицы – значение 2 и так далее до заполнения всей матрицы.
 *
 * Пример для height = 5, width = 6:
 *  1  1  1  1  1  1
 *  1  2  2  2  2  1
 *  1  2  3  3  2  1
 *  1  2  2  2  2  1
 *  1  1  1  1  1  1
 */
// OK работает 25022020 17:55
fun generateRectangles(height: Int, width: Int): Matrix<Int> {
    val result = createMatrix(height, width, 0)
    for (row in 0 until height)
        for (col in 0 until width)
            result[row, col] = arrayListOf(row + 1, col + 1, height - row, width - col).min() ?: 0
    return result
}

// OK работает 25022020 17:55
fun generateRectangles1(height: Int, width: Int): Matrix<Int> {
    val result = createMatrix(height, width, 0)
    for (row in 1..height)
        for (col in 1..width)
            result[row - 1, col - 1] = min(min(row, col), min(height - row + 1, width - col + 1))
    return result
}

/**
 * Сложная *************************** 03 ******************************************
 *
 * Заполнить матрицу заданной высоты height и ширины width диагональной змейкой:
 * в левый верхний угол 1, во вторую от угла диагональ 2 и 3 сверху вниз, в третью 4-6 сверху вниз и так далее.
 *
 * Пример для height = 5, width = 4:
 *  1  2  4  7
 *  3  5  8 11
 *  6  9 12 15
 * 10 13 16 18
 * 14 17 19 20
 */
// Работает, но недоделано - хождение по диагоналям - доделать старт-стоп
fun generateSnake2(height: Int, width: Int): Matrix<Int> //= TODO()
{
    println("height= $height width = $width ")
    val result = createMatrix(height, width, 0)
    //val height = 5
    //val width = 4
    var nomber = 0
    for (ndiag in 0..height + width - 2) // по диагоналям
        for (nndiag in 0..ndiag) {  // номер в диагонали
            val row = nndiag
            val col = ndiag - row
            println("nomber $nomber ndiag $ndiag nndiag $nndiag row  $row col $col")
            if (col<0) continue
            //if (row<0) continue
            if (row>height-1) continue
            if (col>width-1) continue
            if (row + col == ndiag) {
                println("******************nomber ${nomber+1} ndiag $ndiag nndiag $nndiag row  $row col $col")
                result[row, col] = ++nomber
                //println("nomber $nomber ndiag $ndiag nndiag $nndiag row  $row col $col")
            }
        }

    return result
}

// Ok работает совсем дуболомный вариант везде с запасом 25022020 19:00
fun generateSnake(height: Int, width: Int): Matrix<Int>
{
    val result = createMatrix(height, width, 0)
    var nomber = 0
    for (ndiag in 0..height + width - 2) // по диагоналям
        for (row in 0 until height)     // по матрице строки
            for (col in 0 until width)  // по матрице столбцы
                if (row + col == ndiag) result[row, col] = ++nomber
    return result
}

/**
 * Средняя *************************** 04 ******************************************
 *
 * Содержимое квадратной матрицы matrix (с произвольным содержимым) повернуть на 90 градусов по часовой стрелке.
 * Если height != width, бросить IllegalArgumentException.
 *
 * Пример:    Станет:
 * 1 2 3      7 4 1
 * 4 5 6      8 5 2
 * 7 8 9      9 6 3
 */
// Ok работает 25022020 23:37
fun <E> rotate(matrix: Matrix<E>): Matrix<E>
{
    require(matrix.width == matrix.height)
    val result = createMatrix(matrix.width, matrix.height, matrix[0, 0])
    for (row in 0 until matrix.height)
        for (col in 0 until matrix.width)
            result[col, matrix.width - 1 - row] = matrix[row, col]
    println(result.toString())
    return result
}

/**
 * Сложная *************************** 05 ******************************************
 *
 * Проверить, является ли квадратная целочисленная матрица matrix латинским квадратом.
 * Латинским квадратом называется матрица размером n x n,
 * каждая строка и каждый столбец которой содержат все числа от 1 до n.
 * Если height != width, вернуть false.
 *
 * Пример латинского квадрата 3х3:
 * 2 3 1
 * 1 2 3
 * 3 1 2
 */
// OK работает совсем не прямо 26022020 00:50
fun isLatinSquare(matrix: Matrix<Int>): Boolean {
    if (matrix.height != matrix.width) return false
    val n = matrix.height

    for (row in 0 until n) {
        val setRow = mutableSetOf<Int>()
        val setCol = mutableSetOf<Int>()
        for (col in 0 until n) {
            if (matrix[row, col] !in 1..n) return false
            setRow.add(matrix[row, col])
            setCol.add(matrix[col, row])
        }
        if (setRow.size != n || setCol.size != n) return false
    }
    return true
}

// OK работает не совсем прямо 25022020 23:59
fun isLatinSquare2(matrix: Matrix<Int>): Boolean
{
    if (matrix.height != matrix.width) return false
    val n = matrix.height

    for (row in 0 until n)
        for (col in 0 until n)
            if (matrix[row, col] !in 1..n) return false

    for (row in 0 until n) {
        val set = mutableSetOf<Int>()
        for (col in 0 until n)
            set.add(matrix[row, col])
        if (set.size != n) return false
    }
    for (col in 0 until n) {
        val set = mutableSetOf<Int>()
        for (row in 0 until n)
            set.add(matrix[row, col])
        if (set.size != n) return false
    }
    return true
}

// OK работает прямо в лоб тупо проверяет 26022020 00:40
fun isLatinSquare1(matrix: Matrix<Int>): Boolean
{
    if (matrix.height != matrix.width) return false
    val n = matrix.height
    var ret_all = true
    for (nn in 1..n) {
        for (row in 0 until n) {
            var ret_row = false
            var ret_col = false
            for (col in 0 until n) {
                if (matrix[row, col] == nn) ret_row = true  // find in row
                if (matrix[col, row] == nn) ret_col = true  // find in col
            }
            ret_all = ret_all && ret_col && ret_row
        }
    }
    return ret_all
}
/**
 * Средняя *************************** 06 ******************************************
 *
 * В матрице matrix каждый элемент заменить суммой непосредственно примыкающих к нему
 * элементов по вертикали, горизонтали и диагоналям.
 *
 * Пример для матрицы 4 x 3: (11=2+4+5, 19=1+3+4+5+6, ...)
 * 1 2 3       11 19 13
 * 4 5 6  ===> 19 31 19
 * 6 5 4       19 31 19
 * 3 2 1       13 19 11
 *
 * Поскольку в матрице 1 х 1 примыкающие элементы отсутствуют,
 * для неё следует вернуть как результат нулевую матрицу:
 *
 * 42 ===> 0
 */
//Ok паботает совсем прямолинейно 26022020 1:10
fun sumNeighbours(matrix: Matrix<Int>): Matrix<Int> {
    val height = matrix.height
    val width = matrix.width
    val result = createMatrix(height, width, 0)
    for (row in 0 until height)
        for (col in 0 until width) {
            if (row - 1 >= 0) result[row, col] += matrix[row - 1, col]
            if (row + 1 < height) result[row, col] += matrix[row + 1, col]
            if (col - 1 >= 0) result[row, col] += matrix[row, col - 1]
            if (col + 1 < width) result[row, col] += matrix[row, col + 1]

            if (row - 1 >= 0 && col - 1 >= 0) result[row, col] += matrix[row - 1, col - 1]
            if (row - 1 >= 0 && col + 1 < width) result[row, col] += matrix[row - 1, col + 1]
            if (row + 1 < height && col - 1 >= 0) result[row, col] += matrix[row + 1, col - 1]
            if (row + 1 < height && col + 1 < width) result[row, col] += matrix[row + 1, col + 1]
        }
    return result
}

/**
 * Средняя *************************** 07 ******************************************
 *
 * Целочисленная матрица matrix состоит из "дырок" (на их месте стоит 0) и "кирпичей" (на их месте стоит 1).
 * Найти в этой матрице все ряды и колонки, целиком состоящие из "дырок".
 * Результат вернуть в виде Holes(rows = список дырчатых рядов, columns = список дырчатых колонок).
 * Ряды и колонки нумеруются с нуля. Любой из спискоов rows / columns может оказаться пустым.
 *
 * Пример для матрицы 5 х 4:
 * 1 0 1 0
 * 0 0 1 0
 * 1 0 0 0 ==> результат: Holes(rows = listOf(4), columns = listOf(1, 3)): 4-й ряд, 1-я и 3-я колонки
 * 0 0 1 0
 * 0 0 0 0
 */
// Ok Работает без изысков чуть чищенная 26022020 1:36
fun findHoles(matrix: Matrix<Int>): Holes {
    val rows = arrayListOf<Int>()
    for (row in 0 until matrix.height) {
        var row0 = true
        for (col in 0 until matrix.width) if (matrix[row, col] != 0) row0 = false
        if (row0) rows.add(row)
    }
    val columns = arrayListOf<Int>()
    for (col in 0 until matrix.width) {
        var col0 = true
        for (row in 0 until matrix.height) if (matrix[row, col] != 0) col0 = false
        if (col0) columns.add(col)
    }
    return Holes(rows, columns)
}

// Ok Работает без изысков 26022020 1:36
fun findHoles1(matrix: Matrix<Int>): Holes {
    val rows = mutableListOf<Int>()
    val columns = mutableListOf<Int>()
    val holes: Holes = Holes(rows, columns)

    for (row in 0 until matrix.height) {
        var row0 = true
        for (col in 0 until matrix.width){
            if (matrix[row, col] != 0) row0 = false
        }
        if (row0) rows.add(row)
    }
    for (col in 0 until matrix.width) {
        var col0 = true
        for (row in 0 until matrix.height){
            if (matrix[row, col] != 0) col0 = false
        }
        if (col0) columns.add(col)
    }
    return holes
}

/**
 * Класс для описания местонахождения "дырок" в матрице
 */
data class Holes(val rows: List<Int>, val columns: List<Int>)

/**
 * Средняя *************************** 08 ******************************************
 *
 * В целочисленной матрице matrix каждый элемент заменить суммой элементов подматрицы,
 * расположенной в левом верхнем углу матрицы matrix и ограниченной справа-снизу данным элементом.
 *
 * Пример для матрицы 3 х 3:
 *
 * 1  2  3      1  3  6
 * 4  5  6  =>  5 12 21
 * 7  8  9     12 27 45
 *
 * К примеру, центральный элемент 12 = 1 + 2 + 4 + 5, элемент в левом нижнем углу 12 = 1 + 4 + 7 и так далее.
 */
//Ok работает просто 26022020 2:19
fun sumSubMatrix(matrix: Matrix<Int>): Matrix<Int> {
    val result = createMatrix(matrix.height, matrix.width, 0)
    for (row in 0 until matrix.height)
        for (col in 0 until matrix.width)
            for (ro in 0..row)
                for (co in 0..col)
                    result[row, col] += matrix[ro, co]
return result
}

/**
 * Сложная *************************** 09 ******************************************
 *
 * Даны мозаичные изображения замочной скважины и ключа. Пройдет ли ключ в скважину?
 * То есть даны две матрицы key и lock, key.height <= lock.height, key.width <= lock.width, состоящие из нулей и единиц.
 *
 * Проверить, можно ли наложить матрицу key на матрицу lock (без поворота, разрешается только сдвиг) так,
 * чтобы каждой единице в матрице lock (штырь) соответствовал ноль в матрице key (прорезь),
 * а каждому нулю в матрице lock (дырка) соответствовала, наоборот, единица в матрице key (штырь).
 * Ключ при сдвиге не может выходить за пределы замка.
 *
 * Пример: ключ подойдёт, если его сдвинуть на 1 по ширине
 * lock    key
 * 1 0 1   1 0
 * 0 1 0   0 1
 * 1 1 1
 *
 * Вернуть тройку (Triple) -- (да/нет, требуемый сдвиг по высоте, требуемый сдвиг по ширине).
 * Если наложение невозможно, то первый элемент тройки "нет" и сдвиги могут быть любыми.
 */
// ok Работает 26022020 2:48
fun canOpenLock(key: Matrix<Int>, lock: Matrix<Int>): Triple<Boolean, Int, Int> {
    for (he in 0..lock.height - key.height)
        for (wi in 0..lock.width - key.width) {
            var danet = true
            for (hei in 0 until key.height)
                for (wid in 0 until key.width)
                    if (lock[hei + he, wid + wi] + key[hei, wid] != 1) danet = false
            if (danet) return Triple(true, he, wi)
        }
    return Triple(false, 0, 0)
}

/**
 * Простая *************************** 10 ******************************************
 *
 * Инвертировать заданную матрицу.
 * При инвертировании знак каждого элемента матрицы следует заменить на обратный
 */
// ok работает но operator ??? 26022020 2:55 Применить map forEach итд не получается
operator fun Matrix<Int>.unaryMinus(): Matrix<Int> //= this.let { t -> -t }
{ val result = createMatrix(this.height, this.width, 0)
    for (row in 0 until this.height)
        for (col in 0 until this.width)
            result[row, col] = -this[row, col]
    return result
}

// ok работает но operator ??? 26022020 2:55
/*operator fun Matrix<Int>.unaryMinus(): Matrix<Int> {
    val result = createMatrix(this.height, this.width, 0)
    for (row in 0 until this.height)
        for (col in 0 until this.width)
            result[row, col] = -this[row, col]
    return result
}*/

/**
 * Средняя *************************** 11 ******************************************
 *
 * Перемножить две заданные матрицы друг с другом.
 * Матрицы можно умножать, только если ширина первой матрицы совпадает с высотой второй матрицы.
 * В противном случае бросить IllegalArgumentException.
 * Подробно про порядок умножения см. статью Википедии "Умножение матриц".
 */
// Ok работает - перептсал формулу из википедии 26022020 3:41
operator fun Matrix<Int>.times(other: Matrix<Int>): Matrix<Int> {
    require(this.width == other.height)
    val result = createMatrix(this.height, other.width, 0)
    for (row in 0 until this.height)
        for (col in 0 until other.width)
            for (n in 0 until this.width)
                result[row, col] += this[row, n] * other[n, col]
    return result
}
/**
 * Сложная *************************** 12 ******************************************
 *
 * В матрице matrix размером 4х4 дана исходная позиция для игры в 15, например
 *  5  7  9  1
 *  2 12 14 15
 *  3  4  6  8
 * 10 11 13  0
 *
 * Здесь 0 обозначает пустую клетку, а 1-15 – фишки с соответствующими номерами.
 * Напомним, что "игра в 15" имеет квадратное поле 4х4, по которому двигается 15 фишек,
 * одна клетка всегда остаётся пустой. Цель игры -- упорядочить фишки на игровом поле.
 *
 * В списке moves задана последовательность ходов, например [8, 6, 13, 11, 10, 3].
 * Ход задаётся номером фишки, которая передвигается на пустое место (то есть, меняется местами с нулём).
 * Фишка должна примыкать к пустому месту по горизонтали или вертикали, иначе ход не будет возможным.
 * Все номера должны быть в пределах от 1 до 15.
 * Определить финальную позицию после выполнения всех ходов и вернуть её.
 * Если какой-либо ход является невозможным или список содержит неверные номера,
 * бросить IllegalStateException.
 *
 * В данном случае должно получиться
 * 5  7  9  1
 * 2 12 14 15
 * 0  4 13  6
 * 3 10 11  8
 */
// почти Ок кроме одного теста: недоделана но работает
//assert(false) { "IllegalStateException expected" }   // может быть ошибка 0,0 <--> 3,3
fun fifteenGameMoves(matrix: Matrix<Int>, moves: List<Int>): Matrix<Int> {
    println(matrix)
    println("moves= $moves")
    val result = matrix.copy()
    require(matrix.check15())

    for (mov in moves) {
        var cellm = Cell(-1, -1)
        var cell0 = Cell(-1, -1)
        for (row in 0..3)
            for (col in 0..3) {
                if (result[row, col] == mov) cellm = Cell(row, col)
                if (result[row, col] == 0) cell0 = Cell(row, col)
            }
        println("$mov = $cellm ; 0=  $cell0 ")
        require(abs(cellm.row - cell0.row) + abs(cellm.column - cell0.column) == 1)
        result[cellm] = result[cell0].also { result[cell0] = result[cellm] }
    }
    return result
}
private fun <T> Matrix<T>.copy(): Matrix<T> {
    val result = createMatrix(height, width, this[0, 0])
    for (row in 0 until height) {
        for (column in 0 until width) {
            result[row, column] = this[row, column]
        }
    }
    return result
}
private fun <Int> Matrix<Int>.check15(): Boolean {
    if (this.height != 4 || this.width != 4) return false
    val set = mutableSetOf<Int>()
    for (row in 0..3)
        for (col in 0..3) {
            val i15 = this[row, col]
           // require(i15 in 0..15)
           // if (i15 < 0 || this[row, col] > 15) return false
            set.add(this[row, col])
        }
    if (set.size != 16) return false
    return true
}

// почти Ок кроме одного теста:
//assert(false) { "IllegalStateException expected" }   // может быть ошибка 0,0 <--> 3,3
fun fifteenGameMoves1(matrix: Matrix<Int>, moves: List<Int>): Matrix<Int> {
    println(matrix)
    println("moves= $moves")
    require(matrix.height == 4 && matrix.width == 4)
    val result = createMatrix(4, 4, 0)
    val set = mutableSetOf<Int>()
    for (row in 0..3)
        for (col in 0..3) {
            require(matrix[row, col] in 0..15)
            result[row, col] = matrix[row,col]
            set.add(matrix[row, col])
        }
    require(set.size == 16)
    for (mov in moves) {
        var rowL = -1
        var colL = -1
        var row0 = -1
        var col0 = -1
        for (row in 0..3)
            for (col in 0..3) {
                if (result[row, col] == mov) { rowL = row; colL = col }
                if (result[row, col] == 0) { row0 = row; col0 = col }
            }
        println("mov= $mov rowL $rowL colL $colL   0=  row0 $row0  col0 $col0")
        require (abs(rowL - row0) + abs(colL - col0) == 1)
        val r = result[rowL, colL]
        result[rowL, colL] = result[row0, col0]
        result[row0, col0] = r
        // (result[rowL, colL], result[row0, col0]) = Pair (result[row0, col0], result[rowL, colL])
        //result[rowL, colL] = result[row0, col0].also { result[row0, col0]= result[rowL, colL] }
    }
    return result
}

/**
 * Очень сложная *************************** 13 ******************************************
 *
 * В матрице matrix размером 4х4 дана исходная позиция для игры в 15, например
 *  5  7  9  2
 *  1 12 14 15
 *  3  4  6  8
 * 10 11 13  0
 *
 * Здесь 0 обозначает пустую клетку, а 1-15 – фишки с соответствующими номерами.
 * Напомним, что "игра в 15" имеет квадратное поле 4х4, по которому двигается 15 фишек,
 * одна клетка всегда остаётся пустой.
 *
 * Цель игры -- упорядочить фишки на игровом поле, приведя позицию к одному из следующих двух состояний:
 *
 *  1  2  3  4          1  2  3  4
 *  5  6  7  8   ИЛИ    5  6  7  8
 *  9 10 11 12          9 10 11 12
 * 13 14 15  0         13 15 14  0
 *
 * Можно математически доказать, что РОВНО ОДНО из этих двух состояний достижимо из любой исходной позиции.
 *
 * Вернуть решение -- список ходов, приводящих исходную позицию к одной из двух упорядоченных.
 * Каждый ход -- это перемена мест фишки с заданным номером с пустой клеткой (0),
 * при этом заданная фишка должна по горизонтали или по вертикали примыкать к пустой клетке (но НЕ по диагонали).
 * К примеру, ход 13 в исходной позиции меняет местами 13 и 0, а ход 11 в той же позиции невозможен.
 *
 * Одно из решений исходной позиции:
 *
 * [8, 6, 14, 12, 4, 11, 13, 14, 12, 4,
 * 7, 5, 1, 3, 11, 7, 3, 11, 7, 12, 6,
 * 15, 4, 9, 2, 4, 9, 3, 5, 2, 3, 9,
 * 15, 8, 14, 13, 12, 7, 11, 5, 7, 6,
 * 9, 15, 8, 14, 13, 9, 15, 7, 6, 12,
 * 9, 13, 14, 15, 12, 11, 10, 9, 13, 14,
 * 15, 12, 11, 10, 9, 13, 14, 15]
 *
 * Перед решением этой задачи НЕОБХОДИМО решить предыдущую
 */
fun fifteenGameSolution(matrix: Matrix<Int>): List<Int> = TODO()
