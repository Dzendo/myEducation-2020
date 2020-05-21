import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class Test {
    @Test fun testParam() {
        val firstName = "Peter"
        val lastName = "Petersen"
        val birthName = "Lustig"
        assertEquals("$firstName $lastName ($birthName)", getFullName(firstName, lastName, birthName))
    }

    @Test fun testDefault() {
        val firstName = "Peter"
        val lastName = "Petersen"
        assertEquals("$firstName $lastName ($lastName)", getFullName(firstName, lastName))
    }
}