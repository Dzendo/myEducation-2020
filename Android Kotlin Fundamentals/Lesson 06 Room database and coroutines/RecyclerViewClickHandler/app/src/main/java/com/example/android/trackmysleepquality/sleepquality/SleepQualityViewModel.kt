/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.sleepquality

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for SleepQualityFragment.
 *
 * @param sleepNightKey The key of the current night we are working on.
 */
class SleepQualityViewModel(
        private val sleepNightKey: Long = 0L,
        dataSource: SleepDatabaseDao) : ViewModel() {

    /**
     * Hold a reference to SleepDatabase via its SleepDatabaseDao.
     * Держите ссылку на базу данных Sleep через ее Sleep DatabaseDao.
     */
    val database = dataSource

    /** Coroutine setup variables - Переменные настройки сопрограммы */

    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     * задание viewModel позволяет нам отменить все сопрограммы, запущенные этим ViewModel.
     */
    private val viewModelJob = Job()

    /**
     * A [CoroutineScope] keeps track of all coroutines started by this ViewModel.
     * A [область действия сопрограммы] отслеживает все сопрограммы, запущенные этой моделью представления.
     *
     * Because we pass it [viewModelJob], any coroutine started in this scope can be cancelled
     * by calling `viewModelJob.cancel()`
     * Поскольку мы передаем его [задание viewModel], любая сопрограмма, запущенная в этой области, может быть отменена
     * вызывая ' viewModel Job.отменять()`
     *
     * By default, all coroutines started in uiScope will launch in [Dispatchers.Main] which is
     * the main thread on Android. This is a sensible default because most coroutines started by
     * a [ViewModel] update the UI after performing some processing.
     * По умолчанию все сопрограммы, запущенные в uiScope, запускаются в [Dispatchers.Главное] что это такое
     * основной поток на Android. Это разумное значение по умолчанию, потому что большинство сопрограмм начинаются с
     * a [ViewModel] обновляет пользовательский интерфейс после выполнения некоторой обработки.
     */
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    /**
     * Variable that tells the fragment whether it should navigate to [SleepTrackerFragment].
     * Переменная, которая сообщает фрагменту, следует ли ему перейти к [Sleep Tracker Fragment].
     *
     * This is `private` because we don't want to expose the ability to set [MutableLiveData] to
     * Это "личное", потому что мы не хотим выставлять возможность установки [изменяемых живых данных] на
     * the [Fragment]
     */
    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()

    /**
     * When true immediately navigate back to the [SleepTrackerFragment]
     * Когда значение true немедленно перейдите обратно к фрагменту [Sleep Tracker]
     */
    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToSleepTracker

    /**
     * Cancels all coroutines when the ViewModel is cleared, to cleanup any pending work.
     * Отменяет все сопрограммы, когда ViewModel очищается, чтобы очистить любую ожидающую работу.
     *
     * onCleared() gets called when the ViewModel is destroyed.
     * on Cleared () вызывается при уничтожении ViewModel.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Call this immediately after navigating to [SleepTrackerFragment]
     * Вызовите это сразу же после перехода к [фрагменту трекера сна].
     */
    fun doneNavigating() {
        _navigateToSleepTracker.value = null
    }

    /**
     * Sets the sleep quality and updates the database.
     * Устанавливает качество сна и обновляет базу данных.
     *
     * Then navigates back to the SleepTrackerFragment.
     * Затем переходит обратно к фрагменту SleepTracker.
     */
    fun onSetSleepQuality(quality: Int) {
        uiScope.launch {
            // IO is a thread pool for running operations that access the disk, such as
            // our Room database.
            // IO-это пул потоков для выполнения операций, которые обращаются к диску, например
            // наша база данных номеров.
            withContext(Dispatchers.IO) {
                val tonight = database.get(sleepNightKey)
                tonight.sleepQuality = quality
                database.update(tonight)
            }

            // Setting this state variable to true will alert the observer and trigger navigation.
            // Установка этой переменной состояния в true предупредит наблюдателя и вызовет навигацию.
            _navigateToSleepTracker.value = true
        }
    }
}
