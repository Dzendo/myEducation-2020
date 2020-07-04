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

package com.example.android.trackmysleepquality.sleeptracker

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.trackmysleepquality.database.SleepDatabaseDao

/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 * Это в значительной степени шаблонный код для фабрики ViewModel.
 *
    Это в основном стандартный код,
    поэтому он выглядит очень похоже на то, что вы создали на предыдущем уроке,
    и вы можете использовать его для будущих фабрик моделей представлений.
 
 Эта ViewModel создается в APPLICATION c DAO

 * Provides the SleepDatabaseDao and context to the ViewModel.
 * Предоставляет SleepDatabaseDao и контекст для ViewModel.
 */

class SleepTrackerViewModelFactory(
        private val dataSource: SleepDatabaseDao,  // доступ к данным в базе - суперклассу Интерфейс
        private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepTrackerViewModel::class.java)) {
            return SleepTrackerViewModel(dataSource, application) as T
        }
        // В теле create()мы проверяем, что SleepTrackerViewModel класс доступен,
        // и, если он есть, возвращаем его экземпляр.
        // В противном случае мы выбрасываем исключение.
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
// Мв не создали ссылку на базу данных и не будем - нам не нужен весь объект БД, только DAO
// создадим ссылку на DAO доступ к таблице и через него будем оперировать БД
// DAO сама знает как достать БД
// Это ХОРОШАЯ практика
