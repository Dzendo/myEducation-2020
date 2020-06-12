/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunfloweras

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.google.samples.apps.sunfloweras.adapters.GardenPlantingAdapter
import com.google.samples.apps.sunfloweras.adapters.PLANT_LIST_PAGE_INDEX
import com.google.samples.apps.sunfloweras.databinding.FragmentGardenBinding
import com.google.samples.apps.sunfloweras.utilities.InjectorUtils
import com.google.samples.apps.sunfloweras.viewmodels.GardenPlantingListViewModel

// Строит фрагмент садика на первом экране когда позовет ViewPager2 в поле, опреденное в
// <androidx.viewpager2.widget.ViewPager2
//            android:id="@+id/view_pager"
class GardenFragment : Fragment() {

    private lateinit var binding: FragmentGardenBinding

    // Разбирать как создается/строится viewModel:
    // из библиотеки androidx.fragment:fragment-ktx:1.2.0-alpha01
    // реализует inline createViewModelLazy и из InjectorUtils зовет ПРОВАЙДЕР и оттуда Factory
    // а он создает репозиторий садика в этой viewModel а также создает базу Room с прицепленным DAO садика
    private val viewModel: GardenPlantingListViewModel by viewModels {
        InjectorUtils.provideGardenPlantingListViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGardenBinding.inflate(inflater, container, false)
        val adapter = GardenPlantingAdapter()   // это адаптер из области для построения RecyclerView
        binding.gardenList.adapter = adapter   // gardenList - это поле RecyclerView и ему GardenPlantingAdapter()
        // где-то там в адаптерах и реакция на наэатие на карточку посаженного растения из RecyclerView - искать

        binding.addPlant.setOnClickListener {
            navigateToPlantListPage()  // входная кнопка когда садик еще пуст идти к функция внизу модуля т.е. на вкладку Plant
        }

        // AS включить верхнее меню во фрагменте - тогда вызовется onCreateOptionsMenu и onOptionsItemSelected
        setHasOptionsMenu(true)
        //Основы Android Kotlin 07.2.5: DiffUtil и привязка данных с помощью RecyclerView
        // спользуйте submitList (), чтобы обновлять список subscribeUi - функция внизу файлв
        subscribeUi(adapter, binding)
        return binding.root
    }
    // AS Устанавливается значек(меню) фильтрации - рисование значка в квадратике справа наверху
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_plant_list, menu)  // ставит значек фильтрации зоны наверху в качестве меню
    }
    //AS реакция на нажатие на значк в квадратике справа наверху
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter_zone -> {
               viewModel.clearGarden()
                Snackbar.make(binding.root,R.string.garden_cleared, Snackbar.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // подписаться на пользовательский интерфейс - разбирать что делает
    //Основы Android Kotlin 07.2.5: DiffUtil и привязка данных с помощью RecyclerView
    private fun subscribeUi(adapter: GardenPlantingAdapter, binding: FragmentGardenBinding) {
        viewModel.plantAndGardenPlantings.observe(viewLifecycleOwner) { result ->
            binding.hasPlantings = !result.isNullOrEmpty()  // по дороге при высветке успевает проставлять в XML посажено или нет
            adapter.submitList(result)
            //  Используйте submitList (), чтобы обновлять список
        }
    }

    // TODO: convert to data binding if applicable -- преобразование в привязку данных, если это применимо
    private fun navigateToPlantListPage() {
        requireActivity().findViewById<ViewPager2>(R.id.view_pager).currentItem =
            PLANT_LIST_PAGE_INDEX
    }
}
