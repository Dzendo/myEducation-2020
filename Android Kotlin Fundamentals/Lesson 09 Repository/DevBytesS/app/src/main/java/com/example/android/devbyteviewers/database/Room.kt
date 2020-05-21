/*
 * Copyright (C) 2019 Google Inc.
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

package com.example.android.devbyteviewers.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

/*  Шаг 3: Добавить VideoDao
На этом этапе вы реализуете VideoDao и определяете два вспомогательных метода для доступа к базе данных.
Один вспомогательный метод получает видео из базы данных, а другой метод вставляет видео в базу данных.
 */
// Определите @Dao интерфейс, который называется VideoDao:
@Dao
interface VideoDao {
// 1. Рефакторинг для использования LiveData:
//В Room.kt, все, что вам нужно сделать, это обновить, getVideos() чтобы вернуть a LiveData of List of DatabaseVideo.
    //метод, вызываемый getVideos() для извлечения всех видео из базы данных.
// Измените тип возврата этого метода на LiveData,
// чтобы данные, отображаемые в пользовательском интерфейсе,
// обновлялись при каждом изменении данных в базе данных.
    @Query("select * from databasevideo")
    fun getVideos(): LiveData<List<DatabaseVideo>>
    //  Добавить getVideos () Query функция , VideoDao которая возвращает List из DatabaseVideo

    // метод для вставки списка видео, извлеченных из сети, в базу данных
    // Для простоты перезаписать запись базы данных, если запись видео уже присутствует в базе данных.
    // Для этого используйте onConflict аргумент, чтобы установить стратегию конфликта REPLACE.
    // Добавьте Insert вызываемую функцию insertAll() к вашему, VideoDao которая принимает vararg DatabaseVideo:
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(videos: List<DatabaseVideo>)      // Codelabs
    //fun insertAll(vararg videos: DatabaseVideo)  // udacity = дает ошибку на сборке
}

/* Шаг 4: Реализация RoomDatabase
На этом шаге вы добавляете базу данных для автономного кэша путем реализации RoomDatabase.
 */
// @Database аннотацию, чтобы пометить VideosDatabase класс как Room базу данных.
// Объявите DatabaseVideo объект, который принадлежит в этой базе данных, и установите номер версии в 1.
@Database(entities = [DatabaseVideo::class], version = 1)
abstract class VideosDatabase: RoomDatabase() {
    // переменную типа VideoDao для доступа к Dao методам.
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
                        VideosDatabase::class.java,
                        "videos").build()
            }
        }
        return INSTANCE
    }
    // Совет : Свойство .isInitialized Kotlin возвращается, если свойству ( в этом примере) было присвоено значение true,
    // и в противном случае lateinit INSTANCE false


// Теперь вы реализовали базу данных, используя Room.
// В следующей задаче вы узнаете, как использовать эту базу данных, используя шаблон репозитория.
// т.е все лежит в пакете database

// Это последняя задача для реализации вашей базы данных Room, т.е. КЕШа для автономного кэширования.
// В следующем разделе вы интегрируете это в приложение с помощью репозитория!