/*
 * Copyright 2019, The Android Open Source Project
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


package com.example.android.gdgfinders.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

//  https://andre-max.github.io/gdg-json/each_chapter.json
//  https://andre-max.github.io/gdg-json/chapter_region.json

//  https://gdg.community.dev/api/chapter_region?chapters=true
//  https://gdg.community.dev/api/chapter_region

// private const val BASE_URL = "https://gdg.community.dev/"
// @GET("directory.json")

// The alternative URL is for a server with a recent snapshot. If you are having problems
// with the given URL (app crashing), use the alternative.
// Альтернативный URL-адрес для сервера с недавних снимков. Если у вас возникли проблемы
// с заданным URL-адресом (сбой приложения) используйте альтернативу.
//private const val BASE_URL = "https://developers.google.com/community/gdg/groups/"
private const val BASE_URL = "https://gdg.community.dev/api/"

interface GdgApiService {

    @GET("chapter_region?chapters=true")
    fun getChapters():
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
    // Адаптер вызова сопрограммы позволяет нам возвращать отложенное задание с результатом
            Deferred<GdgResponse>
}

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()

object GdgApi {
    val retrofitService: GdgApiService by lazy { retrofit.create(GdgApiService::class.java) }
}
