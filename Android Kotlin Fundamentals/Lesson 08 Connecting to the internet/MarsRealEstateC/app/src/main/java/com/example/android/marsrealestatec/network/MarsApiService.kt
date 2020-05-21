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

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
//import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
//import retrofit2.converter.scalars.ScalarsConverterFactory - поменяли библиотеку
import retrofit2.http.GET
import retrofit2.http.Query

// MarsApiService Класс содержит слой сети для приложения; то есть это API,
// который вы ViewModel будете использовать для связи с веб-сервисом.
// Это класс, в котором вы будете реализовывать API службы Retrofit.

// Задача: отфильтровать результаты
enum class MarsApiFilter(val value: String) {
    SHOW_RENT("rent"),
    SHOW_BUY("buy"),
    SHOW_ALL("all") }


// Мы предоставили переменную для корневого веб-адреса конечной точки сервера Mars:
// константа для базового URL для веб-службы
private const val BASE_URL = " https://android-kotlin-fun-mars-server.appspot.com/"

// используйте Moshi Builder для создания объекта Moshi с KotlinJsonAdapterFactory:
// Как и в случае с Retrofit, здесь вы создаете moshi объект, используя конструктор Moshi
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())  //  Чтобы аннотации Моши правильно работали с Kotlin
        .build()

// конструктор Retrofit.Builder, чтобы создать объект Retrofit.
private val retrofit = Retrofit.Builder()
        //.addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())     // для корутин
        .baseUrl(BASE_URL)
        .build()
/*
Для построения API необходимы как минимум две вещи, доступные для создания API веб-сервисов:
базовый URI (Base URL of our web sevice) для веб-сервиса и фабрика конвертеров (Converter factory).
Конвертер сообщает Retrofit, что делать с данными, полученными от веб-службы.
В этом случае вы хотите, чтобы Retrofit получал ответ JSON из веб-службы и возвращал его как String.
Retrofit имеет функцию, ScalarsConverter которая поддерживает строки и другие примитивные типы,
поэтому вы вызываете addConverterFactory() конструктор с экземпляром ScalarsConverterFactory.
Наконец, вы вызываете build()для создания объекта Retrofit.
 */

// нтерфейс, который определяет, как Retrofit общается с веб-сервером, используя HTTP-запросы
// Сейчас цель состоит в том, чтобы получить строку ответа JSON из веб - службы - getProperties()
//  getProperties() - метод для запроса строки ответа JSON.
// MarsApiService - Retrofit Annotated API Interface
// @GET - HTTP Method GET
// @Query - URI Query Parameters
// <List<MarsProperty>> - Returns Defferred List of Property
interface MarsApiService {
    @GET("realestate") // аннотацию и укажите путь или конечную точку (realestate) для этого метода веб-службы
      fun getProperties(@Query("filter") type: String): // Udacity для фильтрации
    // fun getProperties():
            //Call<String>
            //Call<List<MarsProperty>> - без корутин с обратными вызовами для Moshi
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result:
            Deferred<List<MarsProperty>>
    // Когда getProperties() метод вызывается,
    // Retrofit добавляет конечную точку realestate к базовому URL (который вы определили в конструкторе Retrofit)
    // и создает Call объект. Этот Call объект используется для запуска запроса
    // Deferred Интерфейс определяет сопрограмму работу , которая возвращает значение результата ( Deferred наследует Job).
    // Deferred Интерфейс включает метод await(), который вызывает код для ожидания без блокировки,
    // пока значение не будет готов, а затем это значение возвращается. Для корутин в OverviewViewModel.kt
}
// Передача в API сервиса вы только определенной, создать общественный объект с именем,
// MarsApi чтобы обнажить Retrofit обслуживания остальной части приложения:

//каждый раз, когда ваше приложение вызывает MarsApi.retrofitService,(В OverviewViewModel.kt)
// оно получит одноэлементный объект Retrofit, который реализует MarsApiService
// открытый объект, вызываемый MarsApi для инициализации службы Retrofit.
object MarsApi {
    val retrofitService : MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java) }
    // Метод Retrofit create()создает сервис Retrofit с MarsApiService интерфейсом
}
// Поскольку этот вызов является дорогостоящим, и приложению требуется только один экземпляр службы Retrofit,
// вы открываете службу для остальной части приложения, используя открытый объект с именем MarsApi,
// и лениво инициализируете службу Retrofit

// Далее надо Шаг 3: вызов веб-службы в OverviewViewModel
/*
Здесь создан весь модернизированный сервмс для RetroFit, но не использован, используется он в
В OverviewViewModel.kt, используйте, MarsApi.retrofitService
чтобы поставить в очередь запрос Retrofit getMarsRealEstateProperties(),
переопределяя требуемые обратные вызовы Retrofit,
чтобы назначить ответ JSON или сообщение об ошибке значению _response LiveData
 */

