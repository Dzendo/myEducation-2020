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

package com.example.android.hilt.di

import com.example.android.hilt.navigator.AppNavigator
import com.example.android.hilt.navigator.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * MainActivity получает экземпляр AppNavigator от ServiceLocator вызова provideNavigator(activity: FragmentActivity) функции.
 * Поскольку AppNavigator это интерфейс, мы не можем использовать внедрение конструктора.
 * Чтобы сообщить Hilt, какую реализацию использовать для интерфейса, вы можете использовать @Binds аннотацию к функции внутри модуля Hilt .
 * @Binds должен аннотировать абстрактную функцию
 * (поскольку она абстрактная, она не содержит кода, и класс тоже должен быть абстрактным).
 * Тип возврата абстрактной функции - это интерфейс, для которого мы хотим реализовать (т.е. AppNavigator).
 * Реализация указывается путем добавления уникального параметра с типом реализации интерфейса
 * (т.е. AppNavigatorImpl).
 * Можем ли мы добавить информацию в DatabaseModule созданный ранее класс или нам нужен новый модуль?
 * Есть несколько причин, по которым мы должны создать новый модуль:
 * Для лучшей организации имя модуля должно отражать тип предоставляемой информации.
 * Например, не имеет смысла включать привязки навигации в модуль с именем DatabaseModule.
 * DatabaseModule Модуль установлен в ApplicationComponent, так что переплеты доступны в прикладном контейнере.
 * Наша новая навигационная информация (т.е. AppNavigator) нуждается в информации,
 * специфичной от деятельности (как AppNavigatorImplимеет Activity как зависимость).
 * Следовательно, он должен быть установлен в Activity контейнере, а не в Application контейнере,
 * поскольку именно там Activityдоступна информация о .
 * Модули Hilt не могут содержать как нестатические, так и абстрактные методы привязки,
 * поэтому вы не можете размещать аннотации @Binds и @Provides аннотации в одном классе.
 */


@InstallIn(ActivityComponent::class)
@Module
abstract class NavigationModule {

    // Внутри модуля мы можем добавить привязку для AppNavigator.
    // Это абстрактная функция, которая возвращает интерфейс, о котором мы информируем Hilt (т.е. AppNavigator),
    // а параметр - реализация этого интерфейса (т.е. AppNavigatorImpl).
    @Binds
    abstract fun bindNavigator(impl: AppNavigatorImpl): AppNavigator
}

// Теперь мы должны сообщить Hilt, как предоставлять экземпляры AppNavigatorImpl.
// Поскольку этот класс может быть внедрен в конструктор, мы просто аннотируем его конструктор @Inject.
