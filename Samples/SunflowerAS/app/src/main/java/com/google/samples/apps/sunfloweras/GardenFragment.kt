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

// Строит фрагмент садика на первом экране когда позовет ViewPager2
class GardenFragment : Fragment() {

    private lateinit var binding: FragmentGardenBinding

    // Разбирать как строится viewModel:
    private val viewModel: GardenPlantingListViewModel by viewModels {
        InjectorUtils.provideGardenPlantingListViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGardenBinding.inflate(inflater, container, false)
        val adapter = GardenPlantingAdapter()
        binding.gardenList.adapter = adapter

        binding.addPlant.setOnClickListener {
            navigateToPlantListPage()
        }

        // включить верхнее меню во фрагменте - тогда вызовется onCreateOptionsMenu и onOptionsItemSelected
        setHasOptionsMenu(true)

        subscribeUi(adapter, binding)
        return binding.root
    }
    // Устанавливается значек(меню) фильтрации - рисование значка в квадратике справа наверху
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_plant_list, menu)
    }
    // реакция на нажатие на значк в квадратике справа наверху
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

    private fun subscribeUi(adapter: GardenPlantingAdapter, binding: FragmentGardenBinding) {
        viewModel.plantAndGardenPlantings.observe(viewLifecycleOwner) { result ->
            binding.hasPlantings = !result.isNullOrEmpty()
            adapter.submitList(result)
        }
    }

    // TODO: convert to data binding if applicable
    private fun navigateToPlantListPage() {
        requireActivity().findViewById<ViewPager2>(R.id.view_pager).currentItem =
            PLANT_LIST_PAGE_INDEX
    }
}
