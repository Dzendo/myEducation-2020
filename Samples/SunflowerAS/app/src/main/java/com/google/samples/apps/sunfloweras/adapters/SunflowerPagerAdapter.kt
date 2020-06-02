/*
 * Copyright 2019 Google LLC
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

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.samples.apps.sunfloweras.GardenFragment
import com.google.samples.apps.sunfloweras.PlantListFragment

const val MY_GARDEN_PAGE_INDEX = 0
const val PLANT_LIST_PAGE_INDEX = 1
// Это на основе стандартного адаптера из androidx.viewpager2.adapter строит в фрагменте this
class SunflowerPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     * Сопоставление индексов страниц ViewPager с их соответствующими фрагментами
     */
    // Вызывается и выполняется лямбда ниже в createFragment т.е. зовется нужный[0-1] фрагмент
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        MY_GARDEN_PAGE_INDEX to { GardenFragment() },
        PLANT_LIST_PAGE_INDEX to { PlantListFragment() }
    )
    // стандарт - задается сколько экранов может меняться (в данном случае - 2)
    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
     // При построении фрагмента this именно отсюда вызовется (.invoke() - вызов выполнения лямбды)
     // GardenFragment() - вид садика
     // PlantListFragment() - вид магазина
     // подписи и иконки наверху нарисуются ранее через Mediator вызывающей программе HomeViewPagerFragment
    }
}