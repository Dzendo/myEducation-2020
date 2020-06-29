/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigationadvancedsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * An activity that inflates a layout that has a [BottomNavigationView].
 * Activity, которое раздувает макет с [Нижним навигационным видом].
 */
class MainActivity : AppCompatActivity() {

    // ВНИМАНИЕ! Контроллер объвляется LiveData
    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {   // т.е. при новом старте:
            setupBottomNavigationBar()
        } // Else, нужно дождаться need to wait for onRestoreInstanceState
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        // Теперь эта нижняя панель навигации восстановила свое состояние экземпляра
        // и его selectedItemId, мы можем приступить к настройке
        // Нижняя панель навигации с навигацией
        // т.е. после восстановления после прибивания:
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     * Вызывается при первом создании и при восстановлении состояния.
     * восстанавливает ссылку на нижнее меню material.bottomnavigation.BottomNavigationView
     * восстанавливает navGraphIds список фрагментов из файла навигации navigation.form - регистрация
     * Создает на него controller для нижней навигации
     */
    private fun setupBottomNavigationBar() {
        // Это view material.bottomnavigation.BottomNavigationView откуда просто зовется
        // menu/bottom_nav.xml где и есть три статичных пункта - первый из них и стартует
        // думаю что этот контроллер и включает Home как первый Navigation start фрагмент ????
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)

        val navGraphIds = listOf(R.navigation.home, R.navigation.list, R.navigation.form)


        // Setup the bottom navigation view with a list of navigation graphs
        // Настройка Нижнего навигационного представления со списком навигационных графиков
        // Этот контроллер заведует нижним переключением
        val controller = bottomNavigationView.setupWithNavController(  // Это Своя функция отдает LiveData<NavController>
            navGraphIds = navGraphIds,   // т.е. ходить будем по разным навигациям
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,  // основной контейнер для фрагментов в Main
            intent = intent                         // Он не содержит navconroller and Host
        )
        //  Стандартно бывает , но не здесь а здесь нижний BAR из трех navigation
        //  android:name="androidx.navigation.fragment.NavHostFragment"
        //  app:defaultNavHost="true"
        //  app:navGraph="@navigation/mobile_navigation"

        // Этот слушатель должен менять Контроллнры разных навигационных XML
        // Whenever the selected controller changes, setup the action bar.
        // Всякий раз, когда выбранный контроллер изменяется, настройте панель действий.
        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)
        })
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
}
