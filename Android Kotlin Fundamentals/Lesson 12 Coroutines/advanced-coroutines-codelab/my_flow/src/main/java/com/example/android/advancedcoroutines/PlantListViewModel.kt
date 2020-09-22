/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.advancedcoroutines

import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * The [ViewModel] for fetching a list of [Plant]s.
 * [ViewModel] для получения списка [Plant]s
 */
@FlowPreview
@ExperimentalCoroutinesApi
class PlantListViewModel internal constructor(
    private val plantRepository: PlantRepository
) : ViewModel() {

    /**
     * Request a snackbar to display a string.
     * Запросите снэк-бар, чтобы показать строку
     *
     * This variable is private because we don't want to expose [MutableLiveData].
     * Эта переменная является частной, потому что мы не хотим раскрывать [изменяемые живые данные].
     *
     * MutableLiveData allows anyone to set a value, and [PlantListViewModel] is the only
     * class that should be setting values.
     * Изменяемые данные в реальном маштабе позволяет любому пользователю задать значение, а [модель представления списка растений] является единственным
     * класс, который должен устанавливать значения.
     */
    private val _snackbar = MutableLiveData<String?>()

    /**
     * Request a snackbar to display a string.
     * Запросите снэк-бар, чтобы показать строку.
     */
    val snackbar: LiveData<String?>
        get() = _snackbar

    private val _spinner = MutableLiveData<Boolean>(false)
    /**
     * Show a loading spinner if true
     * Показать загрузочный счетчик, если это пра
     */
    val spinner: LiveData<Boolean>
        get() = _spinner

    /**
     * The current growZone selection.
     * Текущий выбор зоны роста.
     */
    private val growZone = MutableLiveData<GrowZone>(NoGrowZone)

    /**
     * A list of plants that updates based on the current filter.
     * Список растений, который обновляется на основе текущего фильтра.
     * Обратить внимание на стартовый код: Plant.kt
     * inline class GrowZone(val number: Int)
     * val NoGrowZone = GrowZone(-1)
     * Встроенный класс должен иметь единственное свойство, инициализированное в основном конструкторе.
     * Во время выполнения экземпляры встроенного класса будут представлены
     * с использованием этого единственного свойства
     * (см. Подробности о представлении среды выполнения ниже ):
     */
    val plants: LiveData<List<Plant>> = growZone.switchMap { growZone ->
        if (growZone == NoGrowZone) {
            plantRepository.plants
        } else {
            plantRepository.getPlantsWithGrowZone(growZone)
        }
    }
    // 10, add a new property to plantListViewModel
    /**
     * The current growZone selection (flow version)
     * Текущий выбор зоны роста (версия потока)
     */
    @ExperimentalCoroutinesApi
    private val growZoneChannel = ConflatedBroadcastChannel<GrowZone>()

    /**
     * A list of plants that updates based on the current filter (flow version)
     * Список установок, которые обновляются на основе текущего фильтра (версия потока)
     */
    //val plantsUsingFlow: LiveData<List<Plant>> = plantRepository.plantsFlow.asLiveData()
    // 12. Переключение между двумя потоками.
    @FlowPreview
    @ExperimentalCoroutinesApi
    val plantsUsingFlow: LiveData<List<Plant>> = growZoneChannel.asFlow()
            .flatMapLatest { growZone ->
                if (growZone == NoGrowZone) {
                    plantRepository.plantsFlow
                } else {
                    plantRepository.getPlantsWithGrowZoneFlow(growZone)
                }
            }.asLiveData()
            // asLiveDataОператор преобразует Flowв LiveDataс настраиваемым тайм - аут.
            //Как и в случае со liveData сборщиком, тайм-аут поможет Flow выжить перезапуску.
            // Если другой экран наблюдает до истечения тайм-аута, Flow он не будет отменен.

    init {
        // When creating a new ViewModel, clear the grow zone and perform any related udpates
        // При создании новой модели представления очистите зону роста и выполните все связанные UDP-операции
        clearGrowZoneNumber() // keep this
        //10,  fetch the full plant list принесите полный список растений
        //launchDataLoad { plantRepository.tryUpdateRecentPlantsCache() }
        // 14. Управление параллелизмом с потоком
        growZoneChannel.asFlow()
                .mapLatest { growZone ->
                    _spinner.value = true
                    if (growZone == NoGrowZone) {
                        plantRepository.tryUpdateRecentPlantsCache()
                    } else {
                        plantRepository.tryUpdateRecentPlantsForGrowZoneCache(growZone)
                    }
                }
                .onCompletion {  _spinner.value = false }
                .catch { throwable ->  _snackbar.value = throwable.message  }
                .launchIn(viewModelScope)
    }


    /**
     * Filter the list to this grow zone.
     * Отфильтруйте список до этой зоны роста.
     *
     * In the starter code version, this will also start a network request. After refactoring,
     * updating the grow zone will automatically kickoff a network request.
     * В начальной версии кода это также запустит сетевой запрос. После рефакторинга,
     * обновление зоны роста автоматически запускает сетевой запрос.
     */
    @ExperimentalCoroutinesApi
    fun setGrowZoneNumber(num: Int) {
        growZone.value = GrowZone(num)
        // 12. Переключение между двумя потоками.
        growZoneChannel.offer(GrowZone(num))

        // initial code version, remove during flow rewrite
        // начальная версия кода, будет перемещаться во время перезаписи потока
        //  launchDataLoad { plantRepository.tryUpdateRecentPlantsCache() }
        // 12. Переключение между двумя потоками.
        // 14. Управление параллелизмом с потоком
        // Вы можете закомментировать сетевые вызовы в приведенных ниже методах,
        // поскольку они нужны только для данной LiveDataверсии. SPINNER!!!
         launchDataLoad { plantRepository.tryUpdateRecentPlantsForGrowZoneCache(GrowZone(num)) }
    }

    /**
     * Clear the current filter of this plants list.
     * Очистите текущий фильтр этого списка растений.
     *
     * In the starter code version, this will also start a network request. After refactoring,
     * updating the grow zone will automatically kickoff a network request.
     * В начальной версии кода это также запустит сетевой запрос. После рефакторинга,
     * обновление зоны роста автоматически запускает сетевой запрос.
     */
    @ExperimentalCoroutinesApi
    fun clearGrowZoneNumber() {
        growZone.value = NoGrowZone
        // 12. Переключение между двумя потоками.
        growZoneChannel.offer(NoGrowZone)
        // initial code version, will move during flow rewrite
        // начальная версия кода, будет перемещаться во время перезаписи потока
        // 14. Управление параллелизмом с потоком
        // Вы можете закомментировать сетевые вызовы в приведенных ниже методах,
        // поскольку они нужны только для данной LiveDataверсии. SPINNER!!!
         launchDataLoad { plantRepository.tryUpdateRecentPlantsCache() }
    }

    /**
     * Return true iff the current list is filtered.
     * Возвращает true, если текущий список отфильтрован.
     */
    fun isFiltered() = growZone.value != NoGrowZone

    /**
     * Called immediately after the UI shows the snackbar.
     * Вызывается сразу после того, как пользовательский интерфейс показывает снэк-бар.
     */
    fun onSnackbarShown() {
        _snackbar.value = null
    }

    /**
     * Helper function to call a data load function with a loading spinner; errors will trigger a
     * snackbar.
     * Вспомогательная функция для вызова функции загрузки данных с помощью загрузочного счетчика; ошибки вызовут
     * снэк-бар.
     *
     * By marking [block] as [suspend] this creates a suspend lambda which can call suspend
     * functions.
     * Маркировка [заблокировать] как [приостановить] это создает приостановить лямбда, которая может вызвать приостановить
     * должностные обязанности.
     *
     * @param block lambda to actually load data. It is called in the viewModelScope. Before calling
     *              the lambda, the loading spinner will display. After completion or error, the
     *              loading spinner will stop.
     * @param блокирует лямбду для фактической загрузки данных. Он вызывается в области видимости viewModel.
     * Перед вызовом лямбда, загрузочный счетчик будет отображаться.
     * После завершения или ошибки, загрузка спиннера прекратится.
     */
    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _spinner.value = true
                block()
            } catch (error: Throwable) {
                _snackbar.value = error.message
            } finally {
                _spinner.value = false
            }
        }
    }
}
