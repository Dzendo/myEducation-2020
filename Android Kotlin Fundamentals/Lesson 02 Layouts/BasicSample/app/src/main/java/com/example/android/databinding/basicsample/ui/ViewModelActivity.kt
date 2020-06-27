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
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.ViewModelProvider
import com.example.android.databinding.basicsample.R
import com.example.android.databinding.basicsample.data.ProfileLiveDataViewModel
import com.example.android.databinding.basicsample.databinding.ViewmodelProfileBinding

/**
 * This activity uses a [android.arch.lifecycle.ViewModel] to hold the data and respond to user
 * actions. Also, the layout uses [android.databinding.BindingAdapter]s instead of expressions
 * which are much more powerful.
 *
 * @see com.example.android.databinding.basicsample.util.BindingAdapters
 */
class ViewModelActivity : AppCompatActivity() {
    //private lateinit var binding: ViewmodelProfileBinding
    //private val viewModel1: ProfileLiveDataViewModel by viewModels() - только для фрагментов??? SunFlower
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtain (Получать) ViewModel from ViewModelProviders  (\data\ProfileObservableViewModel.kt)
       // val viewModelP = ViewModelProvider(this).get(ProfileLiveDataViewModel::class.java)
       // val viewModel1 by viewModels<ProfileLiveDataViewModel>()
        // Более короткая запись, требует "androidx.navigation:navigation-fragment-ktx:2.2.2"
        val viewModel: ProfileLiveDataViewModel  by viewModels()

        // An alternative ViewModel using Observable fields and @Bindable properties can be used:
        // val viewModel = ViewModelProviders.of(this).get(ProfileObservableViewModel::class.java)

        // Obtain (Получать) binding 
       // val binding: ViewmodelProfileBinding =
       //         DataBindingUtil.setContentView(this, R.layout.viewmodel_profile)
        val binding = setContentView<ViewmodelProfileBinding>(this, R.layout.viewmodel_profile)
        //binding = ViewmodelProfileBinding.inflate(LayoutInflater)
        //setContentView(binding.root)
        // Bind layout with ViewModel Привязка макета с помощью ViewModel
        binding.viewmodel = viewModel

        // LiveData needs the lifecycle owner Живые данные нуждаются в владельце жизненного цикла
        binding.lifecycleOwner = this
    }
}
