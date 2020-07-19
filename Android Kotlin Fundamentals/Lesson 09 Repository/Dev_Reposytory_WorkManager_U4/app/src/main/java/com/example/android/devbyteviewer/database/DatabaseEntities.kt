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

package com.example.android.devbyteviewer.database

import androidx.room.*
import com.example.android.devbyteviewer.domain.Video

/**
 * Database entities go in this file. These are responsible for reading and writing from the database.
 * Сущности базы данных входят в этот файл. Они отвечают за чтение и запись данных из базы данных.
 */


/*
Шаг 2: Добавить объект базы данных
На этом шаге вы создаете объект базы данных с именем DatabaseVideo для представления объектов базы данных.
Вы также реализуете удобные методы для преобразования DatabaseVideo объектов в объекты домена
и для преобразования сетевых объектов в DatabaseVideo объекты.
 */
/**
 * DatabaseVideo represents a video entity in the database.
 * Database Video представляет собой видео-объект в базе данных.
 */
// Создайте объект базы данных DatabaseVideo.
// В database/DatabaseEntities создайте комнату @Entity под названием DatabaseVideo
@Entity
data class DatabaseVideo constructor(
    @PrimaryKey
    val url: String,
    val updated: String,
    val title: String,
    val description: String,
    val thumbnail: String)
/*
В database/DatabaseEntities.kt, создать функцию расширения с именем asDomainModel().
Используйте функцию для преобразования DatabaseVideo объектов базы данных в объекты домена.
 */
/**
 * Map DatabaseVideos to domain entities
 * Сопоставление видео базы данных с доменными сущностями
 * преобразует объекты базы данных в объекты домена:
 */
// Добавьте функцию расширения, которая преобразует объекты базы данных в объекты домена:
// создайте функцию расширения, которая преобразует объекты передачи данных в объекты базы данных
// fun List<DatabaseVideo>.asDomainModel(): List<Video> { Первый вариант Udacity - простейший
fun List<DatabaseVideo>.asDomainModel(): List<Video> =      // List<DevByteVideo> =
     map {
        Video (                     // DevByteVideo(
            url = it.url,
            title = it.title,
            description = it.description,
            updated = it.updated,
            thumbnail = it.thumbnail)
    }

/*
В этом примере приложения преобразование является простым, и часть этого кода не требуется.
Но в реальном приложении структура домена, базы данных и сетевых объектов будет другой.
Вам понадобится логика преобразования, которая может быть сложной.
 */
/*
Откройте network/DataTransferObjects.kt и создайте функцию расширения с именем asDatabaseModel().
Используйте функцию для преобразования сетевых объектов в DatabaseVideo объекты базы данных.
 */