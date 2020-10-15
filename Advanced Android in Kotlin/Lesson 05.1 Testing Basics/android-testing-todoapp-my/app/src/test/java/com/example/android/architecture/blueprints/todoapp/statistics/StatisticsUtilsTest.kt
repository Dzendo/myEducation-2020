package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.hamcrest.core.Is.`is`
//import org.hamcrest.Matchers.`is`
import org.junit.Assert.*
import org.junit.Test

/**
 * Given, When, Then - Учитывая, когда, тогда
 *
 * Given: Setup the objects and the state of the app that you need for your test.
 *      For this test, what is "given" is that you have a list of tasks where the task is active.
 * When: Do the actual action on the object you're testing.
 *      For this test, it means calling getActiveAndCompletedStats.
 * Then: This is where you actually check what happens when you do the action where you check if the test passed or failed.
 *      This is usually a number of assert function calls.
 *      For this test, it is the two asserts that check you have the correct active and completed percentages.
 *
 * Дано: настройка объектов и состояния приложения, которые вам нужны для вашего теста.
 *      Для этого теста "дано" то, что у вас есть список задач, в которых эта задача активна.
 * Когда: выполните фактическое действие на объекте, который вы тестируете.
 *      Для этого теста это означает вызов получить активную и завершенную статистику.
 * Затем: здесь вы фактически проверяете, что происходит, когда вы выполняете действие, в котором вы проверяете, прошел ли тест или провалился.
 *      Обычно это несколько вызовов функций assert.
 *      Для этого теста именно два утверждения проверяют, что у вас есть правильные активные и завершенные проценты.
 *
 * Note that the" Arrange, Act, Assert" (AAA) testing mnemonic is a similar concept.
 * мнемоника тестирования" организовать, действовать, утверждать" (AAA) - это аналогичная концепция.
 */

class StatisticsUtilsTest{

    @Test
    fun getActiveAndCompletedStatsStart_noCompleted_returnsHundredZero() {
       // subjectUnderTest_actionOrInput_resultState

        // Create an active task (the false makes this active)
        // Создайте активную задачу (значение false делает ее активной)

        // Given: Дано:
        // Arrange: организовать
        val tasks = listOf<Task>(
            Task("title", "desc", isCompleted = false)
        )
        // Call your function
        // Вызов функции

        // When: Когда:
        // Act: действовать
        val result = getActiveAndCompletedStatsStart(tasks)

        // Check the result
        // Проверьте результат assert - утверждать

        // Then: Затем:
        // Assert: утверждать

        // REPLACE
        assertEquals(result.completedTasksPercent, 0f)
        assertEquals(result.activeTasksPercent, 100f)

        // WITH
        assertThat(result.activeTasksPercent, `is`(100f))
        assertThat(result.completedTasksPercent, `is`(0f))
    }

    @Test
    fun getActiveAndCompletedStats_noCompleted_returnsHundredZero() {
        val tasks = listOf(
            Task("title", "desc", isCompleted = false)
        )
        // When the list of tasks is computed with an active task
        val result = getActiveAndCompletedStats(tasks)

        // Then the percentages are 100 and 0
        assertThat(result.activeTasksPercent, `is`(100f))
        assertThat(result.completedTasksPercent, `is`(0f))
    }

    @Test
    fun getActiveAndCompletedStats_noActive_returnsZeroHundred() {
        val tasks = listOf(
            Task("title", "desc", isCompleted = true)
        )
        // When the list of tasks is computed with a completed task
        val result = getActiveAndCompletedStats(tasks)

        // Then the percentages are 0 and 100
        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(100f))
    }

    @Test
    fun getActiveAndCompletedStats_both_returnsFortySixty() {
        // Given 3 completed tasks and 2 active tasks
        val tasks = listOf(
            Task("title", "desc", isCompleted = true),
            Task("title", "desc", isCompleted = true),
            Task("title", "desc", isCompleted = true),
            Task("title", "desc", isCompleted = false),
            Task("title", "desc", isCompleted = false)
        )
        // When the list of tasks is computed
        val result = getActiveAndCompletedStats(tasks)

        // Then the result is 40-60
        assertThat(result.activeTasksPercent, `is`(40f))
        assertThat(result.completedTasksPercent, `is`(60f))
    }

    @Test
    fun getActiveAndCompletedStats_error_returnsZeros() {
        // When there's an error loading stats
        val result = getActiveAndCompletedStats(null)

        // Both active and completed tasks are 0
        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(0f))
    }

    @Test
    fun getActiveAndCompletedStats_empty_returnsZeros() {
        // When there are no tasks
        val result = getActiveAndCompletedStats(emptyList())

        // Both active and completed tasks are 0
        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(0f))
    }

}