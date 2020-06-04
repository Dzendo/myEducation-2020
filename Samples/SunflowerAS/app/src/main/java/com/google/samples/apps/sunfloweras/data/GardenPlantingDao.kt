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

package com.google.samples.apps.sunfloweras.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

/**
 * The Data Access Object for the [GardenPlanting] class.
 * Объект доступа к данным для класса [садовая посадка].
 * DAO - определении пользовательского интерфейса для доступа к базе данных
 * GsrdenPlanting === garden_plantings со строками  GardenPlanting
 * suspend - значит будет вызываться в coroutines
 */
@Dao
interface GardenPlantingDao {
    // дать List всех посаженных растений (и в Live его)
    @Query("SELECT * FROM garden_plantings")
    fun getGardenPlantings(): LiveData<List<GardenPlanting>>

    // Посажено - true или нет false конкрентое растение plant_id = :plantId
    @Query("SELECT EXISTS(SELECT 1 FROM garden_plantings WHERE plant_id = :plantId LIMIT 1)")
    fun isPlanted(plantId: String): LiveData<Boolean>

    /**
     * This query will tell Room to query both the [Plant] and [GardenPlanting] tables and handle
     * the object mapping.
     * Этот запрос будет сообщить Room, запрос обоим [завод] и [сад посадка]
     * таблицам и обработка сопоставления объектов.
     *
     */
    // Дать List всех растений из plants id которых есть в garden_plantings с названием plant_id
    @Transaction  // позволяет выполнять несколько методов в рамках одной транзакции.
    @Query("SELECT * FROM plants WHERE id IN (SELECT DISTINCT(plant_id) FROM garden_plantings)")
    fun getPlantedGardens(): LiveData<List<PlantAndGardenPlantings>>

    // просто добавить готовый объект-строку GardenPlanting в garden_plantings
    @Insert
    suspend fun insertGardenPlanting(gardenPlanting: GardenPlanting): Long

    // просто Удалить готовый объект-строку GardenPlanting в garden_plantings
    // Delete ищет в бд запись по ключу. то ли id то ли key plantId ???? какименно
    @Delete
    suspend fun deleteGardenPlanting(gardenPlanting: GardenPlanting)

    /*
    Вот и все. Room сгенерирует весь необходимый код для вставки/замены/удаления GardenPlanting в базу данных.
    Когда вы вызываете insert() из своего кода Kotlin, Room в ыполняет запрос SQL для вставки объекта в базу данных.
    (Примечание. Вы можете вызывать функцию как угодно.)
     */
    // Вставил AS для удаления растения из садика и для комплекта (добавить в тесты)
    @Query("SELECT * FROM garden_plantings WHERE plant_id = :plantId")
    suspend fun getGardenPlanting(plantId: String): GardenPlanting //LiveData<GardenPlanting>

    // Вставил AS для очистки садика и для комплекта (добавить в тесты)
    @Query("DELETE FROM garden_plantings")
    suspend fun clearGarden()
}