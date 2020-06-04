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

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.samples.apps.sunfloweras.utilities.DATABASE_NAME
import com.google.samples.apps.sunfloweras.workers.SeedDatabaseWorker

/**
 * The Room database for this app сразу двух таблиц в комнате
 * База данных Room для этого приложения
 * Есть в уроке 06.01 Android Kotlin Fundamentals
 */
// Создать в Room две таблицы версии 1 и Установите exportSchemaна false, чтобы не держать схемы резервного копирования истории версий.
@Database(entities = [GardenPlanting::class, Plant::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)  // т.е в файле Converters.kt лежат конвертеры дат - две функции
abstract class AppDatabase : RoomDatabase() {
    abstract fun gardenPlantingDao(): GardenPlantingDao    // База данных должна знать о DAO
    abstract fun plantDao(): PlantDao                      // База данных должна знать о DAO

    companion object {

        // For Singleton instantiation Для одноэлементный экземпляр Volatile - НЕ кешировать
        @Volatile private var instance: AppDatabase? = null

        // getInstance() метод с Context параметром, который понадобится построителю базы данных.
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // Создайте и предварительно заполните базу данных.
        // Смотрите эту статью для получения более подробной информации: (Изучать потом)
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)  // DATABASE_NAME = "sunfloweras-db"
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    })
                    .build()
        }
    }
}
