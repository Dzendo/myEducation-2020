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
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.google.samples.apps.sunfloweras.adapters.MY_GARDEN_PAGE_INDEX
import com.google.samples.apps.sunfloweras.adapters.PLANT_LIST_PAGE_INDEX
import com.google.samples.apps.sunfloweras.adapters.SunflowerPagerAdapter
import com.google.samples.apps.sunfloweras.databinding.FragmentViewPagerBinding

/**
Вызывается NavHostFragment как стартовый nav_garden.xml
Работает через viewPager2 - грузит fragment_view_pager.xml
Меняет через адаптер adapters/SunflowerPagerAdapter.kt два фрагмента:
GardenFragment() - вид садика
PlantListFragment() - вид магазина
ViewPager2 построен на компоненте RecyclerView
 вызов Деталей производит
 Возврат назад производит
 */
class HomeViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,   // Надуватель
        container: ViewGroup?,      // Контейнер фрагмента который будем надувать
        savedInstanceState: Bundle? // передаваемые аргументы (здесь нет)
    ): View? {   // возвращает View?, т.е. binding.root, который надо засветить в этом фрагменте

        // Каким-то загадочным образом надуваем в binding layout/fragment_view_pager.xml сразу
        val binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        // равнозначно записи ниже с явным указанием XML, но короче через Binding
        //val binding: FragmentViewPagerBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_view_pager, container, false)

        val tabLayout = binding.tabs        // Полоса для переключения под SunflowerAS для пиктушек, названий и подчеркивания
        val viewPager = binding.viewPager   // сам квадрат внизу-поле для высветки фрагментов типа ViewPager2

        // Используется Скольжение между фрагментами с помощью ViewPager2 - подключенная библиотека androidx
        // устанавливается для viewPager.adapter = SunflowerPagerAdapter(этого фрагмента) а это  ViewPager2

        // Адптеру поля типа ViewPager2 устанавливается "наш" адаптер SunflowerPagerAdapter - в нем две страницы-фрагмента
        viewPager.adapter = SunflowerPagerAdapter(this)
        // далее ViewPager2 сам будет следить и переключать нужную страницу

        // Set the icon and text for each tab - Установите значок и текст для каждой вкладки
        // com.google.android.material.tabs.TabLayoutMediator - Посредник для связывания TabLayout с ViewPager2
        // в tabLayout загоняется Lambda установки  Иконки и подпись на место tab в зависимости от position
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->   // т.е. ему передается binding.tabs и binding.viewPager он их связывает
            tab.setIcon(getTabIcon(position))       // Иконка загоняется в tabs т.е. в спец  поле в xml
            tab.text = getTabTitle(position)        // слова тоже в com.google.android.material.tabs.TabLayout
        }.attach()
        // т.е. приатачивается к TabLayout станд. библ. функция Mediator и ей декларируется что переключать и как
        // Когда там где-то под капотом будет переключаться viewPager 0-1 то по вызову сюда будет присваиваться
        // полям Tab разные значения: Один раз установили и забыли - дальше она сама все вызовет

        // Установка toolbar вверху экрана(activity) с названием программы посередине из XML toolbar
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        // сам toolbar указан в XML и там же стоит что он будет сворачивания панелей инструментов, CollapsingToolbarLayout

        // Когда все-все снарядили своей xml то и передали на высветку навхосту а он высветит в нужном месте
        // когда начнет высвечивать, но придется вызвать адаптер, а он позовет нужный фрвгмент и будет счастье
        return binding.root
    }
    // возвращается ссылка на иконку 0-цветочек или 1-листочки
    private fun getTabIcon(position: Int): Int {
        return when (position) {
            MY_GARDEN_PAGE_INDEX -> R.drawable.garden_tab_selector      // переключатель xml яркий/неяркий цветочек
            PLANT_LIST_PAGE_INDEX -> R.drawable.plant_list_tab_selector // переключатель xml яркий/неяркий листочек
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