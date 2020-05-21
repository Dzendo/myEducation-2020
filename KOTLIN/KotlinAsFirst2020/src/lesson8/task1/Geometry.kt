@file:Suppress("UNUSED_PARAMETER")
package lesson8.task1

import lesson1.task1.sqr
import kotlin.math.*

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример ***************************** -1 *************************************
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
@Suppress("MemberVisibilityCanBePrivate")
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point) : this(linkedSetOf(a, b, c))

    /**
     * Пример: полупериметр ***************************** -1 *************************************
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь ***************************** -1 *************************************
     */
    fun area(): Double {
        val p = halfPerimeter()
        return sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку ***************************** -1 *************************************
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**   ************************** 01 **************************
     * Простая ***************************** 01 *************************************
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    //
// ок маразм но можно и так
    fun distance3(other: Circle): Double =
        with(center.distance(other.center) - (radius + other.radius)) { if (this <= 0.0) 0.0 else this }

    // ок
    fun distance(other: Circle): Double =
        if (center.distance(other.center) <= radius + other.radius) 0.0
        else center.distance(other.center) - (radius + other.radius)

    // ok 17.02.2020 13:21
    fun distance1(other: Circle): Double {
        if (other !is Circle) return 0.0
        val dist = this.center.distance(other.center)
        val sumRad = this.radius + other.radius
        if (dist <= sumRad) return 0.0
        return abs(sumRad - dist)
    }

    /**  ********************************* 02 ************************
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    // ok
    fun contains(p: Point): Boolean = p.distance(center) <= radius

}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
        other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
        begin.hashCode() + end.hashCode()
}

/**   ******************************** 03 *******************************
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
//
// Изврат ФВП нор работает
fun diameter2(vararg points: Point): Segment =
    points.map { pb -> points.map { pe -> Segment(pb, pe) } }.flatMap { it }
        .maxBy { (pb, pe) -> pb.distance(pe) } ?: throw IllegalArgumentException()


// ok 17.02.2020 15:09
fun diameter(vararg points: Point): Segment // = TODO()
{
    if (points.count() < 2) throw IllegalArgumentException()
    var pb: Point = points[0]
    var pe: Point = points[0]
    for (ppb in points)
        for (ppe in points)
            if (ppb.distance(ppe) > pb.distance(pe)) {
                pb = ppb; pe = ppe
            }
    return Segment(pb, pe)
}

/**
 * Простая   *********************** 04 *************************
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
//ok можно и так но хуже выглядит
fun circleByDiameter(diameter: Segment): Circle = with(diameter)
{
    Circle(
        Point((begin.x + this.end.x) / 2, (begin.y + this.end.y) / 2.0),
        (begin.distance(this.end)) / 2.0
    )
}

// ok 17.02.2020 20:22
fun circleByDiameter1(diameter: Segment): Circle =
    Circle(
        Point((diameter.begin.x + diameter.end.x) / 2.0, (diameter.begin.y + diameter.end.y) / 2.0),
        (diameter.begin.distance(diameter.end)) / 2.0
    )

/** ***************************** -1 *************************************
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        require(angle >= 0 && angle < PI) { "Incorrect line angle: $angle" }

    }

    lateinit var point: Point

    constructor(point: Point, angle: Double) : this(point.y * cos(angle) - point.x * sin(angle), angle) {
        println("Создание Линии: x = ${point.x} y=${point.y} b= $b  angle= $angle")
        this.point = point

    }

    /**
     * Средняя  *************************** 05 ***************************
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    // не работает -- деление на ноль работает только с yy2 - разбираться ЛВ Формулы
    fun crossPoint(other: Line): Point // = TODO()
    {
        println(" Обратились к функии crossPoint")
        println(" This  = $this  ")
        println(" Other = $other ")
        println(" This :  x= ${this.point.x}  y= ${this.point.y}  .b = ${this.b}  angle = ${this.angle} ")
        println(" Other:  x= ${other.point.x} y= ${other.point.y} .b = ${other.b}  angle = ${other.angle}  ")

        println()
/*
        val xx1 = other.b / sin(other.angle) - this.b / sin(this.angle)
        val xx2 = (cos(other.angle) / sin(other.angle) - cos(this.angle) / sin(this.angle))
        val xx3 = (cos(this.angle) - this.b) / sin(this.angle)
        val xx = xx1 / xx2 * xx3
        val yy = xx1 / xx2
*/
        val xx1 = other.b * cos(this.angle) - this.b * cos(other.angle)
        val xx2 = sin(this.angle) * cos(other.angle) - sin(other.angle) * cos(this.angle)
        val xx = xx1 / xx2
        //val yy1 = (xx * sin(this.angle) + this.b) / cos(this.angle)
        var yy1 = other.b * sin(this.angle) + this.b * sin(other.angle)
        yy1 = yy1 / xx2
        val yy2 = (xx * sin(other.angle) + other.b) / cos(other.angle)
        println("xx1= $xx1 xx2=$xx2 xx=$xx")
        println("xx= $xx yy1=$yy1 yy2 = $yy2")

        return Point(xx, yy2)
    }

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${cos(angle)} * y = ${sin(angle)} * x + $b)"
}

