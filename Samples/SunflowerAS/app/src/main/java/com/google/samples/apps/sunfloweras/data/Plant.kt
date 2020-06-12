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
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.Calendar.DAY_OF_YEAR

// Это по сути строчка из будущей таблицы SQL plants (аннотированный класс данных Plant)
// первый обязательный элемент построения ROOM таблицы объявдение @Entity data class
@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey @ColumnInfo(name = "id") val plantId: String,   // Название на типа Латинское: "malus-pumila" == plant_id Garden
    val name: String,                                           // Имя по простому: "Apple"
    val description: String,                                    // Описание текст: Яблони культивируются во всем мире и являются
    val growZoneNumber: Int,                                    // 3 или 2 или 9
    val wateringInterval: Int = 7, // how often the plant should be watered, in days - как часто растение следует поливать, в днях
    val imageUrl: String = ""  // Ссылка jpg картинки 4,25 мб и 4938х3264 пикселей
) {

    /**
     * Determines if the plant should be watered.  Returns true if [since]'s date > date of last
     * watering + watering Interval; false otherwise.
     * Определяет, следует ли поливать растение. Возвращает true, если [since]'s date > дата последнего
     * полив + интервал полива; в противном случае ложь.
     */
    // здесь только форимуется ответ и ничего не записывается
    fun shouldBeWatered(since: Calendar, lastWateringDate: Calendar) =
        since > lastWateringDate.apply { add(DAY_OF_YEAR, wateringInterval) }
       // since.after(lastWateringDate.apply { add(DAY_OF_YEAR, wateringInterval) }) // То же самое через API
    // abstract void add(int field, int value) - добавляет value к компоненту времени или даты, указанному в параметре field
    // т.е. к дате lastWateringDate к его ДНЯМ добавить 7 и сравнивать

    // Хорошая функция для строки - нормальное имя - запомнить применять
    override fun toString() = name
}
