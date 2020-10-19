package com.example.android.architecture.blueprints.todoapp.data.source

import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.*
import org.junit.Assert.*

// Используйте runBlockingTest в своих тестовых классах при вызове suspend функции.
@ExperimentalCoroutinesApi
class DefaultTasksRepositoryTest {

    // FakeDataSource переменные-члены (по одной для каждого источника данных для вашего репозитория)
    private lateinit var tasksRemoteDataSource: FakeDataSource
    private lateinit var tasksLocalDataSource: FakeDataSource

    // Class under test
    // переменную, для DefaultTasksRepository которой вы будете тестировать.
    private lateinit var tasksRepository: DefaultTasksRepository

    private val task1 = Task("Title1", "Description1")
    private val task2 = Task("Title2", "Description2")
    private val task3 = Task("Title3", "Description3")
    private val remoteTasks = listOf(task1, task2).sortedBy { it.id }
    private val localTasks = listOf(task3).sortedBy { it.id }
    private val newTasks = listOf(task3).sortedBy { it.id }

    // Создайте метод настройки и инициализации тестируемого объекта DefaultTasksRepository.
    // Это DefaultTasksRepository будет использовать ваш тестовый двойник FakeDataSource.
    // Создайте метод с именем createRepositoryи аннотируйте его с помощью @Before.
    @Before
    fun createRepository() {
        tasksRemoteDataSource = FakeDataSource(remoteTasks.toMutableList())
        tasksLocalDataSource = FakeDataSource(localTasks.toMutableList())
        // Get a reference to the class under test Получите ссылку на тестируемый класс
        tasksRepository = DefaultTasksRepository(
                // TODO Dispatchers.Unconfined should be replaced with Dispatchers.Main
                //  this requires understanding more about coroutines + testing
                //  so we will keep this as Unconfined for now.
                // Диспетчеры TODO.Неограниченные должны быть заменены диспетчерами.Главная
                // это требует большего понимания сопрограмм + тестирования
                //- так что пока мы будем держать это в секрете.

                tasksRemoteDataSource, tasksLocalDataSource, Dispatchers.Unconfined
        )
    }

    @After
    fun tearDown() {
    }

    //  runBlockingTest - функция, предоставляемая тестовой библиотекой сопрограмм
    //  Он принимает блок кода, а затем запускает этот блок кода в специальном контексте сопрограммы,
    //  который выполняется синхронно и немедленно, что означает, что действия будут происходить в детерминированном порядке.
    //  По сути, это заставляет ваши сопрограммы работать как не сопрограммы, поэтому они предназначены для тестирования кода

    @Test
    fun getTasks_requestsAllTasksFromRemoteDataSource()=runBlockingTest {
        // testImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion"
        // When tasks are requested from the tasks repository
        // Когда задачи запрашиваются из репозитория задач
        val tasks = tasksRepository.getTasks(true) as Result.Success

        // Then tasks are loaded from the remote data source
        // Затем задачи загружаются из удаленного источника данных
        assertThat(tasks.data, IsEqual(remoteTasks))
    }
}