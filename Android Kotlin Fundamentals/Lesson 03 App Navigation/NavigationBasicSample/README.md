Android Architecture Components Navigation Basic Sample
Базовый Образец Навигации Компонентов Архитектуры Android
==============================================

### Features Особенности

This sample showcases the following features of the Navigation component:
В этом примере показаны следующие функции навигационного компонента:

 * Navigating via actions
 * Transitions
 * Popping destinations from the back stack
 * Arguments (profile screen receives a user name)
 * Deep links (`www.example.com/user/{user name}` opens the profile screen)

 * Навигация с помощью действий
 * Монтажные переходы
 * Выскакивание пунктов назначения из задней стопки
 * Аргументы (экран профиля получает имя пользователя)
 * Глубокие ссылки (`www.example.com/user/{имя пользователя} ' открывает экран профиля)

### Screenshots Скриншоты
<img src="screenshot.png" height="400" alt="Screenshot"/> 

### Other Resources другие ресурсы

 * Particularly In Java, consider using `Navigation.createNavigateOnClickListener()` to quickly
 create click listeners.
 * Особенно в Java, рассмотрите возможность использования ' навигации.создайте Navigate OnClickListener ()`, чтобы быстро
 создать команду слушателей.
 * Consider including the [Navigation KTX libraries](https://developer.android.com/topic/libraries/architecture/adding-components#navigation)
  for more concise uses of the Navigation component. For example, calls to `Navigation.findNavController(view)` can
 be expressed as `view.findNavController()`.
 * Рассмотрите возможность включения [навигационных библиотек KTX](https://developer.android.com/topic/libraries/architecture/adding-components#navigation)
 для более краткого использования навигационного компонента. Например, вызовы к ' навигации.найти NavController (view)` can
 быть выраженным как " точка зрения.найдите NavController ()`.

### Tests Тесты

This sample contains UI tests that can be run on device (or emulator) or on the host
(as a JVM test, using Robolectric). As of Android Studio (3.3.1), running these tests will default
to Android Instrumented test (device or emulator). In order to run them with Robolectric you have
two options:
Этот пример содержит тесты пользовательского интерфейса, которые могут быть запущены на устройстве( или эмуляторе) или на хосте
(в качестве теста JVM, используя Robolectric). Начиная с версии Android Studio (3.3.1), запуск этих тестов будет выполняться по умолчанию
для Android Instrumented test (устройство или эмулятор). Для того чтобы запустить их с помощью Robolectric у вас есть
два варианта:
 * From the command line, running `./gradlew test`
 * From Android Studio, creating a new "Android JUnit" run configuration and targeting "all
 in package" or a specific class or directory.
 * Из командной строки, запуск `./ gradlew test`
 * Из Android Studio, создав новую конфигурацию запуска" Android JUnit "и таргетинг" all
 в пакете " или в определенном классе или каталоге.




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
