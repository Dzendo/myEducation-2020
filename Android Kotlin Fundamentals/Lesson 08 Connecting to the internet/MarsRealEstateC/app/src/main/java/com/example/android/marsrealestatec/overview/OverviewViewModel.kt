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

package com.example.android.marsrealestatec.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.marsrealestatec.network.MarsApi
import com.example.android.marsrealestatec.network.MarsApiFilter
import com.example.android.marsrealestatec.network.MarsProperty
//import com.example.android.marsrealestatec.network.MarsProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 * [ViewModel], который прикреплен к [фрагменту обзора].
 * Поскольку ответ является, LiveData и мы установили жизненный цикл для переменной привязки,
 * любые изменения в ней обновят пользовательский интерфейс приложения.
 */
// enum чтобы представить все доступные статусы:
enum class MarsApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    // Внутренняя изменяемая строка живых данных, в которой хранится самый последний ответ
    private val _response = MutableLiveData<String>()
    private val _status = MutableLiveData<MarsApiStatus>()
    // The external immutable LiveData for the response String
    // Вечные неизменяемые живые данные для строки ответа
    val response: LiveData<String>
        get() = _response
    val status: LiveData<MarsApiStatus>
        get() = _status

    /* 08.2: загрузка и отображение изображений из интернета
    3. Задача: отобразить интернет-изображение
    Шаг 2: Обновите модель представления
    Затем вы обновляете OverviewViewModel класс, чтобы включить живые данные для одного свойства Mars.
*/
   /* private val _property = MutableLiveData<MarsProperty>()
    val property: LiveData<MarsProperty> //- чтобы высвечивать одно изображение
        get() = _property
*/
    private val _properties = MutableLiveData<List<MarsProperty>>()
    val properties: LiveData<List<MarsProperty>> // много - List
        get() = _properties

        // Когда это LiveData меняется на ненулевое, навигация срабатывает на детали окно
        // (Скоро вы добавите код для наблюдения за этой переменной и запускаете навигацию.)
    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty>()
        val navigateToSelectedProperty: LiveData<MarsProperty>
            get() = _navigateToSelectedProperty

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     * Вызовите getMarsRealEstateProperties() на init, чтобы мы могли немедленно отобразить статус.
     */
    // переменные для задания сопрограммы и CoroutineScope с помощью главного диспетчера:
    private var viewModelJob = Job()  // для корутин
    // Создайте область сопрограммы для этой новой работы, используя главный диспетчер:
    private val coroutineScope = CoroutineScope(
            viewModelJob + Dispatchers.Main )
    init {  //  Когда ViewModel объект создан, он вызывает getMarsRealEstateProperties() метод
        // getMarsRealEstateProperties()
        getMarsRealEstateProperties(MarsApiFilter.SHOW_ALL) // чтобы показать все свойства при первой загрузке приложения
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     * Устанавливает значение статус данных, статус API на Марс.
     */
    // Шаг 3: вызов веб-службы в OverviewViewModel
    // вызывать сервис Retrofit и обрабатывать возвращенную строку JSON
    // List<MarsProperty> список класса дата MarsProperty для хранения объектов
    //private fun getMarsRealEstateProperties() {
    private fun getMarsRealEstateProperties(filter: MarsApiFilter) {  // для обновления запроса фильтрации
        // сопрограмма для выполнения запроса API:
        coroutineScope.launch {
            // создает и запускает сетевой вызов в фоновом потоке, возвращая Deferred объект для этой задачи.
           // val getPropertiesDeferred = MarsApi.retrofitService.getProperties()  // = вызов getProperties()
            val getPropertiesDeferred = MarsApi.retrofitService.getProperties(filter.value)
            try {
                _status.value = MarsApiStatus.LOADING
                val listResult = getPropertiesDeferred.await()
                _status.value = MarsApiStatus.DONE
                _properties.value = listResult
            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                // Это очищает RecyclerView.
                _properties.value = ArrayList()
            }
            /* try {Работает без проверки
                val listResult = getPropertiesDeferred.await()
                _response.value = "Success: ${listResult.size} Mars properties retrieved"
                // Поскольку listResult переменная содержит список MarsProperty объектов,
                // вы можете просто назначить ее _properties.value вместо проверки успешного ответа.
                _properties.value = listResult
/* Если MarsProperty объекты доступны, этот тест устанавливает значение _property LiveData первого свойства в listResult.
                if (listResult.size > 0) {
                    _property.value = listResult[0]
                    }*/
  // В TextView отображает только URL изображения в первой собственности Марса. Есть URL-адрес изображения для отображения!!!
  // Все, что вы сделали до сих пор, - это настроили модель представления и оперативные данные для этого URL.

            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }*/
        }

       // _response.value = "Set the Mars API Response here!" - вывод статики без интернета

      /*  без корутин: с линейным Moshi идет использование соданного ранее сервиса для RetroFit
       MarsApi.retrofitService.getProperties().enqueue(
               object: Callback<List<MarsProperty>> {   // с обратными вызовами по завершению  --> Courutines
                   override fun onFailure(call: Call<List<MarsProperty>>, t: Throwable) {
                       _response.value = "Failure: " + t.message
                   }
        // Успешный ответ должен включать количество возвращенных объектов недвижимости response.body().size:
                   override fun onResponse(call: Call<List<MarsProperty>>, response: Response<List<MarsProperty>>) {
                       _response.value = "Success: ${response.body()?.size} Mars properties retrieved"  // количество свойств, возвращаемых веб-службой:
                   }
               })*/

        /* Первый вариант без преобразований:
        В OverviewViewModel.kt, используйте, MarsApi.retrofitService
        чтобы поставить в очередь запрос Retrofit getMarsRealEstateProperties(),
        переопределяя требуемые обратные вызовы Retrofit,
        чтобы назначить ответ JSON или сообщение об ошибке значению _response LiveData

         MarsApi.retrofitService.getProperties().enqueue(
                object: Callback<String> {  // с обратными вызовами по завершению
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        _response.value = "Failure: " + t.message
                    }

                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        _response.value = response.body()  // выводит содержимое хоста
                    }
                })
         */
        // Метод возвращает объект. Затем вы можете вызвать этот объект, чтобы запустить сетевой запрос в фоновом потоке.
    }
    // метод, который принимает MarsApiFilter аргумент и вызывает getMarsRealEstateProperties() с этим аргументом
    // Шаг 3: Подключите фрагмент к меню параметров в res/menu/overflow_menu.xml
    fun updateFilter(filter: MarsApiFilter) {
        getMarsRealEstateProperties(filter)
    }
    // Шаг 2. Определите навигацию в модели обзора
    // метод, который устанавливает _ navigateToSelectedProperty для выбранного свойства Mars
    fun displayPropertyDetails(marsProperty: MarsProperty) {
        _navigateToSelectedProperty.value = marsProperty
    }

    // метод, который обнуляет значение _navigateToSelectedProperty.
    // Это необходимо, чтобы отметить состояние навигации для завершения
    // и избежать повторного запуска навигации,
    // когда пользователь возвращается из подробного представления.
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
    // Шаг 3: Настройте прослушиватели щелчков в сетевом адаптере и фрагмент overview/PhotoGridAdapter.kt

    //  onCleared() отменяет, Job когда ViewModel закончена.
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
// Вы должны явно указать Android, что приложению необходим доступ к Интернету.

