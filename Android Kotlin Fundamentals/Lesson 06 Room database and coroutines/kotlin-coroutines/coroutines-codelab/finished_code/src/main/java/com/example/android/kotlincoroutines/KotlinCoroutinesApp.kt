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

package com.example.android.kotlincoroutines

import android.app.Application
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy.KEEP
import androidx.work.NetworkType.UNMETERED
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.android.kotlincoroutines.main.RefreshMainDataWork
import java.util.concurrent.TimeUnit

/**
 * Override application to setup background work via [WorkManager]
 * Переопределение приложения для настройки фоновой работы через [Диспетчер работ]
 */
class KotlinCoroutinesApp : Application() {
    /**
     * onCreate is called before the first screen is shown to the user.
     * onCreate вызывается до того, как пользователю будет показан первый экран.
     *
     * Use it to setup any background tasks, running expensive setup operations in a background
     * thread to avoid delaying app start.
     * Используйте его для настройки любых фоновых задач, выполняя дорогостоящие операции настройки в фоновом режиме
     * поток, чтобы избежать задержки запуска приложения.
     */
    override fun onCreate() {
        super.onCreate()
        setupWorkManagerJob()
    }

    /**
     * Setup WorkManager background job to 'fetch' new network data daily.
     * Настройка фонового задания диспетчера работ для ежедневной "выборки" новых сетевых данных.
     */
    private fun setupWorkManagerJob() {
        // initialize WorkManager with a Factory
        // инициализировать WorkManager с помощью фабрики
        val workManagerConfiguration = Configuration.Builder()
                .setWorkerFactory(RefreshMainDataWork.Factory())
                .build()
        WorkManager.initialize(this, workManagerConfiguration)

        // Use constraints to require the work only run when the device is charging and the
        // Используйте ограничения, чтобы требовать выполнения работы только тогда, когда устройство заряжается и
        // network is unmetered
        val constraints = Constraints.Builder()
                .setRequiresCharging(true)
                .setRequiredNetworkType(UNMETERED)
                .build()

        // Specify that the work should attempt to run every day
        // Укажите, что работа должна выполняться каждый день
        val work = PeriodicWorkRequestBuilder<RefreshMainDataWork>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()

        // Enqueue it work WorkManager, keeping any previously scheduled jobs for the same
        // work.
        // Поставит его работы администратора заданий,
        // сохраняя все ранее запланированные задания для одной и той же работы.
        WorkManager.getInstance(this)
                .enqueueUniquePeriodicWork(RefreshMainDataWork::class.java.name, KEEP, work)
    }
}
