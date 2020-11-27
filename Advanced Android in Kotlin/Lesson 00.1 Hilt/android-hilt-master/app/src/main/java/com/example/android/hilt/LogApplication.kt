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

package com.example.android.hilt

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * @HiltAndroidApp запускает генерацию кода Hilt,
 * включая базовый класс для вашего приложения, которое может использовать внедрение зависимостей.
 * Контейнер приложения является родительским контейнером приложения,
 * что означает, что другие контейнеры могут получить доступ к зависимостям,
 * которые он предоставляет.
 * На этом этапе ServiceLocator класс больше не предоставляет зависимости,
 * поэтому мы можем полностью удалить его из проекта.
 * Единственное использование остается в LogApplication классе, в котором мы сохранили его экземпляр.
 * Давайте очистим этот класс, поскольку он нам больше не нужен.
 */
// Давайте продолжим рефакторинг приложения, чтобы удалить ServiceLocator вызовы из MainActivity.
@HiltAndroidApp
class LogApplication : Application()

/*{
    lateinit var serviceLocator: ServiceLocator
    override fun onCreate() {
        super.onCreate()
        serviceLocator = ServiceLocator(applicationContext)
    }
}*/
/*
Если вы посмотрите на начальный код, вы увидите экземпляр ServiceLocator класса, хранящийся в LogApplication классе.
ServiceLocatorСоздает и хранят зависимости, которые получены по требованию, классы , которые в этом нуждаются.
Вы можете думать об этом как о контейнере зависимостей, который прикреплен к жизненному циклу приложения,
поскольку он будет уничтожен, когда приложение это сделает.
 */
