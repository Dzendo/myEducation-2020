/*
 * Copyright (C) 2017 The Android Open Source Project
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

package paging.android.example.com.pagingsample

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * Database Access Object for the Cheese database.
 * Объект доступа к базе данных для базы данных Cheese.
 */
@Dao
interface CheeseDao {
    /**
     * Room knows how to return a LivePagedListProvider, from which we can get a LiveData and serve
     * it back to UI via ViewModel.
     * Комната знает, как вернуть живого поставщика PagedList, от которого мы можем получить живые данные и служить
     * он возвращается в пользовательский интерфейс через ViewModel.
     */
    @Query("SELECT * FROM Cheese ORDER BY name ASC")  //  COLLATE NOCASE ASC DESC
    fun allCheesesByName(): PagingSource<Int, Cheese>

    @Insert
    fun insert(cheeses: List<Cheese>)

    @Insert
    fun insert(cheese: Cheese)

    @Delete
    fun delete(cheese: Cheese)

    // Справочно: количество строк в таблице
    @Query("SELECT COUNT(id) FROM Cheese")
    fun getCount(): Int

    /**
     * Deletes all values from the table.
     * Удаляет все значения из таблицы.
     *
     * This does not delete the table, only its contents.
     * При этом таблица не удаляется, а только ее содержимое.
     */
    @Query("DELETE FROM Cheese")
    //suspend
    fun clear(): Int


}