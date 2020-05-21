DevByteWorkManager - Solution Code  - Код Решения
==================================

Проблемы:

0. implementation 'com.google.android.material:material:1.0.0'
При выставлении 1.1.0 вылет на старте при высветке recyclerview полей

1. API 'BaseVariant.getApplicationIdTextResource' is obsolete and has been replaced with 'VariantProperties.applicationId'.

2. build/tmp/kapt3/stubs/debug/com/example/android/devbyteviewerw/database/VideosDatabase.java
public abstract class VideosDatabase extends androidx.room.RoomDatabase {
Schema export directory is not provided to the annotation processor so we cannot export the schema.
You can either provide `room.schemaLocation` annotation processor argument OR set exportSchema to false.
Каталог экспорта схемы не предоставляется обработчику аннотаций, поэтому мы не можем экспортировать схему.
Вы можете либо предоставить `комнату.аргумент обработчика аннотаций schemaLocation или задайте экспорт схемы в false.
 @Database(entities = { YourEntity.class }, version = 1, exportSchema = false)

3. maven { url "https://kotlin.bintray.com/kotlinx/" }  -- вроде не нужен

           binary-compatibility-validator/
           kotlinx-knit/
           kotlinx/
           org/
4. /*javaCompileOptions {   Вроде не нужен ???
               annotationProcessorOptions {
                   //[WARN] Incremental annotation processing requested,
                   // but support is disabled because the following processors are not incremental:
                   // androidx.room.RoomProcessor (DYNAMIC).
                   arguments = ["room.incremental" : "true"]
               }
           }*/
5. implementation "org.jetbrains.kotlin:kotlin-reflect:$kotlin_version"  // AS иначе:
> Task :app:kaptGenerateStubsDebugKotlin
w: Runtime JAR files in the classpath should have the same version. These files were found in the classpath:
    N:/_AndroidGradle/caches/transforms-2/files-2.1/9b396b39444539f3e0d3b09b937acd61/jetified-kotlin-stdlib-jdk7-1.4-M1.jar (version 1.4)
    N:/_AndroidGradle/caches/transforms-2/files-2.1/139d3cc4a91960f2937564ae66b53906/jetified-kotlin-reflect-1.3.50.jar (version 1.3)
    N:/_AndroidGradle/caches/transforms-2/files-2.1/b47d3d6cf7ee1e281f3292024641e4ee/jetified-kotlin-stdlib-1.4-M1.jar (version 1.4)
    N:/_AndroidGradle/caches/transforms-2/files-2.1/a242c8a44449feb23b4c69410baa74b1/jetified-kotlin-stdlib-common-1.4-M1.jar (version 1.4)
w: Some runtime JAR files in the classpath have an incompatible version. Consider removing them from the classpath
> Task :app:compileDebugKotlin
w: Runtime JAR files in the classpath should have the same version. These files were found in the classpath:
    N:/_AndroidGradle/caches/transforms-2/files-2.1/9b396b39444539f3e0d3b09b937acd61/jetified-kotlin-stdlib-jdk7-1.4-M1.jar (version 1.4)
    N:/_AndroidGradle/caches/transforms-2/files-2.1/139d3cc4a91960f2937564ae66b53906/jetified-kotlin-reflect-1.3.50.jar (version 1.3)
    N:/_AndroidGradle/caches/transforms-2/files-2.1/b47d3d6cf7ee1e281f3292024641e4ee/jetified-kotlin-stdlib-1.4-M1.jar (version 1.4)
    N:/_AndroidGradle/caches/transforms-2/files-2.1/a242c8a44449feb23b4c69410baa74b1/jetified-kotlin-stdlib-common-1.4-M1.jar (version 1.4)
w: Some runtime JAR files in the classpath have an incompatible version. Consider removing them from the classpath

6. избавление от android.arch navigation and work  в androidx
def work_version = "2.3.4"
    //implementation "android.arch.work:work-runtime-ktx:$work_version"
    implementation "androidx.work:work-runtime-ktx:$work_version"
app/src/main/java/com/example/android/devbyteviewerw/DevByteApplication.kt
'getInstance(): WorkManager' is deprecated. Deprecated in Java
WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(

Solution code for the WorkManager codelab.
Код решения для кодовой таблицы Work Manager.

Introduction - Вступление
------------

DevByteWorkManager app displays a list of DevByte videos. DevByte videos are
short videos made by the Google Android developer relations team to introduce
new developer features on Android. This app fetches the DevByte video playlist
from the network using the Retrofit library and displays it on the screen. The
network fetch is scheduled periodically once a day using the WorkManager.
Constraints like device idle, unmettered network and so on are added to the work
request to optimise the battery performance.

Приложение Dev Byte Work Manager отображает список видео Devbytes. Devbytes видео
короткие видеоролики, сделанные командой разработчиков Google Android relations, чтобы представить
новые возможности разработчика на Android. Это приложение извлекает список воспроизведения видео Devbytes
из сети используется библиотека дооснащения и выводит ее на экран. То
сетевая выборка запланирована периодически один раз в день с помощью WorkManager.
К работе добавляются такие ограничения, как холостой ход устройства, неудовлетворенная сеть и так далее
запрос на оптимизацию производительности батареи.


Pre-requisites - Предварительные условия
--------------

You need to know:
- How to open, build, and run Android apps with Android Studio.
- The Android Architecture Components like, ViewModel, LiveData, and Room.
- Building and launching a coroutine.
- Read the logs using the Logcat.
- Binding adapters in Data Binding.
- How to use Retrofit network library.
- Loading cached data using a Repository pattern.

Тебе нужно знать:
- Как открывать, создавать и запускать приложения для Android с помощью Android Studio.
- - Архитектура Android компонентов как модель представления, данные и номер.
- Создание и запуск сопрограммы.
- Чтение журналов с помощью Logcat.
- Привязка адаптеров в привязке данных.
- Использование модифицированной сетевой библиотеки.
- Загрузка кэшированных данных с использованием шаблона репозитория.
 


Getting Started - приступая к работе
---------------

1. Download and run the app.
2. You need Android Studio 3.2 or higher to build this project.
1. Загрузите и запустите приложение.
2. Для создания этого проекта вам понадобится Android Studio 3.2 или выше.

License
-------

Copyright 2019 Google, Inc.

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.
