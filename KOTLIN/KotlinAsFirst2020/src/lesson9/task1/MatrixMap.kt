@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson9.task1

/** ***************************** -1 *************************************
 * Ячейка матрицы: row = ряд, column = колонка
 * Эта ячейка не хранит значение
 * Она хранит только НОМЕРА будущего элемента МАТРИЦЫ
 */
data class CellMap(val row: Int, val column: Int)

/** ***************************** -1 *************************************
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface MatrixMap<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: CellMap): E

    /** ***************************** -1 *************************************
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: CellMap, value: E)
}

/**
 * Простая ***************************** 01 *************************************
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrixMap(height: Int, width: Int, e: E): MatrixMap<E> = MatrixImplMap(height, width, e)

/**
 * Средняя сложность ***************************** 02 *************************************
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImplMap<E>(override val height: Int, override val width: Int, e: E) : MatrixMap<E> {
    private val map = mutableMapOf<CellMap, E>()

    override fun get(row: Int, column: Int): E = map[CellMap(row, column)]!!

    override fun get(cell: CellMap): E = map[cell]!!   // ОБРАБОТАТЬ NULL

    override fun set(row: Int, column: Int, value: E) {
        map[CellMap(row, column)] = value
    }

    override fun set(cell: CellMap, value: E) {
        map[cell] = value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MatrixImplMap<*>

        if (height != other.height) return false
        if (width != other.width) return false
        if (map != other.map) return false

        return true
    }

    override fun hashCode(): Int {
        var result = height
        result = 31 * result + width
        result = 31 * result + map.hashCode()
        return result
    }

    override fun toString(): String {
        return "MatrixImplMap(height=$height, width=$width, map=$map)"
    }
}

