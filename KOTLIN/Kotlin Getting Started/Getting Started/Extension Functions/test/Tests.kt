import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class Test {
    @Test fun testSolution() {
        val string = "hElLo"
        assertEquals("HeLlO", string.invertCase())
    }
}