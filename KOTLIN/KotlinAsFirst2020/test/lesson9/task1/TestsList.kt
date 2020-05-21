package lesson9.task1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class TestsList {
    @Test
    @Tag("Easy")
    fun createMatrixList() {
        val matrix = createMatrixList(4, 6, 0.0)
        assertEquals(4, matrix.height)
        assertEquals(6, matrix.width)
    }

    @Test
    @Tag("Normal")
    fun getSetInt() {
        val matrix = createMatrixList(3, 2, 0)
        var value = 0
        println()
        for (row in 0 until matrix.height) {
            for (column in 0 until matrix.width) {
                matrix[row, column] = value++
                print(matrix[row, column].toString())
            }
            println()
        }
        println()
        println(matrix)
        value = 0
        for (row in 0 until matrix.height) {
            for (column in 0 until matrix.width) {
                assertEquals(value++, matrix[CellList(row, column)])
            }
        }
    }

    @Test
    @Tag("Normal")
    fun getSetString() {
        val matrix = createMatrixList(2, 2, "")
        val strings = listOf("alpha", "beta", "gamma", "omega")
        var index = 0
        for (row in 0 until matrix.height) {
            for (column in 0 until matrix.width) {
                matrix[CellList(row, column)] = strings[index++]
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