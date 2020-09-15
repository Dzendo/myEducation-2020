/*
 * Copyright (C) 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.kotlincoroutines.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.kotlincoroutines.util.singleArgViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * MainViewModel designed to store and manage UI-related data in a lifecycle conscious way.
 * This allows data to survive configuration changes such as screen rotations.
 * In addition, background work such as fetching network results can continue through configuration changes
 * and deliver results after the new Fragment or Activity is available.
 *
 * MainViewModel предназначен для хранения и управления данными,
 * связанными с пользовательским интерфейсом, с учетом жизненного цикла.
 * Это позволяет данным выдерживать изменения конфигурации, такие как поворот экрана.
 * Кроме того, фоновая работа, такая как извлечение сетевых результатов,
 * может продолжаться с помощью изменений конфигурации
 * и доставлять результаты после того, как новый фрагмент или действие будут доступны.
 *
 * @param repository the data source this ViewModel will fetch results from.
 * источник данных, из которого эта модель представления будет извлекать результаты.
 */
class MainViewModel(private val repository: TitleRepository) : ViewModel() {

    companion object {
        /**
         * Factory for creating [MainViewModel]
         * Фабрика для создания [MainViewModel]
         *
         * @param arg the repository to pass to [MainViewModel]
         */
        val FACTORY = singleArgViewModelFactory(::MainViewModel)
    }

    /**
     * Request a snackbar to display a string.
     * Запросите снэк-бар, чтобы показать строку.
     *
     * This variable is private because we don't want to expose MutableLiveData
     * Эта переменная является частной, потому что мы не хотим раскрывать изменяемые текущие данные
     *
     * MutableLiveData allows anyone to set a value,
     * and MainViewModel is the only class that should be setting values.
     * Изменяемые живые данные позволяют любому пользователю установить значение,
     * и MainViewModel-это единственный класс, который должен устанавливать значения.
     */
    private val _snackBar = MutableLiveData<String?>()

    /**
     * Request a snackbar to display a string.
     * Запросите снэк-бар, чтобы показать строку.
     */
    val snackbar: LiveData<String?>
        get() = _snackBar

    /**
     * Update title text via this LiveData
     * Обновите текст заголовка с помощью этих живых данных
     */
    val title = repository.title

    private val _spinner = MutableLiveData<Boolean>(false)

    /**
     * Show a loading spinner if true
     * Показать загрузочный счетчик, если это правда
     */
    val spinner: LiveData<Boolean>
        get() = _spinner

    /**
     * Count of taps on the screen
     * Количество нажатий на экран
     */
    private var tapCount = 0

    /**
     * LiveData with formatted tap count.
     * Живые данные с отформатированным количеством нажатий.
     */
    private val _taps = MutableLiveData<String>("$tapCount нажатий.")

    /**
     * Public view of tap live data.
     */
    val taps: LiveData<String>
        get() = _taps

    /**
     * Respond to onClick events by refreshing the title.
     * Реагируйте на события onClick, обновляя заголовок.
     *
     * The loading spinner will display until a result is returned, and errors will trigger a snackbar.
     * Загрузочный счетчик будет отображаться до тех пор,
     * пока не будет возвращен результат, а ошибки вызовут снэк-бар.
     */
    fun onMainViewClicked() {
        refreshTitle()
        updateTaps()
    }

    /**
     * Wait one second then update the tap count.
     * Подождите одну секунду, а затем обновите количество нажатий.
     */
    private fun updateTaps() {
        // TODO: Convert updateTaps to use coroutines:
        viewModelScope.launch {
            tapCount++
            // suspend this coroutine for one second
            // приостановите эту сопрограмму на одну секунду
            delay(10_000)
            // resume in the main dispatcher резюме в главном диспетчере
            // _snackbar.value can be called directly from main thread
            _taps.postValue("$tapCount нажатий.")
        }
        /* вариант на потоках:
        tapCount++
        BACKGROUND.submit {
            Thread.sleep(10_000)
            _taps.postValue("${tapCount} нажатий")
        }*/
    }
    /*
    Этот код использует BACKGROUND ExecutorService(определенный в util/Executor.kt) для работы в фоновом потоке.
    Поскольку sleepблокирует текущий поток, он заморозит пользовательский интерфейс,
    если он будет вызван в основном потоке.
    Через секунду после того, как пользователь щелкает по основному экрану, он запрашивает снэк-бар.
    Вы можете убедиться в этом, удалив ФОН из кода и запустив его снова.
    Счетчик загрузки не отображается, и через секунду все "перескакивает" в конечное состояние.
     */

