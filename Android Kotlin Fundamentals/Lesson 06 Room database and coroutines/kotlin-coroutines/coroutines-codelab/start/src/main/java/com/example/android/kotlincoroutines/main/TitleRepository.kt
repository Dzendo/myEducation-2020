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
import com.example.android.kotlincoroutines.util.BACKGROUND
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

/**
 * TitleRepository provides an interface to fetch a title or request a new one be generated.
 * Хранилище заголовков предоставляет интерфейс для извлечения заголовка или запроса на создание нового.
 *
 * Repository modules handle data operations. They provide a clean API so that the rest of the app
 * can retrieve this data easily. They know where to get the data from and what API calls to make
 * when data is updated. You can consider repositories to be mediators between different data
 * sources, in our case it mediates between a network API and an offline database cache.
 * Модули репозитория обрабатывают операции с данными. Они предоставляют чистый API, так что остальная часть приложения
 * может легко получить эти данные. Они знают, откуда брать данные и какие вызовы API делать
 * когда данные обновляются. Вы можете рассматривать репозитории как посредники между различными данными
 * исходники, в нашем случае он является посредником между сетевым API и автономным кэшем базы данных.
 */
class TitleRepository(val network: MainNetwork, val titleDao: TitleDao) {

    /**
     * [LiveData] to load title.
     * [Текущие данные] для загрузки заголовка.
     *
     * This is the main interface for loading a title. The title will be loaded from the offline cache.
     * Это основной интерфейс для загрузки заголовка. Заголовок будет загружен из автономного кэша.
     *
     * Observing this will not cause the title to be refreshed,
     * use [TitleRepository.refreshTitleWithCallbacks]  to refresh the title.
     * Соблюдение этого правила не приведет к обновлению заголовка,
     * используйте [Title Repository.обновить заголовок с обратными вызовами], чтобы обновить заголовок.
     *  По возможности лучше использовать обычные функции приостановки из таких библиотек, как Room или Retrofit
     *  В этой реализации используются блокирующие вызовы для сети и базы данных,
     *  но она все же немного проще, чем версия для обратного вызова.
     */
    val title: LiveData<String?> = titleDao.titleLiveData.map { it?.title }

    suspend fun refreshTitle() {

        // API: public suspend fun delay(timeMillis: Long) {
        //delay(500)  // это рлка временно для метки 7
// interact with *blocking* network and IO calls from a coroutine
        // TODO: Add coroutines-based `fun refreshTitle` here 7.
// Вызов withContext переключается на другого диспетчера только для лямбды
       // withContext(Dispatchers.IO) {
            //val result =
                    try {
                // Make network request using a blocking call
                //network.fetchNextTitle().execute()
                        // Make network request using a blocking call
                        /*val result = withTimeout(5_000) {
                            network.fetchNextTitle()
                        }*/
                val result =network.fetchNextTitle()
                titleDao.insertTitle(Title(result))
            } catch (cause: Throwable) {
                // If the network throws an exception, inform the caller
                throw TitleRefreshError("Cath Unable to refresh title", cause)
            }

            /*if (result.isSuccessful) {
                // Save it to database
                titleDao.insertTitle(Title(result.body()!!))
            } else {
                // If it's not successful, inform the callback of the error
                throw TitleRefreshError("Unable to refresh title", null)
            }*/
        }

    }
// И, если вы выбросите исключение из сопрограммы - эта сопрограмма по умолчанию отменит своего родителя.
// Это означает, что легко отменить несколько связанных задач вместе.


    /**
     * Refresh the current title and save the results to the offline cache.
     * Обновите текущий заголовок и сохраните результаты в автономном кэше.
     *
     * This method does not return the new title.
     * Use [TitleRepository.title] to observe the current tile.
     * Этот метод не возвращает новый заголовок.
     * Используйте [Title Repository.название] для наблюдения за текущей tile
     * методе refreshTitleWithCallback реализован обратный вызов
     * для передачи информации о загрузке и состоянии ошибки вызывающей стороне.
     *
     */
    /*
    fun refreshTitleWithCallbacks(titleRefreshCallback: TitleRefreshCallback) {
        // реализацию на основе обратного вызова
        // This request will be run on a background thread by retrofit
        // Этот запрос будет выполняться в фоновом потоке с помощью retrofit
        BACKGROUND.submit {  // Переключиться на другой поток с BACKGROUND ExecutorService
            try {
                // Make network request using a blocking call
                // Сделать сетевой запрос с помощью блокирующего вызова
                // Запустите fetchNextTitle сетевой запрос, используя execute()метод блокировки.
                // Это запустит сетевой запрос в текущем потоке, в этом случае один из потоков в BACKGROUND.
                val result = network.fetchNextTitle().execute()
                if (result.isSuccessful) {
                    // Save it to database // Сохраните результат в базе данных
                    titleDao.insertTitle(Title(result.body()!!))
                    // Inform the caller the refresh is completed
                    // Сообщите вызывающему абоненту, что обновление завершено
                    titleRefreshCallback.onCompleted()
                } else {
                    // If it's not successful, inform the callback of the error
                    // Если это не удалось, сообщить вызывающей стороне о неудачном обновлении.
                    titleRefreshCallback.onError(
                            TitleRefreshError("Else Unable to refresh title Не удалось обновить заголовок", null))
                }
            } catch (cause: Throwable) {
                // If anything throws an exception, inform the caller
                // Если что-то вызывает исключение, сообщите об этом вызывающему абоненту
                titleRefreshCallback.onError(
                        TitleRefreshError("Cath Unable to refresh title Не удалось обновить заголовок", cause))
            }
        }
        // Эта реализация, основанная на обратном вызове, является  main-safe,
        // потому что она не будет блокировать основной поток.
        // Но он должен использовать обратный вызов, чтобы сообщить звонящему о завершении работы.
        // Он также вызывает обратные вызовы в BACKGROUND потоке, который он тоже переключил.
    }*/


/**
 * Thrown when there was a error fetching a new title
 * Выбрасывается, когда произошла ошибка при выборке нового заголовка
 *
 * @property message user ready error message
 * @property cause the original cause of this exception
 * сообщение собственность, готовый пользователю сообщение об ошибке
 * @property cause первоначальная причина этого исключения
 *
 * TitleRepository извлекает результаты из сети и сохраняет их в базе данных.
 * TitleRepository реализует единый API для извлечения или обновления заголовка путем объединения данных из сети и базы данных.
 */
class TitleRefreshError(message: String, cause: Throwable?) : Throwable(message, cause)

interface TitleRefreshCallback {
    fun onCompleted()
    fun onError(cause: Throwable)
}
