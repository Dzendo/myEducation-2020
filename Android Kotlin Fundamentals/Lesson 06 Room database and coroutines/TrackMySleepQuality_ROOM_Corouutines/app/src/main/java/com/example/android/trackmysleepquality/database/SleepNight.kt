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

package com.example.android.trackmysleepquality.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * SleepNight класс данных с параметрами для идентификатора,
 * времени начала (в миллисекундах), времени окончания (в миллисекундах)
 * и числовой оценки качества сна.
 * одна ночь сна как аннотированный класс данных
 * К этой строке таблицы нужны еще: ИнтерфейсDAO - методы-свойства-операции над таблицей - запросы SQL
 */
// Аргумент для tableName является необязательным, но рекомендуется (имя Таблицы) внутри базы
@Entity(tableName = "daily_sleep_quality_table")
data class SleepNight(

        @PrimaryKey(autoGenerate = true)
        var nightId: Long = 0L,

        @ColumnInfo(name = "start_time_milli")      // Время началп сна
        val startTimeMilli: Long = System.currentTimeMillis(),

        @ColumnInfo(name = "end_time_milli")
        var endTimeMilli: Long = startTimeMilli,  // время окончания еще не было записано

        @ColumnInfo(name = "quality_rating")   // качество сна 0-5
        var sleepQuality: Int = -1  // -1 - качественные данные не были собраны
)

// Чтобы идентифицировать nightId первичный ключ, пометьте nightId свойство как @PrimaryKey.
// Установите параметр , autoGenerate чтобы true таким образом , что Room создает идентификатор для каждого объекта.
// Это гарантирует, что идентификатор для каждой ночи уникален.
// ColumnInfo -  Настройте имена свойств (Столбцов)
