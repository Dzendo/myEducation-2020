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

package com.example.android.trackmysleepquality.sleeptracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.databinding.FragmentSleepTrackerBinding
import com.google.android.material.snackbar.Snackbar

/**
 * A fragment with buttons to record start and end times for sleep, which are saved in
 * a database. Cumulative data is displayed in a simple scrollable TextView.
 * Фрагмент с кнопками для записи времени начала и окончания сна, которые сохраняются в
 * база данных. Совокупные данные отображаются в простом прокручиваемом текстовом представлении.
 * (Because we have not learned about RecyclerView yet.)
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
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        // Получить ссылку на объект привязки и раздуть представления фрагментов.
        val binding: FragmentSleepTrackerBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sleep_tracker, container, false)

        // Вам нужна ссылка на приложение, к которому прикреплен этот фрагмент,
        // для передачи поставщику фабрики модели представления
        val application = requireNotNull(this.activity).application

        // // Create an instance of the ViewModel Factory.
        // Вам нужна ссылка на ваш источник данных через ссылку на DAO
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao

        // создать экземпляр класса viewModelFactory. Вы должны передать dataSource и application
        val viewModelFactory = SleepTrackerViewModelFactory(dataSource, application)

        // // Get a reference to the ViewModel associated with this fragment.
        // Теперь, когда у вас есть завод, получите ссылку на SleepTrackerViewModel
        val sleepTrackerViewModel =
                //ViewModelProviders.of(
                ViewModelProvider(
                        this, viewModelFactory).get(SleepTrackerViewModel::class.java)
        // SleepTrackerViewModel::class.java Параметр относится к времени выполнения Java класса этого объекта

        // Назначьте sleepTrackerViewModel переменную привязки для sleepTrackerViewModel:
        binding.sleepTrackerViewModel = sleepTrackerViewModel

        // Добавьте привязку данных для модели представления:
        // Установите текущую активность как владельца жизненного цикла привязки:
        binding.setLifecycleOwner(this)

        sleepTrackerViewModel.navigateToSleepQuality.observe(viewLifecycleOwner, Observer { night ->
            night?.let {
                this.findNavController().navigate(
                        SleepTrackerFragmentDirections
                                .actionSleepTrackerFragmentToSleepQualityFragment(night.nightId))
                sleepTrackerViewModel.doneNavigating()
            }
        })
        // добавьте наблюдатель
        sleepTrackerViewModel.showSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.cleared_message),
                        Snackbar.LENGTH_SHORT // How long to display the message.
                ).show()
                sleepTrackerViewModel.doneShowingSnackbar()
                // Внутри блока наблюдения отобразите снэк-бар и немедленно сбросьте событие.
            }
        })

        return binding.root
    }
}

/*
  suspend- это способ Kotlin пометить функцию или тип функции как доступный для сопрограмм
* Сопрограммы асинхронные и неблокирующие.
* Сопрограммы используют функции приостановки, чтобы сделать асинхронный код последовательным.

Чтобы использовать сопрограммы в Kotlin, вам нужно три вещи:
--Работа
--Диспетчер
--Сфера

-Работа : По сути, работа - это все, что можно отменить.
 У каждого сопрограммы есть работа, и вы можете использовать ее, чтобы отменить сопрограмму.
  Задания могут быть организованы в иерархии родитель-потомок.
  Отмена родительской работы немедленно отменяет всех дочерних элементов работы,
   что намного удобнее, чем отмена каждой сопрограммы вручную.

-Диспетчер: Диспетчер отправляет сопрограммы для работы в различных потоках. Например,
 Dispatcher.Mainза пускает задачи в главном потоке и
 Dispatcher.IO выгружает блокирующие задачи ввода-вывода в общий пул потоков.

-Область действия: область действия сопрограммы определяет контекст, в котором выполняется сопрограмма.
 Область действия объединяет информацию о работе сопрограммы и диспетчере.
 Области отслеживания сопрограмм.
 Когда вы запускаете сопрограмму, она находится «в области видимости»,
 что означает, что вы указали, какая область будет отслеживать сопрограмму.
 */

/*
Совет: Разница между блокировкой и приостановкой заключается в том,
что если поток заблокирован, никакой другой работы не происходит.
Если поток приостановлен, выполняется другая работа до тех пор, пока результат не станет доступен.
 */