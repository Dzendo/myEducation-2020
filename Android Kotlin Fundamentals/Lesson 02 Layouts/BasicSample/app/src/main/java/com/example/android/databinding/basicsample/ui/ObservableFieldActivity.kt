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
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.databinding.ObservableInt
import com.example.android.databinding.basicsample.R
import com.example.android.databinding.basicsample.data.ObservableFieldProfile
import com.example.android.databinding.basicsample.databinding.ObservableFieldProfileBinding
import com.example.android.databinding.basicsample.databinding.ViewmodelProfileBinding

/**
 * This activity shows shows static data and lets the user increment the
 * number of likes by clicking a button. See [ViewModelActivity] for a better implementation.
 * Это действие показывает показывает статические данные и позволяет пользователю увеличивать
 * количество лайков при нажатии на кнопку. См. [модель представления деятельности] для лучшего осуществления.
 */
class ObservableFieldActivity : AppCompatActivity() {

    // создается экземпляр data class с одной записью:
    private val observableFieldProfile = ObservableFieldProfile("Дина", "Дурыкина", ObservableInt(0))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val bindingbas: ObservableFieldProfileBinding =
        //        DataBindingUtil.setContentView(this, R.layout.observable_field_profile)
        val bindingbas: ObservableFieldProfileBinding =
               setContentView(this, R.layout.observable_field_profile)
        val binding = setContentView<ObservableFieldProfileBinding>(this, R.layout.observable_field_profile)
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
    fun onLike(view: View) {
        observableFieldProfile.likes.set(observableFieldProfile.likes.get() + 1)
    }
}
