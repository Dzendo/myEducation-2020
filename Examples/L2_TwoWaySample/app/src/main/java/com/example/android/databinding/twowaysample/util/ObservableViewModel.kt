/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.databinding.twowaysample.util

import androidx.lifecycle.ViewModel
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry

/**
 * An [Observable] [ViewModel] for Data Binding. для привязки данных.
 * Используется как родительский для IntervalTimerViewModel(private val timer: Timer) : ObservableViewModel()
 * По-моему млжно его вообще не использовать а в IntervalTimerViewModel применить:
 * private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }
 * callbacks.add(callback)   callback: Observable.OnPropertyChangedCallback
 * notifyChange(Observable observable, int propertyId) - уведомить int: Идентификатор BR объекта недвижимости, который был изменен, или BR._all если весь Наблюдаемое изменилось.
 * все изменились: notifyChange(this, BR._all )
 * параметр изментлся: notifyChange (this, BR_id)
 *
 */
open class ObservableViewModel : ViewModel(), Observable {

    // Служебный класс для управления наблюдаемыми обратными вызовами.
    // для наблюдения за изменением Preferences значений которые хотим сохранить
    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }
    // метод notifyChange(Observable observable, int propertyId)
    // Уведомляет зарегистрированные обратные вызовы об изменении определенного свойства.

    // это класс interface Observable по-моему можно оставить add(Observable.OnPropertyChangedCallback callback)
    // устанавливается в MainActivity зарегистрированные обратные вызовы в классе callbacks
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.add(callback)
    }
    // не нашел использования м.б. для комплекта
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.remove(callback)
        // remove(Observable.OnPropertyChangedCallback callback) Удалите обратный вызов.
    }

    /* Публичные методы PropertyChangeRegistry
    void notifyChange (Observable observable, int propertyId)
    Уведомляет зарегистрированные обратные вызовы об изменении определенного свойства.
    Observable- Наблюдаемое, которое изменилось.
    propertyId int: Идентификатор BR объекта недвижимости, который был изменен, или BR._ Все если весь Наблюдаемое изменилось.
     */

    /**
     * Notifies listeners that all properties of this instance have changed.
     * Уведомляет слушателей о том, что все свойства этого экземпляра изменились.
     * Похоже переопределяет а зачем?? callbacks.notifyCallbacks(this, 0, null)
     */
    @Suppress("unused")
    fun notifyChange() {
            callbacks.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with [Bindable] to generate a field in
     * `BR` to be used as `fieldId`.
     * Уведомляет слушателей об изменении определенного свойства. getter имущества
     * что изменения должны быть помечены [Bindable] для создания поля в
     * `БР`, чтобы использоваться как идентификатор поля`.
     *
     * @param fieldId The generated BR id for the Bindable field. Сгенерированный BR id для связываемого поля.
     */
    fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }
}
