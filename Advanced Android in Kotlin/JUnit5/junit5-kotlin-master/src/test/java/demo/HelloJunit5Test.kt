package demo

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class HelloJunit5Test {

    @Test
    fun `First test`() {
        print("Hello, JUnit5!")
    }

    @DisplayName("\uD83D\uDC4D")
    @Test
    fun `First test ╯°□°）╯`() {
        print("Hello, JUnit5!")
    }

    @Test
    fun `Base assertions`() {
        assertEquals("a", "a")
        assertEquals(2, 1 + 1, "Optional message")
        assertEquals(2, 1 + 1, { "Assertion message " + "can be lazily evaluated" })
    }

    @Test
    fun `Grouped assertions`() {
        val person = Person("John", "Doe", null)
        assertAll("person",
                Executable { assertEquals("John", person.firstName) },
                Executable { assertEquals("Doe", person.lastName) }
        )
    }

    @Test
    fun `Test exception`() {
        val exception: Exception = assertThrows(IllegalArgumentException::class.java, {
            throw IllegalArgumentException("exception message")
        })
        assertEquals("exception message", exception.message)
    }

    @Test
    fun `Test assertTrue with reference and lambda`() {
        val list = listOf("")
        assertTrue(list::isNotEmpty)
        assertTrue {
            !list.contains("a")
        }
    }
}
