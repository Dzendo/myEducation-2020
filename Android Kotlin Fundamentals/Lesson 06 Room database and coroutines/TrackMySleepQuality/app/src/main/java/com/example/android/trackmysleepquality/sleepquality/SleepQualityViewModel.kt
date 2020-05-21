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
import kotlinx.coroutines.*

/*
Создайте SleepQualityViewModel класс, который принимает sleepNightKey и базу данных в качестве аргументов.
Так же, как вы сделали для SleepTrackerViewModel, вам нужно пройти database с завода.
Вы также должны пройти через sleepNightKey навигацию.
 */
class SleepQualityViewModel(
        private val sleepNightKey: Long = 0L,
        val database: SleepDatabaseDao) : ViewModel() {
    //Внутри SleepQualityViewModel класса определите Job и uiScope
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    // Чтобы вернуться к SleepTrackerFragment использованию того же шаблона,
    // что и выше, объявите _navigateToSleepTracker
    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()
    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToSleepTracker

    fun doneNavigating() {
        _navigateToSleepTracker.value = null
    }
    // Создайте обработчик одним щелчком onSetSleepQuality() для всех изображений с качеством сна.
    // android:onClick="@{() -> sleepQualityViewModel.onSetSleepQuality(5)}"
    fun onSetSleepQuality(quality: Int) {
        uiScope.launch {
            // IO is a thread pool for running operations that access the disk, such as
            // our Room database.
            // IO-это пул потоков для выполнения операций, которые обращаются к диску, например
            // наша база данных номеров.
            withContext(Dispatchers.IO) {
                val tonight = database.get(sleepNightKey) ?: return@withContext
                tonight.sleepQuality = quality
                database.update(tonight)
            }

            // Setting this state variable to true will alert the observer and trigger navigation.
            // Установка этой переменной состояния в true предупредит наблюдателя и вызовет навигацию.
            _navigateToSleepTracker.value = true
        }
    }
    // переопределите onCleared()
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

/*
Создайте обработчик одним щелчком onSetSleepQuality()для всех изображений с качеством сна.

Используйте тот же шаблон сопрограммы, что и в предыдущем коде:
Запустите сопрограмму в uiScope и переключитесь на диспетчер ввода-вывода.
Получить tonight с помощью sleepNightKey.
Установите качество сна.
Обновите базу данных.
Триггерная навигация.
Обратите внимание, что приведенный onSetSleepQuality пример кода выполняет всю работу в обработчике кликов
вместо того, чтобы выделять операции базы данных в другом контексте.
 */