import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class Test {
    @Test fun testSolution() {

        assertTrue(age is Int)
        assertTrue(size is Double)
        assertTrue(name is String)

        assertEquals(18, age)
        assertEquals(182.5, size, 0.01)
        assertEquals("Hans", name)
    }
}