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

package com.example.android.hilt.data

import android.os.Handler
import android.os.Looper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data manager class that handles data manipulation between the database and the UI.
 * Класс диспетчера данных, который обрабатывает манипуляции с данными
 * между базой данных и пользовательским интерфейсом.
 */
// Чтобы сообщить Hilt, как предоставлять экземпляры типа,
// добавьте аннотацию @Inject к конструктору класса, который вы хотите внедрить..
//  @Inject constructor
// Информация Hilt о том, как предоставлять экземпляры разных типов, также называется привязками

// Как упоминалось выше, поскольку мы хотим,
// чтобы контейнер приложения всегда предоставлял один и тот же экземпляр LoggerLocalDataSource,
// мы аннотируем его класс с помощью @Singleton:
@Singleton
class LoggerLocalDataSource @Inject constructor(private val logDao: LogDao) {

    // Теперь Hilt знает, как предоставлять экземпляры LoggerLocalDataSource.
    // Однако на этот раз тип имеет транзитивные зависимости!
    // Чтобы предоставить экземпляр LoggerLocalDataSource, Hilt также необходимо знать,
    // как предоставить экземпляр LogDao.
    // Однако, поскольку LogDao это интерфейс, мы не можем аннотировать его конструктор,
    // так @Inject как интерфейсы его не имеют.
    // Как мы можем сказать Hilt, как предоставлять экземпляры этого типа? 7. Модули рукояти.

    private val executorService: ExecutorService = Executors.newFixedThreadPool(4)
    private val mainThreadHandler by lazy {
        Handler(Looper.getMainLooper())
    }

    fun addLog(msg: String) {
        executorService.execute {
            logDao.insertAll(
                Log(
                    msg,
                    System.currentTimeMillis()
                )
            )
        }
    }

    fun getAllLogs(callback: (List<Log>) -> Unit) {
        executorService.execute {
            val logs = logDao.getAll()
            mainThreadHandler.post { callback(logs) }
        }
    }

    fun removeLogs() {
        executorService.execute {
            logDao.nukeTable()
        }
    }
}
