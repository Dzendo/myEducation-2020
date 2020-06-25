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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.android.databinding.basicsample.util.Popularity  // добавил AS

/**
 * A VM for [com.example.android.databinding.basicsample.ui.SolutionActivity].
 */
class SimpleViewModelSolution : ViewModel() {
    // Делаем поля для наблюдения из UI
    private val _name = MutableLiveData("Ada")
    private val _lastName = MutableLiveData("Lovelace")
    private val _likes =  MutableLiveData(0)

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
        // НЕЧЕСТНО - КЛАСС Enum берется из простой модели от которой ушли
        }
    }
    /*
    Как вы можете видеть, значение LiveData устанавливается вместе со value свойством,
     и вы можете сделать так, чтобы одни LiveData зависели от другого, используя Transformations .
      Этот механизм позволяет библиотеке обновлять пользовательский интерфейс при изменении значения.
     */

    fun onLike() {
        // так увеличиваеься переменная MutableLiveData
        _likes.value = (_likes.value ?: 0) + 1
    }
}


// Кстати здесь еще и потеряли картинку т.к. не подключены адапреты к вер 4-5