package com.example.android.architecture.blueprints.todoapp.taskdetail

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.ServiceLocator
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.source.FakeAndroidTestRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith

// @MediumTest- Отмечает тест как интеграционный тест «среднего времени выполнения»
// (по сравнению с @SmallTest модульными тестами и сквозными @LargeTest тестами).
// Это поможет вам сгруппировать и выбрать размер теста для запуска.
//@RunWith(AndroidJUnit4::class)—Используется в любом классе, использующем AndroidX Test.
@MediumTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class TaskDetailFragmentTest {

    private lateinit var repository: TasksRepository

    @Before
    fun initRepository() {
        repository = FakeAndroidTestRepository()
        ServiceLocator.tasksRepository = repository
    }

    @After  // runBlockingTest{
    fun cleanupDb() = runBlocking {
        ServiceLocator.resetRepository()
    }

    // В этой задаче вы собираетесь запустить TaskDetailFragment с помощью библиотеки тестирования AndroidX.
    // FragmentScenario- это класс из AndroidX Test,
    // который обертывает фрагмент и дает вам прямой контроль над жизненным циклом фрагмента для тестирования.
    // Чтобы написать тесты для фрагментов, вы создаете FragmentScenario для тестируемого фрагмента ( TaskDetailFragment).
    @Test
    fun activeTaskDetails_DisplayedInUi() = runBlocking{// runBlockingTest{{
        // GIVEN - Add active (incomplete) task to the DB Создает задачу.
        val activeTask = Task("Active Task", "AndroidX Rocks", false)

        // WHEN - Details fragment launched to display task
        // Создает a Bundle, который представляет аргументы фрагмента для задачи, которые передаются во фрагмент).
        val bundle = TaskDetailFragmentArgs(activeTask.id).toBundle()
        // Функция создает FragmentScenario, с этим пучком и темой.
        launchFragmentInContainer<TaskDetailFragment>(bundle, R.style.AppTheme)
        Thread.sleep(20000)
    // Примечание. Предоставление темы необходимо, поскольку фрагменты обычно получают свою тематику из родительской активности.
    // При использовании FragmentScenario ваш фрагмент запускается внутри общего пустого действия,
    // чтобы он был должным образом изолирован от кода действия
    // (вы просто тестируете код фрагмента, а не связанную с ним активность).
    // Параметр темы позволяет указать правильную тему.

    }

}