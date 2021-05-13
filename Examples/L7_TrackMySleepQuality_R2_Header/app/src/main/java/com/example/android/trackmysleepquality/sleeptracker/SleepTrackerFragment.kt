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

package com.example.android.trackmysleepquality.sleeptracker

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.databinding.FragmentSleepTrackerBinding
import com.google.android.material.snackbar.Snackbar

/**
 * A fragment with buttons to record start and end times for sleep, which are saved in
 * a database. Cumulative data is displayed in a simple scrollable TextView.
 * (Because we have not learned about RecyclerView yet.)
 * Фрагмент с кнопками для записи времени начала и окончания сна, которые сохраняются в
 * база данных. Совокупные данные отображаются в простом прокручиваемом текстовом представлении.
 * (Потому что мы еще не узнали о RecyclerView.)
 */
class SleepTrackerFragment : Fragment() {

    /**
     * Called when the Fragment is ready to display content to the screen.
     * Вызывается, когда фрагмент готов к отображению содержимого на экране.
     *
     * This function uses DataBindingUtil to inflate R.layout.fragment_sleep_quality.
     * Эта функция использует DataBindingUtil для раздувания R. layout.fragment_sleep_quality.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        // Get a reference to the binding object and inflate the fragment views.
        // Получить ссылку на объект привязки и раздуть представления фрагментов.
        val binding: FragmentSleepTrackerBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sleep_tracker, container, false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao

        val viewModelFactory = SleepTrackerViewModelFactory(dataSource, application)
        val sleepTrackerViewModel =
            ViewModelProvider(this, viewModelFactory).get(SleepTrackerViewModel::class.java)

        binding.sleepTrackerViewModel = sleepTrackerViewModel

        val nStolbov =when (resources.configuration.orientation){
            Configuration.ORIENTATION_PORTRAIT ->  3
            Configuration.ORIENTATION_LANDSCAPE ->  5
            else ->  3
        }
        // Говорит можно объявить и в RecyclerView XML
        val manager = GridLayoutManager(activity, nStolbov)
        binding.sleepList.layoutManager = manager



        // 25 растяжка заголовка на три столбца:
        //25.1 Создайте SpanSizeLookup.
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            //25,2 Используйте Alt-Enter, чтобы переопределить метод getSpanSize.
            override fun getSpanSize(position: Int): Int =  when (position) {
                // 25,3 Верните правильный размер диапазона для каждой позиции.
                0 -> nStolbov
                else -> 1
            }
        }
        // 25,4 Запустите приложение еще раз и проверьте свой заголовок из 3 столбцов!
        binding.lifecycleOwner = viewLifecycleOwner // this

        // Говорит можно объявить и в RecyclerView XML
        val adapter = SleepNightAdapter(SleepNightListener { nightId ->
            sleepTrackerViewModel.onSleepNightClicked(nightId)
            // Toast.makeText(context, "${nightId}", Toast.LENGTH_LONG).show()
        })
        binding.sleepList.adapter = adapter

        sleepTrackerViewModel.nights.observe(viewLifecycleOwner) {
            it?.let {
                //adapter.submitList(it)
                adapter.addHeaderAndSubmitList(it)
                // 23.10 Наконец, обновите, SleepTrackerFragment чтобы передать список DataItem вместо списка SleepNight
                // и вызвать новый метод addHeaderAndSubmitList вместо метода submitList:
                // LiveData observers are sometimes passed null, so make sure you check for null.
                // Наблюдатели живых данных иногда передаются null, поэтому убедитесь, что вы проверяете наличие null.
            }
        }

            // Add an Observer on the state variable for showing a Snackbar message when the CLEAR button is pressed.
            // Добавьте наблюдателя в переменную состояния для отображения сообщения закусочной при нажатии кнопки Очистить.
            sleepTrackerViewModel.showSnackBarEvent.observe(viewLifecycleOwner) {
                if (it == true) { // Observed state is true. Наблюдаемое состояние истинно.
                    Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.cleared_message), Snackbar.LENGTH_SHORT
                    ).show()
                    // Reset state to make sure the snackbar is only shown once,
                    // even if the device has a configuration change.
                    // Сбросьте состояние, чтобы убедиться, что снэк-бар отображается только один раз,
                    // даже если устройство имеет изменение конфигурации.
                    sleepTrackerViewModel.doneShowingSnackbar()
                }
            }

            // Add an Observer on the state variable for Navigating when STOP button is pressed.
            // Добавьте наблюдателя в переменную состояния для навигации при нажатии кнопки STOP.
            sleepTrackerViewModel.navigateToSleepQuality.observe(viewLifecycleOwner) { night ->
                night?.let {
                    // We need to get the navController from this, because button is not ready, and it
                    // just has to be a view. For some reason, this only matters if we hit stop again
                    // after using the back button, not if we hit stop and choose a quality.
                    // Also, in the Navigation Editor, for Quality -> Tracker, check "Inclusive" for
                    // popping the stack to get the correct behavior if we press stop multiple times
                    // followed by back.
                    // Нам нужно получить навигационный контроллер от этого, потому что кнопка не готова, и она
                    // просто должен быть вид. По какой-то причине это имеет значение только в том случае, если мы снова нажмем стоп
                    // после использования кнопки назад, а не если мы нажмем стоп и выберем качество.
                    // Кроме того, в Редакторе навигации для Quality -> Tracker установите флажок "Inclusive" для
                    // выскакивание стека, чтобы получить правильное поведение, если мы нажмем стоп несколько раз
                    // за ним следует спина.
                    // Also: https://stackoverflow.com/questions/28929637/difference-and-uses-of-oncreate-oncreateview-and-onactivitycreated-in-fra
                    this.findNavController().navigate(
                        SleepTrackerFragmentDirections
                            .actionSleepTrackerFragmentToSleepQualityFragment(night.nightId)
                    )
                    // Reset state to make sure we only navigate once, even if the device
                    // has a configuration change.
                    // Сбросить состояние, чтобы убедиться, что мы перемещаемся только один раз, даже если устройство
                    // имеет изменение конфигурации.
                    sleepTrackerViewModel.doneNavigating()
                }
            }
            sleepTrackerViewModel.navigateToSleepDataQuality.observe(viewLifecycleOwner) { night ->
                night?.let {
                    this.findNavController().navigate(
                        SleepTrackerFragmentDirections
                            .actionSleepTrackerFragmentToSleepDetailFragment(night)
                    )
                    sleepTrackerViewModel.onSleepDataQualityNavigated()
                }
            }
            return binding.root
        }

/*
    Советы по отладке
Если ваше приложение компилируется, но не работает, вот несколько вещей, которые нужно проверить:

Убедитесь, что вы добавили хотя бы одну ночь сна.
Вы называете notifyDataSetChanged()в SleepNightAdapter?
Попробуйте установить точку останова, чтобы убедиться, что она вызывается.
Вы зарегистрировали наблюдателя sleepTrackerViewModel.nights в SleepTrackerFragment?
Вы установили адаптер SleepTrackerFragment с помощью binding.sleepList.adapter = adapter?
Содержит ли data в SleepNightAdapter себе непустой список?
Попробуйте установить точку останова в установщике и getItemCount().
     */
}
