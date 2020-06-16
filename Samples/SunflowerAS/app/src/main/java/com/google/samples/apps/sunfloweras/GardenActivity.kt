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
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager

/*import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
*/
import com.google.samples.apps.sunfloweras.databinding.ActivityGardenBinding
import com.google.samples.apps.sunfloweras.workers.SeedDatabaseWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


// Стартовый класс sunflower
class GardenActivity : AppCompatActivity() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delayedInit()
        setContentView<ActivityGardenBinding>(this,R.layout.activity_garden) // Tien

        /* из документации создание binding AS
        val binding1: ActivityGardenBinding = ActivityGardenBinding.inflate(layoutInflater)  // статических методов
        setContentView(binding1.root)
        val binding2: ActivityGardenBinding = ActivityGardenBinding.inflate(layoutInflater, viewGroup, false) // ViewGroup объект в дополнение
        val viewRoot = LayoutInflater.from(this).inflate(R.layout.activity_garden, parent, false)
        val binding3: ActivityGardenBinding = ActivityGardenBinding.bind(viewRoot) // макет был накачан - связать отдельно
        val binding4: ViewDataBinding? = DataBindingUtil.bind(viewRoot)  //тип привязки не известен заранее

        val listItemBinding1 = ListItemBinding.inflate(layoutInflater, viewGroup, false)
        // or for Fragment, ListView, or RecyclerView adapter
        val listItemBinding2 = DataBindingUtil.inflate(layoutInflater, R.layout.list_item, viewGroup, false)
        // <data class="ContactItem"> дать свое имя и разместить в тек пакете
         */
    }
    // Запланировать WorkManager по лекции  Android Kotlin Основы 09.2: WorkManager
    private fun delayedInit() {
        applicationScope.launch {
            Log.w(SeedDatabaseWorker.TAG,"Запланирована")
            setupRecurringWork()
        }
    }
    /**
     * Setup WorkManager background job to 'fetch' new network data daily.
     */

    private fun setupRecurringWork() {  // настройка повторяющейся работы

       /* val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresCharging(true)
                .setRequiresBatteryNotLow(true)
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        setRequiresDeviceIdle(true)
                    }
                }
                .build()
        */
        // Запланируется работа для WorkManager SunFlower
        val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
        WorkManager.getInstance(applicationContext).enqueue(request)

        val repeatingRequest = PeriodicWorkRequestBuilder<SeedDatabaseWorker>(15, TimeUnit.MINUTES)
               // (1, TimeUnit.DAYS)
               // .setConstraints(constraints)
                .build()

        //Timber.d("WorkManager: Periodic Work request for sync is scheduled")
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
                SeedDatabaseWorker.TAG,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingRequest)
    }
}
/*  в activity_garden.xml AS использовал по своему разумению:
   <androidx.drawerlayout.widget.DrawerLayout   вставил из урока 3
        <androidx.fragment.app.FragmentContainerView   вставил по рекомендации AS
        Все работает также. Надо бы прочитать и утвердить что всегда так использовать
 */

/*
Вызывается просто стартовый Layout activity_garden для навигации и все
Здесь самая сокращенная запись от:
private lateinit var binding: ActivityGardenBinding
 override fun onCreate
binding = DataBindingUtil.setContentView (this, R.layout.activity_main)
Можно напихать слушателей но не надо, лучше в фрагменты
 */


//Не работает - вылетает из-за темы и ViewPager2
/*class GardenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGardenBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView (this, R.layout.activity_garden)
        navController = this.findNavController(R.id.nav_host)
        navController = Navigation.findNavController(this, R.id.nav_host)
      //  NavigationUI.setupActionBarWithNavController(this,navController)
// Это вообще-то стандарт для хождения назад по навигации из лекции, но не работает здесь

        //setContentView<ActivityGardenBinding>(this,R.layout.activity_garden)

    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host)
        return navController.navigateUp()
    }
}*/
