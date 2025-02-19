/*
 * Copyright (C) 2019 Google LLC
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

package com.example.android.kotlincoroutines.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Title represents the title fetched from the network
 * Заголовок представляет собой заголовок, извлеченный из сети
 * MainDatabase реализует базу данных с помощью номера , который сохраняет и загружает Title.
 */
@Entity
data class Title constructor(val title: String, @PrimaryKey val id: Int = 0)

/***
 * Very small database that will hold one title
 * Очень маленькая база данных, которая будет содержать один заголовок
 */
// add the suspend modifier to the existing insertTitle
@Dao
interface TitleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTitle(title: Title)
    // Room сделает ваш запрос главным образом безопасным и автоматически выполнит его в фоновом потоке.
    // Однако это также означает, что вы можете вызывать этот запрос только из сопрограммы.
    //
    // suspend  И - это все, что вам нужно сделать, чтобы использовать сопрограммы в комнате. Довольно изящный.

    @get:Query("select * from Title where id = 0")
    val titleLiveData: LiveData<Title?>
}

/**
 * TitleDatabase provides a reference to the dao to repositories
 * База данных Title предоставляет ссылку на dao для репозиториев
 */
@Database(entities = [Title::class], version = 1, exportSchema = false)
abstract class TitleDatabase : RoomDatabase() {
    abstract val titleDao: TitleDao
}

// Адрес БД лежит в КОРНЕ МОДУЛЯ
private lateinit var INSTANCE: TitleDatabase

/**
 * Instantiate a database from a context.
 * Создайте экземпляр базы данных из контекста.
 * Заметим что get межит в корне модуля
 */
fun getDatabase(context: Context): TitleDatabase {
    synchronized(TitleDatabase::class) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room
                    .databaseBuilder(
                            context.applicationContext,
                            TitleDatabase::class.java,
                            "titles_db"
                    )
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }
    return INSTANCE
}
