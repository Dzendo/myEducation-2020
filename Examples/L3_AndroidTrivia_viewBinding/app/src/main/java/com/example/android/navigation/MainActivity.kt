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
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.android.navigation.databinding.ActivityMainBinding
//import kotlinx.android.synthetic.main.activity_main.*

/**
 * LAUNCHER ViewBinding Save-args drawerLayout
 * Организует в XML место для фрагиентов навигации
 * CTRL/O - все ovveride fun
 *
 * Options Menus
 * Bottom Navigation
 * Navigation View
 * Navigation Drawer
 *
 * Action Bar
 * Toolbar
 * Collapsing Toolbar
 *
 */

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // ок работает
        setContentView(binding.root)    //  viewBinding = true без layout
        navController = this.findNavController(R.id.myNavHostFragment)// Переделать в Binding если возможно
        NavigationUI.setupWithNavController(binding.navdrawerNavView, navController) // Запускает в работу переход по пунктам шторки по именам
        NavigationUI.setupActionBarWithNavController(this,navController, binding.navdrawerLayout)
        // первый аргумент ставит navController в BAR, а второй ставит наверх бутерброд и <--
        // запретить навигационный жест, если он не включен в пункт назначения запуска
        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, _: Bundle? ->
            if (nd.id == nc.graph.startDestination)
                binding.navdrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            else
                binding.navdrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }
        // Ставит нижнее меню с тремя фрагментами и больше ничего не надо - все работает
        binding.bottomNavView.setupWithNavController(navController)
    }
    // Включает работу бутерброда и <-- (без этого светятся, но не работают)
    override fun onSupportNavigateUp(): Boolean = NavigationUI.navigateUp(navController, binding.navdrawerLayout)
}
// Если с бутербродом ничего не делать - шторка слева будет выдвигаться все равно

/*
class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout   // бутерброд
    //private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //@Suppress("UNUSED_VARIABLE")  // для датабиндинга через layout:
        //val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
                val binding = ActivityMainBinding.inflate(layoutInflater) // ок работает
                setContentView(binding.root)    //  viewBinding = true без layout

        // В следующих шагах вы используете контроллер навигации, чтобы добавить кнопку Up (<-) в свое приложение:
        // 1. Свяжите NavController с панелью действий с помощью NavigationUI.setupWithNavController.
        // код, чтобы найти объект контроллера навигации:  (используя функцию расширения KTX)
        navController = this.findNavController(R.id.myNavHostFragment)
        drawerLayout = binding.drawerLayout

        //код, чтобы связать контроллер навигации с панелью приложения:
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        // Вы можете предотвратить перемещение ящика в любое место, кроме startDestination - необязательно
        // prevent nav gesture if not on start destination Запретить выдвижку везде кроме Start
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

        // Параметры конфигурации для NavigationUI методов,
        // которые взаимодействуют с реализациями шаблон панели приложений,
        // такие какToolbar, CollapsingToolbarLayout, и ActionBar.
        // РАБОТАЕТ БЕЗ ЭТОГО ВЫЗОВА похоже для NavigationUI.setupWithNavController он не нужен - нужен при спецефическом graf
        //appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout) // где используется - неясно?

        // Свяжите NavController с нашей панелью действий.
        // код, который позволяет пользователю отображать навигационную панель:
        NavigationUI.setupWithNavController(binding.navView, navController)

    }
    // 2. Переопределите метод onSupportNavigateUp из действия и вызовите navigateUp в контроллере nav.
    //метод для вызова navigateUp()в контроллере навигации: (кстати ctrl/O - список методов для переопределения)
    override fun onSupportNavigateUp(): Boolean {
       // navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
        //return navController.navigateUp()
    }
}
*/

/*
 val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
 */