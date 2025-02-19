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

package com.example.android.kotlincoroutines.util

import com.google.gson.Gson
import okhttp3.*

/**
 * A list of fake results to return.
 * Список поддельных результатов для возврата.
 */
private val FAKE_RESULTS = listOf(
        "Привет, сопрограммы!",
        "Моя любимая функция",
        "Асинхронность стала легкой",
        "Сопрограммы на примере",
        "Далее проверьте \n кодовую таблицу \n Advanced Coroutines!"
        /*"Hello, coroutines!",
        "My favorite feature",
        "Async made easy",
        "Coroutines by example",
        "Check out the Advanced Coroutines codelab next!"*/
)

/**
 * This class will return fake [Response] objects to Retrofit, without actually using the network.
 * Этот класс будет возвращать поддельные объекты [Response] для модернизации, фактически не используя сеть.
 */
class SkipNetworkInterceptor: Interceptor {
    private var lastResult: String = ""
    val gson = Gson()

    private var attempts = 0

    /**
     * Return true iff this request should error.
     * Верните true, если этот запрос должен быть ошибочным.
     */
    private fun wantRandomError() = attempts++ % 5 == 0

    /**
     * Stop the request from actually going out to the network.
     * Остановите запрос от фактического выхода в сеть.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        pretendToBlockForNetworkRequest()
        return if (wantRandomError()) {
            makeErrorResult(chain.request())
        } else {
            makeOkResult(chain.request())
        }
    }

    /**
     * Pretend to "block" interacting with the network.
     * Притворитесь, что "блокируете" взаимодействие с сетью.
     *
     * Really: sleep for 500ms.
     * Действительно: спите в течение 500 мс.
     */
    private fun pretendToBlockForNetworkRequest() = Thread.sleep(5_000)

    /**
     * Generate an error result.
     * Сгенерируйте результат ошибки.
     *
     * ```
     * HTTP/1.1 500 Bad server day
     * Content-type: application/json
     *
     * {"cause": "not sure"}
     * ```
     */
    private fun makeErrorResult(request: Request): Response {
        return Response.Builder()
                .code(500)
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .message("Плохой день сервера")
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        gson.toJson(mapOf("cause" to "not sure"))))
                .build()
    }

    /**
     * Generate a success response.
     * Создайте успешный ответ.
     *
     * ```
     * HTTP/1.1 200 OK
     * Content-type: application/json
     *
     * "$random_string"
     * ```
     */
    private fun makeOkResult(request: Request): Response {
        var nextResult = lastResult
        while (nextResult == lastResult) {
            nextResult = FAKE_RESULTS.random()
        }
        lastResult = nextResult
        return Response.Builder()
                .code(200)
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .message("OK")
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        gson.toJson(nextResult)))
                .build()
    }
}