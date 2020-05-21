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

package com.example.android.devbyteviewerw.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.devbyteviewerw.database.VideosDatabase
import com.example.android.devbyteviewerw.database.asDomainModel
import com.example.android.devbyteviewerw.domain.DevByteVideo
import com.example.android.devbyteviewerw.network.DevByteNetwork
import com.example.android.devbyteviewerw.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * Repository for fetching devbyte videos from the network and storing them on disk
 * Репозиторий для извлечения devbytes видео из сети и хранения их на диске
 */
class VideosRepository(private val database: VideosDatabase) {

    val videos: LiveData<List<DevByteVideo>> = Transformations.map(database.videoDao.getVideos()) {
        it.asDomainModel()
    }

    /**
     * Refresh the videos stored in the offline cache.
     * Обновите видео, хранящиеся в автономном кэше.
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     * Эта функция использует диспетчер ввода-вывода для обеспечения работы базы данных insert database
     * происходит на диспетчере ввода-вывода. При переключении на диспетчер ввода-вывода с помощью `с контекстом " это
     * функция теперь безопасна для вызова из любого потока, включая основной поток.
     *
     */
    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            Timber.d("refresh videos is called")
            val playlist = DevByteNetwork.devbytes.getPlaylist().await()
            database.videoDao.insertAll(playlist.asDatabaseModel())
        }
    }
}
