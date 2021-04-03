package demo

import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.api.Test

class AssumptionTest {

    @Test
    fun `Test Java 8 installed`() {
        // res0: kotlin.String! = 15
        assumeTrue(System.getProperty("java.version").startsWith("1.8"))
        print("Not too old version")
    }

    @Test
    fun `Test Java 7 installed`() {
        // res0: kotlin.String! = 15
        assumeTrue(System.getProperty("java.version").startsWith("1.7")) {
            "Assumption doesn't hold"
        }
        print("Need to update")
    }
    @Test
    fun `Test Java 15 installed`() {
        // res0: kotlin.String! = 15
        assumeTrue(System.getProperty("java.version").startsWith("15")) {
            "Assumption doesn't hold"
        }
        print("Not too old version 15")
    }
}
