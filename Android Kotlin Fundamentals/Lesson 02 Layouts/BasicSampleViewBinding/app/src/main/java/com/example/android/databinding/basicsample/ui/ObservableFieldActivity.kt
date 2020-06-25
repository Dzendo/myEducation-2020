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

package com.example.android.databinding.basicsample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.databinding.basicsample.data.ObservableFieldProfile
import com.example.android.databinding.basicsample.databinding.ObservableFieldProfileBinding


/**
 * This activity shows shows static data and lets the user increment the
 * number of likes by clicking a button. See [ViewModelActivity] for a better implementation.
 * Это действие показывает показывает статические данные и позволяет пользователю увеличивать
 * количество лайков при нажатии на кнопку. См. [модель представления деятельности] для лучшего осуществления.
 */
/*
class ObservableFieldActivity : AppCompatActivity() {

    // создается экземпляр data class с одной записью:
    private val observableFieldProfile = ObservableFieldProfile("Дина", "Дурыкина", ObservableInt(0))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val bindingbas: ObservableFieldProfileBinding =
        //        DataBindingUtil.setContentView(this, R.layout.observable_field_profile)
        //val bindingbas: ObservableFieldProfileBinding =
        //       setContentView(this, R.layout.observable_field_profile)
        // Можно и так - работает : плюс не надо import R и DataBindingUtil.setContentView
        val binding =  ObservableFieldProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //val binding = setContentView<ObservableFieldProfileBinding>(this, R.layout.observable_field_profile)
        binding.user = observableFieldProfile  // переменной user в XML присвоить класс data с Диной
    }

    /**
     * This method is triggered by the `android:onclick` attribute in the layout. It puts business
     * logic in the activity, which is not ideal. See {@link ViewModelActivity} for a better
     * solution.
     * Этот метод запускается атрибутом "android: onclick"в макете. Это ставит бизнес
     * логика в деятельности, которая не идеальна. См. {@ссылку на модель представления деятельности} для лучшего
     * решение.
     */
    // отключил и перебросил на функцию из data class - вроеде так более инкапсулировано см XML и data
    fun onLike(unused: View) {
        //observableFieldProfile.likes.set(observableFieldProfile.likes.get() + 1)
        observableFieldProfile.likeInc()
    }
}*/
// ИТОГО В СУхОМ ОСТАТКЕ: Плохой вариант  и не держит поворот телефона (без viewModel)
class ObservableFieldActivity : AppCompatActivity() {
    private val observableFieldProfile = ObservableFieldProfile("Дина", "Дурыкина")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =  ObservableFieldProfileBinding.inflate(layoutInflater)
        binding.user = observableFieldProfile  // переменной user в XML присвоить класс data с Диной
        setContentView(binding.root)
    }
}


