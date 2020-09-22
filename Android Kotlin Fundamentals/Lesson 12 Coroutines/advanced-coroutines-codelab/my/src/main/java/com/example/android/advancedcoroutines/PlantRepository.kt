/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.advancedcoroutines

import androidx.annotation.AnyThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.example.android.advancedcoroutines.util.CacheOnSuccess
import com.example.android.advancedcoroutines.utils.ComparablePair
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/* четырех растений: апельсина, подсолнечника, винограда и авокадо */
/**
 * Repository module for handling data operations.
 * модуль репозитория для обработки операций с данными.
 *
 * This PlantRepository exposes two UI-observable database queries [plants] and
 * [getPlantsWithGrowZone].
 * Этот репозиторий растений предоставляет два UI-наблюдаемых запроса к базе данных [растения] и
 * [*[получить растения с зоной роста].
 *
 * To update the plants cache, call [tryUpdateRecentPlantsForGrowZoneCache] or
 * [tryUpdateRecentPlantsCache].
 * обновите кэш растений, вызовите [попробуйте обновить последние растения для кэша зоны роста] или
 * [попробуйте обновить кэш последних растений].
 */
class PlantRepository private constructor(
    private val plantDao: PlantDao,
    private val plantService: NetworkService,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    /**
     * 6.rezult :
     * LiveData Строитель позволяет значениям CALCulate асинхронно, так как liveData поддерживается сопрограммам.
     * Здесь у нас есть функция приостановки для извлечения LiveData списка растений из базы данных,
     * а также вызов функции приостановки для получения настраиваемого порядка сортировки.
     * Затем мы объединяем эти два значения, чтобы отсортировать список растений и вернуть значение в конструкторе.
     *
     * Вы можете генерировать несколько значений из a LiveData,
     * вызывая emitSource() функцию всякий раз, когда вы хотите передать новое значение.
     * Обратите внимание, что каждый вызов emitSource() удаляет ранее добавленный источник.
     *
     * Сопрограмма начинает выполнение, когда за ней наблюдают,
     * и отменяется, когда сопрограмма успешно завершает работу
     * или при сбое базы данных или сетевого вызова.
     *
     * Если какой-либо из вызовов функции приостановки терпит неудачу,
     * весь блок отменяется и не перезапускается, что помогает избежать утечек.
     *
     */


    // Мы начнем с написания функции приостановки для получения пользовательского порядка сортировки из сети,
    // а затем кэширования его в памяти.
    // 5. Cache for storing the custom sort order
    // 5. Кэш для хранения пользовательского порядка сортировки
    private var plantsListSortOrderCache = CacheOnSuccess(onErrorFallback = { listOf<String>() }) {
        plantService.customPlantSortOrder()  // suspend fun + withContext(Dispatchers.Default)
        // Эта сервис функция передается классу CacheOnSuccess чтобы она ей считала из сети в память(кешировала)
    }


    /**
     * Fetch a list of [Plant]s from the database.
     * Returns a LiveData-wrapped List of Plants.
     * Извлеките список [растений]из базы данных.
     * Возвращает список растений, завернутый в живые данные.
     */
    //val plants = plantDao.getPlants()

    // 6. Теперь, когда логика сортировки на месте,
    // замените код для plants и getPlantsWithGrowZone на LiveData построитель ниже:
    /**
     * Fetch a list of [Plant]s from the database and apply a custom sort order to the list.
     * Returns a LiveData-wrapped List of Plants.
     * Извлеките список [растений]из базы данных и примените к нему пользовательский порядок сортировки.
     * Возвращает список растений, завернутый в живые данные.
     */
    val plants: LiveData<List<Plant>> = liveData<List<Plant>> {
        // Observe plants from the database (just like a normal LiveData + Room return)
        // Наблюдайте за растениями из базы данных (точно так же, как обычные живые данные + возврат комнаты)
        val plantsLiveData = plantDao.getPlants()

        // Fetch our custom sort from the network in a main-safe suspending call (cached)
        // Извлеките нашу пользовательскую сортировку из сети в основном безопасном приостановленном вызове (кэшированном)
        val customSortOrder = plantsListSortOrderCache.getOrAwait()

        // Map the LiveData, applying the sort criteria
        // Сопоставьте текущие данные, применяя критерии сортировки
        emitSource(plantsLiveData.map { plantList -> plantList.applySort(customSortOrder) })
    }

    /** 6.
     * Fetch a list of [Plant]s from the database that matches a given [GrowZone] and apply a
     * custom sort order to the list. Returns a LiveData-wrapped List of Plants.
     * Извлеките список [растений]из базы данных, который соответствует заданной [GrowZone], и примените
     * пользовательский порядок сортировки списка. Возвращает список растений в реальном времени, завернутый в данные.
     *
     * This this similar to [plants], but uses *main-safe* transforms to avoid blocking the main thread.
     * Это похоже на [plants], но использует *main-safe* преобразования, чтобы избежать блокировки основного потока.
     */
    fun getPlantsWithGrowZone(growZone: GrowZone) = liveData {
        val plantsGrowZoneLiveData = plantDao.getPlantsWithGrowZoneNumber(growZone.number)
        val customSortOrder = plantsListSortOrderCache.getOrAwait()
        emitSource(plantsGrowZoneLiveData.map { plantList ->
            plantList.applySort(customSortOrder)
        })
    }
    /** 7.
     * Затем мы можем использовать эту новую безопасную сортировку с LiveData построителем.
     * Обновите блок, чтобы использовать a switchMap,
     * который позволит вам указывать на новое LiveData каждый раз,
     * когда будет получено новое значение.
     */

    fun getPlantsWithGrowZone7(growZone: GrowZone) =
        plantDao.getPlantsWithGrowZoneNumber(growZone.number)

            // Apply switchMap, which "switches" to a new liveData every time a new value is received
            // Применить switch Map, который "переключается" на новые текущие данные каждый раз,
            // когда получено новое значение
            .switchMap { plantList ->

                // Use the liveData builder to construct a new coroutine-backed LiveData
                // Использовать текущий построитель данных построить новый coroutine-backed LiveData
                liveData {
                    val customSortOrder = plantsListSortOrderCache.getOrAwait()

                    // Emit the sorted list to the LiveData builder, which will be the new value
                    // sent to getPlantsWithGrowZoneNumber
                    // Выделяющие отсортированный список на текущий застройщика сведения,
                    // которые будут новое значение отправлено для получения растений с номером зоны роста
                    emit(plantList.applyMainSafeSort(customSortOrder))
                }
            }
    /**7.
     * По сравнению с предыдущей версией, как только пользовательский порядок сортировки получен из сети,
     * его можно использовать с новым основным сейфом applyMainSafeSort.
     * Этот результат затем передается объекту switchMap как новое значение,
     * возвращаемое объектом getPlantsWithGrowZone.
     *
     * Подобно plantsLiveData выше, сопрограмма начинает выполнение, когда оно наблюдается,
     * и завершается либо по завершении, либо в случае сбоя базы данных или сетевого вызова.
     * Разница здесь в том, что можно безопасно выполнять сетевой вызов на карте, поскольку она кэширована.
     *
     */


    /**
     * Fetch a list of [Plant]s from the database that matches a given [GrowZone].
     * Returns a LiveData-wrapped List of Plants.
     * Извлеките список [растений]из базы данных, который соответствует заданной [GrowZone].
     * Возвращает список растений, завернутый в живые данные.
     */
   //fun getPlantsWithGrowZone(growZone: GrowZone) =
   //     plantDao.getPlantsWithGrowZoneNumber(growZone.number)

    /**
     * Returns true if we should make a network request.
     * Возвращает true, если мы должны сделать сетевой запрос.
     */
    private fun shouldUpdatePlantsCache(): Boolean {
        // suspending function, so you can e.g. check the status of the database here
        // функция приостановки, так что вы можете, например, проверить состояние базы данных здесь
        return true
    }

    /**
     * Update the plants cache.
     * Обновите кэш растений.
     *
     * This function may decide to avoid making a network requests on every call based on a
     * cache-invalidation policy.
     * Эта функция может принять решение избегать выполнения сетевых запросов при каждом вызове на основе
     * политика аннулирования кэша.
     */
    suspend fun tryUpdateRecentPlantsCache() {
        if (shouldUpdatePlantsCache()) fetchRecentPlants()
    }

    /**
     * Update the plants cache for a specific grow zone.
     * Обновите кэш растений для определенной зоны выращивания.
     *
     * This function may decide to avoid making a network requests on every call based on a
     * cache-invalidation policy.
     * Эта функция может принять решение избегать выполнения сетевых запросов при каждом вызове на основе
     * политика аннулирования кэша.
     */
    suspend fun tryUpdateRecentPlantsForGrowZoneCache(growZoneNumber: GrowZone) {
        if (shouldUpdatePlantsCache()) fetchPlantsForGrowZone(growZoneNumber)
    }

    /**
     * Fetch a new list of plants from the network, and append them to [plantDao]
     * Извлеките новый список растений из сети и добавьте их в [plant Dao]
     */
    private suspend fun fetchRecentPlants() {
        val plants = plantService.allPlants()
        plantDao.insertAll(plants)
    }

    /**
     * Fetch a list of plants for a grow zone from the network, and append them to [plantDao]
     * Извлеките список растений для зоны выращивания из сети и добавьте их в [plant Dao]
     */
    private suspend fun fetchPlantsForGrowZone(growZone: GrowZone) {
        val plants = plantService.plantsByGrowZone(growZone)
        plantDao.insertAll(plants)
    }

    /** 5. Теперь давайте включим некоторую логику, чтобы применить сортировку к списку растений.
     * A function that sorts the list of Plants in a given custom order.
     * Функция, которая сортирует список растений в заданном пользовательском порядке.
     * Эта функция расширения изменит порядок списка,
     * поместив те, Plants которые находятся в customSortOrder начале списка.
     */
    private fun List<Plant>.applySort(customSortOrder: List<String>): List<Plant> {
        // Our product manager requested that these plants always be sorted first in this
        // order whenever they are present in the array
        // Наш менеджер по продукту просил, чтобы эти растения всегда сортировались в первую очередь в этом случае.
        // order всякий раз, когда они присутствуют в массиве
        return sortedBy { plant ->
            val positionForItem = customSortOrder.indexOf(plant.plantId).let { order ->
                if (order > -1) order else Int.MAX_VALUE
                // просто в списке задирается из порядок сортировки на Int.MAX_VALUE
            }
            // здесь и есть условие сортировки для sortedBy
            ComparablePair(positionForItem, plant.name)
        }
    }
    /** 7. Теперь мы внесем изменения, PlantRepository
     *  чтобы реализовать приостанавливающее преобразование по мере обработки каждого значения,
     *  изучая, как создавать сложные асинхронные преобразования в LiveData.
     *  В качестве предварительного условия давайте создадим версию алгоритма сортировки,
     *  безопасную для использования в основном потоке.
     *  Мы можем использовать withContext для переключения на другой диспетчер только для лямбда-выражения,
     *  а затем возобновить работу с диспетчером, с которого мы начали
     */
    /**
     * The same sorting function as [applySort], but as a suspend function that can run on any thread (main-safe)
     * Та же функция сортировки, что и [applySort], но как функция приостановки, которая может выполняться в любом потоке (main-safe)
     */
    @AnyThread
    private suspend fun List<Plant>.applyMainSafeSort(customSortOrder: List<String>) =
        withContext(defaultDispatcher) {
            this@applyMainSafeSort.applySort(customSortOrder)
        }

    /**
     * Для переключения между любым диспетчером сопрограммы используют withContext.
     * Вызов withContext переключается на другой диспетчер только для лямбды,
     * затем возвращается диспетчеру, который вызвал его с результатом этой лямбды.

     * По умолчанию Котлин сопрограммы предоставляет три диспетчеры: Main, IO, и Default.
     * Диспетчер ввода-вывода оптимизирован для работы ввода-вывода, такой как чтение из сети или с диска,
     * в то время как диспетчер по умолчанию оптимизирован для задач, интенсивно использующих ЦП.
     */

    companion object {

        // For Singleton instantiation
        // Для Одноэлементного экземпляра
        @Volatile private var instance: PlantRepository? = null

        fun getInstance(plantDao: PlantDao, plantService: NetworkService) =
            instance ?: synchronized(this) {
                instance ?: PlantRepository(plantDao, plantService).also { instance = it }
            }
    }
}
