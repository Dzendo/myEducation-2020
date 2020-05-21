@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

/**
 * Класс "комплексое число".
 * complex — совокупный, тесно связанный
 * Общая сложность задания -- лёгкая.
 * Объект класса -- комплексное число вида x+yi. где i*i = -1
 * Про принципы работы с комплексными числами см. статью Википедии "Комплексное число".
 * Complex  re+im*i  вещественные = re + 0*i
 * Аргументы конструктора -- вещественная и мнимая часть числа.
 */
class Complex(val re: Double, val im: Double) {

    /**
     * Конструктор из вещественного числа
     */
    @Suppress("UNREACHABLE_CODE")
    constructor(x: Double) : this(x, 0.0)


    /**
     * Конструктор из строки вида x+yi
     */
    @Suppress("UNREACHABLE_CODE")
    constructor(s: String) : this(
        s.substring(0,(if (s.indexOf('+',1) == -1) s.indexOf('-',1) else s.indexOf('+',1))).toDouble(),
        s.substring((if (s.indexOf('+',1) == -1) s.indexOf('-',1) else s.indexOf('+',1)),
            s.length - 1).toDouble()
    )
    // Это мое безобразие надо переделать но на тестах работает правильно
    // не понимаю где я могу высчитать параметры для первичного конструктора
    // алгоритм минимально устойчивый и уродский - надо Regex
    {

            val posZnakPlus = s.indexOf('+', 1)
            val posZnakMinus = s.indexOf('-', 1)
            val posZnak = if (posZnakPlus == -1) posZnakMinus else posZnakPlus
            val ree = s.substring(0, posZnak ).toDouble()
            val imm = s.substring(posZnak, s.length - 1).toDouble()


    }

    /**
     * Сложение.
     */
    operator fun plus(other: Complex): Complex = Complex(re + other.re,im + other.im)

    /**
     * Смена знака (у обеих частей числа)
     */
    operator fun unaryMinus(): Complex = Complex(-re, -im)


    /**
     * Вычитание
     */
    operator fun minus(other: Complex): Complex = Complex(re - other.re,im - other.im)

    /**
     * Умножение
     */
    operator fun times(other: Complex): Complex = Complex(re * other.re - im * other.im, im * other.re + re * other.im)

    /**
     * Деление
     */
    operator fun div(other: Complex): Complex =
        Complex((re * other.re + im * other.im) / (other.re * other.re + other.im * other.im),
            (im * other.re - re * other.im) / (other.re * other.re + other.im * other.im))

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean = when {
        other !is Complex -> false
        this.re == other.re && this.im == other.im -> true
        else -> false
    }
    /**
     * Преобразование в строку
     */
    override fun toString(): String = "re + ${im}i"
}