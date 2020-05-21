import java.lang.IllegalArgumentException

class Fraction(val numerator: Int, denominator: Int) {

    var denominator: Int = 1
        set(value) {
            field = if (value != 0) value else throw IllegalArgumentException()
        }
    val decimal: Double
        get() = numerator.toDouble()/denominator


    init {
        this.denominator = denominator
    }

}