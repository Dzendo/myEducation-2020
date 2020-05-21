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
 *
 */

package com.example.android.marsrealestatec.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

// преобразовать класс в класс данных Kotlin со свойствами, которые соответствуют полям ответа JSON
// Moshi анализирует эти данные JSON и преобразует их в объекты Kotlin.
// Для этого ему необходим класс данных Kotlin для хранения проанализированных результатов,
// поэтому следующим шагом будет создание этого класса.
@Parcelize
data class MarsProperty(
        val id: String,
        // used to map img_src from the JSON to imgSrcUrl in our class
        // Переименуйте свойство класса img_src в imgSrcUrl и добавьте аннотацию @Json,
        // чтобы переназначить в него поле JSON img_src
        @Json(name = "img_src") val imgSrcUrl: String,
        val type: String,
        val price: Double)  : Parcelable {
    val isRental
        get() = type == "rent"
}
/*Шаг 2: Обновите макет элемента сетки
Теперь вы обновляете макет элемента для сетки изображений,
чтобы знак доллара отображался только на тех изображениях недвижимости,
которые продаются:   С помощью выражений привязки данных
вы можете выполнить этот тест полностью в макете XML для элементов сетки.
res/layout/grid_view_item.xml
 */

        /*
        : Parcelable {
    val isRental
        get() = type == "rent"
           }
         */
/*
Обратите внимание, что каждая из переменных в MarsProperty классе соответствует имени ключа в объекте JSON.
Чтобы соответствовать типам в JSON, вы используете String объекты для всех значений, кроме price, который является Double.
A Double может использоваться для представления любого числа JSON.
Чтобы использовать имена переменных в вашем классе данных,
которые отличаются от имен ключей в ответе JSON, используйте @Json аннотацию
 */
// Шаг 3. Обновление MarsApiService и OverviewViewModel


/*
Теперь вы получаете ответ JSON от веб-службы Mars, что является отличным началом.
Но в действительности вам нужны объекты Kotlin, а не большая строка JSON.
Есть библиотека под названием Moshi , которая представляет собой анализатор JSON для Android,
который преобразует строку JSON в объекты Kotlin.
Retrofit имеет конвертер, который работает с Moshi, так что это отличная библиотека для ваших целей здесь.

В этой задаче вы используете библиотеку Moshi с Retrofit
для анализа ответа JSON от веб-службы на полезные объекты Mars Property Kotlin.
Вы изменяете приложение так, чтобы вместо отображения необработанного JSON
приложение отображало количество возвращенных свойств Mars.
Ответ: [{"price":450000,    // цена собственности на Марс, как число.
"id":"424906",              // идентификатор свойства в виде строки.
"type":"rent",              // или "rent"или "buy".
"img_src":"http://mars.jpl.nasa.gov/msl-raw-images/msss/01000/mcam/1000ML0044631300305227E03_DXXX.jpg"}, // URL изображения в виде строки.
...]
{
   "price":8000000,
   "id":"424908",
   "type":"rent",
   "img_src": "http://mars.jpl.nasa.gov/msl-raw-images/msss/01000/mcam/1000ML0044631290305226E03_DXXX.jpg"
},

Шаг 1: Добавьте зависимости библиотеки Moshi

*/

/*
Навигация выдает ошибку, потому что MarsProperty не подлежит продаже.
Parcelable Интерфейс позволяет объекты сериализации, так что данные объекты,
может передаваться между фрагментами или деятельностью.
В этом случае, чтобы данные внутри MarsProperty объекта, передаваемые фрагменту детали через Safe Args,
MarsProperty должны реализовывать Parcelable интерфейс.
Хорошей новостью является то, что Kotlin предоставляет простой способ реализации этого интерфейса.

В @Parcelize аннотации используются расширения Android Kotlin
для автоматической реализации методов в Parcelable интерфейсе для этого класса.
 Вам не нужно больше ничего делать!
 Изменить определение класса, MarsProperty чтобы расширить Parcelable.
 Шаг 5: соединяем фрагменты
 Вы все еще не перемещаетесь - фактическая навигация происходит во фрагментах.
 Open overview/OverviewFragment.kt
 */