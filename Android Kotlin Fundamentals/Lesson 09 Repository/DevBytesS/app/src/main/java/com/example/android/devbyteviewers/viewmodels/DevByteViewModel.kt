/*
 * Copyright (C) 2019 Google Inc.
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

package com.example.android.devbyteviewers.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.devbyteviewers.database.getDatabase
import com.example.android.devbyteviewers.repository.VideosRepository
import kotlinx.coroutines.*
import java.io.IOException

/**
 * DevByteViewModel designed to store and manage UI-related data in a lifecycle conscious way. This
 * allows data to survive configuration changes such as screen rotations. In addition, background
 * work such as fetching network results can continue through configuration changes and deliver
 * results after the new Fragment or Activity is available.
 * DevByteViewModel предназначен для хранения и управления данными, связанными с пользовательским интерфейсом, в сознательном жизненном цикле. Этот
 * позволяет данным пережить изменения конфигурации, такие как поворот экрана. Кроме того, Предыстория
 * такая работа, как извлечение сетевых результатов, может быть продолжена с помощью изменений конфигурации и доставки
 * результаты после появления нового фрагмента или действия.
 *
 * @param application The application that this viewmodel is attached to, it's safe to hold a
 * reference to applications across rotation since Application is never recreated during actiivty
 * or fragment lifecycle events.
 * @ param application приложение, к которому прикреплен этот viewmodel, безопасно держать в руках
 * * ссылка на приложения через ротацию, так как приложение никогда не воссоздается во время работы
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
     * A playlist of videos displayed on the screen.
     * Список воспроизведения видео, отображаемых на экране.
     */
    // Создайте список воспроизведения.
    // Получить видео LiveData из репозитория и назначить его для playlist переменной.
    val playlist = videosRepository.videos

    /**
     * This is the job for all coroutines started by this ViewModel.
     * Это задание для всех сопрограмм, запущенных этой моделью ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     * Отмена этого задания приведет к отмене всех сопрограмм, запущенных этой моделью представления.
     */
    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     * Это задание для всех сопрограмм, запущенных этой моделью ViewModel.
     *
     * Since we pass viewModelJob, you can cancel all coroutines launched by uiScope by calling
     * viewModelJob.cancel()
     * Поскольку мы передаем задание viewModel, вы можете отменить все сопрограммы, запущенные uiScope, позвонив по телефону
     * задание viewModel.отменять().
     */
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * A playlist of videos that can be shown on the screen. This is private to avoid exposing a
     * way to set this value to observers.
     * Список воспроизведения видео, которые могут быть показаны на экране. Это личное, чтобы избежать разоблачения a
     * способ установки этого значения для наблюдателей.
     */
   // private val _playlist = MutableLiveData<List<DevByteVideo>>() // не нужно для репо Мы заменим этот код на хранилище.

    /**
     * A playlist of videos that can be shown on the screen. Views should use this to get access
     * to the data.
     * Список воспроизведения видео, которые могут быть показаны на экране. Представления должны использовать это для получения доступа
     * к данным.
     */
    //val playlist: LiveData<List<DevByteVideo>>    // не нужно для репо Мы заменим этот код на хранилище.
    //    get() = _playlist                         // не нужно для репо

    /**
     * Event triggered for network error. This is private to avoid exposing a
     * way to set this value to observers.
     * Событие, вызванное для сетевой ошибки. Это личное, чтобы избежать разоблачения a
     * способ установки этого значения для наблюдателей.
     */
    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    /**
     * Event triggered for network error. Views should use this to get access
     * to the data.
     *  Событие, вызванное для сетевой ошибки. Представления должны использовать это для получения доступа
     * к данным.
     */
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    /**
     * Flag to display the error message. This is private to avoid exposing a
     * way to set this value to observers.
     * Флаг для отображения сообщения об ошибке. Это личное, чтобы избежать разоблачения a
     * способ установки этого значения для наблюдателей.
     */
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    /**
     * Flag to display the error message. Views should use this to get access
     * to the data.
     *  Флаг для отображения сообщения об ошибке. Представления должны использовать это для получения доступа
     * к данным.
     */
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    /**
     * init{} is called immediately when this ViewModel is created.
     * init{} вызывается сразу же при создании этой модели представления.
     */
    init {  // Обновите видео с помощью репозитория.
     // refreshDataFromNetwork() // выбирает список воспроизведения видео  напрямую из сети. Мы заменим этот код на хранилище.
        refreshDataFromRepository() // выбирает список воспроизведения видео из репозитория, а не напрямую из сети.
    }

    /**
     * Refresh data from network and pass it via LiveData. Use a coroutine launch to get to
     * background thread.
     * Обновите данные из репозитория. Использовать запуск сопрограмма для работы в
     * фоновый поток.
     */
    
  /*  private fun refreshDataFromNetwork() = viewModelScope.launch {
            // Получение плейлиста напрямую- в прямом эфире из инета и высветка его без репозитория
        try {
            //delay(100000)  // на проверку медленной сети
             val playlist = DevByteNetwork.devbytes.getPlaylist().await()
            _playlist.postValue(playlist.asDomainModel())

            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false

        } catch (networkError: IOException) {
            // Show a Toast error message and hide the progress bar.
            _eventNetworkError.value = true
        }
    }*/

    /**
     * Resets the network error flag.
     * Сбрасывает флаг сетевой ошибки.
     */
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
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
     * Cancel all coroutines when the ViewModel is cleared
     *  Отмените все сопрограммы, когда ViewModel будет очищен
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
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DevByteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DevByteViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}

/*
8. Задача: интегрировать репозиторий, используя стратегию обновления
В этой задаче вы интегрируете свой репозиторий с ViewModel помощью простой стратегии обновления.
Вы отображаете список воспроизведения видео из Room базы данных, а не извлекаете его напрямую из сети.

Обновления базы данных представляют собой процесс обновления или обновления локальной базы данных,
чтобы сохранить его в синхронизации с данными в сети.
Для этого примера приложения вы используете очень простую стратегию обновления,
при которой модуль, запрашивающий данные из репозитория, отвечает за обновление локальных данных.

В реальном приложении ваша стратегия может быть более сложной.
Например, ваш код может автоматически обновлять данные в фоновом режиме
(с учетом пропускной способности) или кэшировать данные,
которые пользователь, скорее всего, будет использовать дальше.
 */