    /**
     * Called immediately after the UI shows the snackbar.
     * Вызывается сразу после того, как пользовательский интерфейс показывает снэк-бар.
     */
    fun onSnackbarShown() {
        _snackBar.value = null
    }



    /**
     * Helper function to call a data load function with a loading spinner, errors will trigger a snackbar.
     * Вспомогательная функция для вызова функции загрузки данных
     * с помощью загрузочного счетчика ошибки вызовут снэк-бар.
     *
     * By marking `block` as `suspend` this creates a suspend lambda which can call suspend functions.
     * Пометив "блок" как "приостановить", это создает приостановленную лямбду,
     * которая может вызывать функции приостановки.
     *
     * @param block lambda to actually load data. It is called in the viewModelScope. Before calling the
     *              lambda the loading spinner will display, after completion or error the loading
     *              spinner will stop
     *              лямбда-код для фактической загрузки данных. Он вызывается в области видимости viewModel.
     *              Прежде чем позвонить в лямбда загрузочный счетчик будет отображаться после завершения
     *              или ошибки загрузки счетчик остановится
     */
    private fun launchDataLoad(block: suspend () -> Unit): Unit {
        viewModelScope.launch {
            try {
                _spinner.value = true
                block()
            } catch (error: TitleRefreshError) {
                _snackBar.value = error.message
            } finally {
                _spinner.value = false
            }
        }
    }
    /**
     * Refresh the title, showing a loading spinner while it refreshes and errors via snackbar.
     * Обновите заголовок, показывая загрузочный счетчик, пока он обновляется и ошибки через снэк-бар.
     * нижняя refreshTitle() тоже работает без функции высшего порядка
     */
    fun refreshTitle() = launchDataLoad {
        repository.refreshTitle()
    }
    /**
     * Refresh the title, showing a loading spinner while it refreshes and errors via snackbar.
     * Обновите заголовок, показывая загрузочный счетчик, пока он обновляется и ошибки через снэк-бар.
     *
     *  Эта функция вызывается каждый раз, когда пользователь щелкает по экрану,
     *  и она заставляет репозиторий обновлять заголовок и записывать новый заголовок в базу данных.
     */
    /*fun refreshTitle() {
        // TODO: Convert refreshTitle to use coroutines
        viewModelScope.launch {
            try {
                _spinner.value = true
                repository.refreshTitle()   // suspend
            } catch (error: TitleRefreshError) {
                _snackBar.value = error.message
            } finally {
                _spinner.value = false
            }
        }
    }*/
}
/*
 При создании сопрограммы из не-сопрограммы начните с launch .
  Таким образом, если они генерируют неперехваченное исключение,
   оно автоматически распространяется на обработчики неперехваченных исключений
    (которые по умолчанию вызывают сбой приложения).

Сопрограмма, запущенная с async, не будет генерировать исключение для вызывающего, пока вы не вызовете await.
Однако вы можете вызывать только await изнутри сопрограммы, поскольку это функция приостановки.
Оказавшись внутри сопрограммы, вы можете использовать запуск или асинхронный запуск
 для запуска дочерних сопрограмм.
  Используйте, launch когда у вас нет результата, который нужно вернуть, и async когда он есть.
 */
    /*

    fun refreshTitle() {

        /* на обратных вызовах:
        В этой реализации обратный вызов используется для следующих действий:

        Перед тем, как начать запрос, он отображает счетчик загрузки с _spinner.value = true
        Когда он получает результат, он очищает счетчик загрузки с помощью _spinner.value = false
        Если появляется ошибка, он сообщает снэк-панели отображать и очищает счетчик
        Обратите внимание, что onCompleted обратный вызов не передается title.
         Поскольку мы записываем все заголовки в Room базу данных,
          пользовательский интерфейс обновляется до текущего заголовка,
           наблюдая за LiveData обновленным файлом Room.
         */
        _spinner.value = true
        repository.refreshTitleWithCallbacks(object : TitleRefreshCallback {
            override fun onCompleted() {
                _spinner.postValue(false)
            }
// Что значит?object: TitleRefreshCallback
//Это способ создания анонимного класса в Котлине.
// Он создает новый объект, который реализует TitleRefreshCallback.
            override fun onError(cause: Throwable) {
                _snackBar.postValue(cause.message)
                _spinner.postValue(false)
            }
        })
    }
}*/
