@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson9.task1

/** ***************************** -1 *************************************
 * Ячейка матрицы: row = ряд, column = колонка
 * Эта ячейка не хранит значение
 * Она хранит только НОМЕРА будущего элемента МАТРИЦЫ
 */
data class Cell0(val row: Int, val column: Int)

/** ***************************** -1 *************************************
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix0<E> {
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
fun <E> createMatrix0(height: Int, width: Int, e: E): Matrix<E> = TODO()

/**
 * Средняя сложность ***************************** 02 *************************************
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl0<E> : Matrix<E> {
    override val height: Int = TODO()

    override val width: Int = TODO()

    override fun get(row: Int, column: Int): E = TODO()

    override fun get(cell: Cell): E = TODO()

    override fun set(row: Int, column: Int, value: E) {
        TODO()
    }

    override fun set(cell: Cell, value: E) {
        TODO()
    }

    override fun equals(other: Any?) = TODO()

    override fun toString(): String = TODO()
}

