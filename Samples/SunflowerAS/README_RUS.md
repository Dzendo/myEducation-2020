Android Sunflower Подсолнух
=================

A gardening app illustrating Android development best practices with Android Jetpack.
Приложение для садоводства, иллюстрирующее лучшие практики разработки Android с помощью Android Jetpack.

Android Sunflower is currently under heavy development.
Android Sunflower в настоящее время находится в стадии интенсивной разработки.
Note that some changes (such as database schema modifications) are not backwards
compatible and may cause the app to crash. In this
case, please uninstall and re-install the app.
Обратите внимание, что некоторые изменения (например, изменения схемы базы данных)  
не являются обратными совместимость и может привести к сбою приложения.  
В этом случае, пожалуйста, удалите и переустановите приложение.

Introduction Вступление
------------

Android Jetpack is a set of components, tools and guidance to make great Android apps. They bring
together the existing Support Library and Architecture Components and arrange them into four
categories:
Android Jetpack - это набор компонентов, инструментов и руководств для создания отличных приложений для Android.  
Они приносят вместе существующие библиотеки поддержки и компоненты архитектуры  
и расположите их в четыре категории:

![Android Jetpack](screenshots/jetpack_donut.png "Android Jetpack Components")

Android Sunflower demonstrates utilizing these components to create a simple gardening app.
Android Sunflower демонстрирует использование этих компонентов для создания простого приложения для садоводства.
Read the
[Introducing Android Sunflower](https://medium.com/androiddevelopers/introducing-android-sunflower-e421b43fe0c2)
article for a walkthrough of the app. статья для пошагового руководства приложения.

Getting Started приступая к работе
---------------
This project uses the Gradle build system. To build this project, use the
`gradlew build` command or use "Import Project" in Android Studio.
Этот проект использует систему сборки Gradle. Чтобы построить этот проект, используйте
команда "gradlew build" или используйте команду "Import Project" в Android Studio.

There are two Gradle tasks for testing the project:
Для тестирования проекта существует две задачи Gradle:
* `connectedAndroidTest` - for running Espresso on a connected device
* `test` - for running unit tests - для выполнения модульных тестов

For more resources on learning Android development, visit the
Дополнительные ресурсы по изучению разработки под Android, посетите
[Developer Guides](https://developer.android.com/guide/) at
[developer.android.com](https://developer.android.com).

### Unsplash API key
### Unsplash API ключ


Sunflower uses the [Unsplash API](https://unsplash.com/developers) to load pictures on the gallery
screen. To use the API, you will need to obtain a free developer API key. See the
[Unsplash API Documentation](https://unsplash.com/documentation) for instructions.
Подсолнечник использует [Unsplash API](https://unsplash.com/developers) для загрузки фотографий в галерею
экран. Чтобы использовать API, вам нужно будет получить бесплатный ключ API разработчика. См.
[Unsplash API документация](https://unsplash.com/documentation) для получения инструкций.


Once you have the key, add this line to the `gradle.properties` file, either in your user home
directory (usually `~/.gradle/gradle.properties` on Linux and Mac) or in the project's root folder:
Как только у вас будет ключ, добавьте эту строку в "gradle".файл свойств, либо в вашем доме пользователя
каталог (обычно`~/.среда Gradle/Gradle в.свойства " на Linux и Mac) или в корневой папке проекта:


```
unsplash_access_key=<your Unsplash access key>
```

The app is still usable without an API key, though you won't be able to navigate to the gallery screen.
Приложение по-прежнему можно использовать без ключа API, хотя вы не сможете перейти на экран галереи.

Screenshots - Скриншоты
-------------

![List of plants](screenshots/phone_plant_list.png "A list of plants - Список растений ")
![Plant details](screenshots/phone_plant_detail.png "Details for a specific plant - Детали для конкретного завода")
![My Garden](screenshots/phone_my_garden.png "Plants that have been added to your garden - Растения, которые были добавлены в ваш сад")

Libraries Used - Используемые Библиотеки
----------------
* [Foundation][0] - Components for core system capabilities, Kotlin extensions and support for
  multidex and automated testing - Компоненты для основных возможностей системы, расширения Kotlin и поддержка для
  многодексное и автоматизированное тестирование..
  * [AppCompat][1] - Degrade gracefully on older versions of Android. Ухудшить изящно на старых версиях Android.
  * [Android KTX][2] - Write more concise, idiomatic Kotlin code. Напишите более лаконичный, идиоматичный код Котлина.
  * [Test][4] - An Android testing framework for unit and runtime UI tests. Платформа тестирования Android для модульных и исполняемых тестов пользовательского интерфейса.
* [Architecture][10] - A collection of libraries that help you design robust, testable, and
  maintainable apps. Start with classes for managing your UI component lifecycle and handling data
  persistence. Коллекция библиотек, которые помогут вам разработать надежные, проверяемые и
  ремонтопригодные приложения. Начните с классов для управления жизненным циклом компонентов пользовательского интерфейса и обработки сохраняемости данных.
  * [Data Binding][11] - Declaratively bind observable data to UI elements. Декларативно привязать наблюдаемые данные к элементам пользовательского интерфейса.
  * [Lifecycles][12] - Create a UI that automatically responds to lifecycle events. Создайте пользовательский интерфейс, который автоматически реагирует на события жизненного цикла.
  * [LiveData][13] - Build data objects that notify views when the underlying database changes. Постройте объекты данных, которые уведомляют представления при изменении базовой базы данных.
  * [Navigation][14] - Handle everything needed for in-app navigation. Обрабатывайте все необходимое для навигации в приложении.
  * [Room][16] - Access your app's SQLite database with in-app objects and compile-time checks. Получите доступ к базе данных SQLite вашего приложения с помощью встроенных объектов и проверок во время компиляции.
  * [ViewModel][17] - Store UI-related data that isn't destroyed on app rotations. Easily schedule
     asynchronous tasks for optimal execution. Храните связанные с пользовательским интерфейсом данные, которые не уничтожаются при вращении приложений. Легко планировать
     асинхронные задачи для оптимального выполнения.
  * [WorkManager][18] -  Manage your Android background jobs. Управляйте своими фоновыми заданиями на Android.
* [UI][30] - Details on why and how to use UI Components in your apps - together or separate Подробные сведения о том, почему и как использовать компоненты пользовательского интерфейса в ваших приложениях-вместе или по отдельности
  * [Animations & Transitions][31] - Move widgets and transition between screens. Перемещение виджетов и переход между экранами.
  * [Fragment][34] - A basic unit of composable UI. Базовая единица составного пользовательского интерфейса.
  * [Layout][35] - Lay out widgets using different algorithms. Выкладывайте виджеты, используя различные алгоритмы.
* Third party
  * [Glide][90] for image loading для загрузки изображений
  * [Kotlin Coroutines][91] for managing background threads with simplified code and reducing needs for callbacks для управления фоновыми потоками с упрощенным кодом и снижения потребности в обратных вызовах

[0]: https://developer.android.com/jetpack/components
[1]: https://developer.android.com/topic/libraries/support-library/packages#v7-appcompat
[2]: https://developer.android.com/kotlin/ktx
[4]: https://developer.android.com/training/testing/
[10]: https://developer.android.com/jetpack/arch/
[11]: https://developer.android.com/topic/libraries/data-binding/
[12]: https://developer.android.com/topic/libraries/architecture/lifecycle
[13]: https://developer.android.com/topic/libraries/architecture/livedata
[14]: https://developer.android.com/topic/libraries/architecture/navigation/
[16]: https://developer.android.com/topic/libraries/architecture/room
[17]: https://developer.android.com/topic/libraries/architecture/viewmodel
[18]: https://developer.android.com/topic/libraries/architecture/workmanager
[30]: https://developer.android.com/guide/topics/ui
[31]: https://developer.android.com/training/animation/
[34]: https://developer.android.com/guide/components/fragments
[35]: https://developer.android.com/guide/topics/ui/declaring-layout
[90]: https://bumptech.github.io/glide/
[91]: https://kotlinlang.org/docs/reference/coroutines-overview.html

 Upcoming features Предстоящие характеристики
-----------------
 Updates will include incorporating additional Jetpack components and updating existing components
as the component libraries evolve.
Обновления будут включать в себя включение дополнительных компонентов реактивного ранца и обновление существующих компонентов
по мере развития библиотек компонентов.

Interested in seeing a particular feature of the Android Framework or Jetpack implemented in this
app? Please open a new [issue](https://github.com/android/sunflower/issues).
Заинтересованы в том, чтобы увидеть конкретную функцию фреймворка Android или Jetpack, реализованную в этом
приложение? Пожалуйста, откройте новый [issue](https://github.com/android/sunflower/issues).

Android Studio IDE setup Настройка IDE для Android Studio
------------------------
For development, the latest version of Android Studio is required. The latest version can be
downloaded from [here](https://developer.android.com/studio/).
Для разработки требуется последняя версия Android Studio. Самая последняя версия может быть
загружено с сайта [here](https://developer.android.com/studio/).

Sunflower uses [ktlint](https://ktlint.github.io/) to enforce Kotlin coding styles.
Here's how to configure it for use with Android Studio (instructions adapted
from the ktlint [README](https://github.com/shyiko/ktlint/blob/master/README.md)):
Использование подсолнечного [ktlint](https://ktlint.github.io/), чтобы применить стили кодирования Котлин.
Вот как настроить его для использования с Android Studio (инструкции адаптированы
из ktlint [README](https://github.com/shyiko/kotlin/blob/master/README.md)):

- Close Android Studio if it's open Закройте Android Studio, если она открыта

- Download ktlint using these [installation instructions](https://github.com/pinterest/ktlint/blob/master/README.md#installation)

- Apply ktlint settings to Android Studio using these [instructions](https://github.com/pinterest/ktlint/blob/master/README.md#-with-intellij-idea)

- Start Android Studio Запуск Android Studio

Additional resources
--------------------
Check out these Wiki pages to learn more about Android Sunflower:
Проверьте эти вики-страницы, чтобы узнать больше о Android Sunflower:

- [Notable Community Contributions](https://github.com/android/sunflower/wiki/Notable-Community-Contributions)

- [Publications](https://github.com/android/sunflower/wiki/Sunflower-Publications)

Non-Goals Не-Цели
---------
The focus of this project is on Android Jetpack and the Android framework.
Thus, there are no immediate plans to implement features outside of this scope.
в центре внимания этого проекта находится Android Jetpack и Android framework.
Таким образом, нет никаких ближайших планов по реализации функций вне этой области.

A note on dependency injection - while many projects (such as
Заметка о внедрении зависимостей-в то время как многие проекты (такие как
[Plaid](https://github.com/nickbutcher/plaid)) use
[Dagger 2](https://github.com/google/dagger) for DI, there are no plans to
incorporate DI into Sunflower.  This allows developers unfamiliar with dependency
injection to better understand Sunflower's code without having to learn DI.
что касается DI, то никаких планов на инкорпорацию в Подсолнух нет.  
Это позволяет разработчикам незнакомым с зависимостью
инъекция, чтобы лучше понять код подсолнечника, не изучая DI.

Support Поддержка
-------

- Stack Overflow:
  - http://stackoverflow.com/questions/tagged/android
  - http://stackoverflow.com/questions/tagged/android-jetpack

If you've found an error in this sample, please file an issue:
Если вы нашли ошибку в этом примере, пожалуйста, отправьте сообщение о проблеме:
https://github.com/android/sunflower/issues

Patches are encouraged, and may be submitted by forking this project and submitting a pull request
through GitHub.
Патчи поощряются и могут быть представлены путем разветвления этого проекта и подачи запроса на вытягивание
через GitHub.

Third Party Content Контент Третьих Лиц
-------------------
Select text used for describing the plants (in `plants.json`) are used from Wikipedia via CC BY-SA 3.0 US (license in `ASSETS_LICENSE`).
Выделите текст, используемый для описания растений (в разделе ' растения.json`) используются из Википедии через CC BY-SA 3.0 US (лицензия в `ASSETS_LICENSE`).
"[seed](https://thenounproject.com/search/?q=seed&i=1585971)" by [Aisyah](https://thenounproject.com/aisyahalmasyira/) is licensed under [CC BY 3.0](https://creativecommons.org/licenses/by/3.0/us/legalcode)

License Лицензия
-------

Copyright 2018 Google, Inc.

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at
Лицензия на Apache Software Foundation (ASF) предоставляется одним или несколькими участниками
лицензионное соглашение. См. файл уведомления, распространяемый вместе с этой работой для
дополнительная информация о праве собственности на авторские права. АЧС лицензирует это
файл для вас под лицензией Apache версии 2.0 (далее "лицензия"); вы не можете
используйте этот файл только в соответствии с лицензией. Вы можете получить копию этого документа.
лицензия на

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.
Если только это не требуется применимым законодательством или не согласовано в письменной форме, программное обеспечение
распространяется по лицензии распространяется на условиях "как есть", без каких-либо ограничений.
Гарантии или условия любого рода, явные или подразумеваемые. Смотрите сами
Лицензия на конкретный язык, регулирующий разрешения и ограничения, предусмотренные в разделе
лицензия.
