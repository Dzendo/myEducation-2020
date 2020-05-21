@file:Suppress("UNUSED_PARAMETER")

//   https://arrow-kt.io/    // Λ rrow Q uery L anguage
package lesson3.task1



import java.io.BufferedOutputStream
import java.math.BigDecimal
import kotlin.math.*

/**
 * Пример ***************************** -1 *************************************
 * https://www.fandroid.info/3-osnovy-kotlin-rekursii-i-tsikly/2/
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result *= i // Please do not fix in master
    }
    return result
}

fun factorial0(n: Int): Double = if (n < 2) 1.0 else n * factorial0(n - 1)

/**
 * Пример ***************************** -1 *************************************
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}


/**
 * Пример ***************************** -1 *************************************
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример ***************************** -1 *************************************
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
    when {
        n == m -> 1
        n < 10 -> 0
        else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
    }


//fun main() {
/*    println("5  ${digitNumber(65535)}")
    println("1, ${digitNumber(0)}")
    println("1, ${digitNumber(7)}")
    println("2, ${digitNumber(10)}")
    println("2, ${digitNumber(99)}")
    println("3, ${digitNumber(123)}")
    println("10, ${digitNumber(Int.MAX_VALUE)}")
}*/




/**
 * Простая ***************************** 01 *************************************
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.  !!!!! - это и есть третий вариант
 */
fun digitNumber3(n: Int): Int = n.toString().length   // Функции высшего порядка 3-ий вариант

fun digitNumber(n: Int): Int = if (n / 10 == 0) 1 else digitNumber(n / 10) + 1  // рекурсия 2-ой вариант

fun digitNumber1(n: Int): Int {    // Линейно-циклический алгоритм 1-ый вариант
    var osttek = n
    var i = 1
    while (true) {
        osttek /= 10
        if (osttek == 0) return i
        i++
    }
}
/*fun main() {

    println("1, ${fib(1)}")
    println("1, ${fib(2)}")
    println("2, ${fib(3)}")
    println("5, ${fib(5)}")
    println("21, ${fib(8)}")
    println("102334155, ${fib(40)}")
    println("1134903170, ${fib(45)}")
    println("1836311903, ${fib(46)}")
}*/

/**
 * Простая ***************************** 02 *************************************
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */


fun fib4(n: Int): Int = (((sqrt(5.0) + 1.0) / 2.0).pow(n) / sqrt(5.0) + 0.5).toInt()  // мат модель расписана ниже

// Математически через комплексные числа работает мгновенно за один раз
fun fib3(n: Int): Int {
    val SQRT5 = sqrt(5.0)
    val PHI = (SQRT5 + 1) / 2
    return (PHI.pow(n) / SQRT5 + 0.5).toInt()
}       // требует математических выкладок  https://habrastorage.org/files/e37/e6d/a39/e37e6da397144b688b46b276293b373a.PNG

// просто и понятно на цикле: все работает сделано т.к. f(n-3) уже не нужно и можно его затирать:
fun fib(n: Int): Int {
    var x = 1
    var y = 1
    for (i in 2 until n) {
        y = y + x        // y = y + x считаем следующее число из себя и предыдущего
        x = y - x        // запоминаем в x предыдущее y для следующего шага цикла можно исп var z
    }
    return y
}

fun fib0(n: Int): Int = if (n <= 2) 1 else fib0(n - 1) + fib0(n - 2)  // рекурсия долго и дорого

