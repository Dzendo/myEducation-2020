import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class Test {

    private val outStream = ByteArrayOutputStream()
    private val originalOut = System.out

    @Before
    fun setUpStreams() {
        System.setOut(PrintStream(outStream))
    }

    @After
    fun restoreStreams() {
        System.setOut(originalOut)
    }

    @Test fun testSmiley() {
        val smiley = Smiley()
        assertTrue(smiley is Drawable)
        smiley.draw()
        assertEquals(":-)", outStream.toString())
    }

    @Test fun testHairySmiley() {
        val smiley = HairySmiley()
        assertTrue(smiley is Drawable)
        assertTrue(smiley is Smiley)
        smiley.draw()
        assertEquals("?:-)", outStream.toString())
    }
}