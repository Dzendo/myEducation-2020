package com.example.android.architecture.blueprints.todoapp.tasks

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

//@Config(sdk = [Build.VERSION_CODES.O_MR1])
@Config(manifest= Config.NONE)
@RunWith(AndroidJUnit4::class)
class TasksViewModelTest{
    @Test
    fun addNewTask_setsNewTaskEvent() {

        // Given a fresh ViewModel
        val tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())

        // When adding a new task
        tasksViewModel.addNewTask()

        // Then the new task event is triggered
        // TODO test LiveData
    }
}