// Цель для этого codelab является обновление ответа в LiveData пределах с ViewModel использованием реальных данных,
// которые вы получаете из Интернета.

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
Шаг 1: Добавьте зависимости библиотеки Moshi
 */
// 6. Задача: использовать сопрограммы с Retrofit
// вы конвертируете свой сетевой сервис и ViewModelиспользуете сопрограммы.

/*
Теперь служба Retrofit API работает,
но она использует обратный вызов с двумя методами обратного вызова, которые вам пришлось реализовать.
Один метод обрабатывает успех, а другой - сбой, а результат сбоя сообщает об исключениях.
Ваш код был бы более эффективным и легким для чтения,
если бы вы могли использовать сопрограммы с обработкой исключений вместо использования обратных вызовов.
Удобно, что у Retrofit есть библиотека, которая объединяет сопрограммы.
 */

/*
08.2: загрузка и отображение изображений из интернета
4. Задача: отобразить сетку изображений с помощью RecyclerView
Ваше приложение теперь загружает информацию о недвижимости из Интернета.
Используя данные из первого MarsProperty элемента списка,
вы создали LiveData свойство в модели представления
и использовали URL-адрес изображения из данных этого свойства для заполнения ImageView.
Но цель состоит в том, чтобы ваше приложение отображало сетку изображений,
поэтому вы хотите использовать RecyclerView с GridLayoutManager.
Шаг 1: Обновите модель представления
Прямо сейчас у модели представления есть объект,
_property LiveData который содержит один MarsProperty объект - первый в списке ответов от веб-службы.
На этом шаге вы измените его, LiveData чтобы он содержал весь список MarsProperty объектов.
Шаг 2: Обновите макеты и фрагменты
Шаг 3: Добавьте адаптер сетки фотографий
Шаг 4. Добавьте адаптер для крепления и соедините детали.
 */