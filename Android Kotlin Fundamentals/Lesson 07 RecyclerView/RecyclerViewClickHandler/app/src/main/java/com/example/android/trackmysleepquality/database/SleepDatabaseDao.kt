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

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
//import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


/**
 * Defines methods for using the SleepNight class with Room.
 * Определяет методы для использования класса Sleep Night с номером.
 */
@Dao
interface SleepDatabaseDao {

    @Insert
    fun insert(night: SleepNight)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     * При обновлении строки со значением, уже установленным в столбце,
     * заменяет старое значение на новое.
     *
     * @param night new value to write
     */
    @Update
    fun update(night: SleepNight)

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     * Выбирает и возвращает строку, которая соответствует предоставленному времени начала, что является нашим ключом.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * from daily_sleep_quality_table WHERE nightId = :key")
    fun get(key: Long): SleepNight

    /**
     * Deletes all values from the table.
     * Удаляет все значения из таблицы.
     *
     * This does not delete the table, only its contents.
     * При этом таблица не удаляется, а только ее содержимое.
     */
    @Query("DELETE FROM daily_sleep_quality_table")
    fun clear()

    /**
     * Selects and returns all rows in the table,
     *Выбирает и возвращает все строки в таблице,
     * sorted by start time in descending order.
     * сортировка по времени начала в порядке убывания.
     */
    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC")
    fun getAllNights(): LiveData<List<SleepNight>>

    /**
     * Selects and returns the latest night.
     * Выбирает и возвращает последнюю ночь.
     */
    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
    fun getTonight(): SleepNight?

    /**
     * Selects and returns the night with given nightId.
     * Выбирает и возвращает ночь с вечером я.
     */
    @Query("SELECT * from daily_sleep_quality_table WHERE nightId = :key")
    fun getNightWithId(key: Long): LiveData<SleepNight>
}
