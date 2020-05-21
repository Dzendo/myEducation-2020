MarsRealEstate - Starter Code        Недвижимость На Марсе - Стартовый Код.
==============================

Starter code for Android Kotlin Fundamentals Codelab 8.1 Getting data from the internet
Стартовый код для Android Kotlin Fundamentals Codelab 8.1 получение данных из интернета

Introduction - Вступление
------------

MarsRealEstate is a demo app that shows available properties for sale and for rent on Mars.
The property data is stored on a Web server as a REST web service.  This app demonstrated
the use of [Retrofit](https://square.github.io/retrofit/) to make REST requests to the 
web service, [Moshi](https://github.com/square/moshi) to handle the deserialization of the 
returned JSON to Kotlin data objects, and [Glide](https://bumptech.github.io/glide/) to load and 
cache images by URL. 

Mars Real Estate-это демонстрационное приложение, которое показывает доступные объекты недвижимости для продажи и аренды на Марсе.
Данные свойств хранятся на веб-сервере в виде веб-службы REST. Это приложение продемонстрировало
использование [модернизации] (http://square.github.io/retrofit/) делать запросы на отдых в адрес
веб-сервис, [Моши](https://github.com/square/moshi) для обработки десериализации объекта
возвращаемый JSON в объекты данных Котлин, и [скольжение](https://bumptech.github.io/glide/) для загрузки и
кэшируйте изображения по URL-адресу.
 

The app also leverages [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel),
[LiveData](https://developer.android.com/topic/libraries/architecture/livedata), 
[Data Binding](https://developer.android.com/topic/libraries/data-binding/) with binding 
adapters, and [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) 
with the SafeArgs plugin for parameter passing between fragments.

Приложение также использует [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel),
[[Живые данные](https://developer.android.com/topic/libraries/architecture/live данные),
[Привязка данных](https://developer.android.com/topic/libraries/data-binding/) с привязкой
адаптеры и [навигация] (https://developer.android.com/topic/libraries/architecture/navigation/)
с помощью плагина Safe Args для передачи параметров между фрагментами.

Pre-requisites - Предварительные условия
--------------

You need to know:
- How to create and use fragments.
- How to navigate between fragments, and use safeArgs to pass data between fragments.
- How to use architecture components including ViewModel, ViewModelProvider.Factory, LiveData, and LiveData transformations.
- How to use coroutines for long-running tasks.

Тебе нужно знать:
- Как создавать и использовать фрагменты.
- Как перемещаться между фрагментами и использовать безопасные Args для передачи данных между фрагментами.
- Как использовать компоненты архитектуры, включая ViewModel, ViewModelProvider.Фабрика, живые данные и преобразования живых данных.
- Как использовать сопрограммы для длительных задач.

Getting Started - приступая к работе
---------------

1. Download and run the app.
1. Загрузите и запустите приложение.

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