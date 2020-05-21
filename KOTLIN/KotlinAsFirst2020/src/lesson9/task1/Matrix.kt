@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson9.task1



/** ***************************** -1 *************************************
 * Ячейка матрицы: row = ряд, column = колонка
 * Эта ячейка не хранит значение
 * Она хранит только НОМЕРА будущего элемента МАТРИЦЫ
 */
data class Cell(val row: Int, val column: Int)

/** ***************************** -1 *************************************
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /** ***************************** -1 *************************************
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)
}

/**
 * Простая ***************************** 01 *************************************
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> = MatrixImpl<E>(height, width, e)
/**
 * Средняя сложность ***************************** 02 *************************************
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int, e: E) : Matrix<E> {
    var listlist: MutableList<MutableList<E>> = MutableList(height) { MutableList(width) { e } }

    override fun get(row: Int, column: Int): E = listlist[row][column]

    override fun get(cell: Cell): E = listlist[cell.row][cell.column]

    override fun set(row: Int, column: Int, value: E) {
        listlist[row][column] = value
    }

    override fun set(cell: Cell, value: E) {
        listlist[cell.row][cell.column] = value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MatrixImpl<*>) return false

        if (height != other.height) return false
        if (width != other.width) return false
        if (listlist != other.listlist) return false

        return true
    }

    override fun hashCode(): Int {
        var result = height
        result = 31 * result + width
        result = 31 * result + listlist.hashCode()
        return result
    }

    override fun toString(): String ="MatrixImpl(height=$height, width=$width, listlist=$listlist)"


}