/**
 * Простая ***************************** 03 *************************************
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
// затем вычисляем наибольший общий делитель (GCD = greatest common divisor) числителя и знаменателя пример Less 11
private tailrec fun gcd(a: Int, b: Int): Int =
    when {
        a == b || b == 0 -> a
        a == 0 -> b
        a > b -> gcd(a % b, b)
        else -> gcd(a, b % a)
    }



//Ok
fun lcm(m: Int, n: Int): Int =
    (max(n, m)..m * n step max(n, m)).firstOrNull { it % min(n, m) == 0 } ?: n  // Последний Ок

//  5-ий вариант - 0 сек - экономит память
fun lcm5(m: Int, n: Int): Int = (max(n, m)..(m * n) step max(n, m)).firstOrNull { it % min(n, m) == 0 } ?: n * m

//  4-ий вариант - 0 сек - функциональная запись настоящая
fun lcm4(m: Int, n: Int): Int = (max(n, m)..m * n).step(max(n, m)).first { it % min(n, m) == 0 }

fun lcm3(m: Int, n: Int): Int =
    (n..m * n).first { it % n == 0 && it % m == 0 }  // 3-ий вариант УРА ЗАРАБОТАЛО ПЕРВАЯ функц запись МОЯ + док - тупая тупая 15 сек

fun lcm2(m: Int, n: Int): Int {   // 2-ой примитивно и работает совсем быстро и правильно (сам)
    for (k in max(n, m)..m * n step max(n, m))
        if (k % min(n, m) == 0) return k
    return n * m
}

fun lcm1(m: Int, n: Int): Int {   // 1-ый примитивно и работает долго (30 сек) но правильно (сам)
    for (k in 1..m * n)
        if ((k % n == 0) && (k % m == 0)) return k
    return n * m
}

/**
 * Простая ***************************** 04 *************************************
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
// Хорошо бы найти библ функцию простых чисел
fun minDivisor4(n: Int): Int = (2..n / 2).firstOrNull() { n % it == 0 } ?: n  // 12 сек этот не ломается на 1 и лом -1

fun minDivisor3(n: Int): Int = (2..n / 2).firstOrNull() { n % it == 0 } ?: abs(n)  // 12 сек этот не ломается на 1 и -1
fun minDivisor2(n: Int): Int = (2..n).firstOrNull() { n % it == 0 } ?: 1  // 12 сек этот не ломается на 1 и -1
fun minDivisor1(n: Int): Int = (2..n).first() { n % it == 0 }   // 12 сек  -- ломается на 1
fun minDivisor(n: Int): Int {  // быстрее вдвое работает
    if (n % 2 == 0) return 2
    for (i in 3..n / 2 step 2) if (n % i == 0) return i
    return n
}

/**
 * Простая ***************************** 05 *************************************
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int = (n / 2 downTo 1).first { n % it == 0 }   // 10 сек этот не ломается на 1 и -1

fun maxDivisor1(n: Int): Int = (n - 1 downTo 1).first { n % it == 0 }   // 20 сек этот не ломается на 1 и -1
fun maxDivisor2(n: Int): Int =
    (2 until n).lastOrNull() { n % it == 0 } ?: 1  // 20 сек только короче нет предела совершенству

/**
 * Простая ***************************** 06 *************************************
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
// Найти бы библиотечную функцию простых чмсел
fun isCoPrime(m: Int, n: Int): Boolean =
    (2..min(m, n)).all { m % it != 0 || n % it != 0 }  // ок работает долго но правильно не берет память

fun isCoPrime111(m: Int, n: Int): Boolean =
    (2..min(m, n)).none { it -> m % it == 0 && n % it == 0 }  // ок работает долго сахароза


fun isCoPrime1(m: Int, n: Int): Boolean =
    (2..min(m, n)).filter { it -> m % it == 0 && n % it == 0 }.isEmpty()  // ок работает долго


/**
 * Простая ***************************** 07 *************************************
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
//  работает математически мгновенно
fun squareBetweenExists(m: Int, n: Int): Boolean =
    sqrt(m.toDouble()).toInt() != sqrt(n.toDouble()).toInt()      //  работает математически
            || sqrt(m.toDouble()).toInt() * sqrt(m.toDouble()).toInt() == m           // нужно для 1 и граничных квадратов

fun squareBetweenExists2(m: Int, n: Int): Boolean =// нужно для сл 1 1   36 48 работает норма
    (sqrt(m.toDouble()).toInt()..sqrt(n.toDouble()).toInt() + 1).any { it * it in m..n }

fun squareBetweenExists1(m: Int, n: Int): Boolean =// нужно для сл 1 1   36 48 работает
    (sqrt(m.toDouble()).toInt()..sqrt(n.toDouble()).toInt() + 1).filter { m <= it * it && it * it <= n }.isNotEmpty()

fun squareBetweenExists0(m: Int, n: Int): Boolean =
    (sqrt(m.toDouble()).toInt() - 1..sqrt(n.toDouble()).toInt() + 1).filter { m <= it * it && it * it <= n }.isNotEmpty()

/**
 * Средняя ***************************** 08 *************************************
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
// Изврат интересный - работаетю т.е. Лямбда с выполнением
fun collatzSteps(x: Int): Int = {
    var n = 0
    var y = x
    while (y != 1) {
        y = if (y % 2 == 0) y / 2
        else 3 * y + 1
        n++
    }
    n
}()

// Ок
fun collatzSteps1(x: Int): Int {
    var n = 0
    var y = x
    while (y != 1) {
        if (y % 2 == 0) y /= 2
        else y = 3 * y + 1
        n++
    }
    return n
}


/**
 * Средняя ***************************** 09 *************************************
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю.
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.sin и другие стандартные реализации функции синуса в этой задаче запрещается.
 */
