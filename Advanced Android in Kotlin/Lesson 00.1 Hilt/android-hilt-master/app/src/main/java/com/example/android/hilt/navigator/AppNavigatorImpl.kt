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

package com.example.android.hilt.navigator

import androidx.fragment.app.FragmentActivity
import com.example.android.hilt.R
import com.example.android.hilt.ui.ButtonsFragment
import com.example.android.hilt.ui.LogsFragment
import javax.inject.Inject

/**
 * Navigator implementation.
 * Реализация навигатора.
 * Теперь мы должны сообщить Hilt, как предоставлять экземпляры AppNavigatorImpl.
 * Поскольку этот класс может быть внедрен в конструктор,
 * мы просто аннотируем его конструктор @Inject.
 */


class AppNavigatorImpl @Inject constructor (private val activity: FragmentActivity) : AppNavigator {

    override fun navigateTo(screen: Screens) {
        val fragment = when (screen) {
            Screens.BUTTONS -> ButtonsFragment()
            Screens.LOGS -> LogsFragment()
        }

        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .addToBackStack(fragment::class.java.canonicalName)
            .commit()
    }
}
/*
AppNavigatorImpl зависит от FragmentActivity.
Поскольку AppNavigator экземпляр предоставляется в Activity контейнере
(он также доступен в Fragment контейнере и в View контейнере,
 поскольку NavigationModule он установлен в ActivityComponent),
FragmentActivity он уже доступен, поскольку он поставляется как предопределенная привязка .
 */
/*
Откройте MainActivity.kt файл и сделайте следующее:

Аннотировать navigator поле с @Inject помощью Hilt,
Удалите private модификатор видимости и
Удалите navigator код инициализации в onCreate функции.
 */

