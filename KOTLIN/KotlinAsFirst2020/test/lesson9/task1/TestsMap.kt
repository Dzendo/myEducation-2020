package lesson9.task1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class TestsMap {
    @Test
    @Tag("Easy")
    fun createMatrixMap() {
        val matrix = createMatrixMap(4, 6, 0.0)
        assertEquals(4, matrix.height)
        assertEquals(6, matrix.width)
    }

    @Test
    @Tag("Normal")
    fun getSetInt() {
        val matrix = createMatrixMap(3, 2, 0)
        var value = 0
        for (row in 0 until matrix.height) {
            for (column in 0 until matrix.width) {
                matrix[row, column] = value++
            }
        }
        value = 0
        for (row in 0 until matrix.height) {
            for (column in 0 until matrix.width) {
                assertEquals(value++, matrix[CellMap(row, column)])
            }
        }
    }

    @Test
    @Tag("Normal")
    fun getSetString() {
        val matrix = createMatrixMap(2, 2, "")
        val strings = listOf("alpha", "beta", "gamma", "omega")
        var index = 0
        for (row in 0 until matrix.height) {
            for (column in 0 until matrix.width) {
                matrix[CellMap(row, column)] = strings[index++]
            }
        }
        index = 0
        for (row in 0 until matrix.height) {
            for (column in 0 until matrix.width) {
                assertEquals(strings[index++], matrix[row, column])
            }
        }
    }
}