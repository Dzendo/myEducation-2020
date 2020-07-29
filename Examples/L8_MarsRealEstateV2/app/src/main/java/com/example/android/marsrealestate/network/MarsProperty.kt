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

package com.example.android.marsrealestate.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

// преобразовать класс в класс данных Kotlin со свойствами, которые соответствуют полям ответа JSON
// Moshi анализирует эти данные JSON и преобразует их в объекты Kotlin.
// Для этого ему необходим класс данных Kotlin для хранения проанализированных результатов,
// поэтому следующим шагом будет создание этого класса.
// 8.8.2 преобразовать класс в класс данных Kotlin со свойствами, которые соответствуют полям ответа JSON
// 8.8.3 Переименуйте свойство класса img_src в imgSrcUrl и добавьте аннотацию @Json, чтобы переназначить в него поле JSON img_src:
// 14 Добавить : @Parcelize  и Parcelable  ( id 'kotlin-android-extensions') experimental = true - Без кучи методов
// 15 добавление подробного экрана
// 15.4.1 сделайте класс доступным, расширив его Parcelable и добавив @Parcelize аннотацию:
@Parcelize
data class MarsProperty(
    val id: String,
    @Json(name = "img_src")  // это название на сайте (в Json)
    val imgSrcUrl: String,   // это название для нас использовать
    val type: String,
    val price: Double) : Parcelable {     // все числовые (без кавычек) объявлять Double
// 15.6.1  isRental логическое значение и установите его значение в зависимости от того, является ли тип свойства «арендным»
    val isRental
        get() = type == "rent"
}
// Порядок полей не совпадает с порядеом на сайте - разбор по именам
/*см. src\main\assets
там данные о собсвенности на марсе, кот будем считывать

[{"price":450000,    // цена собственности на Марс, как число.
    "id":"424906",              // идентификатор свойства в виде строки.
    "type":"rent",              // или "rent"или "buy".
    "img_src":"http://mars.jpl.nasa.gov/msl-raw-images/msss/01000/mcam/1000ML0044631300305227E03_DXXX.jpg"}, // URL изображения в виде строки.
},
...
{
    "price":8000000,
    "id":"424908",
    "type":"rent",
    "img_src": "http://mars.jpl.nasa.gov/msl-raw-images/msss/01000/mcam/1000ML0044631290305226E03_DXXX.jpg"
}]
*/
