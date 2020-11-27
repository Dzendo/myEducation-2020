/*
 * Copyright (C) 2020 The Android Open Source Project
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

package com.example.android.hilt.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.hilt.LogApplication
import com.example.android.hilt.R
import com.example.android.hilt.navigator.AppNavigator
import com.example.android.hilt.navigator.Screens
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Main activity of the application.
 * Основная деятельность приложения.
 *
 * Container for the Buttons & Logs fragments. This activity simply tracks clicks on buttons.
 * Контейнер для фрагментов кнопок и журналов.
 * Это упражнение просто отслеживает нажатия на кнопки.
 */
/**
 * Теперь у Hilt есть вся необходимая информация для внедрения экземпляров LogsFragment.
 * Тем не менее, перед запуском приложения, Эфес должен быть в курсе того, Activity что Саваофа Fragment ля работы.
 * Нам нужно аннотировать MainActivity с помощью @AndroidEntryPoint.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var navigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Удалите navigator код инициализации в onCreate функции.
       // navigator = (applicationContext as LogApplication).serviceLocator.provideNavigator(this)

        if (savedInstanceState == null) {
            navigator.navigateTo(Screens.BUTTONS)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }
    }
}

/*
Обратите внимание, что экземпляр LoggerLocalDataSource будет таким же, как и тот, который мы использовали,
 LogsFragment поскольку тип ограничен контейнером приложения.
  Однако экземпляр AppNavigator будет отличаться от экземпляра,
   MainActivity поскольку мы не ограничили его соответствующим Activity контейнером.
 */
