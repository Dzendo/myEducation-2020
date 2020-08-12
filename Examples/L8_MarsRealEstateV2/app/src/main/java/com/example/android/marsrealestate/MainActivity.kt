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

package com.example.android.marsrealestate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.marsrealestate.databinding.ActivityMainBinding

// UI Controller (activity/fragment) + Observer --> ViewModel +LiveData --> Network;  Observer <-- LiveData
// Не используется слой Repository - Хранилище
// Будут использоваться библтотеки, разработанные сообществом RetroFit
// - помогают прил соответсвовать лучшим рекомендациям Android:
// Загрузка изображений в фоновом потоке и кеширование загруженных изображений
// REST - Общая веб архитектура без сохранения состояния - создаются с исп стандартного веба
// REST - REpresentational  State Transfer architecture restfull-сервисы
// REST ---> URI (http...) --> server  ----> XML/JSON ---> REST

// app -> parameters --> RetroFit -> URI&HTTP method --> server
// app <- Kotlin Objects <-- RetroFit <-- JSON <- server

// GITHUB :
// 1. Какие разрешения Андроид требует библиотека (Контакты? список звонков) (Библ имеют собвств Manifest)
// 2. Таргетинг на последние версии Android SDK (требует Google Play)
// 3. GitHub хорошо любим: Звезды > 30 000 Fork > 5 800 Watch 1627 Issues(вопросы) 53 в работе и закрытых 2 000 и Futures или проблемы
// 4. Исходный текст,
// 5. Тесты автомат, документация, Простота,
// 6. Последние обновления, сессии, блоги, конференции
// 7. Размер библиотеки-время запуска, юридические вещи - лицензии ВРЕМЯ НА ВХОД
// В основном google() - JetPack   jcenter() - библиотеки сообществ

// 11... - получение и вывод одного изображения через Glide
// 12..  - Монтаж и настройка RecyclerView в Grid сетку
// 13..  - добавите обработку ошибок и сообщения о состоянии, чтобы сделать ваше приложение более надежным
// 14..  - Parcel and :Parcelable experimental = true
// 15.. добавление подробного экрана
// 16.. добавление фильтра
class MainActivity : AppCompatActivity() {

    /**
     * Our MainActivity is only responsible for setting the content view that contains the Navigation Host.
     * Наша основная деятельность связана только с настройкой представления контента, содержащего Навигационный Хост.
     * N:\2020_GCAAD\Android Kotlin Fundamentals\Lesson 08 Connecting to the internet\MarsRealEstateV2\app\build\generated\data_binding_base_class_source_out\debug\out\com\example\android\marsrealestate\databinding
     * <androidx.fragment.app.FragmentContainerView/>
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
       
        //setContentView(R.layout.activity_main)
        /*
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_main)
       val binding = ActivityMainBinding.inflate(layoutInflater)
       setContentView(binding.root)
         */
    }
}

/*
Данные по недвижимости Mars хранятся на веб-сервере в виде веб-службы REST.
Веб-сервисы, использующие архитектуру REST, создаются с использованием стандартных веб-компонентов и протоколов.

Вы делаете запрос к веб-сервису стандартным способом через URI.
Знакомый веб-URL на самом деле является типом URI, и оба они используются взаимозаменяемо на протяжении всего курса.
Например, в приложении для этого урока вы получаете все данные со следующего сервера:

https://android-kotlin-fun-mars-server.appspot.com

Если вы введете следующий URL в своем браузере, вы получите список всех доступных объектов недвижимости на Марсе!
URI == URL
https://android-kotlin-fun-mars-server.appspot.com/realestate
https://mars.udacity.com/realestate
https://mars.udacity.com/realestate?filter=rent
https://mars.udacity.com/realestate?filter=rent&region=utopiaplanitia&size=2500

см. src\main\assets
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

Ответ от веб-службы обычно форматируется в JSON , формате обмена для представления структурированных данных.
Вы узнаете больше о JSON в следующей задаче, но краткое объяснение состоит в том,
что объект JSON представляет собой набор пар ключ-значение,
иногда называемых словарем, хэш-картой или ассоциативным массивом.
Коллекция объектов JSON представляет собой массив JSON, и это массив, который вы возвращаете в ответ от веб-службы.

Чтобы получить эти данные в приложение,
вашему приложению необходимо установить сетевое соединение и установить связь с этим сервером,
а затем получить и проанализировать данные ответа в формате, который может использовать приложение.
В этой кодовой метке вы используете клиентскую библиотеку REST под названием Retrofit, чтобы установить это соединение.
см com\example\android\marsrealestate\network\MarsApiService.kt
*/