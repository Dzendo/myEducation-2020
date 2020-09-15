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
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters

/**
 * Worker job to refresh titles from the network while the app is in the background.
 * Рабочее задание для обновления заголовков из сети, пока приложение находится в фоновом режиме.
 *
 * WorkManager is a library used to enqueue work that is guaranteed to execute after its constraints are met.
 * It can run work even when the app is in the background, or not running.
 *
 * Work Manager-это библиотека, используемая для постановки в очередь работы,
 * которая гарантированно выполняется после выполнения ее ограничений.
 * Он может работать даже тогда, когда приложение находится в фоновом режиме или не работает.
 */
class RefreshMainDataWork(context: Context, params: WorkerParameters, private val network: MainNetwork) :
        CoroutineWorker(context, params) {

    /**
     * Refresh the title from the network using [TitleRepository]
     * Обновите заголовок из сети с помощью [title Repository]
     *
     * WorkManager will call this method from a background thread.
     * It may be called even after our app has been terminated by the operating system,
     * in which case [WorkManager] will start just enough to run this [Worker].
     *
     * Рабочий менеджер вызовет этот метод из фонового потока.
     * Он может быть вызван даже после того, как наше приложение было прекращено операционной системой,
     * в этом случае [менеджер работ] запустится ровно настолько, чтобы запустить этого [работника].
     */
    override suspend fun doWork(): Result {
       // return Result.success()         // TODO: Use coroutines from WorkManager
        val database = getDatabase(applicationContext)
        val repository = TitleRepository(network, database.titleDao)

        return try {
            repository.refreshTitle()
            Result.success()
        } catch (error: TitleRefreshError) {
            Result.failure()
        }
    }

    class Factory(val network: MainNetwork = getNetworkService()) : WorkerFactory() {
        override fun createWorker(appContext: Context, workerClassName: String, workerParameters: WorkerParameters): ListenableWorker? {
            return RefreshMainDataWork(appContext, workerParameters, network)
        }

    }
}
/*
Обратите внимание, что CoroutineWorker.doWork() это функция приостановки.
 В отличие от более простого Worker класса, этот код НЕ запускается на Executor, указанном в вашей конфигурации WorkManager,
а вместо этого использует диспетчер в coroutineContext члене (по умолчанию Dispatchers.Default).
 */