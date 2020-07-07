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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
     */
    suspend fun refreshTitle() {
        try {   // Make network request using a blocking call
            val result = withTimeout(5_000) {
                network.fetchNextTitle()
            }
            titleDao.insertTitle(Title(result))
        } catch (error: Throwable) {   // If the network throws an exception, inform the caller
            throw TitleRefreshError("Unable to refresh title", error)
        }
    }

    // И, если вы выбросите исключение из сопрограммы - эта сопрограмма по умолчанию отменит своего родителя.
    // Это означает, что легко отменить несколько связанных задач вместе.

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
 * @property message user ready error message
 * @property cause the original cause of this exception
 * сообщение собственность, готовый пользователю сообщение об ошибке
 * @property cause первоначальная причина этого исключения
 *
 * TitleRepository извлекает результаты из сети и сохраняет их в базе данных.
 * TitleRepository реализует единый API для извлечения или обновления заголовка путем объединения данных из сети и базы данных.
 */
class TitleRefreshError(message: String, cause: Throwable) : Throwable(message, cause)

interface TitleRefreshCallback {
    fun onCompleted()
    fun onError(cause: Throwable)
}
