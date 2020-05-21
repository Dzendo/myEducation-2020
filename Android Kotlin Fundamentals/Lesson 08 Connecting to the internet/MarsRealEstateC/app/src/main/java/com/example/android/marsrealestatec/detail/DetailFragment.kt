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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.marsrealestatec.databinding.FragmentDetailBinding


/**
 * This [Fragment] will show the detailed information about a selected piece of Mars real estate.
 */
class DetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //@Suppress("UNUSED_VARIABLE")
        val application = requireNotNull(activity).application
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.setLifecycleOwner(this)

        // Эта строка получает выбранный MarsProperty объект из Safe Args.
        val marsProperty = DetailFragmentArgs.fromBundle(arguments!!).selectedProperty
        // Обратите внимание на использование оператора ненулевого утверждения Котлина (!!).
        // Если этого selectedProperty не произошло, произошло что-то ужасное,
        // и вы действительно хотите, чтобы код выдал нулевой указатель.
        // (В рабочем коде вы должны каким-то образом обработать эту ошибку.)

        // чтобы получить новый DetailViewModelFactory
        val viewModelFactory = DetailViewModelFactory(marsProperty, application)
       /* binding.viewModel = ViewModelProvider(this,viewModelFactory).get(DetailViewModel::class.java)*/

        //  эту строку, чтобы получить DetailViewModel с завода и соединить все детали.
        binding.viewModel = ViewModelProvider(
                this, viewModelFactory).get(DetailViewModel::class.java)

        return binding.root
    }
}