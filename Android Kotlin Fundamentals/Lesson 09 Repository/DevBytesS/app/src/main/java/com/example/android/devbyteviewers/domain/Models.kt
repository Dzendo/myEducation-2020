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

package com.example.android.devbyteviewers.domain

import com.example.android.devbyteviewers.util.smartTruncate

/**
 * Domain objects are plain Kotlin data classes that represent the things in our app. These are the
 * objects that should be displayed on screen, or manipulated by the app.
 * Доменные объекты-это простые классы данных Kotlin, которые представляют вещи в нашем приложении. Это те самые
 * объекты, которые должны отображаться на экране или управляться приложением.
 *
 * @see database for objects that are mapped to the database
 * @see network for objects that parse or prepare network calls
 * @см. раздел База данных для объектов, сопоставленных с базой данных
 * @см. раздел Сеть для объектов, которые анализируют или готовят сетевые вызовы
 */

/**
 * Videos represent a devbyte that can be played.
 * Видео представляют собой devbyte, который можно воспроизводить.
 */
data class DevByteVideo(val title: String,
                        val description: String,
                        val url: String,
                        val updated: String,
                        val thumbnail: String) {

    /**
     * Short description is used for displaying truncated descriptions in the UI
     * Краткое описание используется для отображения усеченных описаний в пользовательском интерфейсе
     * Объекты передачи данных идут в этом файле. Они отвечают за синтаксический анализ ответов с сервера
     * или форматирование объектов для отправки на сервер. Вы должны преобразовать их в доменные объекты, прежде чем
     * использующий их.
     *
     *  @see domain package for
     *  @см. пакет домена для
     */
    val shortDescription: String
        get() = description.smartTruncate(200)
}

/*
Этот пакет содержит data классы для представления данных приложения.
Например, DevByteVideo класс данных в domain/Models.kt классе представляет одно видео DevByte.
 */