// работает но не доделана - окончат вариант см sin2
//Спросила в Slacke 16.02.2020: Добрый день, хочется знать лучшее решение и услышать критику своего эту задачу с 299 по 339 строки
fun sin(x: Double, eps: Double): Double {  // pi*100 = 314 Проходит только с БинДесимал - дурдом хотя считает
    print(x)
    var sinx:BigDecimal = x.toBigDecimal()
    var y:BigDecimal = x.toBigDecimal()
    var i = 1
    var iznack = -1
    var fack :BigDecimal = 1.0.toBigDecimal()
    var xpow:BigDecimal = x.toBigDecimal()
    while((y * y) > (eps * eps).toBigDecimal()) {
        fack = fack * (i+1).toBigDecimal() * (i+2).toBigDecimal()
        xpow = xpow * x.toBigDecimal() * x.toBigDecimal()
        y= xpow / fack
        sinx = sinx + iznack.toBigDecimal() * y
        i = i + 2
        iznack = - iznack
        //  println("$y --> $eps  xpow = $xpow  fack = $fack   sinx = $sinx")
        // if (i > 250) break
    }
    println ("   $sinx")
    return sinx.toDouble()
}


fun sin2(x: Double, eps: Double): Double {  // pi*100 = 314 Проходит только с БинДесимал
    print(x)
    var sinx: BigDecimal = x.toBigDecimal()
    var y: BigDecimal = x.toBigDecimal()
    var i = 1
    var iznack = -1

    while ((y * y) > (eps * eps).toBigDecimal()) {
        y = y * (x.toBigDecimal() * x.toBigDecimal()) / ((i + 1).toBigDecimal() * (i + 2).toBigDecimal())
        sinx += iznack.toBigDecimal() * y
        i += 2
        iznack = -iznack
        //  println("$y --> $eps  xpow = $xpow  fack = $fack   sinx = $sinx")
        // if (i > 250) break
    }
    println("   $sinx")
    return sinx.toDouble()
}
// не работает
fun sin1(x: Double, eps: Double): Double {  // pi*100 = 314 Проходит только с БинДесимал работает до 8 PI
    print(x)
    var sinx: Double = x
    var y: Double = x
    var i = 1
    var iznack = -1

    while(abs(y) > eps) {
        y = y * ((x * x) / ((i+1).toDouble() * (i+2).toDouble()))
        sinx = sinx + iznack.toDouble() * y
        i = i + 2
        iznack = - iznack
        //  println("$y --> $eps  xpow = $xpow  fack = $fack   sinx = $sinx")
        // if (i > 250) break
    }
    println ("   $sinx")
    return sinx
}
/**
 * Средняя ***************************** 10 *************************************
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.cos и другие стандартные реализации функции косинуса в этой задаче запрещается.
 */
