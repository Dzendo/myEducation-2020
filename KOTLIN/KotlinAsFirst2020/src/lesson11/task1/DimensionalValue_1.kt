@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

/**
 * Класс "Величина с размерностью".
 *
 * Предназначен для представления величин вроде "6 метров" или "3 килограмма"
 * Общая сложность задания - средняя.
 * Величины с размерностью можно складывать, вычитать, делить, менять им знак.
 * Их также можно умножать и делить на число.
 *
 * В конструктор передаётся вещественное значение и строковая размерность.
 * Строковая размерность может:
 * - либо строго соответствовать одной из abbreviation класса Dimension (m, g)
 * - либо соответствовать одной из приставок, к которой приписана сама размерность (Km, Kg, mm, mg)
 * - во всех остальных случаях следует бросить IllegalArgumentException
 */
class DimensionalValue_1(value: Double, dimension: String) : Comparable<DimensionalValue> {


    val _value = value
    val _dimension = dimension
    var dimDivision = 1.0
    var basDimension = ""
    var preDimension = ""
    var baseDimension: Dimension
    var prefixDimension: DimensionPrefix = DimensionPrefix.KILO
   // var baseDimension1: Dimension

    init {
      //  println("_value= $_value _dimension= $_dimension _dimension.length= ${_dimension.length}  _dimension[0] = ${_dimension[0]}")
        if (dimension.length <= 0 || dimension.length > 2) throw  IllegalArgumentException()
        if (dimension.length == 1)
            if (dimension[0] != 'm' && dimension[0] != 'g') throw  IllegalArgumentException()
        if (dimension.length == 2)
            if (dimension[0] != 'K' && dimension[0] != 'm') throw  IllegalArgumentException()
        if (dimension.length == 2) {
            dimDivision *= if (dimension[0] == 'K') 1000.0 else 0.001
            preDimension = dimension[0].toString()
            prefixDimension = if (preDimension == "K") DimensionPrefix.KILO else DimensionPrefix.MILLI
        }
        basDimension = dimension[dimension.lastIndex].toString()
        baseDimension = if (basDimension == "g") Dimension.GRAM else Dimension.METER
//        baseDimension1 = Dimension.valueOf(basDimension)


       // println(" dimDivision =$dimDivision baseDimension=$baseDimension preDimension=$preDimension")
      // println("Dimension = ${baseDimension1.name}")
    }

    /**
     * Величина с БАЗОВОЙ размерностью (например для 1.0Kg следует вернуть результат в граммах -- 1000.0)
     */
    val value: Double get() = this._value * dimDivision

    /**
     * БАЗОВАЯ размерность (опять-таки для 1.0Kg следует вернуть GRAM)
     */
    val dimension: Dimension get() = baseDimension

    /**
     * Конструктор из строки. Формат строки: значение пробел размерность (1 Kg, 3 mm, 100 g и так далее).
     */
    @Suppress("UNREACHABLE_CODE")
    constructor(s: String) : this(s.split(' ')[0].toDouble(), s.split(' ')[1])

    /**
     * Сложение с другой величиной. Если базовая размерность разная, бросить IllegalArgumentException
     * (нельзя складывать метры и килограммы)
     */
    operator fun plus(other: DimensionalValue): DimensionalValue =
        if (this.dimension != other.dimension) throw IllegalArgumentException()
        else DimensionalValue(this.value + other.value, this.basDimension)
    /**
     * Смена знака величины
     */
    operator fun unaryMinus(): DimensionalValue = DimensionalValue(this._value * (-1.0), this.dimension.abbreviation)

    /**
     * Вычитание другой величины. Если базовая размерность разная, бросить IllegalArgumentException
     */
    operator fun minus(other: DimensionalValue): DimensionalValue =
        if (this.dimension != other.dimension) throw IllegalArgumentException()
        else DimensionalValue(this.value - other.value, this.basDimension)

    /**
     * Умножение на число
     */
    operator fun times(other: Double): DimensionalValue =
        if (this.dimension != dimension) throw IllegalArgumentException()
        else DimensionalValue(this.value * other, this.basDimension)

    /**
     * Деление на число
     */
    operator fun div(other: Double): DimensionalValue =
        if (this.dimension != dimension) throw IllegalArgumentException()
        else DimensionalValue(this.value / other, this.basDimension)


    /**
     * Деление на другую величину. Если базовая размерность разная, бросить IllegalArgumentException
     */
    operator fun div(other: DimensionalValue): Double =
        if (this.dimension != other.dimension) throw IllegalArgumentException()
        else this.value / other.value

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean {
        if (other !is DimensionalValue) return false
        if (this.dimension != other.dimension) return false
        if (this.value != other.value) return false
        return true
    }

    /**
     * Сравнение на больше/меньше. Если базовая размерность разная, бросить IllegalArgumentException
     */
    override fun compareTo(other: DimensionalValue): Int {
        if (this.dimension != other.dimension) throw IllegalArgumentException()
        return when {
            this.value - other.value > 1e-8 -> 1
            this.value - other.value < -1e-8 -> -1
            else -> 0
        }
    }

    override fun hashCode(): Int {
        var result = _value.hashCode()
        result = 31 * result + dimension.hashCode()
        result = 31 * result + dimDivision.hashCode()
        result = 31 * result + basDimension.hashCode()
        result = 31 * result + preDimension.hashCode()
        result = 31 * result + baseDimension.hashCode()
        result = 31 * result + prefixDimension.hashCode()
        return result
    }

}

/**
 * Размерность. В этот класс можно добавлять новые варианты (секунды, амперы, прочие), но нельзя убирать
 */
enum class Dimension_1(val abbreviation: String) {
    METER("m"),
    GRAM("g");
}

/**
 * Приставка размерности. Опять-таки можно добавить новые варианты (деци-, санти-, мега-, ...), но нельзя убирать
 */
enum class DimensionPrefix_1(val abbreviation: String, val multiplier: Double) {
    KILO("K", 1000.0),
    MILLI("m", 0.001);
}