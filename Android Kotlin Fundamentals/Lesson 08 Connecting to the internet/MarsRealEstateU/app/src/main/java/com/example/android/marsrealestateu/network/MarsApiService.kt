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

package com.example.android.marsrealestateu.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
//import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://mars.udacity.com/"
enum class MarsApiFilter(val value: String) { SHOW_RENT("rent"), SHOW_BUY("buy"), SHOW_ALL("all") }

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for full Kotlin compatibility.
 * Постройте объект Moshi, который будет использоваться Retrofit, убедившись, что вы добавили адаптер Kotlin для полной совместимости с Kotlin.
 */
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi object.
 * Использование модифицированной застройщика построить объект модернизации с использованием Моши  Converter с нашего Моши объекта.
 */
private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()

/**
 * A public interface that exposes the [getProperties] method
 * Открытый интерфейс, который предоставляет метод [getProperties]
 */
interface MarsApiService {
    /**
     * Returns a Coroutine [Deferred] [List] of [MarsProperty] which can be fetched with await() if in a Coroutine scope.
     * The @GET annotation indicates that the "realestate" endpoint will be requested with the GET  HTTP method
     * Возвращает сопрограмму [отложенный] [список] свойства [Mars], которая может быть извлечена с помощью функции await (), если она находится в области действия сопрограммы.
     * Аннотация @GET указывает, что конечная точка "недвижимость" будет запрошена с помощью метода GET HTTP
     */
    @GET("realestate")
    fun getProperties(@Query("filter") type: String):
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
    // Адаптер вызова сопрограммы позволяет нам возвращать отложенное задание с результатом
            Deferred<List<MarsProperty>>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 * Публичный объект Api, предоставляющий доступ к службе ленивой инициализации дооснащения
 */
object MarsApi {
    val retrofitService : MarsApiService by lazy { retrofit.create(MarsApiService::class.java) }
}
