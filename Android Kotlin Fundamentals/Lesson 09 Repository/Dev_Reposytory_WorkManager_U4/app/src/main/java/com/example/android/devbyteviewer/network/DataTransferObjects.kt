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

package com.example.android.devbyteviewer.network

import com.example.android.devbyteviewer.database.DatabaseVideo
import com.example.android.devbyteviewer.domain.Video
import com.squareup.moshi.JsonClass

/**
 * DataTransferObjects go in this file.
 * These are responsible for parsing responses from the server
 * or formatting objects to send to the server.
 * You should convert these to domain objects before using them.
 * Объекты передачи данных идут в этот файл.
 * Они отвечают за синтаксический анализ ответов с сервера
 * или форматирование объектов для отправки на сервер.
 * Вы должны преобразовать их в доменные объекты, прежде чем использовать их.
 */

/**
 * VideoHolder holds a list of Videos.
 * Держатель видео содержит список видео.
 *
 * This is to parse first level of our network result which looks like
 * Это делается для разбора первого уровня нашего сетевого результата который выглядит следующим образом
 *
 * {
 *   "videos": []
 * }
 */
@JsonClass(generateAdapter = true)
data class NetworkVideoContainer(val videos: List<NetworkVideo>)

/**
 * Videos represent a devbyte that can be played.
 * Видео представляет собой devbyte, который можно воспроизводить.
 */
// класс данных для объекта передачи данных  data transfer object
// Объект передачи данных используется для анализа результата сети.
@JsonClass(generateAdapter = true)
data class NetworkVideo(
        val title: String,
        val description: String,
        val url: String,
        val updated: String,
        val thumbnail: String,
        val closedCaptions: String?)

/**
 * Convert Network results to database objects
 * Преобразования сети к объектам базы данных
 * объявляется функция расширения к NetworkVideoContainer
 */
// удобный метод asDomainModel() для преобразования сетевых результатов в список объектов домена
fun NetworkVideoContainer.asDomainModel(): List<Video> =
     videos.map {
        Video(
                title = it.title,
                description = it.description,
                url = it.url,
                updated = it.updated,
                thumbnail = it.thumbnail)
    }


// Объекты передачи данных отличаются от объектов домена,
//потому что они содержат дополнительную логику для анализа сетевых результатов.

/*
Откройте network/DataTransferObjects.ktи создайте функцию расширения с именем asDatabaseModel().
Используйте функцию для преобразования сетевых объектов в DatabaseVideo объекты базы данных.
 */
/**
 * Convert Network results to database objects
 * Преобразования сети к объектам базы данных
 * преобразует объекты передачи данных в объекты базы данных:
 */
/*fun NetworkVideoContainer.asDatabaseModel(): List<DatabaseVideo> =
    videos.map {
        DatabaseVideo(
            title = it.title,
            description = it.description,
            url = it.url,
            updated = it.updated,
            thumbnail = it.thumbnail
        )
    }*/
// Первый вариант Udacity - простейший
fun NetworkVideoContainer.asDatabaseModel(): Array<DatabaseVideo> =
    videos.map {
        DatabaseVideo (
           title = it.title,
           description = it.description,
           url = it.url,
           updated = it.updated,
           thumbnail = it.thumbnail)
    }.toTypedArray()

 