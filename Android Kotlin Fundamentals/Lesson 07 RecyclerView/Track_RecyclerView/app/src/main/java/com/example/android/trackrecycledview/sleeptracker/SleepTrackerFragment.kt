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

package com.example.android.trackrecycledview.sleeptracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
//import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.android.trackrecycledview.R
import com.example.android.trackrecycledview.database.SleepDatabase
import com.example.android.trackrecycledview.databinding.FragmentSleepTrackerBinding
import com.google.android.material.snackbar.Snackbar

/**
 * A fragment with buttons to record start and end times for sleep, which are saved in
 * a database. Cumulative data is displayed in a simple scrollable TextView.
 * (Because we have not learned about RecyclerView yet.)
 * The Clear button will clear all data from the database.
 * * Фрагмент с кнопками для записи времени начала и окончания сна, которые сохраняются в
 * база данных. Совокупные данные отображаются в простом прокручиваемом текстовом представлении.
 * (Потому что мы еще не узнали о RecyclerView.)
 * Кнопка Очистить очистит все данные из базы данных.
 */

// Старт 07,3 Grid
class SleepTrackerFragment : Fragment() {

    /**
     * Called when the Fragment is ready to display content to the screen.
     * Вызывается, когда фрагмент готов к отображению содержимого на экране.
     * This function uses DataBindingUtil to inflate R.layout.fragment_sleep_quality.
     * Эта функция использует DataBindingUtil для раздувания R.layout.fragment_sleep_quality.
     * It is also responsible for passing the [SleepTrackerViewModel] to the
     * [FragmentSleepTrackerBinding] generated by DataBinding. This will allow DataBinding
     * to use the [ LiveData] on our ViewModel.
     * Он также отвечает за передачу [Sleep Tracker ViewModel] в систему
     * [Привязка трекера сна фрагмента] генерируется привязкой данных. Это позволит привязать данные
     * * чтобы использовать [текущие данные] на нашем ViewModel.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        // Получить ссылку на объект привязки и раздуть представления фрагментов
        val binding: FragmentSleepTrackerBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sleep_tracker, container, false)

        val application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel Factory.
        // Создайте экземпляр фабрики ViewModel.
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = SleepTrackerViewModelFactory(dataSource, application)

        // Get a reference to the ViewModel associated with this fragment.
        // Получить ссылку на ViewModel, связанную с этим фрагментом.
        val sleepTrackerViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(SleepTrackerViewModel::class.java)

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        // Чтобы использовать модель представления с привязкой данных, необходимо явным образом
        // дайте объекту привязки ссылку на него.
        binding.sleepTrackerViewModel = sleepTrackerViewModel

        // 3. теперь надо рассказать в фрагменте RecyclerView об этом адаптере в SleepTrackerFragment
        // В SleepTrackerFragment, создать новый SleepNightAdapter,
        // и использовать привязки , чтобы связать его с RecyclerView:
        // val  adapter = SleepNightAdapter()  // создать адаптер

        // Пересоздать лоя слушателя щелчков
        // Отображение тоста при нажатии на предмет
        val adapter = SleepNightAdapter(SleepNightListener { nightId ->
            Toast.makeText(context, "$nightId", Toast.LENGTH_LONG).show()
            //  вызывать обработчик щелчков onSleepNighClicked()
            sleepTrackerViewModel.onSleepNightClicked(nightId)
        })
        // Определите приемник щелчков, передав лямбду в SleepNightAdapter.
        // Эта простая лямбда отображает тост nightId, как показано ниже.
        // Вам придется импортировать Toast. Ниже приведено полное обновленное определение.
        // 07.4 Запустите приложение, нажмите элементы и убедитесь, что они отображают тост с правильным nightId.
        // Поскольку элементы имеют растущие nightId значения, а приложение отображает самую последнюю ночь в первую очередь,
        // элемент с самым низким значением nightId находится внизу списка.




        // Ассоциировать adapterс RecyclerView.:
        binding.sleepList.adapter = adapter  // сказать RecyclerView его использовать на экране

        // Сказать адаптеру, какие данные он должен адапритовать:
        // добавляем наблюдателя к списку ночей из ViewModel , но только тогда, когда элемент на экране
        sleepTrackerViewModel.nights.observe(viewLifecycleOwner) {
            it?.let {
                //adapter.data = it   // присвойте значение адаптеру data Без Дифф первая версия
               // adapter.submitList(it)  // с использованием дифф 07,2 задача 5
                adapter.addHeaderAndSubmitList(it)  // 07.5 заголовок
            }
        }

        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        // Укажите текущее действие в качестве владельца жизненного цикла привязки.
        // Это необходимо для того, чтобы привязка могла наблюдать за текущими обновлениями данных.
        binding.lifecycleOwner = viewLifecycleOwner

        // Add an Observer on the state variable for showing a Snackbar message
        // when the CLEAR button is pressed.
        // Добавьте наблюдателя в переменную состояния для отображения сообщения о снэк-баре
        // при нажатии кнопки Очистить.
        sleepTrackerViewModel.showSnackBarEvent.observe(viewLifecycleOwner) {
            if (it == true) { // Observed state is true.
                Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.cleared_message),
                        Snackbar.LENGTH_SHORT // How long to display the message.
                ).show()
                // Reset state to make sure the toast is only shown once, even if the device
                // has a configuration change.
                // Сбросить состояние, чтобы убедиться, что тост отображается только один раз, даже если устройство
                // имеет изменение конфигурации.
                sleepTrackerViewModel.doneShowingSnackbar()
            }
        }

        // Add an Observer on the state variable for Navigating when STOP button is pressed.
        // Добавьте наблюдателя в переменную состояния для навигации при нажатии кнопки STOP..
        sleepTrackerViewModel.navigateToSleepQuality.observe(viewLifecycleOwner) { night ->
            night?.let {
                // We need to get the navController from this, because button is not ready, and it
                // just has to be a view. For some reason, this only matters if we hit stop again
                // after using the back button, not if we hit stop and choose a quality.
                // Also, in the Navigation Editor, for Quality -> Tracker, check "Inclusive" for
                // popping the stack to get the correct behavior if we press stop multiple times
                // followed by back.
                // Нам нужно получить навигационный контроллер от этого, потому что кнопка не готова, и это
                // просто должен быть вид. По какой-то причине это имеет значение только в том случае, если мы снова нажмем стоп
                // после использования кнопки назад, а не если мы нажмем стоп и выберем качество.
                // Кроме того, в навигационном редакторе для Quality -> Tracker установите флажок "Inclusive" для
                // выскакивание стека, чтобы получить правильное поведение, если мы нажмем стоп несколько раз
                // за ним следует спина.
                // Also: https://stackoverflow.com/questions/28929637/difference-and-uses-of-oncreate-oncreateview-and-onactivitycreated-in-fra
                this.findNavController().navigate(
                        SleepTrackerFragmentDirections
                                .actionSleepTrackerFragmentToSleepQualityFragment(night.nightId))
                // Reset state to make sure we only navigate once, even if the device
                // has a configuration change.
                // Сбросить состояние, чтобы убедиться, что мы перемещаемся только один раз, даже если устройство
                // имеет изменение конфигурации.
                sleepTrackerViewModel.doneNavigating()
            }
        }

        // Add an Observer on the state variable for Navigating when and item is clicked.
        // Добавьте наблюдателя в переменную состояния для навигации при нажатии на элемент и.
        sleepTrackerViewModel.navigateToSleepDetail.observe(viewLifecycleOwner) { night ->
            night?.let {
                                                // action_sleep_tracker_fragment_to_sleepDetailFragment
                this.findNavController().navigate(
                        SleepTrackerFragmentDirections
                              .actionSleepTrackerFragmentToSleepDetailFragment(night))
                sleepTrackerViewModel.onSleepDetailNavigated()
            }
        }

        // 07.3: GridLayout с RecyclerView
        /* Конструктор принимает до четырех аргументов: контекст, который является ,
         число пролетов (колоннами, в вертикальной компоновке по умолчанию),
          ориентации ( по умолчанию является вертикальным),
           и является ли это обратный макетом ( по умолчанию ).
        */
        val manager = GridLayoutManager(activity, 3)
       // val manager = GridLayoutManager(activity, 5, GridLayoutManager.HORIZONTAL, false)
        binding.sleepList.layoutManager = manager  // скажите, RecyclerView чтобы использовать это GridLayoutManager
        // Объект RecyclerView находится в объекте привязки и вызывается sleepList. (См fragment_sleep_tracker.xml.)

