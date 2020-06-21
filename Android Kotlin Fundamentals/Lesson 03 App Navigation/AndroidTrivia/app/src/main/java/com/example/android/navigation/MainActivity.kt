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
 */

package com.example.android.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.ActivityMainBinding

//import com.example.android.navigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout   // бутерброд
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //@Suppress("UNUSED_VARIABLE")  // для датабиндинга через layout:
        //val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
                val binding = ActivityMainBinding.inflate(layoutInflater) // ок работает
                setContentView(binding.root)    //  viewBinding = true без layout 


        // В следующих шагах вы используете контроллер навигации, чтобы добавить кнопку Up (<-) в свое приложение:
        // 1. Свяжите NavController с панелью действий с помощью NavigationUI.setupWithNavController.
        // код, чтобы найти объект контроллера навигации:  (используя функцию расширения KTX)
        val navController = this.findNavController(R.id.myNavHostFragment)
        drawerLayout = binding.drawerLayout

        //код, чтобы связать контроллер навигации с панелью приложения:
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        // Вы можете предотвратить перемещение ящика в любое место, кроме startDestination - необязательно
        // prevent nav gesture if not on start destination
        // запретить навигационный жест, если он не включен в пункт назначения запуска
        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, _: Bundle? ->
            if (nd.id == nc.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
        // Вот и все! Наличие таких слушателей делает возможным делать много интересных вещей,
        // когда пользователь перемещается, и хранит код в одном месте приложения.

        // Свяжите NavController с нашей панелью действий.
        // код, который позволяет пользователю отображать навигационную панель:
        NavigationUI.setupWithNavController(binding.navView, navController)

        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout) // где используется - неясно?
    }
    // 2. Переопределите метод onSupportNavigateUp из действия и вызовите navigateUp в контроллере nav.
    //метод для вызова navigateUp()в контроллере навигации: (кстати ctrl/O - список методов для переопределения)
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
        //return navController.navigateUp()
    }
}
