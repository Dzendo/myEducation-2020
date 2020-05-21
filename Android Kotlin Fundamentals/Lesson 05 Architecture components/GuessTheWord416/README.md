ViewModel and ViewModelFactory
==================================

Use as starter code for the ViewModel codelab. Lesson 5
Используйте в качестве стартового кода для таблицы кодов ViewModel.

Introduction - Вступление
------------

This starter app is a two player game, GuessTheWord.
It is a word guessing app you can play with one or more friends. To play, hold the device in landscape,
facing away from you with your thumbs on the **Skip** and **Got It** buttons.
Your friends can then give you clues to help you guess the word.
If you get the word right, press **Got It**. If you're stuck, press **Skip**.
You will modify the app to use Architecture components and best practices.

Это стартовое приложение - игра для двух игроков, Угадай слово. Это приложение для угадывания слов,
в которое вы можете играть с одним или несколькими друзьями. Чтобы играть,
держите устройство в горизонтальном положении, повернувшись от вас большими пальцами на кнопках
* * Skip* * и * * Got It**.
Затем ваши друзья могут дать вам подсказки, которые помогут вам угадать слово.
Если вы правильно поняли слово, нажмите * * Got It**. Если вы застряли, нажмите * * пропустить**.
Вы измените приложение, чтобы использовать компоненты архитектуры и лучшие практики.

Pre-requisites - Предварительные условия
--------------

You need to know:
- How to open, build, and run Android apps with Android Studio.
- How to use the Navigation Architecture Component
- Passing the data between navigation destinations.
- Read the logs using the Logcat.

Тебе нужно знать:
- Как открывать, создавать и запускать приложения для Android с помощью Android Studio.
- Как использовать компонент архитектуры навигации
- Передача данных между навигационными пунктами назначения.
- Чтение журналов с помощью Logcat.


Getting Started - приступая к работе
---------------

1. Download and run the app.
1. Загрузите и запустите приложение.
2. обновил AS до 4.1.alpha06,com.android.tools.build:gradle:4.1.0-alpha06,gradle-6.3-bin.zip
3. ext.kotlin_version = '1.4-M1' maven { url = "https://dl.bintray.com/kotlin/kotlin-eap" }
3. Обновил:
//android.databinding.enableV2=true
buildFeatures { dataBinding = true}
kotlinOptions { jvmTarget = '1.8' }
id "androidx.navigation.safeargs.kotlin"

License
-------

Copyright 2019 Google, Inc.

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
