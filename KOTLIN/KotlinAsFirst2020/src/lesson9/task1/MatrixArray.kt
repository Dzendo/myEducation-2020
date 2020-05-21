@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson9.task1
// Примерно аналогичным образом можно представить элементы матрицы в виде одного сквозного массива,
// или же в виде массива массивов.


/** ***************************** -1 *************************************
 * Ячейка матрицы: row = ряд, column = колонка
 * Эта ячейка не хранит значение
 * Она хранит только НОМЕРА будущего элемента МАТРИЦЫ
 */
data class CellArray(val row: Int, val column: Int)

/** ***************************** -1 *************************************
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface MatrixArray<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: CellArray): E

    /** ***************************** -1 *************************************
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: CellArray, value: E)
}

/**
 * Простая ***************************** 01 *************************************
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrixArray(height: Int, width: Int, e: E): MatrixArray<E> //= MatrixImplArray(height, width, e)
{
    var array: Array<Array<E>> = Array(height, { Array(width, {}) }) as Array<Array<E>>
    var ee = e

    array = Array(height,{Array(width,{})}) as Array<Array<E>>
    for (h in 0 until height) {
        // array[h] = Array(width,{}) as Array<E>
        println(h)
        for (w in 0 until width) {
            println(w)
            array[h][w] = e
        }
    }
return MatrixImplArray(height, width, e)
}

/**
 * Средняя сложность ***************************** 02 *************************************
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImplArray<E>(override val height: Int, override val width: Int, e: E) : MatrixArray<E> {
    //private var array: Array<Array<E>> = emptyArray()
    private lateinit var array: Array<Array<E>> //= arrayListOf(Array<E>(width)) as Array<Array<E>>

    init {
        array = Array(height,{Array(width,{})}) as Array<Array<E>>
        for (h in 0 until height) {
            array[h] = Array(width,{}) as Array<E>
            println(h)
            for (w in 0 until width) {
                println(w)
                array[h][w] = e
            }
        }
    }

    override fun get(row: Int, column: Int): E = array[row][column]

    override fun get(cell: CellArray): E = array[cell.row][cell.column]

    override fun set(row: Int, column: Int, value: E) {
        array[row][column] = value
    }

    override fun set(cell: CellArray, value: E) {
        array[cell.row][cell.column] = value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MatrixImpl<*>

        if (height != other.height) return false
        if (width != other.width) return false
      // if (!array.contentDeepEquals(other.array)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = height
        result = 31 * result + width
        result = 31 * result + array.contentDeepHashCode()
        return result
    }

    override fun toString(): String {
        return "MatrixImpl(height=$height, width=$width, array=${array.contentToString()})"
    }
}

