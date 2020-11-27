/*
 * Copyright (C) 2020 The Android Open Source Project
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

package com.example.android.hilt.di

import android.content.Context
import androidx.room.Room
import com.example.android.hilt.data.AppDatabase
import com.example.android.hilt.data.LogDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Поскольку LoggerLocalDataSource область действия ограничена контейнером приложения,
 * LogDao привязка должна быть доступна в контейнере приложения.
 * Мы указываем это требование с помощью @InstallIn аннотации,
 * передавая класс компонента Hilt, связанный с ним (т.е. ApplicationComponent:class):
 * SingletonComponent::class
 */

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
// В Kotlin модули, содержащие только @Provides функции, могут быть object классами.
// Таким образом, провайдеры оптимизируются и почти встраиваются в сгенерированный код.

// Поскольку AppDatabase это еще один класс, которым наш проект не владеет,
// потому что он сгенерирован Room, мы также можем предоставить его, используя @Provides функцию,
// аналогичную тому, как мы создаем экземпляр базы данных в ServiceLocator классе:
    // Поскольку мы всегда хотим, чтобы Hilt предоставлял один и тот же экземпляр базы данных,
    // мы аннотируем @Provides provideDatabase метод с помощью @Singleton.
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "logging.db"
        ).build()
    }

    // Мы можем аннотировать функцию с помощью @Provides модулей в Hilt,
    // чтобы сообщить Hilt, как предоставлять типы, которые не могут быть введены конструктором.
// код сообщает Hilt, что database.logDao() необходимо выполнить при предоставлении экземпляра LogDao
    @Provides
    fun provideLogDao(database: AppDatabase): LogDao {
        return database.logDao()
    }
}

/*
В ServiceLocator реализации класса экземпляр LogDao получается путем вызова logsDatabase.logDao().
 Следовательно, чтобы предоставить экземпляр LogDao, у нас есть транзитивная зависимость от AppDatabase класса.
 */
