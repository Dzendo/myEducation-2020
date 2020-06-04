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

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Calendar

/**
 * [GardenPlanting] represents when a user adds a [Plant] to their garden, with useful metadata.
 * Properties such as [lastWateringDate] are used for notifications (such as when to water the
 * plant).
 * [Садовая посадка] представляет собой, когда пользователь добавляет [растение] в свой сад, с полезными метаданными.
 * Такие свойства, как [последние данные полива], используются для уведомлений (например, когда нужно поливать воду).
 * в саду).
 *
 * Declaring the column info allows for the renaming of variables without implementing a
 * database migration, as the column name would not change.
 * Объявление информации о столбце позволяет переименовывать переменные без реализации
 * миграция базы данных, так как имя столбца не изменится.
 */
// Это по сути строчка из будущей таблицы SQL (аннотированный класс данных) наверное Garden
// первый обязательный элемент построения ROOM
@Entity(
    tableName = "garden_plantings",     // имя таблицы
    foreignKeys = [                     // внешние Ключи таблицы ????
        ForeignKey(entity = Plant::class, parentColumns = ["id"], childColumns = ["plant_id"])
    ],
    indices = [Index("plant_id")]  // индекс ???
)
// Класс данных хранимых в строках таблицы с его полями
data class GardenPlanting(
        // Видимо имя растения которое передается при создании строчки и участвует в Index и ForeignKey
    @ColumnInfo(name = "plant_id") val plantId: String,  // видимо id из Plant имя тоже plantId в обоих

    /**
     * Indicates when the [Plant] was planted. Used for showing notification when it's time
     * to harvest the plant.
     * Указывает, когда было посажено [растение]. Используется для отображения уведомлений, когда пришло время
     * чтобы собрать урожай с растения.
     */
    @ColumnInfo(name = "plant_date") val plantDate: Calendar = Calendar.getInstance(),

    /**
     * Indicates when the [Plant] was last watered. Used for showing notification when it's
     * time to water the plant.
     * Указывает, когда [растение] в последний раз поливали. Используется для отображения уведомлений, когда приходит
     * время поливать растение.
     */
    @ColumnInfo(name = "last_watering_date")
    val lastWateringDate: Calendar = Calendar.getInstance()  // а через сколько поливать в Plant
) {      //  первичный ключ Room авто создает идентификатор для каждого объекта.
        // Это гарантирует, что идентификатор для каждого растения уникален.
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var gardenPlantingId: Long = 0
}