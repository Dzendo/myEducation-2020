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

package com.example.android.devbytevieweru.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.android.devbytevieweru.database.getDatabase
import com.example.android.devbytevieweru.repository.VideosRepository
import retrofit2.HttpException
import timber.log.Timber

class RefreshDataWorker(appContext: Context, params: WorkerParameters):
        CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorkeru"
    }

    override suspend fun doWork(): Result {  // Codelabs
        val database = getDatabase(applicationContext)
        val repository = VideosRepository(database)

        try {
            repository.refreshVideos( )
            Timber.d("WorkManager: Work request for sync is run")
        } catch (e: HttpException) {
            return Result.retry()
        }

        return Result.success()
    }

    /**
     * A coroutine-friendly method to do your work.
     */
  /*  override suspend fun doWork(): Payload {
        val database = getDatabase(applicationContext)
        val repository = VideosRepository(database)
        return try {
            repository.refreshVideos()
            Payload(Result.SUCCESS)
        } catch (e: HttpException) {
            Payload(Result.RETRY)
        }
    }*/
}
