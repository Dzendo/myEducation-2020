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
 *
 */

package com.example.android.devbyteviewer.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.android.devbyteviewer.database.getDatabase
import com.example.android.devbyteviewer.repository.VideosRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * DevByteViewModel designed to store and manage UI-related data in a lifecycle conscious way.
 * This allows data to survive configuration changes such as screen rotations.
 * In addition, background work such as fetching network results can continue through configuration changes
 * and deliver results after the new Fragment or Activity is available.
 * DevByteViewModel предназначен для хранения и управления пользовательского интерфейса, данные в сознательном жизненного пути.
 * Это позволяет данным выдерживать изменения конфигурации, такие как поворот экрана.
 * Кроме того, фоновая работа, такая как извлечение сетевых результатов, может продолжаться с помощью изменений конфигурации
 * и доставлять результаты после того, как новый фрагмент или действие будут доступны.
 *
 * @param application The application that this viewmodel is attached to, it's safe to hold a
 * reference to applications across rotation since Application is never recreated during actiivty
 * or fragment lifecycle events.
 * приложение @param приложение, к которому прикреплена эта viewmodel, безопасно держать в руках
 * ссылка на приложения через ротацию, так как приложение никогда не воссоздается во время активности
 * или фрагмент событий жизненного цикла.
 */
class DevByteViewModel(application: Application) : AndroidViewModel(application) {
    // Чтобы завершить автономное кэширование, вам необходимо интегрировать репозиторий в модель представления.
    /**
     * The data source this ViewModel will fetch results from.
     * Источник данных, из которого эта модель представления будет извлекать результаты.
     */
    // private val database = getDatabase(application) udacity - По другому Создайте базу данных singleton.
    // private val videosRepository = VideosRepository(database) udacity - По другому Затем создайте свой репозиторий.
    private val videosRepository = VideosRepository(getDatabase(application))

    /**
     * This is the job for all coroutines started by this ViewModel.
     * Это задание для всех сопрограмм, запущенных этой моделью представления.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     * Отмена этого задания приведет к отмене всех сопрограмм, запущенных этой моделью представления.
     */
    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     * Это основная область применения для всех сопрограмм, запущенных MainViewModel.
     *
     * Since we pass viewModelJob, you can cancel all coroutines launched by uiScope by calling
     * Поскольку мы передаем задание viewModel, вы можете отменить все сопрограммы, запущенные uiScope:
     * viewModelJob.cancel()
     */
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * A playlist of videos displayed on the screen.
     * Список воспроизведения видео, отображаемых на экране.
     */
    // Создайте список воспроизведения.
    // Получить видео LiveData из репозитория и назначить его для playlist переменной.
    val playlist = videosRepository.videos

    /**
     * A playlist of videos that can be shown on the screen.
     * This is private to avoid exposing a way to set this value to observers.
     * Список воспроизведения видео, которые могут быть показаны на экране.
     * Это личное, чтобы избежать раскрытия способа установки этого значения наблюдателям.
     * Представления должны использовать playlist для получения доступа к данным.
     */
    // private val _playlist = MutableLiveData<List<Video>>()   // не нужно для репо Мы заменим этот код на хранилище.
    // val playlist: LiveData<List<Video>>                      // не нужно для репо Мы заменим этот код на хранилище.
    //     get() = _playlist                                    // не нужно для репо Мы заменим этот код на хранилище.

    /**
     * Event triggered for network error. This is private to avoid exposing a
     * way to set this value to observers.
     * Событие, вызванное для сетевой ошибки. Это личное, чтобы избежать разоблачения a
     * способ установки этого значения для наблюдателей.
     */
    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError
    /**
     * Event triggered for network error. Views should use this to get access
     * to the data.
     *  Событие, вызванное для сетевой ошибки. Представления должны использовать это для получения доступа
     * к данным.
     */

    /**
     * Flag to display the error message. This is private to avoid exposing a
     * way to set this value to observers.
     * Флаг для отображения сообщения об ошибке. Это личное, чтобы избежать разоблачения a
     * способ установки этого значения для наблюдателей.
     */
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown
    /**
     * Flag to display the error message. Views should use this to get access
     * to the data.
     *  Флаг для отображения сообщения об ошибке. Представления должны использовать это для получения доступа
     * к данным.
     */

    /**
     * init{} is called immediately when this ViewModel is created.
     * init{} вызывается немедленно при создании этой модели представления.
     */
    init {
       // refreshDataFromNetwork()  // выбирает список воспроизведения видео  напрямую из сети. Мы заменим этот код на хранилище.

      /*  viewModelScope.launch {
            videosRepository.refreshVideos() // выбирает список воспроизведения видео из репозитория, а не напрямую из сети. Udacity
        }*/

        refreshDataFromRepository() // выбирает список воспроизведения видео из репозитория, а не напрямую из сети. CodeLabs
    }
    /** Подключеник репозитория
     * Refresh data from the repository. Use a coroutine launch to run in a
     * background thread.
     * Обновите данные из репозитория. Использовать запуск сопрограмма для работы в
     * фоновый поток.
     */
    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                videosRepository.refreshVideos()  // Обновите видео с помощью репозитория.
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                if(playlist.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }
    /**
     * Resets the network error flag.
     * Сбрасывает флаг сетевой ошибки.
     */
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    /**
     * Refresh data from network and pass it via LiveData.
     * Use a coroutine launch to get to background thread.
     * Обновите данные из сети и передайте их через Live Data.
     * Используйте запуск сопрограммы, чтобы добраться до фонового потока.
     */
  /*  private fun refreshDataFromNetwork() = viewModelScope.launch {
        try {
            val playlist = Network.devbytes.getPlaylist().await()
            _playlist.postValue(playlist.asDomainModel())
        } catch (networkError: IOException) {
        // delay(2000)  //  Этот код задержки задержит установку ошибки сети
            // Show an infinite loading spinner if the request fails
            // challenge exercise: show an error to the user if the network request fails
            // Показать бесконечный загрузочный счетчик, если запрос не выполняется
            // challenge упражнение: показать пользователю ошибку, если сетевой запрос не выполняется
        }
    }*/

    /**
     * Cancel all coroutines when the ViewModel is cleared
     * Отмените все сопрограммы, когда ViewModel будет очищен
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Factory for constructing DevByteViewModel with parameter
     * Фабрика для построения DevByteViewModel с параметром
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DevByteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DevByteViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
