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
 *  прописывается когда у вас есть база данных и пользовательский интерфейс
 * ViewModel for SleepTrackerFragment.
 * ViewModel для фрагмента трекера сна.
 * Ваша модель представления спящего трекера будет обрабатывать нажатия кнопок,
 * взаимодействовать с базой данных через DAO и предоставлять данные в пользовательский интерфейс через LiveData.
 * Все операции с базой данных нужно будет запускать из основного потока пользовательского интерфейса,
 * и вы будете делать это с помощью сопрограмм.
 */
//  AndroidViewModel() Этот класс такой же, как ViewModel,
//  но он принимает контекст приложения в качестве параметра
//  и делает его доступным в качестве свойства
//  Это понадобится вам позже для доступа к ресурсам.
//  По Моему он не будет уничтожаться с фрагментом ?
class SleepTrackerViewModel(
        val database: SleepDatabaseDao, // доступ к данным в базе - суперклассу
        application: Application) : AndroidViewModel(application) { // наш application передаем- суперклассу
    // Если ему нужно использовать ApplicationContext, то он должен расширить AndroidViewModel.

    // позволяет отменить (в onCleared) все сопрограммы, запущенные этой моделью представления
    private var viewModelJob = Job()

    // Область действия определяет, в каком потоке будет выполняться сопрограмма, и область также должна знать о задании
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // добавить навигацию, чтобы, когда пользователь нажимал кнопку « Стоп» ,
    // приложение переходило к пункту, SleepQualityFragment чтобы получить оценку качества.
    // LiveData который изменяется, когда вы хотите, чтобы приложение переместилось на SleepQualityFragment
    private val _navigateToSleepQuality = MutableLiveData<SleepNight>()
            val navigateToSleepQuality: LiveData<SleepNight>
                get() = _navigateToSleepQuality

    // Получить в LiveData<List<SleepNight>> все ночи из базы данных и назначить их для nights переменной для наблюдения.
    private val nights = database.getAllNights()
            val nightsString = Transformations.map(nights) { nights -> // Transformations.map срабатывает каждый раз при изменении nights
                 formatNights(nights, application.resources)   // функцию из Util.kt и строки из strings.xml
            }
    // т.к. fun getAllNights(): LiveData<List<SleepNight>> т.е. возвращает LiveData<List<SleepNight>>
    // то nights тоже LiveData и nightsString тоже LiveData<List<SleepNight>> только строковое
    // таким образом nights тоже LiveData и nightsString тоже всегда будут иметь
    //     последнее - актуальное значение и его можно просто отображать - само изменится
    //     а если и прослушивать то будешь в курсе об изменениях (если надо, но необязательно)

    // tonight для хранения текущей ночи. должны иметь возможность наблюдать за данными и изменять их.
    private var tonight = MutableLiveData<SleepNight?>()
            init { // Чтобы инициализировать tonight переменную как можно скорее
                initializeTonight()
            }
            // Видимость кнопок - задизейбл
            val startButtonVisible = Transformations.map(tonight) {
                it == null
            }
            val stopButtonVisible = Transformations.map(tonight) {
                it != null
            }
            val clearButtonVisible = Transformations.map(nights) {
                it?.isNotEmpty()
            }

    private var _showSnackbarEvent = MutableLiveData<Boolean>()
            val showSnackBarEvent: LiveData<Boolean>
                get() = _showSnackbarEvent

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }

    private fun initializeTonight() {  //  В uiScope, запустите сопрограмму getTonightFromDatabase():
        uiScope.launch {
            tonight.value = getTonightFromDatabase()   // см ниже
        }
    }
    // suspend function - функция приостановки должна выглядеть следующим образом:
    private suspend fun getTonightFromDatabase(): SleepNight? { // сопрограмма
        // возвращает результат из сопрограммы, которая выполняется в Dispatchers.IO контексте
        return withContext(Dispatchers.IO) {
            // пусть сопрограмма получит сегодня вечером (самую новую ночь) из базы данных
            var night = database.getTonight()  // получаем последнюю ночь от DAO
            // Если время начала и окончания не совпадают, то есть ночь уже завершена, вернитесь null
            if (night?.endTimeMilli != night?.startTimeMilli) {
                night = null
            }
            //  В противном случае верните ночь.
            night
        }
    }
    // Обработчики кнопок подлючаются через LiveData прямо ViewModel<-->XML минуя котлин код фрагмента
        // обработчик кликов для кнопки «Пуск» ( в XML: android:onClick="@{() -> sleepTrackerViewModel.onStartTracking()}")
        //  Вам необходимо создать новый SleepNight, вставить его в базу данных и назначить его tonight
        fun onStartTracking() {
            // нужен этот результат для продолжения и обновления пользовательского интерфейса
            uiScope.launch {
                val newNight = SleepNight()  // фиксирует текущее время как время начала.
                insert(newNight)  // Это функция см ниже
                tonight.value = getTonightFromDatabase()  // запуск, обновление tonight.
            }
        }
    // функция приостановки insert
    private suspend fun insert(night: SleepNight) {
        //  запустите сопрограмму в контексте ввода-вывода и вставьте ночь в базу данных, вызвыв insert()из DAO
        withContext(Dispatchers.IO) {
            database.insert(night)  // Это функция из DAO
        }
    }
    // обработчик щелчка для кнопки «Стоп».
    fun onStopTracking() {
        uiScope.launch {
            val oldNight = tonight.value?: return@launch  // указывает функцию, из которой возвращается этот оператор
            oldNight.endTimeMilli = System.currentTimeMillis()  // проставляем время окончания сна
            update(oldNight) // Это функция см ниже обновить запись со временем окончания
            _navigateToSleepQuality.value = oldNight   // navigateToSleepQuality: LiveData<SleepNight>
            // Когда эта переменная имеет значение,  а ее отслеживает SleepFragment и он
            // приложение переходит к SleepQualityFragment, минуя ночь.
            // SleepFragment зовет навигатор и просит занрузить фрагмент качества сна
            // Кстати val oldNight ввели а не _navigateToSleepQuality.value = System.currentTimeMillis()
            // чтобы записать в базу иначе изменим наблюдаемую и SleepFragment перехватит и вызовет переход
            // тогда не произойдет записи в базу, поэтому сначала пишем oldNight потом меняем _navigateToSleepQuality
        }
    }
    // suspend- эта функция приостановки
    private suspend fun update(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.update(night) // Это функция из DAO
        }
    }
   // обработчик кликов для кнопки Очистить
    fun onClear() {
        uiScope.launch {
            clear() // Это функция см ниже
            tonight.value = null
           _showSnackbarEvent.value = true  // снэк-бар
        }
    }
    // функция приостановки
    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear() // Это функция из DAO
        }
    }
    // функцию, которая сбрасывает переменную, которая запускает навигацию
    fun doneNavigating() {
        _navigateToSleepQuality.value = null
    }

    // При разрушении фрагмента
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        // Уничтожить все courutine
    }
}

/*
Важно: теперь вы можете увидеть шаблон:

*Запустите сопрограмму, которая выполняется в основном потоке или потоке пользовательского интерфейса,
 поскольку результат влияет на пользовательский интерфейс.
*Вызовите функцию приостановки для выполнения длительной работы,
 чтобы не блокировать поток пользовательского интерфейса во время ожидания результата.
*Длительная работа не имеет ничего общего с пользовательским интерфейсом.
 Переключитесь на контекст ввода-вывода, чтобы работа могла выполняться в пуле потоков,
  который оптимизирован и отведен для таких операций.
*Затем вызовите функцию базы данных, чтобы сделать работу.

Шаблон показан Здесь:

fun someWorkNeedsToBeDone {
   uiScope.launch {
        suspendFunction ()
   }
}
suspend fun suspendFunction () {
   withContext (Dispatchers.IO) {
       longrunningWork ()
   }
}
 */