        // 07.5 Чтобы исправить ширину заголовка, вам нужно указать, object потому setSpanSizeLookup что не принимает лямбду
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int =  when (position) {
                0 -> 3
                else -> 1
            }
        }

        return binding.root
    }
}
/* 07.5
Чтобы исправить ширину заголовка, вам нужно указать,
 GridLayoutManager когда нужно охватить данные по всем столбцам.
  Вы можете сделать это, настроив SpanSizeLookup на GridLayoutManager.
   Это объект конфигурации, который GridLayoutManager
    используется для определения количества диапазонов для каждого элемента в списке.
 */


/*
6. Резюме 07.3
Менеджеры по расположению управляют тем, как элементы RecyclerView расположены.
RecyclerView поставляется с готовыми менеджерами компоновки для общих случаев использования, таких как LinearLayout горизонтальные и вертикальные списки, а также GridLayout для сеток.
Для более сложных вариантов использования реализуйте пользовательский LayoutManager.
С точки зрения дизайна, GridLayout лучше всего использовать для списков элементов, которые могут быть представлены в виде значков или изображений.
GridLayout упорядочивает элементы в сетке из строк и столбцов. Предполагая вертикальную прокрутку, каждый элемент в строке занимает так называемый «промежуток».
Вы можете настроить, сколько промежутков занимает элемент, создавая более интересные сетки без необходимости в специальном менеджере компоновки.
Создайте макет элемента для одного элемента в сетке, и менеджер макета позаботится о расположении элементов.
Вы можете установить LayoutManager для RecyclerView либо в файле макета XML, который содержит <RecyclerView>элемент, либо программно.
 */