/**
 * Средняя   ************************************ 06 *************************
 *
 * Построить прямую по отрезку
 */
//ok 18.02.2020 0:23
fun lineBySegment(s: Segment): Line = Line(s.begin, atan(abs(s.begin.y - s.end.y) / abs(s.begin.x - s.end.x)))

/**
 * Средняя  *************************  07  ******************************
 *
 * Построить прямую по двум точкам
 */
//ok 18.02.2020 0:23
fun lineByPoints(a: Point, b: Point): Line = Line(a, atan(abs(a.y - b.y) / abs(a.x - b.x)))

// ok 18.02.2020 0:17
fun lineByPoints1(a: Point, b: Point): Line {
    println("a= $a  b= $b")
    val x = abs(a.x - b.x)
    val y = abs(a.y - b.y)
    val angle = atan(y / x)
    println("x= $x y=$y angel = $angle")
    return Line(a, angle)
}

/**
 * Сложная *************************  08  ******************************
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */

// ok
fun bisectorByPoints4(a: Point, b: Point): Line = {
    val angle = atan((a.y - b.y) / (a.x - b.x)) + PI / 2.0
    Line(Point((a.x + b.x) / 2.0, (a.y + b.y) / 2.0), angle - if (angle >= PI) PI else 0.0)
}()

// ok
fun bisectorByPoints(a: Point, b: Point): Line =
    Line(Point((a.x + b.x) / 2.0, (a.y + b.y) / 2.0),
        (atan((a.y - b.y) / (a.x - b.x)) + PI / 2.0).let { if (it >= PI) it - PI else it })

// ok но переписать if
fun bisectorByPoints2(a: Point, b: Point): Line =
    Line(
        Point((a.x + b.x) / 2, (a.y + b.y) / 2),
        if (atan((a.y - b.y) / (a.x - b.x)) + PI / 2.0 >= PI) (atan((a.y - b.y) / (a.x - b.x)) + PI / 2.0) - PI
        else (atan((a.y - b.y) / (a.x - b.x)) + PI / 2.0)
    )


// ok 18.02.2020 13:30 добавил два теста и много отл печати мб ошибки на отриц углах
fun bisectorByPoints1(a: Point, b: Point): Line {
    val c: Point = Point((a.x + b.x) / 2, (a.y + b.y) / 2)
    println(" ${a.toString()},  ${b.toString()},  ${c.toString()}")
    val x = a.x - b.x
    val y = a.y - b.y
    var angle = atan(y / x)
    println(angle)
    angle = angle + PI / 2.0
    println(angle)
    if (angle >= PI) angle = angle - PI
    println("x= $x y=$y angel = $angle")

    return Line(c, angle)

}

