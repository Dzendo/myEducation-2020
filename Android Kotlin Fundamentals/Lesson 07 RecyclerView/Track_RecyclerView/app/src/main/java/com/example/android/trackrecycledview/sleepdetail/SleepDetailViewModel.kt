package com.example.android.trackrecycledview.sleepdetail

import androidx.lifecycle.LiveData
//import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.trackrecycledview.database.SleepDatabaseDao
import com.example.android.trackrecycledview.database.SleepNight
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext

/**
 * ViewModel for SleepQualityFragment.
 * ViewModel для фрагмента качества сна.
 *
 * @param *sleepNightKey The key of the current night we are working on.
 * @param *sleep Night Key Ключ текущей ночи, над которой мы работаем.
 */
// Эта модель представления принимает ключ для a SleepNight и DAO в конструкторе.
class SleepDetailViewModel(
        sleepNightKey: Long = 0L,
        dataSource: SleepDatabaseDao) : ViewModel() {

    /**
     * Hold a reference to SleepDatabase via its SleepDatabaseDao.
     * Держите ссылку на базу данных Sleep через ее Sleep DatabaseDao.
     */
    val database = dataSource

    /** Coroutine setup variables - Переменные настройки сопрограммы */

    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     * задание viewModel позволяет нам отменить все сопрограммы, запущенные этим ViewModel.
     */
    private val viewModelJob = Job()

    private val night: LiveData<SleepNight>

    fun getNight() = night

/*
Тело класса имеет код , чтобы получить SleepNight для данного ключа,
и navigateToSleepTracker переменное управление навигацией обратно к SleepTrackerFragment когда Close нажата кнопка.
Функция возвращает и определена в (в пакете).

getNightWithId()LiveData<SleepNight>SleepDatabaseDaodatabase
 */
    init {
        night=database.getNightWithId(sleepNightKey)
    }

    /**
     * Variable that tells the fragment whether it should navigate to [*SleepTrackerFragment].
     * Variable that tells the fragment whether it should navigate to [*SleepTrackerFragment].
     *
     * This is `private` because we don't want to expose the ability to set [MutableLiveData] to
     * the [*Fragment]
     * Это "личное", потому что мы не хотим выставлять возможность установки [изменяемых живых данных] на
     * фрагмент].
     */
    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()

    /**
     * When true immediately navigate back to the [*SleepTrackerFragment]
     * Когда значение true немедленно перейдите обратно к фрагменту [Sleep Tracker]
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
     * Call this immediately after navigating to [*SleepTrackerFragment]
     * Вызовите это сразу же после перехода к [фрагменту трекера сна]
     */
    fun doneNavigating() {
        _navigateToSleepTracker.value = null
    }

    fun onClose() {
        _navigateToSleepTracker.value = true
    }

}