import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class Test {
    @Test fun testFirstOrNull() {
        assertNull("".firstCharacterOrNull())
        assertNull(" ".firstCharacterOrNull())
        assertEquals('H', "Hallo".firstCharacterOrNull())
        assertEquals('h', "hallo".firstCharacterOrNull())
    }

    @Test fun testToUpperCase() {
        assertNull(toUpperCaseIfNotNull(null))
        assertEquals("HELLO", toUpperCaseIfNotNull("hello"))
    }
}