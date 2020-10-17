package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

//  . Robolectric - это библиотека, которая создает смоделированную среду Android для тестов
//  и работает быстрее, чем загрузка эмулятора или запуск на устройстве
// Always show the result of every unit test when running via command line, even if it passes.
//testOptions.unitTests { includeAndroidResources = true}
//  вместо @Config(manifest= Config.NONE)
// Если вам нужно запустить смоделированный код Android в test исходном наборе,
// вы можете добавить зависимость Robolectric и @RunWith(AndroidJUnit4::class)аннотацию.


//@Config(sdk = [Build.VERSION_CODES.O_MR1])
@Config(manifest= Config.NONE)
@RunWith(AndroidJUnit4::class)
class TasksViewModelTest{

    // Executes each task synchronously using Architecture Components.
    // Выполняет каждую задачу синхронно с использованием компонентов архитектуры.
    //  Когда вы пишете тесты, включающие тестирование LiveData, используйте это правило!
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Subject under test
    private lateinit var tasksViewModel: TasksViewModel
 // XXXXX  val tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext()) xxx- нужен свежий

    @Before
    fun setupViewModel() {
        tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun addNewTask_setsNewTaskEvent() {

        // Given a fresh ViewModel Учитывая свежий взгляд модели
        // в @Before val tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())

        // When adding a new task При добавлении новой задачи
        tasksViewModel.addNewTask()

        // На этом шаге вы используете getOrAwaitValue метод и пишете инструкцию assert,
        // которая проверяет, newTaskEvent был ли запущен.
        // Then the new task event is triggered Затем запускается новое событие задачи
        // тодо test LiveData см LiveDataTestUtil
        val value = tasksViewModel.newTaskEvent.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled(), not(nullValue()))
    // Что такое getContentIfNoteHandled?
    //В приложении TO-DO вы используете настраиваемый Event класс для LiveData представления одноразовых событий
    // (таких как навигация или всплывающая закусочная) getContentIfNotHandled предоставляет «разовую» возможность.
    // При первом вызове он получает содержимое файла Event.
    // Любые дополнительные вызовы getContentIfNotHandled того же контента будут возвращены null.
    // Вот как Event осуществляется доступ к данным в коде приложения, и поэтому мы используем его для тестов.
    // Вы можете узнать больше о событиях здесь .
    // https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150

    }
    @Test
    fun setFilterAllTasks_tasksAddViewVisible() {

        // Given a fresh ViewModel
        // в @Before val tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())

        // When the filter type is ALL_TASKS
        tasksViewModel.setFiltering(TasksFilterType.ALL_TASKS)

        // Then the "Add task" action is visible
        assertThat(tasksViewModel.tasksAddViewVisible.getOrAwaitValue(), `is`(true))
    }
}


/* Это много шаблонного кода, чтобы увидеть сингл LiveData в тесте!
@Test
fun addNewTask_setsNewTaskEvent() {
    // Given a fresh ViewModel Учитывая свежий взгляд модели
    val tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())
    // Create observer - no need for it to do anything! Создайте наблюдателя - ему не нужно ничего делать!
    val observer = Observer<Event<Unit>> {}
    try {
        // Observe the LiveData forever Наблюдайте за живыми данными вечно
        tasksViewModel.newTaskEvent.observeForever(observer)
        // When adding a new task При добавлении новой задачи
        tasksViewModel.addNewTask()
        // Then the new task event is triggered Затем запускается новое событие задачи
        val value = tasksViewModel.newTaskEvent.value
        assertThat(value?.getContentIfNotHandled(), (not(nullValue())))
    } finally {
        // Whatever happens, don't forget to remove the observer! Что бы ни случилось, не забудьте убрать наблюдателя!
        tasksViewModel.newTaskEvent.removeObserver(observer)
    }
}
 */