/* Конец разработки Codelabs 07.2 со всеми комментариями:

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.trackrecycledview.R
import com.example.android.trackrecycledview.database.SleepDatabase
import com.example.android.trackrecycledview.databinding.FragmentSleepTrackerBinding
import com.google.android.material.snackbar.Snackbar

/**
 * A fragment with buttons to record start and end times for sleep, which are saved in
 * a database. Cumulative data is displayed in a simple scrollable TextView.
 * (Because we have not learned about RecyclerView yet.)
 * The Clear button will clear all data from the database.
 * * Фрагмент с кнопками для записи времени начала и окончания сна, которые сохраняются в
 * база данных. Совокупные данные отображаются в простом прокручиваемом текстовом представлении.
 * (Потому что мы еще не узнали о RecyclerView.)
 * Кнопка Очистить очистит все данные из базы данных.
 */

class SleepTrackerFragment : Fragment() {

    /**
     * Called when the Fragment is ready to display content to the screen.
     * Вызывается, когда фрагмент готов к отображению содержимого на экране.
     * This function uses DataBindingUtil to inflate R.layout.fragment_sleep_quality.
     * Эта функция использует DataBindingUtil для раздувания R.layout.fragment_sleep_quality.
     * It is also responsible for passing the [SleepTrackerViewModel] to the
     * [FragmentSleepTrackerBinding] generated by DataBinding. This will allow DataBinding
     * to use the [LiveData] on our ViewModel.
     * Он также отвечает за передачу [Sleep Tracker ViewModel] в систему
     * [Привязка трекера сна фрагмента] генерируется привязкой данных. Это позволит привязать данные
     * * чтобы использовать [текущие данные] на нашем ViewModel.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        // Получить ссылку на объект привязки и раздуть представления фрагментов
        val binding: FragmentSleepTrackerBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sleep_tracker, container, false)

        val application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel Factory.
        // Создайте экземпляр фабрики ViewModel.
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = SleepTrackerViewModelFactory(dataSource, application)

        // Get a reference to the ViewModel associated with this fragment.
        // Получить ссылку на ViewModel, связанную с этим фрагментом.
        val sleepTrackerViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(SleepTrackerViewModel::class.java)

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        // Чтобы использовать модель представления с привязкой данных, необходимо явным образом
        // дайте объекту привязки ссылку на него.
        binding.sleepTrackerViewModel = sleepTrackerViewModel

        // 3. теперь надо рассказать в фрагменте RecyclerView об этом адаптере в SleepTrackerFragment
        // В SleepTrackerFragment, создать новый SleepNightAdapter,
        // и использовать привязки , чтобы связать его с RecyclerView:
        val  adapter = SleepNightAdapter()  // создать адаптер
        // Ассоциировать adapterс RecyclerView.:
        binding.sleepList.adapter = adapter  // сказать RecyclerView его использовать на экране

        // Сказать адаптеру, какие данные он должен адапритовать:
        // добавляем наблюдателя к списку ночей из ViewModel , но только тогда, когда элемент на экране
        sleepTrackerViewModel.nights.observe(viewLifecycleOwner, Observer {
            it?.let {
                //adapter.data = it   // присвойте значение адаптеру data Без Дифф первая версия
                adapter.submitList(it)  // с использованием дифф 07,2 задача 5
            }
        })

        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        // Укажите текущее действие в качестве владельца жизненного цикла привязки.
        // Это необходимо для того, чтобы привязка могла наблюдать за текущими обновлениями данных.
        binding.setLifecycleOwner(this)

        // Add an Observer on the state variable for showing a Snackbar message
        // when the CLEAR button is pressed.
        // Добавьте наблюдателя в переменную состояния для отображения сообщения о снэк-баре
        // при нажатии кнопки Очистить.
        sleepTrackerViewModel.showSnackBarEvent.observe(this, Observer {
            if (it == true) { // Observed state is true.
                Snackbar.make(
                        activity!!.findViewById(android.R.id.content),
                        getString(R.string.cleared_message),
                        Snackbar.LENGTH_SHORT // How long to display the message.
                ).show()
                // Reset state to make sure the toast is only shown once, even if the device
                // has a configuration change.
                // Сбросить состояние, чтобы убедиться, что тост отображается только один раз, даже если устройство
                // имеет изменение конфигурации.
                sleepTrackerViewModel.doneShowingSnackbar()
            }
        })

        // Add an Observer on the state variable for Navigating when STOP button is pressed.
        // Добавьте наблюдателя в переменную состояния для навигации при нажатии кнопки STOP..
        sleepTrackerViewModel.navigateToSleepQuality.observe(this, Observer { night ->
            night?.let {
                // We need to get the navController from this, because button is not ready, and it
                // just has to be a view. For some reason, this only matters if we hit stop again
                // after using the back button, not if we hit stop and choose a quality.
                // Also, in the Navigation Editor, for Quality -> Tracker, check "Inclusive" for
                // popping the stack to get the correct behavior if we press stop multiple times
                // followed by back.
                // Нам нужно получить навигационный контроллер от этого, потому что кнопка не готова, и это
                // просто должен быть вид. По какой-то причине это имеет значение только в том случае, если мы снова нажмем стоп
                // после использования кнопки назад, а не если мы нажмем стоп и выберем качество.
                // Кроме того, в навигационном редакторе для Quality -> Tracker установите флажок "Inclusive" для
                // выскакивание стека, чтобы получить правильное поведение, если мы нажмем стоп несколько раз
                // за ним следует спина.
                // Also: https://stackoverflow.com/questions/28929637/difference-and-uses-of-oncreate-oncreateview-and-onactivitycreated-in-fra
                this.findNavController().navigate(
                        SleepTrackerFragmentDirections
                                .actionSleepTrackerFragmentToSleepQualityFragment(night.nightId))
                // Reset state to make sure we only navigate once, even if the device
                // has a configuration change.
                // Сбросить состояние, чтобы убедиться, что мы перемещаемся только один раз, даже если устройство
                // имеет изменение конфигурации.
                sleepTrackerViewModel.doneNavigating()
            }
        })
        return binding.root
    }
}

 */