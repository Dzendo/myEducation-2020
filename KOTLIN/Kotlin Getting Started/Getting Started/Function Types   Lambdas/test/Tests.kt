import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class Test {

    @Test fun testSum() {
        val v1 = 1
        val v2 = 6
        assertEquals(v1 + v2, sum(v1, v2))
    }

    @Test fun testOperateOnEachSum() {
        val list = listOf(1, 2, 3, 4, 5)
        val v2 = 2
        assertEquals(list.map { it + v2 }, operateOnEach(list, v2) { val1, val2 -> val1 + val2 })
    }

    @Test fun testOperateOnEachMin() {
        val list = listOf(1, 2, 3, 4, 5)
        val v2 = 2
        assertEquals(list.map { it - v2 }, operateOnEach(list, v2) { val1, val2 -> val1 - val2 })
    }

    @Test fun testSumOnEach() {
        val list = listOf(1, 2, 3, 4, 5)
        val v2 = 2
        assertEquals(list.map { it + v2 }, sumOnEach(list, v2))
    }
}