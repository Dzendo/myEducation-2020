package lesson11.task1

import kotlin.math.max

/**
 * Класс "беззнаковое большое целое число".
 *
 * Общая сложность задания -- очень сложная.
 * Объект класса содержит целое число без знака произвольного размера
 * и поддерживает основные операции над такими числами, а именно:
 * сложение, вычитание (при вычитании большего числа из меньшего бросается исключение),
 * умножение, деление, остаток от деления,
 * преобразование в строку/из строки, преобразование в целое/из целого,
 * сравнение на равенство и неравенство
 */
class UnsignedBigInteger(s: String) : Comparable<UnsignedBigInteger> {
    val ss = s.trim().reversed()

    /**
     * Конструктор из строки
     */
    /* constructor(s: String) {
        TODO()
    }*/

    /**
     * Конструктор из целого
     */
    constructor(i: Int) : this(i.toString())
    /*{
        TODO()
    }*/

    /**
     * Сложение
     */
    operator fun plus(other: UnsignedBigInteger): UnsignedBigInteger //= TODO()
    {
        val sss = CharArray(max(this.ss.length + 1, other.ss.length + 1)) { '0' }
        for (i in 0..max(this.ss.length, other.ss.length)){
            val aa = ((this.ss[i].toInt() + other.ss[i].toInt()) + (sss[i].toInt() * 10 + sss[i+1].toInt())).toString()
            sss[i] = sss[i] + aa[0].toInt()
            sss[i+1] = sss[i+1] + aa[1].toInt()
        }
        return  UnsignedBigInteger(sss.toString().reversed())
    }

    /**
     * Вычитание (бросить ArithmeticException, если this < other)
     */
    operator fun minus(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

    /**
     * Умножение
     */
    operator fun times(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

    /**
     * Деление
     */
    operator fun div(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

    /**
     * Взятие остатка
     */
    operator fun rem(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

    /**
     * Сравнение на равенство (по контракту Any.equals)
     */
    override fun equals(other: Any?): Boolean = TODO()

    /**
     * Сравнение на больше/меньше (по контракту Comparable.compareTo)
     */
    override fun compareTo(other: UnsignedBigInteger): Int = TODO()

    /**
     * Преобразование в строку
     */
    override fun toString(): String = TODO()

    /**
     * Преобразование в целое
     * Если число не влезает в диапазон Int, бросить ArithmeticException
     */
    fun toInt(): Int = TODO()

}