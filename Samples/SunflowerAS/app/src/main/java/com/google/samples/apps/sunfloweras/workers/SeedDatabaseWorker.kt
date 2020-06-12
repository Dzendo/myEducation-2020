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

package com.google.samples.apps.sunfloweras.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.samples.apps.sunfloweras.data.AppDatabase
import com.google.samples.apps.sunfloweras.data.Plant
import com.google.samples.apps.sunfloweras.utilities.PLANT_DATA_FILENAME

// Android Kotlin Основы 09.2: WorkManager
class SeedDatabaseWorker(
        context: Context,
        workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result =
        try {
            applicationContext.assets.open(PLANT_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val plantType = object : TypeToken<List<Plant>>() {}.type
                    val plantList: List<Plant> = Gson().fromJson(jsonReader, plantType)

                    val database = AppDatabase.getInstance(applicationContext)
                    database.plantDao().insertAll(plantList)
                    Log.w(TAG,"Успех")
                    Result.success()
                }
            }

        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }

    // Определите имя работы, чтобы однозначно идентифицировать этого работника.
    companion object {
       const val TAG = "SeedDatabaseWorker"
    }
}
/*
class SeedDatabaseWorkerSun(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    // doWork()Метод внутри Workerкласса вызывается в фоновом потоке. ==> coroutineScope не надо
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(PLANT_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val plantType = object : TypeToken<List<Plant>>() {}.type
                    val plantList: List<Plant> = Gson().fromJson(jsonReader, plantType)

                    val database = AppDatabase.getInstance(applicationContext)
                    database.plantDao().insertAll(plantList)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }
// Определите имя работы, чтобы однозначно идентифицировать этого работника.
    companion object {
        private const val TAG = "SeedDatabaseWorker"
    }
}*/

/*
Worker
 В этом классе вы определяете фактическую работу (задачу) для выполнения в фоновом режиме.
 Вы расширяете этот класс и переопределяете doWork() метод.
 Метод doWork(), где вы положили код , который будет выполняться в фоновом режиме, например,
 синхронизации данных с сервером или обработки изображений.
WorkRequest
 Этот класс представляет запрос на запуск работника в фоновом режиме.
 Используется WorkRequest для настройки того, как и когда запускать рабочую задачу,
 с помощью Constraints таких устройств, как подключенное устройство или подключенный Wi-Fi.
 Вы реализуете WorkRequest в более поздней задаче.
WorkManager
 Этот класс расписывает и запускает ваш WorkRequest. WorkManager планирование рабочих запросов таким образом,
 чтобы распределить нагрузку на системные ресурсы, соблюдая при этом указанные ограничения.
 Вы реализуете WorkManager в более поздней задаче.
 */
/*
Result.success()- работа выполнена успешно.
Result.failure()- работа выполнена с постоянным провалом.
Result.retry()- работа столкнулась с кратковременным отказом и должна быть повторена.
 */