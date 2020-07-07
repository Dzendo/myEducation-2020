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
 *
 * MainViewModel designed to store and manage UI-related data in a lifecycle conscious way. This
 * allows data to survive configuration changes such as screen rotations. In addition, background
 * work such as fetching network results can continue through configuration changes and deliver
 * results after the new Fragment or Activity is available.
 * MainViewModel предназначен для хранения и управления данными, связанными с пользовательским интерфейсом, сознательно в течение всего жизненного цикла. Этот
 * позволяет данным пережить изменения конфигурации, такие как поворот экрана. Кроме того, фон
 * работа, такая как извлечение сетевых результатов, может продолжаться через изменения конфигурации и доставлять
 * результаты после появления нового фрагмента или действия.
 *
 * @param repository the data source this ViewModel will fetch results from.
 * репозиторий @param источник данных, из которого эта модель представления будет извлекать результаты.
 *
 * * MainViewModel обрабатывает события onMainViewClicked и будет общаться с MainActivity использованиемLiveData.
 * MainViewModel представляет состояние экрана и обрабатывает события.
 * Он скажет хранилищу обновить заголовок, когда пользователь нажимает на экран.
 */
class MainViewModel(private val repository: TitleRepository) : ViewModel() {

    companion object {
        /**
         * Здесь Static организуется Factory для ViewModel() и потом из MainAcivity и вызывается
         * Factory for creating [MainViewModel]
         * Фабрика для создания [MainViewModel]
         *
         * @param arg the repository to pass to [MainViewModel]
         * @param arg репозиторий для передачи в [MainViewModel]
         */
        val FACTORY = singleArgViewModelFactory(::MainViewModel)
    }

    /**
     * Request a snackbar to display a string.
     * Запросите снэк-бар, чтобы отобразить строку.
     *
     * This variable is private because we don't want to expose MutableLiveData
     * Эта переменная является частной, потому что мы не хотим предоставлять изменяемые живые данные
     *
     * MutableLiveData allows anyone to set a value, and MainViewModel is the only
     * class that should be setting values.
     * Изменяемые живые данные позволяют любому установить значение, и MainViewModel является единственным
     * класс, который должен устанавливать значения.
     */
    private val _snackBar = MutableLiveData<String?>()

    /**
     * Request a snackbar to display a string.
     * Запросите снэк-бар, чтобы отобразить строку.
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
     * Показать загрузочный спиннер, если true
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
     * Живые данные с отформатированным количеством нажатий
     */
    private val _taps = MutableLiveData<String>("$tapCount taps")

    /**
     * Public view of tap live data.
     * Публичное представление данных tap live.
     */
    val taps: LiveData<String>
        get() = _taps

    /**
     * Respond to onClick events by refreshing the title.
     * Реагируйте на события onClick, обновляя заголовок.
     *
     * The loading spinner will display until a result is returned, and errors will trigger
     * a snackbar.
     * Загрузочный счетчик будет отображаться до тех пор, пока не будет возвращен результат, и будут вызваны ошибки
     * закусочная.
     */
    fun onMainViewClicked() {
        refreshTitle()
        updateTaps()
    }
    /**
     * Wait one second then update the tap count.
     * Подождите одну секунду, а затем обновите количество нажатий.
     * Поскольку sleep блокирует текущий поток, он заморозил бы пользовательский интерфейс, если бы он был вызван в основном потоке.
     * Через одну секунду после того, как пользователь щелкает по основному виду, он запрашивает снэк-бар.
     * Это можно увидеть, удалив фоновую информацию из кода и снова запустив ее.
     * Загрузочный счетчик не будет отображаться, и через секунду все «перейдет» в конечное состояние.
     * справочно val BACKGROUND = Executors.newFixedThreadPool(2)  java.util.concurrent.Executors
     */
    private fun updateTaps() {
        // launch a coroutine in viewModelScope
        // запуск сопрограммы в view ModelScope
        viewModelScope.launch {
            // suspend this coroutine for one second приостановите эту сопрограмму на одну секунду
            delay(1_000) // delay является suspend функцией
            // resume in the main dispatcher резюме в главном диспетчере
            // _snackbar.value can be called directly from main thread может быть вызван непосредственно из основного потока
            _taps.value = "${++tapCount} taps"
        }
    }

    /**
     * Called immediately after the UI shows the snackbar.
     * Вызывается сразу после того, как пользовательский интерфейс показывает снэк-бар.
     */
    fun onSnackbarShown() {
        _snackBar.value = null
    }

    /**
     * Refresh the title, showing a loading spinner while it refreshes and errors via snackbar.
     * Обновите заголовок, показав загрузочный счетчик, пока он обновляется и ошибки через снэк-бар.
     *  val title = repository.title
     *  Эта функция вызывается каждый раз, когда пользователь нажимает на экран,
     *  и это заставит хранилище обновить заголовок и записать новый заголовок в базу данных.
     */
    fun refreshTitle() = launchDataLoad {
        repository.refreshTitle()
    }

    /**
     * Helper function to call a data load function with a loading spinner, errors will trigger a
     * snackbar.
     *
     * By marking `block` as `suspend` this creates a suspend lambda which can call suspend
     * functions.
     *
     * @param block lambda to actually load data. It is called in the viewModelScope. Before calling the
     *              lambda the loading spinner will display, after completion or error the loading
     *              spinner will stop
     */
    // Использование сопрограмм в функциях высшего порядка
    // Чтобы построить эту абстракцию, launchDataLoad требуется аргумент, block который является лямбда-приостановкой.
    // Приостановить лямбду позволяет вызывать функции приостановки.
    // Вот как Kotlin внедряет компиляторы сопрограмм, launch и runBlocking мы использовали его в этом коде.
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
}

/* private fun updateTaps() { viewModelScope.launch {
Этот код делает то же самое, ожидая одну секунду, прежде чем показывать закусочную.
Тем не менее, есть некоторые важные различия:

1. viewModelScope.launch запустит сопрограмму в viewModelScope.
 Это означает, что когда работа, которую мы передали, viewModelScope будет отменена,
  все сопрограммы в этой работе / области будут отменены.
   Если пользователь покинул Activity перед delay возвратом,
    эта сопрограмма будет автоматически отменена при onCleared вызове после уничтожения ViewModel.
2. Так как viewModelScope имеет диспетчер по умолчанию Dispatchers.Main,
 эта сопрограмма будет запущена в главном потоке.
  Позже мы увидим, как использовать разные темы.
3. Функция delay является suspend функцией.
 Это показано в Android Studio значком в левом желобе.
  Даже если эта сопрограмма выполняется в основном потоке, delay она не будет блокировать поток в течение одной секунды.
   Вместо этого диспетчер запланирует возобновление сопрограммы через одну секунду при следующем утверждении.
 */

/*
При создании сопрограммы из не-сопрограммы начните с запуска .

Таким образом, если они выдают неперехваченное исключение,
 оно будет автоматически передано неперехваченным обработчикам исключений
  (которые по умолчанию завершают работу приложения).
   Сопрограмма начали с async не сгенерирует исключение в вызывающем до вызова await.
    Тем не менее, вы можете вызывать только await из сопрограммы, так как это функция приостановки.

Оказавшись внутри сопрограммы, вы можете использовать запуск или асинхронный запуск дочерних сопрограмм.
 Используйте, launch когда у вас нет результата, чтобы вернуться, и async когда у вас есть.
 */
