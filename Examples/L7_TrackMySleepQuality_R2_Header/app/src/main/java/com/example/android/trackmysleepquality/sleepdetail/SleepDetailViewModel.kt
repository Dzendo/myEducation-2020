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
 */

package com.example.android.trackmysleepquality.sleepdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import kotlinx.coroutines.Job

/**
 * ViewModel for SleepQualityFragment.
 * ViewModel для фрагмента качества сна.
 *
 * @param sleepNightKey The key of the current night we are working on.
 * @param sleep Night Key Ключ текущей ночи, над которой мы работаем.
 */
class SleepDetailViewModel(
        private val sleepNightKey: Long = 0L,
        dataSource: SleepDatabaseDao) : ViewModel() {

    /**
     * Hold a reference to SleepDatabase via its SleepDatabaseDao.
     * Держите ссылку на базу данных Sleep через ее Sleep DatabaseDao.
     */
    val database = dataSource

    /** Coroutine setup variables Переменные настройки сопрограммы */

    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     * задание viewModel позволяет нам отменить все сопрограммы, запущенные этой ViewModel.
     */
    private val viewModelJob = Job()

    /*
    MediatorLiveData
MediatorLiveData дает возможность собирать данные из нескольких LiveData в один.
Это удобно если у вас есть несколько источников из которых вы хотите получать данные.
Вы объединяете их в один и подписываетесь только на него.
     */
    private val night = MediatorLiveData<SleepNight>()
    //private val night1: LiveData<SleepNight> = TODO()


    fun getNight() = night

    init {
        night.addSource(database.getNightWithId(sleepNightKey), night::setValue)
       // night=database.getNightWithId(sleepNightKey)
    }


    /**
     * Variable that tells the fragment whether it should navigate to [SleepTrackerFragment].
     * Переменная, которая сообщает фрагменту, должен ли он перейти к [Sleep Tracker Fragment].
     *
     * This is `private` because we don't want to expose the ability to set [MutableLiveData] to
     * the [Fragment]
     * Это " личное`, потому что мы не хотим раскрывать возможность установки [изменяемых живых данных] в
     * фрагмент]
     */
    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()

    /**
     * When true immediately navigate back to the [SleepTrackerFragment]
     * При значении true немедленно вернитесь к фрагменту [Sleep Tracker]
     *
     */
    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToSleepTracker

    /**
     * Cancels all coroutines when the ViewModel is cleared, to cleanup any pending work.
     * Отменяет все сопрограммы, когда ViewModel очищается, чтобы очистить любую ожидающую работу.
     *
     * onCleared() gets called when the ViewModel is destroyed.
     * on Cleared () вызывается при уничтожении ViewModel.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    /**
     * Call this immediately after navigating to [SleepTrackerFragment]
     * Вызовите это сразу же после перехода к [Sleep Tracker Fragment]
     */
    fun doneNavigating() {
        _navigateToSleepTracker.value = null
    }

    fun onClose() {
        _navigateToSleepTracker.value = true
    }

}

 