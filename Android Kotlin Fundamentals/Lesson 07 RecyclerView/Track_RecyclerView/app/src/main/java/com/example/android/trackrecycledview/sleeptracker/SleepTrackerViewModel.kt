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

package com.example.android.trackrecycledview.sleeptracker

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.android.trackrecycledview.database.SleepDatabaseDao
import com.example.android.trackrecycledview.database.SleepNight
import com.example.android.trackrecycledview.formatNights
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for SleepTrackerFragment.
 */
class SleepTrackerViewModel(
        dataSource: SleepDatabaseDao,
        application: Application) : ViewModel() {

    /**
     * Hold a reference to SleepDatabase via SleepDatabaseDao.
     * Держите ссылку на базу данных Sleep через SleepDatabaseDao.
     */
    val database = dataSource

    /** Coroutine variables - Переменные сопрограммы*/

    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     * задание viewModel позволяет нам отменить все сопрограммы, запущенные этим ViewModel.
     */
    private var viewModelJob = Job()

    /**
     * A [CoroutineScope] keeps track of all coroutines started by this ViewModel.
     * A [область действия сопрограммы] отслеживает все сопрограммы, запущенные этой моделью представления.
     *
     * Because we pass it [viewModelJob], any coroutine started in this uiScope can be cancelled
     * Поскольку мы передаем его [viewModelJob], любая сопрограмма, запущенная в этом uiScope, может быть отменена
     * by calling `viewModelJob.cancel()`
     *
     * By default, all coroutines started in uiScope will launch in [Dispatchers.Main] which is
     * the main thread on Android. This is a sensible default because most coroutines started by
     * a [ViewModel] update the UI after performing some processing.
     * По умолчанию все сопрограммы, запущенные в uiScope, запускаются в [Dispatchers.*Главное] что это такое
     * основной поток на Android. Это разумное значение по умолчанию, потому что большинство сопрограмм начинаются с
     * a [ViewModel] обновляет пользовательский интерфейс после выполнения некоторой обработки.
     */
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var tonight = MutableLiveData<SleepNight?>()

    // 7.1 шаг 5
    //private  Удалить private из nights, потому что вы создадите наблюдателя, которому нужен доступ к этой переменной
    val nights = database.getAllNights()

    /**
     * Converted nights to Spanned for displaying.
     * Преобразованные ночи в растянутые для показа.
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
     * Если в базе данных есть какие-либо ночи, покажите кнопку Очистить.
     */
    val clearButtonVisible = Transformations.map(nights) {
        it?.isNotEmpty()
    }


    /**
     * Request a toast by setting this value to true.
     *Запросите тост, установив это значение в true.
     * This is private because we don't want to expose setting this value to the Fragment.
     * Это личное, потому что мы не хотим выставлять установку этого значения на фрагмент.
     */
    private var _showSnackbarEvent = MutableLiveData<Boolean?>()

    /**
     * If this is true, immediately `show()` a toast and call `doneShowingSnackbar()`.
     *  Если это правда, немедленно "покажите ()" тост и вызовите " готово показывать снэк-бар ()".
     */
    val showSnackBarEvent: LiveData<Boolean?>
        get() = _showSnackbarEvent

    /**
     * Variable that tells the Fragment to navigate to a specific [*SleepQualityFragment]
     * Переменная, указывающая фрагменту на переход к определенному фрагменту [качество сна]
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     * Это личное, потому что мы не хотим выставлять установку этого значения на фрагмент.
     */
    private val _navigateToSleepQuality = MutableLiveData<SleepNight>()

    /**
     * If this is non-null, immediately navigate to [*SleepQualityFragment] and call [doneNavigating]
     * Если это ненулевое значение, немедленно перейдите к [фрагмент качества сна] и вызовите [готово навигация]
     */
    val navigateToSleepQuality: LiveData<SleepNight>
        get() = _navigateToSleepQuality

    /**
     * Call this immediately after calling `show()` on a toast.
     * Вызовите это сразу же после вызова "show ()" на тосте.
     *
     * It will clear the toast request, so if the user rotates their phone it won't show a duplicate toast.
     * Он очистит запрос тоста, поэтому, если пользователь вращает свой телефон, он не будет показывать дубликат тоста.
     */
    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = null
    }

    /**
     * Call this immediately after navigating to [*SleepQualityFragment]
     * Вызовите это сразу же после перехода к [фрагменту качества сна]
     *
     * It will clear the navigation request, so if the user rotates their phone it won't navigate twice.
     * Он очистит навигационный запрос, поэтому, если пользователь вращает свой телефон, он не будет перемещаться дважды.
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
     * время начала и окончания будет одинаковым.j
     *
     *  If the start time and end time are not the same, then we do not have an unfinished recording.
     *  Если время начала и время окончания не совпадают, то у нас нет незавершенного процесса запись.
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

    private suspend fun insert(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.insert(night)
        }
    }

    private suspend fun update(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.update(night)
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    /**
     * Executes when the START button is clicked.
     * Выполняется при нажатии кнопки Пуск.
     */
    fun onStart() {
        uiScope.launch {
            // Create a new night, which captures the current time, and insert it into the database.
            // Создайте новую ночь, которая фиксирует текущее время, и вставьте ее в базу данных.
            val newNight = SleepNight()

            insert(newNight)

            tonight.value = getTonightFromDatabase()
        }
    }

    /**
     * Executes when the STOP button is clicked.
     * Выполняется при нажатии кнопки STOP.
     */
    fun onStop() {
        uiScope.launch {
            // In Kotlin, the return@label syntax is used for specifying which function among
            // several nested ones this statement returns from.
            // In this case, we are specifying to return from launch().
            // В Kotlin синтаксис return@label используется для указания того, какая функция среди
            // несколько вложенных объектов, из которых возвращается этот оператор.
            // В этом случае мы указываем, чтобы вернуться из запуска ().
            val oldNight = tonight.value ?: return@launch

            // Update the night in the database to add the end time.
            // Обновите ночь в базе данных, чтобы добавить время окончания.
            oldNight.endTimeMilli = System.currentTimeMillis()

            update(oldNight)

            // Set state to navigate to the SleepQualityFragment.
            // Установите состояние для перехода к фрагменту качества сна.
            _navigateToSleepQuality.value = oldNight
        }
    }

    /**
     * Executes when the CLEAR button is clicked.
     * Выполняется при нажатии кнопки Очистить.
     */
    fun onClear() {
        uiScope.launch {
            // Clear the database table. Очистите таблицу базы данных.
            clear()

            // And clear tonight since it's no longer in the database
            // И ясно сегодня вечером, так как его больше нет в базе данных
            tonight.value = null

            // Show a snackbar message, because it's friendly.
            // Покажите сообщение закусочной, потому что это дружелюбно.
            _showSnackbarEvent.value = true
        }
    }

    /**
     * Called when the ViewModel is dismantled.
     * At this point, we want to cancel all coroutines;
     * otherwise we end up with processes that have nowhere to return to
     * using memory and resources.
     * Вызывается при демонтаже ViewModel.
     * На данном этапе мы хотим отменить все сопрограммы;
     * в противном случае мы имеем дело с процессами, которым некуда возвращаться
     * использование памяти и ресурсов.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    /**
     * Navigation for the SleepDetail fragment.
     * Навигация для фрагмента деталей сна.
     */

    // Реализовать _navigateToSleepDetail.
    // Как вы делали ранее, определите private MutableLiveData для состояния навигации.
    // И публичный стол, val чтобы пойти с этим.
    private val _navigateToSleepDetail = MutableLiveData<Long>()
        val navigateToSleepDetail
            get() = _navigateToSleepDetail
    //07.4.5 Задача: обрабатывать щелчки элементов
    // Шаг 1: навигация по клику
    // функцию обработчика щелчков.
    fun onSleepNightClicked(id: Long) {
        // активируйте навигацию, установив _navigateToSleepDetail переданную id ночь щелчка
        _navigateToSleepDetail.value = id
    }
    // Определите метод для вызова после завершения навигации приложения
    fun onSleepDetailNavigated() {
        _navigateToSleepDetail.value = null
    }
    // Откройте SleepTrackerFragment.kt и прокрутите вниз до кода,
    // который создает адаптер и определяет SleepNightListener отображение тоста
}