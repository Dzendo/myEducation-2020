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

package com.google.samples.apps.sunfloweras

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.google.samples.apps.sunfloweras.adapters.MY_GARDEN_PAGE_INDEX
import com.google.samples.apps.sunfloweras.adapters.PLANT_LIST_PAGE_INDEX
import com.google.samples.apps.sunfloweras.adapters.SunflowerPagerAdapter
import com.google.samples.apps.sunfloweras.databinding.FragmentViewPagerBinding

class HomeViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,   // Надуватель
        container: ViewGroup?,      // Контейнер фрагмента который будем надувать
        savedInstanceState: Bundle? // передаваемые аргументы
    ): View? {   // возвращает View?, т.е. binding.root, который надо засветить в этом фрагменте

        // Каким-то загадочным образом надуваем в binding layout/fragment_view_pager.xml сразу
        val binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        // равнозначно записи ниже с явным указанием XML, но короче через Binding
        //val binding: FragmentViewPagerBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_view_pager, container, false)

        val tabLayout = binding.tabs        // Полоса для переключения под Sunflower
        val viewPager = binding.viewPager   // сам квадрат внизу-поле для высветки фрагментов

        // Используется Скольжение между фрагментами с помощью ViewPager2 - подключенная библиотека androidx
        // устанавливается для viewPager.adapter = SunflowerPagerAdapter(этого фрагмента) а это  ViewPager2

        viewPager.adapter = SunflowerPagerAdapter(this)

        // Set the icon and text for each tab - Установите значок и текст для каждой вкладки
        // в tabLayout загоняется Lambda установки  Иконки и подпись на место tab в зависимости от position
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()
        // т.е. приатачивается к TabLayout станд. библ. функция Mediator и ей декларируется что переключать и как
        // Когда там где-то под капотом будет переключаться viewPager 0-1 то по вызову сюда будет присваиваться
        // полям Tab разные значения: Один раз установили и забыли - дальше она сама все вызовет

        // Установка toolbar вверху экрана(activity) с названием программы посередине из XML toolbar
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        return binding.root
    }
    // возвращается ссылка на иконку 0-цветочек или 1-листочки
    private fun getTabIcon(position: Int): Int {
        return when (position) {
            MY_GARDEN_PAGE_INDEX -> R.drawable.garden_tab_selector
            PLANT_LIST_PAGE_INDEX -> R.drawable.plant_list_tab_selector
            else -> throw IndexOutOfBoundsException()
        }
    }
    // возвращается слово подписи 0 - MY GARDEN или 1 - PLANT LIST
    private fun getTabTitle(position: Int): String? {
        return when (position) {
            MY_GARDEN_PAGE_INDEX -> getString(R.string.my_garden_title)
            PLANT_LIST_PAGE_INDEX -> getString(R.string.plant_list_title)
            else -> null
        }
    }
}