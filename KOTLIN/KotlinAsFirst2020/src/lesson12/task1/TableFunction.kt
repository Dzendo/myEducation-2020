@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1

import java.lang.IllegalArgumentException
import kotlin.math.abs

/**
 * Класс "табличная функция".
 *
 * Общая сложность задания -- средняя.
 * Объект класса хранит таблицу значений функции (y) от одного аргумента (x).
 * В таблицу можно добавлять и удалять пары (x, y),
 * найти в ней ближайшую пару (x, y) по заданному x,
 * найти (интерполяцией или экстраполяцией) значение y по заданному x.
 *
 * Класс должен иметь конструктор по умолчанию (без параметров).
 */
class TableFunction : Throwable(IllegalArgumentException()) {


    val table: MutableMap<Double, Double> = mutableMapOf()

    /**
     * Количество пар в таблице
     */
    val size: Int get() = table.count()

    /**
     * Добавить новую пару.
     * Вернуть true, если пары с заданным x ещё нет,
     * или false, если она уже есть (в этом случае перезаписать значение y)
     */
    fun add(x: Double, y: Double): Boolean = table.put(x, y) == null

    /**
     * Удалить пару с заданным значением x.
     * Вернуть true, если пара была удалена.
     */
    fun remove(x: Double): Boolean = table.remove(x) != null


    /**
     * Вернуть коллекцию из всех пар в таблице
     */
    fun getPairs(): Collection<Pair<Double, Double>> = table.toList()

    /**
     * Вернуть пару, ближайшую к заданному x.
     * Если существует две ближайшие пары, вернуть пару с меньшим значением x.
     * Если таблица пуста, бросить IllegalStateException. Ошибка : IllegalArgumentException
     */
    // Плоховато
    fun findPair(x: Double): Pair<Double, Double>? //= TODO()
    {
        if (size == 0) throw IllegalArgumentException()
        var xxx: Double = 0.0
        var deltaX = Double.MAX_VALUE
        for (xx in table.keys)
            if (abs(xx - x) < deltaX ||((abs(xx - x) == deltaX) && x < xx)) {
                deltaX = abs(xx - x)
                xxx = xx
            }
        return xxx to table.getOrDefault(xxx,0.0)
    }
    /**
     * Вернуть значение y по заданному x.
     * Если в таблице есть пара с заданным x, взять значение y из неё.
     * Если в таблице есть всего одна пара, взять значение y из неё.
     * Если таблица пуста, бросить IllegalStateException. IllegalArgumentException
     * Если существуют две пары, такие, что x1 < x < x2, использовать интерполяцию.
     * Если их нет, но существуют две пары, такие, что x1 < x2 < x или x > x2 > x1, использовать экстраполяцию. Ошибка в задании
     * Если их нет, но существуют две пары, такие, что x1 < x2 < x или x < x1 < x2, использовать экстраполяцию.
     */
    // Недоделано - работает частный случай
    fun getValue(x: Double): Double   //= TODO()
    {
        if (size == 0) throw IllegalArgumentException()
        if (table.containsKey(x)) return table.getValue(x)
        if (size == 1) return table.getValue(table.keys.toList()[0])

        val sortTable = table.keys.toList().sorted()

        //println("x= $x sort= $sortTable table = $table")

        var x1 = sortTable[0]
        var x2 = sortTable[1]
        when {
            x < sortTable[0] -> {x1 = sortTable[0]; x2 = sortTable[1]}
            x > sortTable[size - 1] -> {x1 = sortTable[size - 2]; x2 = sortTable[size - 1]}
            else -> for (i in sortTable.indices - 1)
                if (sortTable[i] < x) {
                    x1 = sortTable[i]
                    x2 = sortTable[i + 1]
                    break
                }
        }
        return table[x1]!! + (table[x2])!!.minus(table[x1]!!) * (x - x1) / (x2 - x1)
    }
    /**
     * Таблицы равны, если в них одинаковое количество пар,
     * и любая пара из второй таблицы входит также и в первую
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TableFunction) return false
        if (this.size != other.size) return false

        for ((key) in other.table)
            if (!table.containsKey(key)) return false
        return true
    }

    override fun hashCode(): Int = table.hashCode()

}