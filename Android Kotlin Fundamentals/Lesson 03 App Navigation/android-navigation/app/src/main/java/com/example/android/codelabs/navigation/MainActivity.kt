/*
 * Copyright (C) 2018 The Android Open Source Project
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

package com.example.android.codelabs.navigation

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

/**
 * A simple activity demonstrating use of a NavHostFragment with a navigation drawer.
 * Простое упражнение, демонстрирующее использование NavHostFragment с навигационным ящиком.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_activity)

        // Устанавливается стандартный бар из XML
        // <androidx.appcompat.widget.Toolbar
        //            android:id="@+id/toolbar"
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Связывается NavHostFragment с ( вытаскивается ссылка на фрагмент и его supportFragmentManager
        //          <fragment
        //            android:id="@+id/my_nav_host_fragment"
        val host: NavHostFragment = supportFragmentManager
                .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return

        // Set up Action Bar Настройка панели действий
        // вытаскиваем ссылку на navController с NavHostFragment
        val navController = host.navController

        //appBarConfiguration = AppBarConfiguration(navController.graph)
        // коммент т.к. ниже такой же но с добавленной анимацией

        // TODO STEP 9.5 - Create an AppBarConfiguration with the correct top-level destinations
        // TODO шаг 9.5-создание конфигурации Панели приложений с правильными назначениями верхнего уровня
        // You should also remove the old appBarConfiguration setup above
        // Вы также должны удалить старую настройку конфигурации панели приложений выше
        // устанавливается бутерброд со стелками DrawerLayout? и верхними франментами навигации
        val drawerLayout : DrawerLayout? = findViewById(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(
                setOf(R.id.home_dest, R.id.deeplink_dest),
                drawerLayout)
        // TODO END STEP 9.5
        // устанавливается бутерброд со стелками DrawerLayout? и верхними франментами навигации
        setupActionBar(navController, appBarConfiguration)

        setupNavigationMenu(navController)

        setupBottomNavMenu(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                Integer.toString(destination.id)
            }

            Toast.makeText(this@MainActivity, "Navigated to $dest",
                    Toast.LENGTH_LONG).show()
            Log.d("NavigationActivity", "Navigated to $dest")
        }
    }

    private fun setupBottomNavMenu(navController: NavController) {
        // TODO STEP 9.3 - Use NavigationUI to set up Bottom Nav
       // Чтобы выполнить шаг 9.3 - используйте навигационный интерфейс для настройки нижней навигации
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav?.setupWithNavController(navController)
        // TODO END STEP 9.3
    }

    private fun setupNavigationMenu(navController: NavController) {
        // TODO STEP 9.4 - Use NavigationUI to set up a Navigation View
        // Чтобы выполнить шаг 9.4 - используйте навигационный интерфейс для настройки навигационного представления
        // In split screen mode, you can drag this view out from the left
        // This does NOT modify the actionbar
       // В режиме разделенного экрана вы можете перетащить этот вид слева
      // Это не изменяет панель действий
        val sideNavView = findViewById<NavigationView>(R.id.nav_view)
        sideNavView?.setupWithNavController(navController)
        // Обратите внимание, что эта версия метода принимает а, NavigationView а не BottomNavigationView.
        // TODO END STEP 9.4
    }

    private fun setupActionBar(navController: NavController,
                               appBarConfig : AppBarConfiguration) {
        // TODO STEP 9.6 - Have NavigationUI handle what your ActionBar displays
        // Чтобы сделать шаг 9.6-пусть навигационный интерфейс обрабатывает то, что отображается на панели действий
        // This allows NavigationUI to decide what label to show in the action bar
        // By using appBarConfig, it will also determine whether to
        // show the up arrow or drawer menu icon
        // Это позволяет навигационному интерфейсу решать, какую метку отображать на панели действий
        // Используя конфигурацию appBar, он также определит, следует ли
        // показать стрелку вверх или значок меню ящика
        setupActionBarWithNavController(navController, appBarConfig)
        // TODO END STEP 9.6
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val retValue = super.onCreateOptionsMenu(menu)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        // The NavigationView already has these same navigation items, so we only add
        // navigation items to the menu here if there isn't a NavigationView
        // Навигационное представление уже имеет эти же навигационные элементы, поэтому мы только добавляем
        // навигационные элементы в меню здесь, если нет навигационного представления
        if (navigationView == null) {
            menuInflater.inflate(R.menu.overflow_menu, menu)
            return true
        }
        return retValue
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //return super.onOptionsItemSelected(item)
        // TODO STEP 9.2 - Have Navigation UI Handle the item selection - make sure to delete
        //  the old return statement above
        // убедитесь, что вы удалили старый оператор возврата

        // Have the NavigationUI look for an action or destination matching the menu
        // item id and navigate there if found.
        // Otherwise, bubble up to the parent.
        // Пусть навигационный интерфейс ищет действие или пункт назначения, соответствующие меню
        // идентификатор элемента и перейдите туда, если он найден.
        // В противном случае, пузырь до родителя.
        return item.onNavDestinationSelected(findNavController(R.id.my_nav_host_fragment))
                || super.onOptionsItemSelected(item)
        // TODO END STEP 9.2
    }

    // TODO STEP 9.7 - Have NavigationUI handle up behavior in the ActionBar
    // Чтобы выполнить шаг 9.7-пусть пользовательский интерфейс навигации обрабатывает поведение в панели действий
    override fun onSupportNavigateUp(): Boolean {
        // Allows NavigationUI to support proper up navigation or the drawer layout
        // drawer menu, depending on the situation
        return findNavController(R.id.my_nav_host_fragment).navigateUp(appBarConfiguration)
        // import androidx.navigation.ui.navigateUp
    }
    // TODO END STEP 9.7
}
