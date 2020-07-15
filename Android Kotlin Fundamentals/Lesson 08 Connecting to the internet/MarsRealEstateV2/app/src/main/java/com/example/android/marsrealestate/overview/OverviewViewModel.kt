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

package com.example.android.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.marsrealestate.network.MarsApi
import com.example.android.marsrealestate.network.MarsApiFilter
import com.example.android.marsrealestate.network.MarsProperty
//import com.example.android.marsrealestate.network.MarsProperty
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
// 13.1 оздать MarsApiStatus enum с LOADING, ERROR, DONE государствами.
enum class MarsApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel : ViewModel() {
    // 8,9,..,1 Добавьте переменные для задания сопрограммы и CoroutineScope с помощью главного диспетчера:(для интерфейса)
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    // The internal MutableLiveData String that stores the status of the most recent request
    // Внутренняя изменяемая строка живых данных, в которой хранится статус самого последнего запроса
    // The external immutable LiveData for the request status String
    // Внешние неизменяемые текущие данные для строки состояния запроса
// 11.1.3 Добавьте инкапсулированное LiveData<MarsProperty>свойство для показа одного изображения:
    private val _response = MutableLiveData<MarsProperty>()  // результат - количество изображений
    val response: LiveData<MarsProperty>  // до этого был LiveData<String> - текстовая затычка для вывода
        get() = _response
// 11.1.2 В OverViewViewModel, переименуйте response LiveData в status:
// 13.2 Изменить _status тип с String на MarsApiStatus.

