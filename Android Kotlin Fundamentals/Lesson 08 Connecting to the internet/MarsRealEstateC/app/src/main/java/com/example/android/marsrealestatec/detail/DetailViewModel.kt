/*
 *  Copyright 2019, The Android Open Source Project
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.example.android.marsrealestatec.detail

import android.app.Application
import androidx.lifecycle.*
import com.example.android.marsrealestatec.R
import com.example.android.marsrealestatec.network.MarsProperty

/**
 * The [ViewModel] that is associated with the [DetailFragment].
 */
// 08.3.5 Шаг 1. Создайте модель детального вида и обновите макет детали.
//class DetailViewModel(@Suppress("UNUSED_PARAMETER")marsProperty: MarsProperty, app: Application) : AndroidViewModel(app)
class DetailViewModel( marsProperty: MarsProperty,
                       app: Application) : AndroidViewModel(app) {
    //В определении класса добавьте LiveData выбранное свойство Mars, чтобы представить эту информацию в подробном представлении.
    // Следуйте обычному шаблону создания MutableLiveData для MarsProperty себя самого,
    // а затем выставляйте неизменную публичную LiveData собственность.
    private val _selectedProperty = MutableLiveData<MarsProperty>()
    val selectedProperty: LiveData<MarsProperty>
        get() = _selectedProperty
    init {  // установите значение выбранного свойства Mars с помощью MarsProperty объекта из конструктора
        _selectedProperty.value = marsProperty
    }
    // Откройте res/layout/fragment_detail.xml и посмотрите на это в режиме конструктора

    // Это преобразование проверяет, является ли выбранное свойство арендуемым, используя тот же тест из первой задачи.
    // Если свойство является арендным, преобразование выбирает соответствующую строку из ресурсов с помощью when{} переключателя Kotlin.
    // Обе эти строки должны иметь номер в конце, так что вы можете объединить property.price потом.
    val displayPropertyPrice = Transformations.map(selectedProperty) {
        app.applicationContext.getString(
                when (it.isRental) {
                    true -> R.string.display_price_monthly_rental
                    false -> R.string.display_price
                }, it.price)
    }

    // Это преобразование объединяет несколько строковых ресурсов в зависимости от того, является ли тип свойства арендным
    val displayPropertyType = Transformations.map(selectedProperty) {
        app.applicationContext.getString(R.string.display_type,
                app.applicationContext.getString(
                        when (it.isRental) {
                            true -> R.string.type_rent
                            false -> R.string.type_sale
                        }))
    }
    // Open res/layout/fragment_detail.xml
}


/*
Этот фрагмент запускается, когда пользователь нажимает на изображение в обзорной сетке.
Для этого вам нужно добавить onClick слушателя к RecyclerView элементам сетки, а затем перейти к новому фрагменту.
Вы перемещаетесь, вызывая LiveData изменение в ViewModel, как вы делали на протяжении всех этих уроков.
Вы также используете плагин Safe Args компонента Navigation
для передачи выбранной MarsProperty информации из фрагмента обзора во фрагмент детали.
 */