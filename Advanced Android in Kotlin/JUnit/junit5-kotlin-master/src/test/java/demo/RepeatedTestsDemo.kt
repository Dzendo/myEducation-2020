package demo

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.RepetitionInfo
import org.junit.jupiter.api.TestInfo

class RepeatedTestsDemo {

    @RepeatedTest(10, name = "{displayName} {currentRepetition} из {totalRepetitions}")
    fun `Повторяемый тест`() {

    }

    @RepeatedTest(5)
    fun `Repeated test with repetition info and test info`(repetitionInfo: RepetitionInfo, testInfo: TestInfo) {
        assertEquals(5, repetitionInfo.totalRepetitions)
        val testDisplayNameRegex = """repetition \d of 5""".toRegex()
        assertTrue(testInfo.displayName.matches(testDisplayNameRegex))
    }
}