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
 */

package com.example.android.trackmysleepquality.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * A database that stores SleepNight information.
 * And a global method to get access to the database.
 * База данных, хранящая информацию о ночном сне.
 * И глобальный метод получения доступа к базе данных.
 *
 * This pattern is pretty much the same for any database,
 * so you can reuse it.
 * Этот шаблон практически одинаков для любой базы данных,
 * таким образом, вы можете использовать его повторно.
 */
@Database(entities = [SleepNight::class], version = 1, exportSchema = false)
abstract class SleepDatabase : RoomDatabase() {

    /**
     * Connects the database to the DAO.
     * подключает базу данных к DAO.
     */
    abstract val sleepDatabaseDao: SleepDatabaseDao

    /**
     * Define a companion object, this allows us to add functions on the SleepDatabase class.
     *
     * For example, clients can call `SleepDatabase.getInstance(context)` to instantiate
     * a new SleepDatabase.
     * Определите сопутствующий объект,это позволит нам добавить функции в класс базы данных Sleep.
     */
    companion object {
        /**
         * INSTANCE will keep a reference to any database returned via getInstance.
         * Экземпляр будет хранить ссылку на любую базу данных, возвращаемую через getInstance.
         *
         * This will help us avoid repeatedly initializing the database, which is expensive.
         * Это поможет нам избежать многократной инициализации базы данных, которая является дорогостоящей.
         *
         *  The value of a volatile variable will never be cached, and all writes and
         *  reads will be done to and from the main memory. It means that changes made by one
         *  thread to shared data are visible to other threads.
         *  Значение изменчивой переменной никогда не будет кэшироваться, и все записи и
         * чтение будет производиться в основную память и из нее. Это означает, что изменения, внесенные одним
         * поток к общим данным виден другим потокам..
         */
        @Volatile
        private var INSTANCE: SleepDatabase? = null

        /**
         * Helper function to get the database.
         * Вспомогательная функция для получения базы данных.
         *
         * If a database has already been retrieved, the previous database will be returned.
         * Otherwise, create a new database.
         * Если база данных уже была получена, то будет возвращена предыдущая база данных.
         * В противном случае создайте новую базу данных.
         *
         * This function is threadsafe, and callers should cache the result for multiple database
         * calls to avoid overhead.
         * Эта функция является потокобезопасной, и вызывающие устройства должны кэшировать результат для нескольких баз данных
         * звонки, чтобы избежать накладных расходов.
         *
         * This is an example of a simple Singleton pattern that takes another Singleton as an
         * argument in Kotlin.
         * Это пример простого Одноэлементного паттерна, который принимает другой Одноэлемент в качестве
         * спор в Котлине.
         *
         * To learn more about Singleton read the wikipedia article:
         * Чтобы узнать больше о синглтоне читайте статью в Википедии:
         * https://en.wikipedia.org/wiki/Singleton_pattern
         *
         * @param context The application context Singleton, used to get access to the filesystem.
         * @ param context одноэлементный контекст приложения, используемый для получения доступа к файловой системе.
         */
        fun getInstance(context: Context): SleepDatabase {
            // Multiple threads can ask for the database at the same time, ensure we only initialize
            // it once by using synchronized. Only one thread may enter a synchronized block at a
            // time.
            // Несколько потоков могут запрашивать базу данных одновременно, убедитесь, что мы только инициализируем ее
            // это один раз с помощью synchronized. Только один поток может войти в синхронизированный блок одновременно.
            // время.
            synchronized(this) {
                // Copy the current value of INSTANCE to a local variable so Kotlin can smart cast.
                // Smart cast is only available to local variables.
                // Копируем текущее значение экземпляра локальной переменной, так Котлин может умного бросания.
                // Smart cast доступен только для локальных переменных.
                var instance = INSTANCE
                // If instance is `null` make a new database instance.
                // Если экземпляр имеет значение 'null', создайте новый экземпляр базы данных.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            SleepDatabase::class.java,
                            "sleep_history_database"
                    )
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this lesson. You can learn more about
                            // migration with Room in this blog post:
                            // Стирает и перестраивает вместо миграции, если нет объекта миграции.
                            // Миграция не является частью этого урока. Вы можете узнать больше о компании
                            // миграция с комнатой в этом блоге:
                            // https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
                            .fallbackToDestructiveMigration()
                            .build()
                    // Assign INSTANCE to the newly created database.
                    // Назначить экземпляр вновь созданной базе данных.
                    INSTANCE = instance
                }
                // Return instance; smart cast to be non-null.
                // Return instance; smart cast должен быть ненулевым.
                return instance
            }
        }
    }
}
