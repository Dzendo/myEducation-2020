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

package com.example.android.devbyteviewer.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.android.devbyteviewer.database.getDatabase
import com.example.android.devbyteviewer.repository.VideosRepository
import retrofit2.HttpException
import timber.log.Timber

// Библиотека предоставляет все WorkManager классы, необходимые для выполнения последних двух упражнений.
//  implementation "androidx.work:work-runtime-ktx:2.3.4"

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
        CoroutineWorker(appContext, params) {
// Мы собираемся использовать a CoroutineWorker, потому что мы хотим использовать сопрограммы
// для обработки нашего асинхронного кода и потоков
// Вам также необходимо передать Context и WorkerParameters классу и его родительскому классу.

    // В RefreshDataWorker.kt, создать companion object и определить имя работы,
    // которая может быть использована для однозначной идентификации данного работника.
    companion object {  // Создайте уникальный идентификатор для вашей работы:
        const val WORK_NAME = "com.example.android.devbyteviewers.work.RefreshDataWorkerU2"
        // const val WORK_NAME = "RefreshDataWorker"
    }
    // Это то, что «работает» ваш RefreshDataWorker, в нашем случае, синхронизируя с сетью
    override suspend fun doWork(): Result {
        // Добавьте переменные для базы данных и хранилища.
        val database = getDatabase(applicationContext)
        val repository = VideosRepository(database)

    // Правильно верните успех или повторите попытку:
        try {
            repository.refreshVideos( )
            Timber.d("WorkManager: Result.success *******************Work request for sync is run****************")
        } catch (e: HttpException) {
            Timber.d("WorkManager: Result.retry *******************Work request for sync is run****************")
            return Result.retry()
        }

        return Result.success()
    }
}

// Система Android дает Worker максимум 10 минут, чтобы завершить выполнение и вернуть ListenableWorker.Result объект.
// По истечении этого времени система принудительно останавливает Worker.

/*
Result.success()- работа выполнена успешно.
Result.failure()- работа выполнена с постоянным провалом.
Result.retry()- работа столкнулась с кратковременным отказом и должна быть повторена.
 */

/* Deprecate вариант с ARH:
override suspend fun doWork(): Payload {
        val database = getDatabase(applicationContext)
        val repository = VideosRepository(database)

        return try {
            repository.refreshVideos()
            Payload(Result.SUCCESS)
        } catch (e: HttpException) {
            Payload(Result.RETRY)
        }
    }
 */

/*
Большинство реальных приложений должны выполнять длительные фоновые задачи.
Например, приложение может загружать файлы на сервер,
синхронизировать данные с сервера и сохранять их в Room базе данных,
отправлять журналы на сервер или выполнять дорогостоящие операции с данными.
Такие операции должны выполняться в фоновом режиме, вне потока пользовательского интерфейса (основного потока).
Фоновые задачи потребляют ограниченные ресурсы устройства, такие как оперативная память и батарея.
Это может привести к плохому восприятию пользователя, если оно не обрабатывается правильно.
 */
/*
Worker
В этом классе вы определяете фактическую работу (задачу) для выполнения в фоновом режиме.
Вы расширяете этот класс и переопределяете doWork() метод.
doWork()Метод , где вы положили код , который будет выполняться в фоновом режиме,
например, синхронизации данных с сервером или обработки изображений.
Вы реализуете Worker в этой задаче.
WorkRequest
Этот класс представляет запрос на запуск работника в фоновом режиме.
Используется WorkRequest для настройки того, как и когда запускать рабочую задачу,
с помощью Constraints таких устройств, как подключенное устройство или подключенный Wi-Fi.
Вы реализуете WorkRequest в более поздней задаче.
WorkManager
Этот класс расписывает и запускает ваш WorkRequest.
WorkManager планирование рабочих запросов таким образом,
чтобы распределить нагрузку на системные ресурсы, соблюдая при этом указанные ограничения.
Вы реализуете WorkManager в более поздней задаче.

 */