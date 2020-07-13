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
 *
 */

package com.example.android.marsrealestatec.overview

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.marsrealestatec.R
import com.example.android.marsrealestatec.databinding.FragmentOverviewBinding
import com.example.android.marsrealestatec.network.MarsApiFilter

//import com.example.android.marsrealestatec.databinding.GridViewItemBinding

/**
 * This fragment shows the the status of the Mars real-estate web services transaction.
 * Этот фрагмент показывает состояние транзакции веб-сервисов Mars real-estate.
 */
class OverviewFragment : Fragment() {

    /**
     * Lazily initialize our [OverviewViewModel].
     * Лениво инициализирует OverviewViewModel, что означает ,
     * что OverviewViewModel создается в первый раз , когда он используется.
     */
    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     * Раздувает макет с привязкой данных, устанавливает его владельца жизненного цикла в OverviewFragment
     * чтобы включить привязку данных для наблюдения за живыми данными,а также настроить RecyclerView с адаптером.
     *
     * Этот метод раздувает fragment_overview макет с помощью привязки данных,
     * устанавливает для владельца жизненного цикла привязки себе ( this)
     * и устанавливает для него viewModel переменную в binding объекте.
     * Поскольку мы установили владельца жизненного цикла,
     * все LiveData используемые в привязке данных будут автоматически отслеживаться для любых изменений,
     * и пользовательский интерфейс будет обновляться соответствующим образом.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)
       // val binding = GridViewItemBinding.inflate(inflater)  // временно для урока одного изображения

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        // Позволяет привязке данных наблюдать живые данные с жизненным циклом этого фрагмента
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        // Предоставление привязке доступа к OverviewViewModel
        binding.viewModel = viewModel
        // инициализируйте RecyclerView адаптер в binding.photosGrid новый PhotoGridAdapter объект.
        //binding.photosGrid.adapter = PhotoGridAdapter()

        // Этот код добавляет PhotoGridAdapter.onClickListener объект в PhotoGridAdapter конструктор
        // и вызывает viewModel.displayPropertyDetails() переданный MarsProperty объект.
        // Это вызывает LiveData в представлении модель для навигации.
        binding.photosGrid.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        // чтобы наблюдать navigatedToSelectedProperty из модели обзорного вида
        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            // Наблюдатель проверяет, не является ли MarsProperty- it в лямбде - не нулевым, и если это так,
            // он получает контроллер навигации из фрагмента с помощью findNavController()
            if ( null != it ) {
                this.findNavController().navigate(
                        OverviewFragmentDirections.actionShowDetail(it))
                // сообщить модели представления, что необходимо сбросить LiveData нулевое состояние,
                // чтобы случайно не запустить навигацию снова, когда приложение вернется в OverviewFragment
                viewModel.displayPropertyDetailsComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    /**
     * Inflates the overflow menu that contains filtering options.
     * Раздувает меню переполнения, содержащее параметры фильтрации.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
                when (item.itemId) {
                    R.id.show_rent_menu -> MarsApiFilter.SHOW_RENT
                    R.id.show_buy_menu -> MarsApiFilter.SHOW_BUY
                    else -> MarsApiFilter.SHOW_ALL
                }
        )
        // Вернитесь true, потому что вы обработали пункт меню
        return true
    }
}
