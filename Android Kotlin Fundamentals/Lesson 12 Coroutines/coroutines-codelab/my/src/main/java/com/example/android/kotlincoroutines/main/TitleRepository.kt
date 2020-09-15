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
import androidx.lifecycle.map
import kotlinx.coroutines.*

/**
 * TitleRepository provides an interface to fetch a title or request a new one be generated.
 * Репозиторий заголовков предоставляет интерфейс для извлечения заголовка или запроса на создание нового.
 *
 * Repository modules handle data operations.
 * They provide a clean API so that the rest of the app can retrieve this data easily.
 * They know where to get the data from and what API calls to make when data is updated.
 * You can consider repositories to be mediators between different data sources,
 * in our case it mediates between a network API and an offline database cache.
 *
 * Модули репозитория обрабатывают операции с данными.
 * Они предоставляют чистый API, так что остальная часть приложения может легко получить эти данные.
 * Они знают, откуда брать данные и какие вызовы API делать при обновлении данных.
 * Вы можете рассматривать репозитории как посредники между различными источниками данных,
 * в нашем случае он является посредником между сетевым API и автономным кэшем базы данных.
 *
 */
class TitleRepository(val network: MainNetwork, val titleDao: TitleDao) {

    /**
     * [LiveData] to load title.
     * [[Текущие данные] для загрузки заголовка.
     *
     * This is the main interface for loading a title. The title will be loaded from the offline cache.
     * Это основной интерфейс для загрузки заголовка. Заголовок будет загружен из автономного кэша.
     *
     * Observing this will not cause the title to be refreshed,
     * use [TitleRepository.refreshTitleWithCallbacks] to refresh the title.
     * Наблюдение за этим не приведет к обновлению заголовка,
     * используйте [Title Repository.обновить заголовок с обратными вызовами], чтобы обновить заголовок.
     */
    val title: LiveData<String?> = titleDao.titleLiveData.map { it?.title }


    // TODO: Add coroutines-based `fun refreshTitle` here

    /**
     * Refresh the current title and save the results to the offline cache.
     * Обновите текущий заголовок и сохраните результаты в автономном кэше.
     *
     * This method does not return the new title.
     * Use [TitleRepository.title] to observe the current tile.
     * Этот метод не возвращает новый заголовок.
     * Используйте [Title Repository.название] для наблюдения за текущей плиткой.
     */
    suspend fun refreshTitle() {
        try {
            val result = withTimeout(50_000) {
                network.fetchNextTitle()
            }
            titleDao.insertTitle(Title(result))
        } catch (error: Throwable) {
            throw TitleRefreshError("Не удалось обновить заголовок", error)
        }
    }

    /**
     * This API is exposed for callers from the Java Programming language.
     *
     * The request will run unstructured, which means it won't be able to be cancelled.
     *
     * @param titleRefreshCallback a callback
     */
    fun refreshTitleInterop(titleRefreshCallback: TitleRefreshCallback) {
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            try {
                refreshTitle()
                titleRefreshCallback.onCompleted()
            } catch (throwable: Throwable) {
                titleRefreshCallback.onError(throwable)
            }
        }
    }
}


/**
 * Thrown when there was a error fetching a new title
 * Выбрасывается, когда произошла ошибка при выборке нового заголовка
 *
 * @property message user ready error message сообщение об ошибке готовности пользователя
 * @property cause the original cause of this exception первоначальная причина этого исключения
 */
class TitleRefreshError(message: String, cause: Throwable) : Throwable(message, cause)

interface TitleRefreshCallback {
    fun onCompleted()
    fun onError(cause: Throwable)
}

  /*  fun refreshTitleWithCallbacks(titleRefreshCallback: TitleRefreshCallback) {
        // This request will be run on a background thread by retrofit
        // Этот запрос будет выполняться в фоновом потоке с помощью retrofit
        //  com.example.android.kotlincoroutines.util.BACKGROUND
        BACKGROUND.submit {
            try {
                // Make network request using a blocking call
                // Сделать сетевой запрос с помощью блокирующего вызова
                val result = network.fetchNextTitle().execute()
                if (result.isSuccessful) {
                    // Save it to database
                    // Сохраните его в базе данных
                    titleDao.insertTitle(Title(result.body()!!))
                    // Inform the caller the refresh is completed
                    // Сообщите вызывающему абоненту, что обновление завершено
                    titleRefreshCallback.onCompleted()
                } else {
                    // If it's not successful, inform the callback of the error
                    // Если это не удалось, сообщите обратному вызову об ошибке
                    titleRefreshCallback.onError(
                            TitleRefreshError("Не удалось обновить заголовок", null))
                }
            } catch (cause: Throwable) {
                // If anything throws an exception, inform the caller
                // Если что-то вызывает исключение, сообщите об этом вызывающему абоненту
                titleRefreshCallback.onError(
                        TitleRefreshError("Не удалось обновить заголовок", cause))
            }
        }
    }



    /**
     *  Этот шаблон следует использовать для интеграции с API-интерфейсами блокировки в вашем коде
     * или для выполнения интенсивной работы с ЦП.
     * Эта реализация использует блокирующие вызовы для сети и базы данных,
     * но все же немного проще, чем версия обратного вызова.
     * По возможности лучше использовать обычные функции приостановки из библиотек, таких как Room или Retrofit.
     */
    suspend fun refreshTitle() {
        // TODO: Refresh from network and write to database
        // interact with *blocking* network and IO calls from a coroutine
        withContext(Dispatchers.IO) {
            val result = try {
                // Make network request using a blocking call
                network.fetchNextTitle().execute()
            } catch (cause: Throwable) {
                // If the network throws an exception, inform the caller
                throw TitleRefreshError("Не удалось обновить заголовок", cause)
            }

            if (result.isSuccessful) {
                // Save it to database
                titleDao.insertTitle(Title(result.body()!!))
            } else {
                // If it's not successful, inform the callback of the error
                throw TitleRefreshError("Не удалось обновить заголовок", null)
            }
        }
    }
   */