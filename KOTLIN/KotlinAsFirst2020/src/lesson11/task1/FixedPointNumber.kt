@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.round


/**
 * Класс "вещественное число с фиксированной точкой"
 *
 * Общая сложность задания - сложная.
 * Объект класса - вещественное число с заданным числом десятичных цифр после запятой (precision, точность).
 * Например, для ограничения в три знака это может быть число 1.234 или -987654.321.
 * Числа можно складывать, вычитать, умножать, делить
 * (при этом точность результата выбирается как наибольшая точность аргументов),
 * а также сравнить на равенство и больше/меньше, преобразовывать в строку и тип Double.
 *
 * Вы можете сами выбрать, как хранить число в памяти
 * (в виде строки, целого числа, двух целых чисел и т.д.).
 * Представление числа должно позволять хранить числа с общим числом десятичных цифр не менее 9.
 */
class FixedPointNumber(d: Double, p: Int) : Comparable<FixedPointNumber> {
    /**
     * Точность - число десятичных цифр после запятой.
     */
    val nomberDouble: Double = d
    val precision: Int = p
       // get() = let{ println("precision $precision"); field}

        /**
     * Конструктор из строки, точность выбирается в соответствии
     * с числом цифр после десятичной точки.
     * Если строка некорректна или цифр слишком много,
     * бросить NumberFormatException.
     *
     * Внимание: этот или другой конструктор можно сделать основным
     */
    constructor(s: String) : this(s.toDouble(), if (s.contains('.')) s.length - (s.indexOf('.') + 1) else 0)

    /**
     * Конструктор из вещественного числа с заданной точностью
     */
   // constructor(d: Double, p: Int) {  // вынес наверх в основной

    /**
     * Конструктор из целого числа (предполагает нулевую точность)
     */
    constructor(i: Int) : this(i.toDouble(), 0)

    /**
     * Сложение.
     *
     * Здесь и в других бинарных операциях
     * точность результата выбирается как наибольшая точность аргументов.
     * Лишние знаки отрбрасываются, число округляется по правилам арифметики.
     */
    operator fun plus(other: FixedPointNumber): FixedPointNumber =
        FixedPointNumber(this.nomberDouble + other.nomberDouble, max(this.precision, other.precision))

    /**
     * Смена знака
     */
    operator fun unaryMinus(): FixedPointNumber = FixedPointNumber(-this.nomberDouble, this.precision)

    /**
     * Вычитание
     */
    operator fun minus(other: FixedPointNumber): FixedPointNumber =
        FixedPointNumber(this.nomberDouble - other.nomberDouble, max(this.precision, other.precision))

    /**
     * Умножение
     */
    operator fun times(other: FixedPointNumber): FixedPointNumber =
        FixedPointNumber( round((this.nomberDouble * other.nomberDouble) * 10.0.pow(max(this.precision, other.precision))) /
                10.0.pow(max(this.precision, other.precision)), max(this.precision, other.precision))

    /**
     * Деление
     */
    operator fun div(other: FixedPointNumber): FixedPointNumber =
        FixedPointNumber( round((this.nomberDouble / other.nomberDouble) * 10.0.pow(min(this.precision, other.precision))) /
                10.0.pow(min(this.precision, other.precision)), min(this.precision, other.precision))


    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean =
        (other is FixedPointNumber) && this.nomberDouble == other.nomberDouble && this.precision == other.precision

    /**
     * Сравнение на больше/меньше
     */
    override fun compareTo(other: FixedPointNumber): Int = when  {
        this.nomberDouble - other.nomberDouble >= 0.1.pow(this.precision) ->  1
        this.nomberDouble - other.nomberDouble <= 0.1.pow(this.precision) -> -1
        else -> 0
    }

    /**
     * Преобразование в строку
     */
    override fun toString(): String = "${this.nomberDouble}"

    /**
     * Преобразование к вещественному числу
     */
    fun toDouble(): Double = this.nomberDouble

    override fun hashCode(): Int {
        var result = nomberDouble.hashCode()
        result = 31 * result + precision
        return result
    }
}