fun cos(x: Double, eps: Double): Double {
    print(x)
    var cosx: BigDecimal = 1.toBigDecimal()
    var y: BigDecimal = 1.toBigDecimal()
    var i = 0
    var iznack = -1

    while ((y * y) > (eps * eps).toBigDecimal()) {
        y = y * (x.toBigDecimal() * x.toBigDecimal()) / ((i + 1).toBigDecimal() * (i + 2).toBigDecimal())
        cosx = cosx + iznack.toBigDecimal() * y
        i = i + 2
        iznack = -iznack
        //  println("$y --> $eps  xpow = $xpow  fack = $fack   sinx = $sinx")
        // if (i > 250) break
    }
    println("   $cosx")
    return cosx.toDouble()
}

/**
 * Средняя ***************************** 11 *************************************
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
//Ok
fun revert2(n: Int): Int = n.toString().reversed().toInt()   // операции со строками - запрещено
//Ok
fun revert(n: Int): Int {
    var rez = 0
    var osttek = n
    while (osttek != 0) {
        rez = rez * 10 + (osttek % 10)
        osttek /= 10
    }
    println(" n = $n   rez= $rez  ")
    return rez
}

/**
 * Средняя ***************************** 12 *************************************
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */

// ок  работает - так тоже можно
fun isPalindrome(n: Int): Boolean = with(n.toString()){ this == this.reversed() }

// ок работает - можно погордится
fun isPalindrome3(n: Int): Boolean = n.toString() == n.toString().reversed()

// Ок работает
fun isPalindrome2(n: Int): Boolean {
    var rez = 0
    var osttek = n
    while (osttek != 0) {   // разворачиваю исходное число наоборот в rez
        rez = rez * 10 + (osttek % 10)
        osttek /= 10
    }
    println(" n = $n   rez= $rez  ")
    return n == rez
}

// Плохо сделано из предыдущего но работает правильно:
fun isPalindrome1(n: Int): Boolean {
    var rez = 0
    var osttek = n
    while (osttek != 0) {  // разворачиваю исходное число наоборот в rez
        rez = rez * 10 + (osttek % 10)
        osttek /= 10
    }
    println(" n = $n   rez= $rez  ")
    osttek = n
    while (osttek != 0) {
        if (osttek % 10 != rez % 10) return false
        osttek /= 10
        rez /= 10
    }
    return true
}


/**
 * Средняя ***************************** 13 *************************************
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
// Ok
fun hasDifferentDigits(n: Int): Boolean = n.toString().toSet().count() > 1

// Ok
fun hasDifferentDigits1(n: Int): Boolean {
    println(" n = $n     ")

    var osttek = n
    while (osttek != 0) {
        val rez = osttek % 10
        osttek /= 10
        // println("rez = $rez  ")
        var ostrez = n
        while (ostrez != 0) {
            if (ostrez % 10 != rez) return true
            ostrez /= 10
        }
    }
    return false
}

/*fun main() {
    println("Нужная цифра = ${squareSequenceDigit(20)}")
}*/

