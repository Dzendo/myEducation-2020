/*
 * Copyright (C) 2019 Google Inc.
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
 */

package com.example.android.devbyteviewerw.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.devbyteviewerw.domain.DevByteVideo


/**
 * Database entities go in this file. These are responsible for reading and writing from the
 * database.
 * Объекты базы данных входят в этот файл. Они отвечают за чтение и письмо с самого начала.
 * база данных.
 */


/**
 * DatabaseVideo represents a video entity in the database.
 * Database Video представляет собой видео-объект в базе данных.
 */
@Entity
data class DatabaseVideo constructor(
        @PrimaryKey
        val url: String,
        val updated: String,
        val title: String,
        val description: String,
        val thumbnail: String)


/**
 * Map DatabaseVideos to domain entities
 * Сопоставление видео базы данных с доменными сущностями
 */
fun List<DatabaseVideo>.asDomainModel(): List<DevByteVideo> {
    return map {
        DevByteVideo(
                url = it.url,
                title = it.title,
                description = it.description,
                updated = it.updated,
                thumbnail = it.thumbnail)
    }
}
