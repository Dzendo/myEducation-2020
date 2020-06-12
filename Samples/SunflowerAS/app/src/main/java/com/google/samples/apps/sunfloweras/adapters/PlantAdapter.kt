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

package com.google.samples.apps.sunfloweras.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.samples.apps.sunfloweras.HomeViewPagerFragmentDirections
import com.google.samples.apps.sunfloweras.PlantListFragment
import com.google.samples.apps.sunfloweras.data.Plant
import com.google.samples.apps.sunfloweras.databinding.ListItemPlantBinding

/**
 * Adapter for the [RecyclerView] in [PlantListFragment].
 */
// из PlantListFragment для RecyclerView 07,1-5
//  Android Kotlin Fundamentals 07.2.6: DiffUtil and data binding with RecyclerView
// TODO:  6. Task: Use DataBinding with RecyclerView
class PlantAdapter : ListAdapter<Plant, RecyclerView.ViewHolder>(PlantDiffCallback()) {

    // TODO: convert to data binding if applicable -- преобразование в привязку данных, если это применимо
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlantViewHolder(ListItemPlantBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    // TODO: convert to data binding if applicable -- преобразование в привязку данных, если это применимо
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val plant = getItem(position)
        (holder as PlantViewHolder).bind(plant)
    }

    // наверно это реагирование на нажатие на карточке -> переход к деталям растения на 2-ой экран
    // т.е. переопределяется data переменная setClickListener из layout/list_item_plant.xml
    // TODO: convert to data binding if applicable -- преобразование в привязку данных, если это применимо
    class PlantViewHolder(
        private val binding: ListItemPlantBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.plant?.let { plant ->
                    navigateToPlant(plant, it)
                }
            }
        }
        //  это реагирование на нажатие на карточке -> переход к деталям растения на 2-ой экран
        private fun navigateToPlant(
            plant: Plant,
            view: View
        ) {
            val direction =
                HomeViewPagerFragmentDirections.actionViewPagerFragmentToPlantDetailFragment(
                    plant.plantId
                )
            view.findNavController().navigate(direction)
        }

        // TODO: convert to data binding if applicable -- преобразование в привязку данных, если это применимо
        fun bind(item: Plant) {
            binding.apply {
                plant = item
                executePendingBindings()
            }
        }
    }
}
//Когда DiffUtil выясняется, что изменилось, RecyclerView можно использовать эту информацию для обновления только тех элементов,
// которые были изменены, добавлены, удалены или перемещены, что гораздо эффективнее, чем повторение всего списка.
private class PlantDiffCallback : DiffUtil.ItemCallback<Plant>() {
    // Эти функции генерируются как обязательные для DiffUtil:
    override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
        return oldItem.plantId == newItem.plantId
    }
    // DiffUtil использует этот тест, чтобы определить, был ли элемент добавлен, удален или перемещен.
    override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
        return oldItem == newItem
        //  Эта проверка на равенство проверит все поля, потому что plant это класс данных.
        //  Data классы автоматически определяют equals и несколько других методов для вас.
        //  Если между oldItem и есть различия newItem, этот код сообщает, DiffUtil что элемент обновлен
    }
}