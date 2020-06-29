Android Architecture Components Advanced Navigation Sample
Пример Расширенной Навигации Компонентов Архитектуры Android
==============================================

### Features Особенности

В этом примере показано поведение Нижнего навигационного вида, следующего за
This sample showcases the behavior of a bottom navigation view following the [Principles of
Navigation](https://developer.android.com/topic/libraries/architecture/navigation#fixed).

 * Fixed start destination
 * Navigation state should be represented via a stack of destinations
 * The Up button never exits your app
 * Up and Back are identical within your app's task
 * Deep linking and navigating to a destination should yield the same stack

 * Фиксированный пункт назначения запуска
 * Состояние навигации должно быть представлено в виде стека пунктов назначения.
 * Кнопка вверх никогда не выходит из вашего приложения
 * Вверх и назад идентичны в рамках задачи вашего приложения
 * Глубокое связывание и навигация к месту назначения должны давать один и тот же стек

Check out the Проверьте это
[UI tests](https://github.com/googlesamples/android-architecture-components/tree/master/NavigationAdvancedSample/app/src/androidTest/java/com/example/android/navigationadvancedsample)
to learn about specific scenarios. чтобы узнать о конкретных сценариях.

Перевел все комментарии и Strings на русский под локализацию
Первое что вижу:
- Binding ек используется - нет в Gradle binding = yes
- SaveArgs не используется  - нет плагина

Main.kt
Не нужно звать заполнения после востановления - ничего не восстанавливается
Только как кривая заготовка сохранения - восстановления параметров программы
Основной фрагмент куда загржают в layout/activity_main.xml не имеет Он не содержит navconroller and Host
Видимо управляется setupBottomNavigationBar из которого грузится три различных Navigation
Start: MainActivity + activity_main + setupBottomNavigationBar (своя) + Observe(liveDatacontroller)) с трудом
Кривота казанская - не демонстрирует ничего кроме умения товарища выкручиваться нестандартными методами
Насоздана куча функций и ими заткнуты стандартные переходы
LiveData притыркнута чтобы была - видимо требовали с него
Сделан пример 16 месяцев назад - начало 2019 года
Делался с 2017 года
Это не лучшие а ъудшие практики по архитектуре
причем на этом же идет частично описание дока на навигацию
НЕ использовать как образец
Можно использовать как прикол выкручивания
Пои этом обновлены библиотеки до Android Studio 4.0.0 т.е. июнь 2020 года








License
-------

Copyright 2018 The Android Open Source Project, Inc.

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.
