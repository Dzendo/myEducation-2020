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

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

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
     * Fetch a list of [Plant]s from the database.
     * Returns a LiveData-wrapped List of Plants.
     * Извлеките список [растений]из базы данных.
     * Возвращает список растений, завернутый в живые данные.
     */
    val plants = plantDao.getPlants()

    /**
     * Fetch a list of [Plant]s from the database that matches a given [GrowZone].
     * Returns a LiveData-wrapped List of Plants.
     * Извлеките список [растений]из базы данных, который соответствует заданной [GrowZone].
     * Возвращает список растений, завернутый в живые данные.
     */
    fun getPlantsWithGrowZone(growZone: GrowZone) =
        plantDao.getPlantsWithGrowZoneNumber(growZone.number)

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