  //  private val _status = MutableLiveData<String>()
  //  val status: LiveData<String>
      private val _status = MutableLiveData<MarsApiStatus>()
      val status: LiveData<MarsApiStatus>
        get() = _status  //  11.4 и ответ об ошибке на значение состояния на _status с _response
// 11.1.3 Добавьте инкапсулированное LiveData<MarsProperty>свойство:
    private val _property = MutableLiveData<MarsProperty>()  // Данные для одного изображения
    val property: LiveData<MarsProperty>
        get() = _property
// 12.1.2  переименованы _property к _properties, и назначьте его List на MarsProperty:
    private val _properties = MutableLiveData<List<MarsProperty>>()  // Содержит все данные
    val properties: LiveData<List<MarsProperty>>
        get() = _properties


    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     * Вызовите getMarsRealEstateProperties() на init, чтобы мы могли немедленно отобразить статус.
     */
    init {
        // 16.5 передайте MarsApiFilter.SHOW_ALL в качестве параметра по умолчанию getMarsRealEstateProperties():
        getMarsRealEstateProperties(MarsApiFilter.SHOW_ALL)
        //getMarsRealEstateProperties()
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     * Устанавливает значение статус данных, статус API на Марс.
     */
    // 8.9...2  замените enque() код сопрограммой для выполнения запроса API:
    // 8.9...3  переменную и присвойте ей вызов getProperties():
    // 8.9...4  Добавить try/catch блок с вызовом getPropertiesDeferred.await().
    // 8.9...5  Затем верните размер списка (как и прежде) в сообщении об успехе и сообщение об исключении в сообщении об ошибке.
    // 8.9...7  Создайте и запустите свой код и убедитесь, что приложение работает точно так же, как и раньше
   // private fun getMarsRealEstateProperties() {
// 16.3  добавьте MarsApiFilter параметр в getMarsRealEstateProperties()
    private fun getMarsRealEstateProperties(filter: MarsApiFilter) {
        coroutineScope.launch {
// 16.4  Передайте filter.value в retrofitService.getProperties():
            val getPropertiesDeferred = MarsApi.retrofitService.getProperties(filter.value)
            //val getPropertiesDeferred = MarsApi.retrofitService.getProperties()
            try {
                // 13.3 используя enums определенную выше, заданное _status значение LOADING, DONE или ERROR
                _status.value = MarsApiStatus.LOADING
                val listResult = getPropertiesDeferred.await()  // await говорит что это корутинный  Deferred
                _status.value = MarsApiStatus.DONE
// 11.1.4 Обновите, getMarsRealEstateProperties()чтобы установить _property для первого MarsProperty из listResult:
               // if (listResult.isNotEmpty())  _property.value = listResult[0]
// 12.1.3  Затем обновите getMarsRealEstateProperties (), чтобы он возвращал весь список вместо одного элемента:
                _properties.value = listResult
                //_response.value = "Success: ${listResult.size} Mars properties retrieved"
            } catch (e: Exception) {
              //  _status.value = "Failure: ${e.message}" // 11.4 и ответ об ошибке на значение состояния на _status с _response
                // 13.3 В случае ошибки очистите свойства LiveData, установив для него новое пустое значение ArrayList:
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }
    // 11.1.5 В fragment_overview.xml изменении TextView привязки к viewModel.property.imgSrcUrl.
// Благодаря корутинам избавилист от двух обратных вызовов и получили линейный Try/Catch, а работать будет также
// 8,9,..,6 отмените, Job когда ViewModel закончено
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    //15.2.1  для навигации к selectedProperty подробному экрану
    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty>()
    val navigateToSelectedProperty: LiveData<MarsProperty>
        get() = _navigateToSelectedProperty
    //15.2.2  начать навигацию к подробному экрану по нажатию кнопки
    fun displayPropertyDetails(marsProperty: MarsProperty) {
// 15.3.5  Если вы установите точку останова в отладчике, вы увидите, что при нажатии на изображение вызывается обработчик щелчков в onCreateView,
        _navigateToSelectedProperty.value = marsProperty
    }
    // 15.2.2 Навигация звкончена
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
// 16.6  Теперь создайте updateFilter() метод для запроса данных, вызвав getMarsRealEstateProperties новый фильтр:
    fun updateFilter(filter: MarsApiFilter) {
        getMarsRealEstateProperties(filter)
    }
}
    /*
     private fun getMarsRealEstateProperties() {
        // 8.7 Не забудьте удалить местозаполнитель присвоения ответа, иначе вы не увидите свои результаты!
       // _response.value = "Set the Mars API Response here!"

        // 8.6 В OverviewViewModel.kt, используйте, MarsApi.retrofitService
        // чтобы поставить в очередь запрос Retrofit getMarsRealEstateProperties(),
        // переопределяя требуемые обратные вызовы Retrofit,
        // чтобы назначить ответ JSON или сообщение об ошибке значению _response LiveData.
        // Убедитесь в том , чтобы импортировать ДООСНАСТКУ версии Callback, Call и Response.

        /*
        Первый вариант без преобразований:
        В OverviewViewModel.kt, используйте, MarsApi.retrofitService
        чтобы поставить в очередь запрос Retrofit getMarsRealEstateProperties(),
        переопределяя требуемые обратные вызовы Retrofit,
        чтобы назначить ответ JSON или сообщение об ошибке значению _response LiveData
         */
        // Метод возвращает объект. Затем вы можете вызвать этот объект, чтобы запустить сетевой запрос в фоновом потоке.
        // 8.8.7 чтобы обрабатывать список MarsProperty вместо String.
        MarsApi.retrofitService.getProperties().enqueue( object: Callback<List<MarsProperty>> { //<String> {  // Callback - обратный вызов передается в качестве параметра
            override fun onFailure(call: Call<List<MarsProperty>>, t: Throwable) {  //<String>, t: Throwable) {
                _response.value = "Failure: " + t.message
            }
            override fun onResponse(call: Call<List<MarsProperty>>, response: Response<List<MarsProperty>>) {  //<String>, response: Response<String>) {
                _response.value = "Success: ${response.body()?.size} Mars properties retrieved" //response.body()
            }
        })
        // 8.8.8 Создайте и запустите приложение. Вы должны увидеть одно сообщение, показывающее количество свойств в ответе. - 838 участков
        // при вызове из программы этот код поставит ст. объект Callback<String> к себе там в очередь
        // когда MarsApi.retrofitService выполнит getProperties() то он вызовет обратным вызовом
        // при ошибке - onFailure ; при удаче - onResponse ==> здесь то мы и получим ответ
    }
     */