/*
Retrofit создает сетевой API для приложения на основе содержимого веб-службы.
Он извлекает данные из веб-службы и направляет их через отдельную библиотеку преобразователей,
которая знает, как декодировать данные и возвращать их в виде полезных объектов.
Модернизация включает в себя встроенную поддержку популярных форматов веб-данных, таких как XML и JSON.
В конечном итоге модернизация создает для вас большую часть сетевого уровня,
включая такие важные детали, как выполнение запросов в фоновых потоках.
 */

/*
8. Резюме
Веб-сервисы REST (Representational state transfer - Передача репрезентативного состояния)
Веб - сервис является сервисом в Интернете , что позволяет вашему приложению , чтобы сделать запросы и получить данные обратно.
Общие веб-сервисы используют архитектуру REST . Веб-сервисы, которые предлагают архитектуру REST, называются сервисами RESTful . Веб-сервисы RESTful создаются с использованием стандартных веб-компонентов и протоколов.
Вы делаете запрос к веб-сервису REST стандартным способом через URI.
Чтобы использовать веб-сервис, приложение должно установить сетевое соединение и установить связь с сервисом. Затем приложение должно получить и проанализировать данные ответа в формате, который приложение может использовать.
Библиотека Retrofit - это клиентская библиотека, которая позволяет вашему приложению отправлять запросы в веб-службу REST.
Используйте конвертеры, чтобы сообщить Retrofit, что делать с данными, которые отправляются в веб-службу и возвращаются из веб-службы. Например, ScalarsConverterконвертер обрабатывает данные веб-службы как один Stringили другой примитив.
Чтобы приложение могло подключаться к Интернету, добавьте "android.permission.INTERNET"разрешение в манифест Android.
JSON-разбор
Ответ от веб-службы часто форматируется в JSON , общем формате обмена для представления структурированных данных.
Объект JSON представляет собой набор пар ключ-значение. Эту коллекцию иногда называют словарем , хэш-картой или ассоциативным массивом .
Коллекция объектов JSON представляет собой массив JSON. Вы получаете массив JSON в ответ от веб-службы.
Ключи в паре ключ-значение заключены в кавычки. Значения могут быть числами или строками. Строки также заключены в кавычки.
Библиотека Moshi - это анализатор JSON для Android, который преобразует строку JSON в объекты Kotlin. Retrofit имеет конвертер, который работает с Moshi.
Moshi сопоставляет ключи в ответе JSON со свойствами в объекте данных с таким же именем.
Чтобы использовать другое имя свойства для ключа, аннотируйте это свойство @Jsonаннотацией и именем ключа JSON.
Дооснащение и сопрограммы
Адаптеры вызовов позволяют Retrofit создавать API, которые возвращают что-то отличное от Callкласса по умолчанию . Используйте CoroutineCallAdapterFactoryкласс, чтобы заменить Callего сопрограммой Deferred.
Используйте await()метод Deferredобъекта, чтобы заставить ваш сопрограммный код ждать без блокировки, пока значение не будет готово, а затем значение возвращается.
 */

/* 08.3 загрузки и отображения фотографий с веб-URL.
Вы также повторно посмотрите, как построить RecyclerView и использовать его для отображения сетки изображений на странице обзора.
Измените приложение MarsRealEstate, чтобы получить URL-адрес изображения из данных свойств Mars,
 и используйте Glide для загрузки и отображения этого изображения.
Добавьте анимацию загрузки и значок ошибки в приложение.
Используйте RecyclerView для отображения сетки изображений свойств Марса.
Добавить статус и обработку ошибок в RecyclerView.
 */

/*08.3 Фильтрация и подробные представления с интернет-данными
Одним из способов решения этой задачи является
тестирование типа каждого MarsProperty в обзорной сетке и отображение только соответствующих свойств.
Однако у реального веб-сервиса Mars есть параметр или параметр запроса (называемый filter),
который позволяет вам получать только свойства типа rent или типа buy.
Вы можете использовать этот запрос фильтра с realestate URL-адресом веб-службы в браузере следующим образом:
https://android-kotlin-fun-mars-server.appspot.com/realestate?filter=buy
В этой задаче вы изменяете MarsApiService класс для добавления опции запроса к запросу веб-службы с помощью Retrofit.
Затем вы подключаете меню параметров, чтобы повторно загрузить все данные о свойствах Марса, используя эту опцию запроса.
Поскольку ответ, который вы получаете от веб-службы, содержит только интересующие вас свойства,
вам вообще не нужно изменять логику отображения вида для обзорной сетки.
 */
