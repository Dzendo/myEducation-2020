/*
 * Copyright 2018, The Android Open Source Project
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
 *
 */

package com.example.android.devbyteviewer.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface VideoDao {
    @Query("select * from databasevideo")
    fun getVideos(): LiveData<List<DatabaseVideo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg videos: DatabaseVideo)
}

/* Шаг 10: Реализация RoomDatabase
На этом шаге вы добавляете базу данных для автономного кэша путем реализации RoomDatabase.
 */
// @Database аннотацию, чтобы пометить VideosDatabase класс как Room базу данных.
// Объявите DatabaseVideo объект, который принадлежит в этой базе данных, и установите номер версии в 1.
@Database(entities = [DatabaseVideo::class], version = 1, exportSchema = false)
abstract class VideosDatabase: RoomDatabase() {
    // помнить и хранить переменную типа VideoDao для доступа к Dao методам.
    abstract val videoDao: VideoDao
}
// База данных номеров почти завершена.
// переменную, вызываемую INSTANCE вне классов, для хранения одноэлементного объекта.
// Параметр VideosDatabase должен быть одноэлементным,
// чтобы предотвратить одновременное открытие нескольких экземпляров базы данных.
// Создайте переменную для вас singleton:
private lateinit var INSTANCE: VideosDatabase

// Создайте и определите getDatabase()метод вне классов.
// В getDatabase(), инициализируйте и верните INSTANCE переменную внутри synchronized блока.
// Определите getDatabase()функцию для возврата VideosDatabase:
fun getDatabase(context: Context): VideosDatabase {

    // Убедитесь, что ваш код синхронизирован, поэтому он безопасен для потоков:
    // Оберните приведенное выше выражение if следующим утверждением:
    synchronized(VideosDatabase::class.java) {
        // Проверьте, была ли база данных инициализирована: Если это не так, то инициализируйте его.
        if (!::INSTANCE.isInitialized) {        // проверки инициализации переменной
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                    VideosDatabase::class.java,   // Это инициализация базы данных
                    "videos").build()  // videos - это имя Room на диске
        }
    }
    return INSTANCE   // возврат ссылки на инициализированнную базу данных
}
// Совет : Свойство .isInitialized Kotlin возвращается, если свойству ( в этом примере) было присвоено значение true,
// и в противном случае lateinit INSTANCE false

// Теперь вы реализовали базу данных, используя Room.
// В следующей задаче вы узнаете, как использовать эту базу данных, используя шаблон репозитория.
// т.е все лежит в пакете database

// Это последняя задача для реализации вашей базы данных Room, т.е. КЕШа для автономного кэширования.
