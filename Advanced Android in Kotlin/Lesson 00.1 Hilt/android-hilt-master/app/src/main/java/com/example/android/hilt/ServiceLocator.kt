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
/*
package com.example.android.hilt

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.room.Room
import com.example.android.hilt.data.AppDatabase
import com.example.android.hilt.data.LoggerLocalDataSource
import com.example.android.hilt.navigator.AppNavigator
import com.example.android.hilt.navigator.AppNavigatorImpl
import com.example.android.hilt.util.DateFormatter

/**
 * Контейнер представляет собой класс , который отвечает за предоставление зависимости в вашем коде ,
 * и знает , как создавать экземпляры других типов приложения.
 * Он управляет графом зависимостей, необходимых для предоставления этих экземпляров,
 * путем их создания и управления их жизненным циклом.

 * Контейнер предоставляет методы для получения экземпляров предоставляемых им типов.
 * Эти методы всегда могут возвращать другой или тот же экземпляр.
 * Если метод всегда предоставляет один и тот же экземпляр, мы говорим, что тип привязан к контейнеру.
 */

class ServiceLocator(applicationContext: Context) {

    private val logsDatabase = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java,
        "logging.db"
    ).build()


    //  ServiceLocator всегда будет возвращать один и тот же экземпляр LoggerLocalDataSource
    //  всякий раз, когда он вызывается.
    //  Это то, что называется «привязкой экземпляра к контейнеру».
    val loggerLocalDataSource = LoggerLocalDataSource(logsDatabase.logDao())
    // Как упоминалось выше, поскольку мы хотим,
    // чтобы контейнер приложения всегда предоставлял один и тот же экземпляр LoggerLocalDataSource,
    // мы аннотируем его класс с помощью @Singleton:


    // при вызове provideDateFormatter()всегда возвращается другой экземпляр DateFormatter.
    fun provideDateFormatter() = DateFormatter()
    // Чтобы сообщить Hilt, как предоставлять экземпляры типа,
    // добавьте аннотацию @Inject к конструктору класса, который вы хотите внедрить DateFormatter.kt .

    fun provideNavigator(activity: FragmentActivity): AppNavigator {
        return AppNavigatorImpl(activity)
    }
}
*/
/**
 * Вместо того, ServiceLocator чтобы получать зависимости по запросу из наших классов,
 * мы будем использовать Hilt, чтобы предоставить нам эти зависимости.
 * Приступим к замене вызовов ServiceLocator из наших классов.
 */
