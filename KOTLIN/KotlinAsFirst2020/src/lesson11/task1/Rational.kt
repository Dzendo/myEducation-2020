package lesson11.task1

import kotlin.math.abs

/**
 * Пример: класс "рациональное число" -- ДРОБЬ
 *
 * Объект класса представляет число M/N, где M - целое, N - целое положительное.
 * Дробь M/N удобно хранить в несократимом виде: GCD(M, N) = 1.
 * Рациональные числа можно складывать, вычитать, умножать, делить, сравнивать,
 * преобразовывать к целому или вещественному.
 */
/*
Kotlin поддерживает стиль функционального программирования, известный как хвостовая рекурсия .
Это позволяет некоторым алгоритмам, которые обычно пишутся с использованием циклов,
вместо этого писать с использованием рекурсивной функции, но без риска переполнения стека.
Когда функция отмечена tailrec модификатором и соответствует требуемой форме,
компилятор оптимизирует рекурсию, оставляя вместо этого быструю и эффективную версию на основе цикла:
Чтобы иметь право на tailrec модификатор, функция должна вызывать себя как последнюю операцию, которую она выполняет.
Вы не можете использовать хвостовую рекурсию, когда после рекурсивного вызова есть больше кода,
и вы не можете использовать его в блоках try / catch / finally.
 */
class Rational(numerator: Int, denominator: Int) : Comparable<Rational> {

    val numerator: Int

    val denominator: Int

// вычисляем наибольший общий делитель (GCD = greatest common divisor) двух чисел: числителя и знаменателя
    private tailrec fun gcd(a: Int, b: Int): Int =
        when {
            a == b || b == 0 -> a
            a == 0 -> b
            a > b -> gcd(a % b, b)
            else -> gcd(a, b % a)
        }
// операторы из анонимного инициализатора init выполняются сразу же после создания объекта класса
    init {
        if (denominator == 0) throw ArithmeticException("Denominator cannot be zero")
        var gcd = gcd(abs(numerator), abs(denominator))
        if (denominator < 0) gcd = -gcd
        this.numerator = numerator / gcd
        this.denominator = denominator / gcd
    }
    // Не понимаю а где сокращаются дроби от результатов операций??
// Описывается внутри класса с помощью модификатора operator операции:
    /**
     * Сложение ***************************** -1 *************************************
     */
    // могут вызываться не только непосредственно в виде r1.plus(r2),
    // но и с использованием знаков операций — в данном случае r1 + r2.
    //  https://kotlinlang.org/docs/reference/operator-overloading.html
    operator fun plus(other: Rational) = Rational(
        numerator * other.denominator + denominator * other.numerator,
        denominator * other.denominator
    )
// numerator, denominator (можно также писать this.numerator и this.denominator)
    /**
     * Смена знака (Y = -X) ***************************** -1 *************************************
     */
    operator fun unaryMinus() = Rational(-numerator, denominator)

    /**
     * Вычитание ***************************** -1 *************************************
     */
    operator fun minus(other: Rational) = plus(other.unaryMinus())

    /**
     * Умножение ***************************** -1 *************************************
     */
    operator fun times(other: Rational) =
        Rational(numerator * other.numerator, denominator * other.denominator)

    /**
     * Деление ***************************** -1 *************************************
     */
    operator fun div(other: Rational) =
        Rational(numerator * other.denominator, denominator * other.numerator)

    /** Это уже НЕ операторы а просто функции в классе ДРОБЬ
     * Преобразование к целому, т.е. делим нацело **************** -1 *************************************
     */
    fun toInt() = numerator / denominator

    /**
     * Преобразование к вещественному ***************************** -1 *************************************
     */
    fun toDouble() = numerator.toDouble() / denominator

    /**
     * Сравнение на равенство (переопределяет Any.equals) ***************************** -1 *************************************
     */
    override fun equals(other: Any?) =
        when {
            this === other -> true
            other is Rational -> numerator == other.numerator && denominator == other.denominator
            else -> false
        }

    override fun hashCode(): Int {
        var result = numerator
        result = 31 * result + denominator
        return result
    }

    /**
     * Преобразование в строку ***************************** -1 *************************************
     */
    override fun toString() = "$numerator/$denominator"

    /**
     * Сравнение на неравенство (переопределяет Comparable.compareTo) ***************************** -1 *************************************
     */
    override fun compareTo(other: Rational): Int {
        val diff = (this - other)
        // В зависимости от знака числителя разницы, this > other, this < other или this == other
        return diff.numerator
    }
    /* Для описания и использования операций сравнения в Java и Котлине имеется специальный интерфейс "сравниваемый":
    interface Comparable<T> {
    // Возвращает положительное число, если получатель больше other,
    // отрицательное число, если получатель меньше other,
    // ноль, если они равны
    fun compareTo(other: T): Int
    }
     */
}