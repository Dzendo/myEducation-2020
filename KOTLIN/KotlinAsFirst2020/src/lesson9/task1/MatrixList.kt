@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson9.task1

/** ***************************** -1 *************************************
 * Ячейка матрицы: row = ряд, column = колонка
 * Эта ячейка не хранит значение
 * Она хранит только НОМЕРА будущего элемента МАТРИЦЫ
 */
data class CellList(val row: Int, val column: Int)

/** ***************************** -1 *************************************
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface MatrixList<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: CellList): E = get(cell.row, cell.column)

    /** ***************************** -1 *************************************
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: CellList, value: E) = set(cell.row, cell.column, value)
}

/**
 * Простая ***************************** 01 *************************************
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrixList(height: Int, width: Int, e: E): MatrixList<E> = MatrixImplList(height, width, e)


/*{ fun <E> createMatrixList(height: Int, width: Int, e: E): MatrixList<E> //= TODO()
    val result = MatrixImplList(height, width, 100)
   // result[0, 0] = e
   // result[0, 1] = e
    // ... Конечно, здесь лучше бы написать цикл
    return result
}*/
/**
 * Средняя сложность ***************************** 02 *************************************
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImplList<E>(override val height: Int, override val width: Int, e: E) : MatrixList<E> {
    //override val height: Int = TODO() // вынесен наверх в конструктор

    //override val width: Int = TODO()  // вынесен наверх в конструктор

    private val list = mutableListOf<E>()

    init {
        for (i in 0 until height * width) {
            list.add(e)
        }
    }

    override fun get(row: Int, column: Int): E = list[row * width + column] //??

   // override fun get(cell: CellList): E = get(cell.row, cell.column) // не требуется - определен в интерфейсе

    override fun set(row: Int, column: Int, value: E) {
        list[row * width + column] = value
    }

    // override fun set(cell: CellList, value: E) = set(cell.row, cell.column, value) // не требуется - определен в интерфейсе


    override fun toString(): String
    //= "MatrixImplList(height=$height, width=$width, list=$list)" // это сгенерированный Idea
    {
        val sb = StringBuilder()
        sb.append("[")
        for (row in 0..height - 1) {
            sb.append("[")
            for (column in 0..width - 1) {
                sb.append(this[row, column])
                // Подумайте здесь про запятые и пробелы, или попробуйте использовать joinToString
            }
            sb.append("]")
        }
        sb.append("]")
        return "$sb" // or, sb.toString()

    }

    override fun equals(other: Any?): Boolean =
        other is MatrixImplList<*> &&
                height == other.height &&
                width == other.width // && elements comparison ???
    /*{   // это сгенерированный Idea
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MatrixImplList<*>

        if (height != other.height) return false
        if (width != other.width) return false
        if (list != other.list) return false

        return true
    }*/

    override fun hashCode(): Int { // это сгенерированный Idea
        var result = height
        result = 31 * result + width
        result = 31 * result + list.hashCode()

        /*var result = 5
        result = result * 31 + height
        result = result * 31 + width
        // Something for elements...  */
        return result
    }

}

