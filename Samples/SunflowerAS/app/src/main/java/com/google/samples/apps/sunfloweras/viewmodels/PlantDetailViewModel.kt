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

package com.google.samples.apps.sunfloweras.viewmodels

import androidx.lifecycle.*
import com.google.samples.apps.sunfloweras.PlantDetailFragment
import com.google.samples.apps.sunfloweras.data.GardenPlantingRepository
import com.google.samples.apps.sunfloweras.data.PlantRepository
import kotlinx.coroutines.launch

/**
 * The ViewModel used in [PlantDetailFragment].
 */
class PlantDetailViewModel(
    val plantRepository: PlantRepository,
    private val gardenPlantingRepository: GardenPlantingRepository,
    private val plantId: String
) : ViewModel(){

    val isPlanted = gardenPlantingRepository.isPlanted(plantId)
    val plant = plantRepository.getPlant(plantId)

    var isAddDelete: Boolean = true
    fun doAddDeletePlantToGarden() {
        if (!isAddDelete)
            viewModelScope.launch {
                gardenPlantingRepository.createGardenPlanting(plantId)
            }
        else
            viewModelScope.launch {
            val gardenPlanting = gardenPlantingRepository.getGardenPlanting(plantId)
                gardenPlantingRepository.removeGardenPlanting(gardenPlanting)
            }
    }
}