/**
 * Средняя  *************************  09  ******************************
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
// ok НО мнен не нравится сама идею - должно быть проще, т.к. это средняя
// строю Pair mapом через flat с поиком минимума
fun findNearestCirclePair2(vararg circles: Circle): Pair<Circle, Circle> =
    circles.map { cr -> circles.map { crc -> Pair(cr, crc) } }.flatMap { it }
        .minBy { (cr, crc) -> if (cr != crc) cr.distance(crc) else Double.MAX_VALUE }
        ?: throw IllegalArgumentException()
// берем varrang входной как List ( Окружностей)
// Строю из List окружностей Пары окружностей во всех возможный комбинайиях
// Получается List Listов Пар поэтому Flat в плоский List Пар(окр,окр)
// ищу пару с мин рассотянием между ее окружностями
// Использую функцию "расст между двумя окружн" сделанную выше - ранее
// НО не нужны в поиске пары окружности с самой собой - поэтому IF
// Бросить искл если не нашлось такой пары

fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    if (circles.count() < 2) throw IllegalArgumentException()
    var rezPar: Pair<Circle, Circle> = Pair(circles[0], circles[1])
    var minDist = Double.MAX_VALUE
    for (cr in circles)
        for (crc in circles)
            if (cr != crc && cr.distance(crc) < minDist) {   // саму с собой окр не мерять
                minDist = cr.distance(crc)
                rezPar = Pair(cr, crc)
            }
    return rezPar
}


/**
 * Сложная   *************************  10  ******************************
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
// ok тест проходит 18,02,2020 17:25
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle =
    Circle(
        bisectorByPoints(a, b).crossPoint(bisectorByPoints(b, c)),
        bisectorByPoints(a, b).crossPoint(bisectorByPoints(b, c)).distance(b)
    )

// ok тест проходит 18,02,2020 17:25
fun circleByThreePoints1(a: Point, b: Point, c: Point): Circle //= TODO()
{
    println("$a, $b, $c")
    val biserab = bisectorByPoints(a, b)
    val biserbc = bisectorByPoints(b, c)
    val cent = biserab.crossPoint(biserbc)
    val diam = cent.distance(b)
    val cir = Circle(cent, diam)
    println("Центр = $cir Диаметр = $diam")
    return cir
}

/**
 * Очень сложная *************************  11  ******************************
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
// Наверху есть две самые удаленные точки из множества:
//ok 18.02.2020 21:20 Можно переделать в ФВП
fun minContainingCircle(vararg points: Point): Circle
{
    var circleRez: Circle = Circle(points[0], 0.0)
    var minRad = Double.MAX_VALUE
    for (v1 in points)
        for (v2 in points)
// Проверка три точки - А вдруг, тогда мин
            for (v3 in points) {
                if (v3 == v1 || v3 == v2 || v1 == v2) continue
                val circleV123 = circleByThreePoints(v1, v2, v3)
                if (circleV123.radius > minRad) continue
                if (!points.all { circleV123.contains(it) }) continue
                circleRez = circleV123
                minRad = circleRez.radius
            }
// Проверка на отрезок - за один шаг сразу на макс отрезок А вдруг, тогда мин
    val maxSeg = diameter(*points)
    val circleV12 = circleByDiameter(maxSeg)
    if (maxSeg.begin.distance(maxSeg.end) / 2 < minRad)
        if (points.all { circleV12.contains(it) }) {
            circleRez = circleV12
            minRad = circleRez.radius
        }
    println("minContainingCircle= $minRad Circle = $circleRez")
    return circleRez
}

//ok 18.02.2020 20:52
fun minContainingCircle3(vararg points: Point): Circle
{
    var circleRez: Circle = Circle(points[0], 0.0)
    var minRad = Double.MAX_VALUE
    for (v1 in points)
        for (v2 in points) {
            if (v1 == v2) continue
// Проверка три точки - А вдруг, тогда мин
            for (v3 in points) {
                if (v3 == v1 || v3 == v2) continue
                val circleV123 = circleByThreePoints(v1, v2, v3)
                if (circleV123.radius > minRad) continue
                if (!points.all { circleV123.contains(it) }) continue
                circleRez = circleV123
                minRad = circleRez.radius
            }
// Проверка на отрезок - А вдруг, тогда мин
            if (v1.distance(v2) / 2 > minRad) continue
            val circleV12 = Circle(Point((v1.x + v2.x) / 2, (v1.y + v2.y) / 2), v1.distance(v2) / 2)
            if (!points.all { circleV12.contains(it) }) continue
            circleRez = circleV12
            minRad = circleRez.radius
        }
    return circleRez
}

// Наверху есть функция contains для Circle и через нее:
//ok 18.02.2020 20:22
fun minContainingCircle2(vararg points: Point): Circle
{
    var circleRez: Circle = Circle(points[0], 0.0)
    var minRad = Double.MAX_VALUE
    for (v1 in points)
        for (v2 in points)
            for (v3 in points) {
                if (v1 == v2 || v2 == v3 || v1 == v3) continue
                if (circleByThreePoints(v1, v2, v3).radius > minRad) continue
                if (!points.all { circleByThreePoints(v1, v2, v3).contains(it) }) continue
                circleRez = circleByThreePoints(v1, v2, v3)
                minRad = circleRez.radius
            }
    // на этом дала зеленый но это обман
    // требуется еще эта проверка на минимальный диаметр/2 содержит все точки:
    for (v1 in points)
        for (v2 in points) {
            if (v1 == v2) continue
            if (v1.distance(v2) / 2 > minRad) continue
            if (!points.all { Circle( Point((v1.x + v2.x) / 2, (v1.y + v2.y) / 2),v1.distance(v2) / 2).contains(it) }) continue
            circleRez = Circle(Point((v1.x + v2.x) / 2, (v1.y + v2.y) / 2), v1.distance(v2) / 2)
            minRad = circleRez.radius
        }
    return circleRez
}

// ok 18.02.2020 19:50  - работает но ощущуние что не все: мало проверок
fun minContainingCircle1(vararg points: Point): Circle //= TODO()
{
    var circleRez: Circle = Circle(points[0], 0.0)
    var minRad = Double.MAX_VALUE
    for (v1 in points)
        for (v2 in points)
            for (v3 in points) {
                if (v1 == v2 || v2 == v3 || v1 == v3) continue
                if (circleByThreePoints(v1, v2, v3).radius > minRad) continue
                var cont = true
                for (v4 in points)
                    if(v4.distance(circleByThreePoints(v1, v2, v3).center) >
                        circleByThreePoints(v1, v2, v3).radius) cont = false
                if (!cont) continue
                circleRez = circleByThreePoints(v1, v2, v3)
                minRad = circleRez.radius
            }
    // на этом дала зеленый но это обман
    // требуется еще эта проверка на минимальный диаметр/2 содержит все точки:
    for (v1 in points)
        for (v2 in points) {
            if (v1 == v2) continue
            if (v1.distance(v2) / 2 > minRad) continue
            var cont = true
            for (v4 in points)
                if (v4.distance(Point((v1.x + v2.x) / 2, (v1.y + v2.y) / 2)) > v1.distance(v2) / 2) cont = false
            if (!cont) continue
            circleRez = Circle(Point((v1.x + v2.x) / 2, (v1.y + v2.y) / 2), v1.distance(v2) / 2)
            minRad = circleRez.radius
        }
    return circleRez
}


