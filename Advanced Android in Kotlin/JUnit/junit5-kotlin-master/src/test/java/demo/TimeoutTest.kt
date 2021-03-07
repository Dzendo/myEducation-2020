package demo

import org.junit.jupiter.api.Assertions.assertTimeout
import org.junit.jupiter.api.Assertions.assertTimeoutPreemptively
import org.junit.jupiter.api.Test
import java.time.Duration.ofMillis

class TimeoutTest {

    @Test
    fun `Timeout not exceeded`() {
        // Тест упадёт после выполнения лямбда-выражения, если оно превысит 1000 мс
        assertTimeout(ofMillis(1000)) {
            print("Выполняется операция, которая займёт не больше 1 секунды")
            Thread.sleep(3)
        }
    }

    @Test
    fun `Timeout not exceeded with preemptively exit`() {
        // Тест упадёт, как только время выполнения превысит 1000 мс
        assertTimeoutPreemptively(ofMillis(1000)) {
            print("Выполняется операция, которая займёт не больше 1 секунды")
            Thread.sleep(3)
        }
    }
}