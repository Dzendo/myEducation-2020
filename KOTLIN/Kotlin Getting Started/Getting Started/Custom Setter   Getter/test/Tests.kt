import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import java.lang.IllegalArgumentException


class Test {

    @Rule
    @JvmField
    var exceptionRule = ExpectedException.none()


    @Test
    fun testSolutionValid() {
        val frac = Fraction(2, 1)
    }


    @Test
    fun testSolutionInvalidConstructor() {
        exceptionRule.expect(IllegalArgumentException::class.java)
        val fracInvalid = Fraction(2, 0)
    }


    @Test
    fun testSolutionInvalidSet() {
        val frac = Fraction(2, 1)
        exceptionRule.expect(IllegalArgumentException::class.java)
        frac.denominator = 0
    }
}