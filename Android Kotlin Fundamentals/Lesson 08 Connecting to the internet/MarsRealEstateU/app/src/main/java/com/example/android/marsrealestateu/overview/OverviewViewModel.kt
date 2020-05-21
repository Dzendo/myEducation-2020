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

package com.example.android.marsrealestateu.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.marsrealestateu.network.MarsApi
import com.example.android.marsrealestateu.network.MarsApiFilter
import com.example.android.marsrealestateu.network.MarsProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class MarsApiStatus { LOADING, ERROR, DONE }
/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 * он [ViewModel], который прикреплен к [обзорному фрагменту].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    // Внутренние изменяемые оперативные данные, хранящие статус самого последнего запроса
    private val _status = MutableLiveData<MarsApiStatus>()

    // The external immutable LiveData for the request status
    // Вечные неизменяемые живые данные для статуса запроса
    val status: LiveData<MarsApiStatus>
        get() = _status

    // Internally, we use a MutableLiveData, because we will be updating the List of MarsProperty  with new values
    // Внутренне мы используем изменяемые живые данные, потому что мы будем обновлять список свойств Mars новыми значениями
    private val _properties = MutableLiveData<List<MarsProperty>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    // Внешний интерфейс живых данных для свойства является неизменяемым, поэтому только этот класс может изменять его
    val properties: LiveData<List<MarsProperty>>
        get() = _properties

    // Internally, we use a MutableLiveData to handle navigation to the selected property
    // / / Внутренне мы используем изменяемые текущие данные для обработки навигации к выбранному свойству
    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty>()

    // The external immutable LiveData for the navigation property
    // Внешние неизменяемые текущие данные для свойства навигации
    val navigateToSelectedProperty: LiveData<MarsProperty>
        get() = _navigateToSelectedProperty

    // Create a Coroutine scope using a job to be able to cancel when needed
    // Создайте область сопрограммы с помощью задания, которое можно будет отменить при необходимости
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    // сопрограмма выполняется с помощью главного диспетчера (UI)
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     * Вызовите get Mars Real Estate Properties () на init, чтобы мы могли немедленно отобразить статус.
     */
    init {
        getMarsRealEstateProperties(MarsApiFilter.SHOW_ALL)
    }

    /**
     * Gets filtered Mars real estate property information from the Mars API Retrofit service and
     * updates the [MarsProperty] [List] and [MarsApiStatus] [LiveData]. The Retrofit service
     * returns a coroutine Deferred, which we await to get the result of the transaction.
     * @param filter the [MarsApiFilter] that is sent as part of the web server request
     * Получает отфильтрованную информацию о недвижимости Mars real estate от службы модернизации Mars API и
     * обновления [собственность Марс] [список] и [API статус Марса] [оперативные данные]. Служба модернизации оборудования
     * возвращает отложенную сопрограмму, которую мы ждем, чтобы получить результат транзакции.
     * @param filter Фильтр [Maps Api Filter], который отправляется как часть запроса веб-сервера
     */
     private fun getMarsRealEstateProperties(filter: MarsApiFilter) {
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            val getPropertiesDeferred = MarsApi.retrofitService.getProperties(filter.value)
            try {
                _status.value = MarsApiStatus.LOADING
                // this will run on a thread managed by Retrofit
                val listResult = getPropertiesDeferred.await()
                _status.value = MarsApiStatus.DONE
                _properties.value = listResult
            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the Retrofit service to stop.
     * Когда [ViewModel] будет завершен, мы отменяем нашу сопрограмму [viewModelJob], которая говорит службе модернизации остановиться.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * When the property is clicked, set the [_navigateToSelectedProperty] [MutableLiveData]
     * @param marsProperty The [MarsProperty] that was clicked on.
     * При нажатии на это свойство установите значение [_navigate в выбранное свойство] [изменяемые текущие данные]
     * @param mars Property свойство [Mars Property], которое было нажато.
     */
    fun displayPropertyDetails(marsProperty: MarsProperty) {
        _navigateToSelectedProperty.value = marsProperty
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     * после завершения навигации убедитесь, что для параметра перейти к выбранному свойству установлено значение null
     */
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    /**
     * Updates the data set filter for the web services by querying the data with the new filter
     * by calling [getMarsRealEstateProperties]
     * @param filter the [MarsApiFilter] that is sent as part of the web server request
     * Обновление фильтра набора данных для веб-служб путем запроса данных с помощью нового фильтра
     * по телефону [get Mars Real Estate Properties]
     * @param filter Фильтр [Maps Api Filter], который отправляется как часть запроса веб-сервера
     */
    fun updateFilter(filter: MarsApiFilter) {
        getMarsRealEstateProperties(filter)
    }
}