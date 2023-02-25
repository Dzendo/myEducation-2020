/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.sleepdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
//import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.databinding.FragmentSleepDetailBinding


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * * Действия, содержащие этот фрагмент, должны реализовывать
 * [SleepDetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events. для обработки событий взаимодействия.
 * Use the [SleepDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 * создайте экземпляр этого фрагмента.
 *
 */
class SleepDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        // Get a reference to the binding object and inflate the fragment views.
        // Получить ссылку на объект привязки и раздуть представления фрагментов.
        val binding: FragmentSleepDetailBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sleep_detail, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = SleepDetailFragmentArgs.fromBundle(requireArguments())

        // Create an instance of the ViewModel Factory.
        // Создайте экземпляр фабрики ViewModel.
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = SleepDetailViewModelFactory(arguments.sleepNightKey, dataSource)

        // Get a reference to the ViewModel associated with this fragment.
        // Получить ссылку на ViewModel, связанную с этим фрагментом.
        val sleepDetailViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(SleepDetailViewModel::class.java)

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        // Чтобы использовать модель представления с привязкой данных, вы должны явно
        // дайте объекту привязки ссылку на него.
        binding.sleepDetailViewModel = sleepDetailViewModel

        binding.lifecycleOwner = viewLifecycleOwner //this

        // Add an Observer to the state variable for Navigating when a Quality icon is tapped.
        // Добавьте наблюдателя в переменную состояния для навигации при нажатии на значок качества.
        sleepDetailViewModel.navigateToSleepTracker.observe(viewLifecycleOwner) {
            if (it == true) { // Observed state is true. Наблюдаемое состояние истинно.
                this.findNavController().navigate(
                        SleepDetailFragmentDirections.actionSleepDetailFragmentToSleepTrackerFragment())
                // Reset state to make sure we only navigate once, even if the device
                // has a configuration change.
                // Сбросить состояние, чтобы убедиться, что мы перемещаемся только один раз, даже если устройство
                // имеет изменение конфигурации.
                sleepDetailViewModel.doneNavigating()
            }
        }

        return binding.root
    }
}
