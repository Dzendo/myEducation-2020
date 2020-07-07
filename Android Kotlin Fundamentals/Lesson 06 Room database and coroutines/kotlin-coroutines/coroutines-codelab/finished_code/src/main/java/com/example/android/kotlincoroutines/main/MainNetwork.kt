/*
 * Copyright (C) 2019 Google LLC
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

package com.example.android.kotlincoroutines.main

import com.example.android.kotlincoroutines.util.SkipNetworkInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * MainNetwork реализует сетевой API, который выбирает новый заголовок.
 * Он использует Retrofit для получения названий.
 * Retrofit настроен для случайного возврата ошибок или фиктивных данных,
 * но в остальном ведет себя так, как будто он выполняет реальные сетевые запросы.
 */

private val service: MainNetwork by lazy {
    val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(SkipNetworkInterceptor())
            .build()

    val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    retrofit.create(MainNetwork::class.java)
}

fun getNetworkService() = service

/**
 * Main network interface which will fetch a new welcome title for us
 * Основной сетевой интерфейс, который принесет нам новый приветственный заголовок
 * Добавьте модификатор приостановки к функции
 *Удалите Call оболочку из возвращаемого типа
 * Теперь, когда Room и Retrofit поддерживают функции приостановки,
 * мы можем использовать их из нашего репозитория
 */
// add suspend modifier to the existing fetchNextTitle
// change return type from Call<String> to String
interface MainNetwork {
    @GET("next_title.json")
    suspend fun fetchNextTitle(): String
}

/*
Чтобы использовать функции приостановки с Retrofit, вы должны сделать две вещи:

1. Добавьте модификатор приостановки к функции
2. Удалите Call оболочку из возвращаемого типа. Здесь мы возвращаемся String,
но вы также можете вернуть сложный тип с поддержкой json.
Если вы все еще хотите предоставить полный доступ к Retrofit Result,
вы можете вернуться Result<String>вместо String функции приостановки.
Retrofit автоматически сделает функции приостановки максимально безопасными, чтобы вы могли вызывать их напрямую Dispatchers.Main.
*/
/*
Как Room, так и Retrofit делают функции приостановки безопасными .
Безопасно вызывать эти функции приостановки Dispatchers.Main,
 даже если они извлекаются из сети и записываются в базу данных.
 */

/*
Как Room, так и Retrofit используют собственный диспетчер и не используют его Dispatchers.IO.
Room запустит сопрограммы, используя запрос и транзакцию по умолчанию, Executor которые настроены.
Retrofit создаст новый Call объект под капотом и вызовет enqueue для него, чтобы отправить запрос асинхронно.
 */


