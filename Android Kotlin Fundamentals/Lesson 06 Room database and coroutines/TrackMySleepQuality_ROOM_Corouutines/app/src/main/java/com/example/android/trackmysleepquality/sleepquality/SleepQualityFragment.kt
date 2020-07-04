/*
 * Copyright 2019, The Android Open Source Project
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

package com.example.android.trackmysleepquality.sleepquality

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.databinding.FragmentSleepQualityBinding

/**
 * Fragment that displays a list of clickable icons,
 * each representing a sleep quality rating.
 * Фрагмент, отображающий список кликабельных значков,
 * каждый из них представляет собой оценку качества сна.
 * Once the user taps an icon, the quality is set in the current sleepNight
 * and the database is updated.
 * Как только пользователь нажимает на значок, качество устанавливается в текущей ночи сна
 * и база данных обновляется.
 */
class SleepQualityFragment : Fragment() {

    /**
     * Called when the Fragment is ready to display content to the screen.
     * Вызывается, когда фрагмент готов к отображению содержимого на экране.
     *
     * This function uses DataBindingUtil to inflate R.layout.fragment_sleep_quality.
     * Эта функция использует DataBindingUtil для раздувания R.layout.fragment_sleep_quality.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        // Получить ссылку на объект привязки и раздуть представления фрагментов.
        //val binding: FragmentSleepQualityBinding = DataBindingUtil.inflate(
        //        inflater, R.layout.fragment_sleep_quality, container, false)
        val binding = FragmentSleepQualityBinding.inflate(
                inflater,  container, false)

        // после того, как вы получите application
        val application = requireNotNull(this.activity).application

        // нужно получить то, arguments что поставляется с навигацией.
        // Эти аргументы в SleepQualityFragmentArgs.
        // Вам нужно извлечь их из связки.
        //val arguments = SleepQualityFragmentArgs.fromBundle(requireArguments())
        
        // Сюда передается ID ноиер записи в базе данных этого сна sleepNightKey

        // Вам аргументы, используя navArgs собственность делегат 05.21 идеально
        val arguments by navArgs<SleepQualityFragmentArgs>()


        // Далее получите dataSource, т.е. подхватить ссылку на БД созланную ранее
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao

        // Создайте фабрику, передав в dataSource и sleepNightKey
        //val viewModelFactory = SleepQualityViewModelFactory(arguments.sleepNightKey, dataSource)

        // Получить ViewModel ссылку.
        /*val sleepQualityViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(SleepQualityViewModel::class.java)
        */
        // Найти способ создавать с параметром через
        val sleepQualityViewModel: SleepQualityViewModel by viewModels { SleepQualityViewModelFactory(arguments.sleepNightKey, dataSource)}
        // Найти как обойтись без фактори

        // Добавьте ViewModel к объекту привязки
        binding.sleepQualityViewModel = sleepQualityViewModel

        // Добавьте наблюдателя над navigateToSleepTracker: LiveData<Boolean?> = true/false 
        // Флажок пора ли переходить обратно на первый экран
        // При появлении запроса импортируйте androidx.lifecycle.Observer
        sleepQualityViewModel.navigateToSleepTracker.observe(viewLifecycleOwner) {
            if (it == true) { // Observed state is true.
                this.findNavController().navigate(
                        SleepQualityFragmentDirections.actionSleepQualityFragmentToSleepTrackerFragment())
                sleepQualityViewModel.doneNavigating()
            }
        }

        // Шаг 3: Обновите файл макета и запустите приложение
        /*
        <data>
            <variable
                name="sleepQualityViewModel"
                type="com.example.android.trackmysleepquality.sleepquality.SleepQualityViewModel" />
        </data>
        android:onClick="@{() -> sleepQualityViewModel.onSetSleepQuality(5)}"
         */

        return binding.root
    }
}
