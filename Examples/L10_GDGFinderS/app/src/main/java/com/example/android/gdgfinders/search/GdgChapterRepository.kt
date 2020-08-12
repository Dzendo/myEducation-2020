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


package com.example.android.gdgfinders.search

import android.location.Location
import com.example.android.gdgfinders.network.GdgApiService
import com.example.android.gdgfinders.network.GdgChapter
import com.example.android.gdgfinders.network.GdgResponse
import com.example.android.gdgfinders.network.LatLong
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class GdgChapterRepository(gdgApiService: GdgApiService) {

    /**
     * A single network request, the results won't change. For this lesson we did not add an offline cache for simplicity
     * and the result will be cached in memory.
     * Один сетевой запрос-и результаты не изменятся. Для этого урока мы не добавили автономный кэш для простоты
     * и результат будет кэшироваться в памяти.
     */
    private val request = gdgApiService.getChapters()

    /**
     * An in-progress (or potentially completed) sort, this may be null or cancelled at any time.
     * Если сортировка выполняется (или потенциально завершена), она может быть нулевой или отменена в любое время.
     *
     * If this is non-null, calling await will get the result of the last sorting request.
     * Если это ненулевое значение, вызов await приведет к получению результата последнего запроса сортировки.
     *
     * This will be cancelled whenever location changes, as the old results are no longer valid.
     * Это будет отменено всякий раз, когда местоположение меняется, так как старые результаты больше не действительны.
     */
    private var inProgressSort: Deferred<SortedData>? = null

    var isFullyInitialized = false
        private set


    /**
     * Get the chapters list for a specified filter.
     * Получите список глав для указанного фильтра.
     *
     * This will be cancel if a new location is sent before the result is available.
     * Это будет отменено, если новое местоположение будет отправлено до того, как результат будет доступен.
     *
     * This works by first waiting for any previously in-progress sorts, and if a sort has not yet started
     * it will start a new sort (which may happen if location is disabled on the device)
     * Это работает, сначала ожидая любых ранее выполненных сортировок, и если сортировка еще не началась
     * он начнет новую сортировку (что может произойти, если местоположение на устройстве отключено)
     */
    suspend fun getChaptersForFilter(filter: String?): List<GdgChapter> {
        val data = sortedData()
        return when(filter) {
            null -> data.chapters
            else -> data.chaptersByRegion.getOrElse(filter) { emptyList() }
        }
    }

    /**
     * Get the filters sorted by distance from the last location.
     * Получите фильтры, отсортированные по расстоянию от последнего местоположения.
     *
     * This will cancel if a new location is sent before the result is available.
     * Это будет отменено, если новое местоположение будет отправлено до того, как результат будет доступен.
     *
     * This works by first waiting for any previously in-progress sorts, and if a sort has not yet started
     * it will start a new sort (which may happen if location is disabled on the device)
     * Это работает, сначала ожидая любых ранее выполненных сортировок, и если сортировка еще не началась
     * он начнет новую сортировку (что может произойти, если местоположение на устройстве отключено)
     */
    suspend fun getFilters(): List<String> = sortedData().filters

    /**
     * Get the computed sorted data after it completes, or start a new sort if none are running.
     * Получите вычисленные сортированные данные после их завершения или начните новую сортировку, если ни один из них не выполняется.
     *
     *
     * This will always cancel if the location changes while the sort is in progress.
     * Это всегда будет отменено, если местоположение изменяется во время выполнения сортировки.
     *
     */
    private suspend fun sortedData(): SortedData = withContext(Dispatchers.Main) {
        // We need to ensure we're on Dispatchers.Main so that this is not running on multiple Dispatchers and we
        // modify the member inProgressSort.
        // Нам нужно убедиться, что мы на диспетчерах.Главное чтобы это работало не на нескольких диспетчерах а у нас
        // измените элемент в процессе сортировки.

        // Since this was called from viewModelScope, that will always be a simple if check (not expensive), but
        // by specifying the dispatcher we can protect against incorrect usage.
        // Поскольку это было вызвано из области видимости viewModel, это всегда будет простой проверкой if (не дорогой), но
        // указав диспетчера, мы можем защитить его от неправильного использования..

        // if there's currently a sort running (or completed) wait for it to complete and return that value
        // otherwise, start a new sort with no location (the user has likely not given us permission to use location
        // yet)
        // если в данный момент выполняется сортировка (или завершена), дождитесь ее завершения и верните это значение
        // в противном случае начните новую сортировку без местоположения (пользователь, скорее всего, не дал нам разрешения на использование местоположения
        // пока)
        inProgressSort?.await() ?: doSortData()
    }

    /**
     * Call this to force a new sort to start.
     * Вызовите это, чтобы заставить новый вид начать работу.
     *
     * This will start a new coroutine to perform the sort. Future requests to sorted data can use the deferred in
     * [inProgressSort] to get the result of the last sort without sorting the data again. This guards against multiple
     * sorts being performed on the same data, which is inefficient.
     * Это приведет к запуску новой сопрограммы для выполнения сортировки. Запросы функций на сортировку данных могут использовать отложенные в
     * [в процессе сортировки], чтобы получить результат последней сортировки без повторной сортировки данных. Это предохраняет от множественного
     * сортировка выполняется на одних и тех же данных, что неэффективно.
     *
     * This will always cancel if the location changes while the sort is in progress.
     * Это всегда будет отменено, если местоположение изменяется во время выполнения сортировки.
     *
     * @return the result of the started sort
     * возвращает результат начатой сортировки
     */
    private suspend fun doSortData(location: Location? = null): SortedData {
        // since we'll need to launch a new coroutine for the sorting use coroutineScope.
        // coroutineScope will automatically wait for anything started via async {} or await{} in it's block to
        // complete.
        // поскольку нам нужно будет запустить новую сопрограмму для сортировки, используйте сопрограммы cope.
        // coroutineScope будет автоматически ждать, пока что-то начнется через асинхронный {} или{} в блок
        // полный.
        val result = coroutineScope {
            // launch a new coroutine to do the sort (so other requests can wait for this sort to complete)
            // запустите новую сопрограмму для выполнения сортировки (чтобы другие запросы могли дождаться завершения этой сортировки)
            val deferred = async { SortedData.from(request.await(), location) }
            // cache the Deferred so any future requests can wait for this sort
            // кэшируйте отложенные запросы, чтобы любые будущие запросы могли ждать этой сортировки
            inProgressSort = deferred
            // and return the result of this sort
            // и возвращает результат такого рода
            deferred.await()
        }
        return result
    }

    /**
     * Call when location changes.
     * Позвоните, когда местоположение изменится.
     *
     * This will cancel any previous queries, so it's important to re-request the data after calling this function.
     * Это отменит все предыдущие запросы, поэтому важно повторно запросить данные после вызова этой функции.
     *
     * @param location the location to sort by
     * @param location расположение для сортировки по
     */
    suspend fun onLocationChanged(location: Location) {
        // We need to ensure we're on Dispatchers.Main so that this is not running on multiple Dispatchers and we
        // modify the member inProgressSort.
        // Нам нужно убедиться, что мы на диспетчерах.Главное чтобы это работало не на нескольких диспетчерах а у нас
        // измените элемент в процессе сортировки.

        // Since this was called from viewModelScope, that will always be a simple if check (not expensive), but
        // by specifying the dispatcher we can protect against incorrect usage.
        // Поскольку это было вызвано из области видимости viewModel, это всегда будет простой проверкой if (не дорогой), но
        // указав диспетчера, мы можем защитить его от неправильного использования.
        withContext(Dispatchers.Main) {
            isFullyInitialized = true

            // cancel any in progress sorts, their result is not valid anymore.
            inProgressSort?.cancel()

            doSortData(location)
        }
    }

    /**
     * Holds data sorted by the distance from the last location.
     * Содержит данные, отсортированные по расстоянию от последнего местоположения.
     *
     * Note, by convention this class won't sort on the Main thread. This is not a public API and should
     * only be called by [doSortData].
     * Обратите внимание, что по соглашению этот класс не будет сортироваться в основном потоке. Это не публичный API и должно быть
     * ызывается только по [сортированным данным].
     */
    private class SortedData private constructor(
        val chapters: List<GdgChapter>,
        val filters: List<String>,
        val chaptersByRegion: Map<String, List<GdgChapter>>
    ) {

        companion object {
            /**
             * Sort the data from a [GdgResponse] by the specified location.
             * Сортировка данных из ответа [Gdg] по указанному местоположению.
             *
             * @param response the response to sort
             * @param location the location to sort by, if null the data will not be sorted.
             * @param response ответ на сортировку
             * @param location расположение для сортировки, если значение null, то данные не будут отсортированы.
             */
            suspend fun from(response: GdgResponse, location: Location?): SortedData {
                return withContext(Dispatchers.Default) {
                    // this sorting is too expensive to do on the main thread, so do thread confinement here.
                    val chapters: List<GdgChapter> = response.chapters.sortByDistanceFrom(location)
                    // use distinctBy which will maintain the input order - this will have the effect of making
                    // a filter list sorted by the distance from the current location
                    val filters: List<String> = chapters.map { it.region } .distinctBy { it }
                    // group the chapters by region so that filter queries don't require any work
                    val chaptersByRegion: Map<String, List<GdgChapter>> = chapters.groupBy { it.region }
                    // return the sorted result
                    SortedData(chapters, filters, chaptersByRegion)
                }

            }


            /**
             * Sort a list of GdgChapter by their distance from the specified location.
             * Отсортируйте список глав Gdg по их удаленности от указанного местоположения.
             *
             * @param currentLocation returned list will be sorted by the distance, or unsorted if null
             * @возвращаемый список @ param current Location будет отсортирован по расстоянию или несортирован, если значение равно null
             */
            private fun List<GdgChapter>.sortByDistanceFrom(currentLocation: Location?): List<GdgChapter> {
                currentLocation ?: return this

                return sortedBy { distanceBetween(it.geo, currentLocation)}
            }

            /**
             * Calculate the distance (in meters) between a LatLong and a Location.
             * Вычислите расстояние (в метрах) между Латлонгом и местоположением.
             */
            private fun distanceBetween(start: LatLong, currentLocation: Location): Float {
                val results = FloatArray(3)
                Location.distanceBetween(start.lat, start.long, currentLocation.latitude, currentLocation.longitude, results)
                return results[0]
            }
        }
    }
}