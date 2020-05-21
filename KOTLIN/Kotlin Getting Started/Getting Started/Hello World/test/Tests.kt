import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import org.junit.Test



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

    @Test
    fun testSolution() {
        main()
        assertTrue(outStream.toString().contains("Hello World"))
    }
}