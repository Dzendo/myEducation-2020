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

// Это по сути строчка из будущей таблицы SQL garden_plantings (аннотированный класс данных garden_plantings)
// первый обязательный элемент построения ROOM таблицы объявдение @Entity data class
@Entity(
    tableName = "garden_plantings",     // имя таблицы
    foreignKeys = [                     // Внешние ключи позволяют связывать таблицы между собой.
        ForeignKey(entity = Plant::class, parentColumns = ["id"], childColumns = ["plant_id"])
        // т.е. здесь (в Garden) могут быть только растения с (plant_id) лат названием имеющимся в Plant (id)
    // Другие т.е. которых нет в Plant в Garden не добавятся; Также не даст удалить из Plant при посаженном в Garden
    ],
    indices = [Index("plant_id")]  // ндекс может повысить производительность таблицы, м.б. указан в @ColumnInfo
)
// Класс данных хранимых в строках таблицы с его полями
data class GardenPlanting(
        // Имя растения латинское которое передается при создании строчки и участвует в Index и ForeignKey
    @ColumnInfo(name = "plant_id") val plantId: String,  // видимо id из Plant имя тоже plantId в обоих

    /**
     * Indicates when the [Plant] was planted. Used for showing notification when it's time
     * to harvest the plant.
     * Указывает, когда было посажено [растение]. Используется для отображения уведомлений, когда пришло время
     * чтобы собрать урожай с растения.
     */
    @ColumnInfo(name = "plant_date") val plantDate: Calendar = Calendar.getInstance(),  // т.е. сегодня

    /**
     * Indicates when the [Plant] was last watered. Used for showing notification when it's
     * time to water the plant.
     * Указывает, когда [растение] в последний раз поливали. Используется для отображения уведомлений, когда приходит
     * время поливать растение.
     */
    @ColumnInfo(name = "last_watering_date")
    val lastWateringDate: Calendar = Calendar.getInstance()  // а через сколько поливать в Plant
) {      //  первичный ключ Room авто создает идентификатор для каждого объекта. Здесь id - число Long
        // Это гарантирует, что идентификатор для каждого растения уникален.
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var gardenPlantingId: Long = 0
    // заметить что id не входит в параметры, а просто переменная класса, но в базу пишется
    // свойство id не  будет учитываться в реализациях функций toString(), equals(), hashCode() и copy(),
    // и из него не будет создана только компонентная функция componentN().
    // Даже если два объекта класса Person будут иметь разные значения свойств id, они будут считаться равными.
}