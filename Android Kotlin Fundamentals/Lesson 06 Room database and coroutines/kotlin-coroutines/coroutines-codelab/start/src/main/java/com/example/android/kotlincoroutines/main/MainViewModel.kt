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
import com.example.android.kotlincoroutines.util.BACKGROUND
import com.example.android.kotlincoroutines.util.singleArgViewModelFactory

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
     */
    private fun updateTaps() {
        // TODO: Convert updateTaps to use coroutines
        tapCount++
        BACKGROUND.submit {
            Thread.sleep(1_000)
            _taps.postValue("${tapCount} taps")
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
     */
    fun refreshTitle() {
        // TODO: Convert refreshTitle to use coroutines
        _spinner.value = true
        repository.refreshTitleWithCallbacks(object : TitleRefreshCallback {
            override fun onCompleted() {
                _spinner.postValue(false)
            }

            override fun onError(cause: Throwable) {
                _snackBar.postValue(cause.message)
                _spinner.postValue(false)
            }
        })
    }
}