/**
 * Сложная ***************************** 14 *************************************
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
// написать вариант со строками

//  Новый третий варианет работает чистить
fun squareSequenceDigit(n: Int): Int {
    println("squareSequenceDigit n = $n")
    var str = ""
    var nsqr = 0 // текущий номер числа для квадрата
    while (str.length < n) {
        nsqr++
        str += (nsqr * nsqr).toString()
}
   // val rez = (1..n).forEach(if (str.length < n) {str += (n * n).toString()
    println("squareSequenceDigit n-1 = ${n-1} c = ${str[n - 1]}  $str")
    return str[n-1].toString().toInt()
}


//  Рабочий второй вариант все правильно цифровой
fun squareSequenceDigit2(n: Int): Int {
    println("squareSequenceDigit n = $n")
    var nn = 0  // текущий номер найденной цифры
    var nsqr = 0 // текущий номер числа для квадрата
    while (nn < n) {
        nsqr++
        nn += kolNumbers(nsqr * nsqr)   // всего цифр в пересчитанных квадратах
        print(nsqr * nsqr)
    }
    //println()
    //println("12345678901234567890123456789012345678901234567890")

    val nnn = nn - n + 1  // Нужная цифра в последнем квадрате с конца

    //println("nsqr = $nsqr  sqr = ${nsqr * nsqr} кол-во цифр = $ kolsqr  Накоплено цифр nn = $nn нужна с конца $nnn")

    return nubmerKol(nsqr * nsqr, nnn)
}


fun kolNumbers(number: Int): Int {  // возвращает количество цифр в цифре
    var ostsqr = number
    var kolsqr = 0   // кол-во цифр в этом квадрате - нарастает  цикле
    while (ostsqr != 0) {
        kolsqr++  // пересчет кол-ва цифр в очередном числе
        ostsqr = (ostsqr / 10)
    }   // работает - kolsqr =  количество цифр в квадрате
    return kolsqr
}

fun nubmerKol(number: Int, pos: Int): Int { // возвращает цмфру с позиции POS из числа считая справа

    var needNumber = 0
    var ostrez = number  // восстановил последний квадрат вместо того чтобы запоминать выше
    for (i in 1..pos) {
        needNumber = ostrez % 10
        ostrez /= 10
    }
    return needNumber
}
//  Рабочий первый вапмает все правильно
fun squareSequenceDigit1(n: Int): Int {
    println("squareSequenceDigit n = $n")
    var nn = 0  // текущий номер найденной цифры
    var nsqr = 0 // текущий номер числа для квадрата

    while (nn < n) {  nsqr++
        var ostsqr = nsqr * nsqr     // квадрат текущего числа
        print(ostsqr)
        var kolsqr = 0   // кол-во цифр в этом квадрате - нарастает во влож цикле

        while (ostsqr != 0) {   kolsqr++  // пересчет кол-ва цифр в очередном квадрате
            ostsqr = (ostsqr / 10)
        }   // работает - kolsqr =  количество цифр в квадрате
        nn += kolsqr   // всего цифр в пересчитанных квадратах
    }

    val nnn = nn - n +1  // Нужная цифра в последнем квадрате с конца
    println()
    println("12345678901234567890123456789012345678901234567890")
    println("nsqr = $nsqr  sqr = ${nsqr * nsqr} кол-во цифр = $ kolsqr  Накоплено цифр nn = $nn нужна с конца $nnn")
    var needNumber = 0
    var ostrez = nsqr * nsqr  // восстановил последний квадрат вместо того чтобы запоминать выше
    for (i in 1..nnn){
        needNumber = ostrez % 10
        ostrez /= 10
    }
    return needNumber
}


/**
 * Сложная ***************************** 15 *************************************
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
// написать вариант со строками

fun fibSequenceDigit(n: Int): Int {
    println("fibSequenceDigit n = $n")
    var nn = 0  // текущий номер найденной цифры
    var nsqr = 0 // текущий номер числа fib
    while (nn < n) {
        nsqr++
        nn += kolNumbers(fib1(nsqr))   // всего цифр в пересчитанных Фабиначах
    }
    val nnn = nn - n + 1  // Нужная цифра в последнем Фабиначи с конца

    println("nsqr = $nsqr  sqr = ${nsqr} кол-во цифр = $ kolsqr  Накоплено цифр nn = $nn нужна с конца $nnn")

    return nubmerKol(fib1(nsqr), nnn)
}


fun fib1(n: Int): Int {
    var x = 1
    var y = 1

    for (i in 2 until n) {
        y += x        // y = y + x считаем следующее число из себя и предыдущего
        x = y - x     // запоминаем в x предыдущее y для следующего шага цикла можно исп var z
    }
    return y
}