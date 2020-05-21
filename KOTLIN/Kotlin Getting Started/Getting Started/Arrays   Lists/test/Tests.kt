import org.junit.Assert.*
import org.junit.Test

class Test {
    @Test fun testList() {
        assertEquals(listOf("A", "B", "C"), createStringList())
    }

    @Test fun testArray() {
        assertArrayEquals(arrayOf("A", "B", "C"), createStringArray())
    }
}