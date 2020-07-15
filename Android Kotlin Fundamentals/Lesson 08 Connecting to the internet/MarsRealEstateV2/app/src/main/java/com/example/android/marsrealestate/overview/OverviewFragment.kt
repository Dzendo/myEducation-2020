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
 *
 */

package com.example.android.marsrealestate.overview

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
//import androidx.lifecycle.ViewModelProvider
import com.example.android.marsrealestate.R
import com.example.android.marsrealestate.databinding.FragmentOverviewBinding
import com.example.android.marsrealestate.databinding.GridViewItemBinding
import com.example.android.marsrealestate.network.MarsApiFilter

/**
 * This fragment shows the the status of the Mars real-estate web services transaction.
 * * Этот фрагмент показывает состояние транзакции веб-сервисов Mars real-estate.
 */
class OverviewFragment : Fragment() {

    /**
     * Lazily initialize our [OverviewViewModel].
     * Лениво инициализирует OverviewViewModel, что означает ,
     * что OverviewViewModel создается в первый раз , когда он используется.
     */
   // private val viewModel: OverviewViewModel by lazy {
   //     ViewModelProvider(this).get(OverviewViewModel::class.java)
   // }
      private val viewModel: OverviewViewModel by viewModels()
    //private val viewModel  by viewModels<OverviewViewModel>()


    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     *  Раздувает макет с привязкой данных, устанавливает его владельца жизненного цикла в OverviewFragment
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
// 12.1.5 надуть FragmentOverviewBinding обратно вместо GridViewItemBinding
        val binding = FragmentOverviewBinding.inflate(inflater)
// 11.2.4  Вернуться OverviewFragment, заменить FragmentOverviewBinding на GridViewItemBinding. - чтобы увидеть первую картинку
// 11.2.5  Вы должны увидеть красивый объект на Марсе.
       // val binding = GridViewItemBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        // Позволяет привязке данных наблюдать живые данные с жизненным циклом этого фрагмента
        binding.lifecycleOwner = viewLifecycleOwner //this

        // Giving the binding access to the OverviewViewModel
        // Предоставление привязки доступа к OverviewViewModel
        binding.viewModel = viewModel

// 12.2.9  установите адаптер в RecyclerView( photosGrid.adapter в объекте привязки) на новый PhotoGridAdapter
// 15.3.4  обновите PhotosGridAdapter привязку, чтобы добавить прослушиватель кликов,
// который передает выбранное свойство viewModel.displayPropertyDetails(): (для нажатия в RecyclerView)
        //binding.photosGrid.adapter = PhotoGridAdapter()
        binding.photosGrid.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
// 15.3.5 Если вы установите точку останова в отладчике, вы увидите, что при нажатии на изображение вызывается обработчик щелчков в onCreateView,
        })

// 15.5.1  добавьте наблюдателя, navigateToSelectedProperty
// который звонит, navigate() чтобы перейти к подробному экрану,
// когда MarsProperty нет null.
        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner) {
            if ( null != it ) {
                this.findNavController().navigate(OverviewFragmentDirections.actionShowDetail(it))
                viewModel.displayPropertyDetailsComplete()
            }
        }

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
// 16.7 предоставить опцию меню, чтобы пользователь мог изменить фильтр
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
            when (item.itemId) {
                R.id.show_rent_menu -> MarsApiFilter.SHOW_RENT
                R.id.show_buy_menu -> MarsApiFilter.SHOW_BUY
                else -> MarsApiFilter.SHOW_ALL
            }
        )
        return true
    }
}
