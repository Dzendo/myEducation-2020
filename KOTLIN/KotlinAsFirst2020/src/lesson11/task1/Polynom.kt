@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

import kotlin.math.max
import kotlin.math.pow

/**
 * Класс "полином с вещественными коэффициентами".
 *
 * Общая сложность задания -- сложная.
 * Объект класса -- полином от одной переменной (x) вида 7x^4+3x^3-6x^2+x-8.
 * многочлен от одной переменной
 * Количество слагаемых неограничено.
 *
 * Полиномы можно складывать -- (x^2+3x+2) + (x^3-2x^2-x+4) = x^3-x^2+2x+6,
 * вычитать -- (x^3-2x^2-x+4) - (x^2+3x+2) = x^3-3x^2-4x+2,
 * умножать -- (x^2+3x+2) * (x^3-2x^2-x+4) = x^5+x^4-5x^3-3x^2+10x+8,
 * делить с остатком -- (x^3-2x^2-x+4) / (x^2+3x+2) = x-5, остаток 12x+16
 * вычислять значение при заданном x: при x=5 (x^2+3x+2) = 42.
 *
 * В конструктор полинома передаются его коэффициенты, начиная со старшего.
 * Нули в середине и в конце пропускаться не должны, например: x^3+2x+1 --> Polynom(1.0, 2.0, 0.0, 1.0)  ОШИБКА !!!!
 * Нули в середине и в конце пропускаться не должны, например: x^3+2x^2+1 --> Polynom(1.0, 2.0, 0.0, 1.0)
 * Старшие коэффициенты, равные нулю, игнорировать, например Polynom(0.0, 0.0, 5.0, 3.0) соответствует 5x+3
 */
class Polynom(vararg coeffs: Double) {

// Хранить коэфф буду в обратном порядке: от конст по возр степени и без ведущих нулей
    val ffeoc = coeffs.reversed().take(coeffs.size - coeffs.indexOfFirst { it != 0.0 })


    /**
     * Геттер: вернуть значение коэффициента при x^i
     */
    fun coeff(i: Int): Double = ffeoc[i]

    /**
     * Расчёт значения при заданном x
     */
    fun getValue(x: Double): Double //= ffeoc.sumBy { it,index -> it * x.pow(index) }
    {
        var rez = 0.0
        for (i in ffeoc.indices) rez += ffeoc[i] * x.pow(i)
        return rez
    }

    /**
     * Степень (максимальная степень x при ненулевом слагаемом, например 2 для x^2+x+1).
     *
     * Степень полинома с нулевыми коэффициентами считать равной 0.
     * Слагаемые с нулевыми коэффициентами игнорировать, т.е.
     * степень 0x^2+0x+2 также равна 0.
     */
    fun degree(): Int = ffeoc.size - 1  // ведущий нули подаваляются при создании полинома

    /**
     * Сложение
     * Последовательность индексов большего по длинне полинома через map превращаю в сумму коэфф полиномов
     *    * -разваливает DoubleArray в vararg
     *    reversed() т.к. работаю с развернутыми коэяя - см выше
     *   rez[i] = this.ffeoc[i] + other.ffeoc[i]
     *
     */
    //  Складывает правильно 02032020 18:00
    operator fun plus(other: Polynom): Polynom = Polynom(*(0..max(this.ffeoc.size - 1, other.ffeoc.size - 1))
        .map {(if (it in this.ffeoc.indices) this.ffeoc[it] else 0.0) +
                (if (it in other.ffeoc.indices) other.ffeoc[it] else 0.0)}.reversed().toDoubleArray())
    // Складывает правильно 02032020 04:00 человч текст

     /* operator */ fun plus1(other: Polynom): Polynom
    {
     val rez = mutableListOf<Double>()
        for (i in 0 until max(this.ffeoc.size, other.ffeoc.size))
            rez.add(
                (if (i in this.ffeoc.indices) this.ffeoc[i] else 0.0) +
                        (if (i in other.ffeoc.indices) other.ffeoc[i] else 0.0)
            )
        return Polynom(*rez.reversed().toDoubleArray())
    }


    /**
     * Смена знака (при всех слагаемых)
     */
    operator fun unaryMinus(): Polynom = Polynom(*this.ffeoc.map { -it }.reversed().toDoubleArray())


    /**
     * Вычитание
     *  A-B = A + (-B)
     */
    // ok работает 02032020 18:05
    operator fun minus(other: Polynom): Polynom = this + (-other)

    // ok работает 02032020 04:05
    /*operator*/ fun minus1(other: Polynom): Polynom
    {
        val rez = mutableListOf<Double>()
        for (i in 0 until max(this.ffeoc.size, other.ffeoc.size))
            rez.add(
                (if (i in this.ffeoc.indices) this.ffeoc[i] else 0.0) -
                        (if (i in other.ffeoc.indices) other.ffeoc[i] else 0.0)
            )
        return Polynom(*rez.reversed().toDoubleArray())
    }



    /**
     * Умножение
     */
    operator fun times(other: Polynom): Polynom //= TODO()
    {
        //val aaa = (0..this.degree() + other.degree())
        val rez = mutableListOf<Double>()
        for (k in 0..this.degree() + other.degree()) rez.add(0.0)
        for (i in 0..this.degree())
            for (j in 0..other.degree())
                rez[i + j] += this.ffeoc[i] * other.ffeoc[j]

        return Polynom(*rez.reversed().toDoubleArray())
    }


    /**
     * Деление
     *
     * Про операции деления и взятия остатка см. статью Википедии
     * "Деление многочленов столбиком". Основные свойства:
     *
     * Если A / B = C и A % B = D, то A = B * C + D и степень D меньше степени B
     */
    operator fun div(other: Polynom): Polynom //= TODO()
    {

        return Polynom(0.0)
    }

    /**
     * Взятие остатка
     */
    operator fun rem(other: Polynom): Polynom = TODO()

    override fun equals(other: Any?): Boolean {

        if (this === other) return true
        if (other !is Polynom) return false
       // other as Polynom
        if (this.ffeoc != other.ffeoc) return false

        return true
    }

    override fun hashCode(): Int = ffeoc.hashCode()

}
