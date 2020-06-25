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

package com.example.android.databinding.basicsample.data

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.android.databinding.basicsample.BR
import com.example.android.databinding.basicsample.util.ObservableViewModel
// в этом файде пример viewModel LiveData варианта и viewModel ObservableViewModel варианта
// третий вариант лежит в util ObservableViewModel

/**
 * This class is used as a variable in the XML layout and it's fully observable, meaning that
 * changes to any of the exposed observables automatically refresh the UI. *
 * Этот класс используется в качестве переменной в XML-макете, и он полностью наблюдаем, что означает, что
 * изменения в любом из открытых наблюдаемых объектов автоматически обновляют пользовательский интерфейс. *
 * 
 * Это для viewModel LiveData варианта
 */
class ProfileLiveDataViewModel : ViewModel() {
    private val _name = MutableLiveData("LiveData")
    private val _lastName = MutableLiveData("ViewModel")
    private val _likes =  MutableLiveData(0)
// заводим public поля чтобы private не изменили извне - Инкапсуляция
    val name: LiveData<String> = _name
    val lastName: LiveData<String> = _lastName
    val likes: LiveData<Int> = _likes

    // popularity is exposed as LiveData using a Transformation instead of a @Bindable property.
    // популярность выставляется как живые данные с использованием преобразования вместо свойства @Bindable.
    val popularity: LiveData<Popularity> = Transformations.map(_likes) {
        when {
            it > 9 -> Popularity.STAR
            it > 4 -> Popularity.POPULAR
            else -> Popularity.NORMAL
        }
    }
    // Этот fun вызывается из XML так оперируются LiveData поля:
    fun onLike() {
        _likes.value = (_likes.value ?: 0) + 1
    }
}

/**
 * As an alternative to LiveData, you can use Observable Fields and binding properties.
 * В качестве альтернативы живым данным можно использовать наблюдаемые поля и свойства привязки.
 *
 * `Popularity` is exposed here as a `@Bindable` property so it's necessary to call
 * `notifyPropertyChanged` when any of the dependent properties change (`likes` in this case).
 * 'Популярность' выставляется здесь как свойство ' @Bindable`, поэтому необходимо вызвать
 * "notifyPropertyChanged" при изменении любого из зависимых свойств (в данном случае "нравится").
 * 
 *  * Это для viewModel ObservableViewModel вариант (используется в тестах)
 */
class ProfileObservableViewModel : ObservableViewModel() {
    // Без data class прямо здесь ФИО
    val name = ObservableField("Observable")
    val lastName = ObservableField("ViewModel")
    val likes =  ObservableInt(0)

    fun onLike() {
        likes.increment()
        // You control when the @Bindable properties are updated using `notifyPropertyChanged()`.
        // Вы управляете обновлением свойств @Bindable с помощью функции ' notifyPropertyChanged ()'.
        notifyPropertyChanged(BR.popularity)
    }

    @Bindable
    fun getPopularity(): Popularity {
        return likes.get().let {
            when {
                it > 9 -> Popularity.STAR
                it > 4 -> Popularity.POPULAR
                else -> Popularity.NORMAL
            }
        }
    }
}

enum class Popularity {
    NORMAL,
    POPULAR,
    STAR
}
// расширяется на свойство increment() ObservableInt и используется выше в fun onLike() { likes.increment()
private fun ObservableInt.increment() {
    set(get() + 1)
}
