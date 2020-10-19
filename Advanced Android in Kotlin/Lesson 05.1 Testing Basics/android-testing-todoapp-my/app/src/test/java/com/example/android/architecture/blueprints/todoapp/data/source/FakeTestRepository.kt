package com.example.android.architecture.blueprints.todoapp.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.runBlocking

// TasksRepositor - интерфейс созданный из DefaultTasksRepository refactor ...
//  FakeTestRepository не нужно использовать FakeDataSources или что-то подобное;
//  ему просто нужно возвращать реалистичные фальшивые выходные данные с учетом входных данных.

// runBlocking против runBlockingTests
// Когда вы находитесь в тестовых классах, то есть в классах с @Test функциями,
// используйте runBlockingTest для получения детерминированного поведения.

// Теперь вы можете использовать FakeTestRepository
// вместо реального репозитория в TasksFragment и TaskDetailFragment.

// поддельный репозиторий для тестирования:
class FakeTestRepository : TasksRepository {
    // Вы будете использовать a LinkedHashMap для хранения списка задач
    var tasksServiceData: LinkedHashMap<String, Task> = LinkedHashMap()
    // и MutableLiveData для наблюдаемых задач.
    private val observableTasks = MutableLiveData<Result<List<Task>>>()

// getTasks—Этот метод должен взять tasksServiceData и превратить его в список
// с помощью, tasksServiceData.values.toList() а затем вернуть это как Success результат.
    override suspend fun getTasks(forceUpdate: Boolean): Result<List<Task>> {
    return Result.Success(tasksServiceData.values.toList())
    }

    // refreshTasks- обновляет значение observableTasksдо того, что возвращает getTasks().
    override suspend fun refreshTasks() {
        observableTasks.value = getTasks()
    }

// observeTasks—Создает сопрограмму с использованием runBlocking и запуском refreshTasks, затем возвращается observableTasks.
    override fun observeTasks(): LiveData<Result<List<Task>>> {
    runBlocking { refreshTasks() }
    return observableTasks
    }

// При тестировании лучше иметь некоторые из них Tasks уже в вашем репозитории.
// Вы можете вызывать saveTask несколько раз, но чтобы упростить задачу,
// добавьте вспомогательный метод специально для тестов, позволяющий добавлять задачи.
// Добавьте addTasks метод, который принимает несколько vararg задач, добавляет каждую из них HashMap, а затем обновляет задачи.
    fun addTasks(vararg tasks: Task) {
        for (task in tasks) {
            tasksServiceData[task.id] = task
        }
        runBlocking { refreshTasks() }
    }

    override suspend fun refreshTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override fun observeTask(taskId: String): LiveData<Result<Task>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTask(taskId: String, forceUpdate: Boolean): Result<Task> {
        TODO("Not yet implemented")
    }

    override suspend fun saveTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun completeTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun completeTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun activateTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun activateTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun clearCompletedTasks() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllTasks() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(taskId: String) {
        TODO("Not yet implemented")
    }
}