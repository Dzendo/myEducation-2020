/*
 * Copyright 2018, The Android Open Source Project
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

package com.example.android.trackmysleepquality.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.formatNights
import kotlinx.coroutines.*

/**
 * ViewModel for SleepTrackerFragment.
 * ViewModel для фрагмента трекера сна.
 */
class SleepTrackerViewModel(
        val database: SleepDatabaseDao,
        application: Application) : AndroidViewModel(application) {

    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     * задание viewModel позволяет нам отменить все сопрограммы, запущенные этой ViewModel.
     */
    private var viewModelJob = Job()

    /**
     * A [CoroutineScope] keeps track of all coroutines started by this ViewModel.
     * Область действия [сопрограммы] отслеживает все сопрограммы, запущенные этой моделью представления.
     *
     * Because we pass it [viewModelJob], any coroutine started in this uiScope can be cancelled
     * by calling `viewModelJob.cancel()`
     * Поскольку мы передаем его [viewModelJob], любая сопрограмма, запущенная в этом uiScope, может быть отменена
     * вызывая ' viewModel Job.отменять()`
     *
     * By default, all coroutines started in uiScope will launch in [Dispatchers.Main] which is
     * the main thread on Android. This is a sensible default because most coroutines started by
     * a [ViewModel] update the UI after performing some processing.
     * По умолчанию все сопрограммы, запущенные в uiScope, запускаются в [Dispatchers.Главное] что есть
     * основной поток на Android. Это разумное значение по умолчанию, потому что большинство сопрограмм начинаются с
     * a [ViewModel] обновляет пользовательский интерфейс после выполнения некоторой обработки.
     */
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var tonight = MutableLiveData<SleepNight?>()

    val nights = database.getAllNights()

    /**
     * Converted nights to Spanned for displaying.
     * Преобразованные ночи в Spanned для отображения.
     */
    val nightsString = Transformations.map(nights) { nights ->
        formatNights(nights, application.resources)
    }

    /**
     * If tonight has not been set, then the START button should be visible.
     * Если сегодня вечером не было установлено, то кнопка запуска должна быть видна.
     */
    val startButtonVisible = Transformations.map(tonight) {
        null == it
    }

    /**
     * If tonight has been set, then the STOP button should be visible.
     * Если сегодня вечером был установлен, то кнопка остановки должна быть видна.
     */
    val stopButtonVisible = Transformations.map(tonight) {
        null != it
    }

    /**
     * If there are any nights in the database, show the CLEAR button.
     */
    val clearButtonVisible = Transformations.map(nights) {
        it?.isNotEmpty()
    }

    /**
     * Request a toast by setting this value to true.
     * Запросите тост, установив это значение в true.
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     * Это личное, потому что мы не хотим раскрывать установку этого значения для фрагмента.
     */
    private var _showSnackbarEvent = MutableLiveData<Boolean>()

    /**
     * If this is true, immediately `show()` a toast and call `doneShowingSnackbar()`.
     * Если это так, немедленно "покажите ()" тост и вызовите " готово показать снэк-бар ()"
     */
    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent

    /**
     * Variable that tells the Fragment to navigate to a specific [SleepQualityFragment]
     * Переменная, указывающая фрагменту перейти к определенному фрагменту [качество сна]
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     * Это личное, потому что мы не хотим раскрывать установку этого значения для фрагмента.
     */

    private val _navigateToSleepQuality = MutableLiveData<SleepNight>()


    private val _navigateToSleepDataQuality = MutableLiveData<Long>()
    val navigateToSleepDataQuality
        get() = _navigateToSleepDataQuality
    /**
     * Call this immediately after calling `show()` on a toast.
     * Вызовите это сразу же после вызова "show ()" на тосте.
     *
     * It will clear the toast request, so if the user rotates their phone it won't show a duplicate
     * toast.
     * Он очистит запрос тостов, поэтому, если пользователь повернет свой телефон, он не покажет дубликат
     * тост.
     */

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }
    /**
     * If this is non-null, immediately navigate to [SleepQualityFragment] and call [doneNavigating]
     * Если это ненулевое значение, немедленно перейдите к [фрагмент качества сна] и вызовите [done Navigating]
     */
    val navigateToSleepQuality: LiveData<SleepNight>
        get() = _navigateToSleepQuality

    /**
     * Call this immediately after navigating to [SleepQualityFragment]
     * Вызовите это сразу после перехода к [фрагмент качества сна]
     *
     * It will clear the navigation request, so if the user rotates their phone it won't navigate
     * twice.
     * Он очистит навигационный запрос, поэтому, если пользователь повернет свой телефон, он не будет перемещаться
     * дважды.
     */
    fun doneNavigating() {
        _navigateToSleepQuality.value = null
    }

    init {
        initializeTonight()
    }

    private fun initializeTonight() {
        uiScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }

    /**
     *  Handling the case of the stopped app or forgotten recording,
     *  the start and end times will be the same.j
     *  Обработка случая остановленного приложения или забытой записи,
     * время начала и окончания будет одинаковым.
     *
     *  If the start time and end time are not the same, then we do not have an unfinished
     *  recording.
     *  Если время начала и время окончания не совпадают, то у нас нет незавершенного проекта.
     * запись.
     */
    private suspend fun getTonightFromDatabase(): SleepNight? {
        return withContext(Dispatchers.IO) {
            var night = database.getTonight()
            if (night?.endTimeMilli != night?.startTimeMilli) {
                night = null
            }
            night
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    private suspend fun update(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.update(night)
        }
    }

    private suspend fun insert(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.insert(night)
        }
    }

    /**
     * Executes when the START button is clicked.
     * Выполняется при нажатии кнопки Пуск.
     */
    fun onStartTracking() {
        uiScope.launch {
            // Create a new night, which captures the current time,
            // and insert it into the database.
            val newNight = SleepNight()

            insert(newNight)

            tonight.value = getTonightFromDatabase()
        }
    }

    /**
     * Executes when the STOP button is clicked.
     * Выполняется при нажатии кнопки STOP.
     */
    fun onStopTracking() {
        uiScope.launch {
            // In Kotlin, the return@label syntax is used for specifying which function among
            // several nested ones this statement returns from.
            // In this case, we are specifying to return from launch(),
            // not the lambda.
            val oldNight = tonight.value ?: return@launch

            // Update the night in the database to add the end time.
            oldNight.endTimeMilli = System.currentTimeMillis()

            update(oldNight)

            // Set state to navigate to the SleepQualityFragment.
            _navigateToSleepQuality.value = oldNight
        }
    }

    /**
     * Executes when the CLEAR button is clicked.
     * Выполняется при нажатии кнопки Очистить.
     */
    fun onClear() {
        uiScope.launch {
            // Clear the database table.
            clear()

            // And clear tonight since it's no longer in the database
            tonight.value = null
        }

        // Show a snackbar message, because it's friendly.
        _showSnackbarEvent.value = true
    }

    /**
     * Called when the ViewModel is dismantled.
     * At this point, we want to cancel all coroutines;
     * otherwise we end up with processes that have nowhere to return to
     * using memory and resources.
     * Выполняется при нажатии кнопки Очистить.Вызывается при демонтаже ViewModel.
     * На этом этапе мы хотим отменить все сопрограммы;
     * в противном случае мы имеем дело с процессами, которым некуда возвращаться
     * использование памяти и ресурсов.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    //07.4.5 Задача: обрабатывать щелчки элементов
    // Шаг 1: навигация по клику
    // функцию обработчика щелчков.
    fun onSleepNightClicked(id: Long){
        _navigateToSleepDataQuality.value = id
    }
    // Определите метод для вызова после завершения навигации приложения
    fun onSleepDataQualityNavigated() {
        _navigateToSleepDataQuality.value = null
    }
    // Откройте SleepTrackerFragment.kt и прокрутите вниз до кода,
    // который создает адаптер и определяет SleepNightListener отображение тоста
}
