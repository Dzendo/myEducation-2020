# Android Navigation codelab

Content: https://codelabs.developers.google.com/codelabs/android-navigation/
// Command to navigate to flow_step_one_dest
findNavController().navigate(R.id.flow_step_one_dest)
Обратите внимание, что вы передаете либо пункт назначения, либо идентификатор действия для навигации.
Это идентификаторы, определенные в графе навигации XML.
Это пример передачи идентификатора пункта назначения.
методы типа navigate()или popBackStack()
Есть несколько способов получить NavControllerобъект, связанный с вашим NavHostFragment:
Fragment.findNavController()
View.findNavController()
Activity.findNavController(viewId: Int)
Ваш NavControllerассоциируется с NavHostFragment и они должны иметь NavHostFragment родительский

Вы также можете использовать удобный метод
Navigation.createNavigateOnClickListener(@IdRes destId: int, bundle: Bundle).
Этот метод создаст OnClickListenerдля перехода к заданному месту назначения
с набором аргументов, которые будут переданы получателю.
 view.findViewById<Button>(R.id.navigate_action_button)?.setOnClickListener {
            //val flowStepNumberArg = 1
            //val action = HomeFragmentDirections.nextAction(flowStepNumberArg)
            val action = HomeFragmentDirections.nextAction()  // арумент = 1 по умролчанию из графа
            findNavController().navigate(action)
        }

9. Навигация с использованием меню, ящиков и нижней навигации
NavigationUI и навигация-UI-KTX
Использование NavigationUI с меню параметров
Использование NavigationUI для настройки нижней навигации
Использование NavigationUI для настройки ящика навигации
10. Глубокая связь с пунктом назначения
Глубокие ссылки и навигация
Глубокие ссылки - это способ перейти в середину навигации вашего приложения,
будь то фактическая ссылка на URL или ожидаемое намерение из уведомления.
Одним из преимуществ использования библиотеки навигации для обработки глубоких ссылок является то,
что она обеспечивает пользователям запуск в нужном месте назначения с соответствующим задним стеком из других точек входа,
таких как виджеты приложений, уведомления или веб-ссылки (описанные в следующем шаге).
Добавить глубокую ссылку
Мы будем использовать, NavDeepLinkBuilderчтобы подключить виджет приложения к месту назначения.
1. Открыть DeepLinkAppWidgetProvider.kt
2. Добавьте PendingIntent построенное с помощью NavDeepLinkBuilder:
По умолчанию NavDeepLinkBuilder запустит ваш лаунчер Activity.
Вы можете переопределить это поведение, передавая действие в качестве контекста
или устанавливая явный класс действия через setComponentName().
Создать виджет на тел и связать его с иконкой- app
При нажатии на виджет - вызывает app с ЭТОГО МЕСТА и с переданным аргументом восстановив стек
DeepLink Backstack
Backstack для глубокой ссылки определяется с помощью навигационной диаграммы, которую вы передаете.
Если у явного действия, которое вы выбрали, есть родительское действие , эти родительские действия также включаются.
Backstack генерируется с использованием адресатов, указанных с помощью app:startDestination.
В этом приложении у нас есть только одно действие и один уровень навигации,
поэтому backstack приведет вас к home_dest месту назначения.

11. Связывание веб-ссылки с пунктом назначения
Элемент <deepLink>
Одним из наиболее распространенных применений глубокой ссылки является разрешение веб-ссылке открыть действие в вашем приложении.
Традиционно вы использовали бы фильтр намерений и связывали URL с действием , которое хотите открыть.
<deepLink>это элемент, который вы можете добавить в пункт назначения на вашем графике.
Каждый <deepLink>элемент имеет один обязательный атрибут: app:uri.
В дополнение к прямому соответствию URI поддерживаются следующие функции:

Предполагается, что URI без схемы - это http и https.
Например, www.example.comбудет соответствовать http://www.example.comи https://www.example.com.
Вы можете использовать заполнители в форме {placeholder_name}для соответствия одному или нескольким символам.
Строковое значение заполнителя доступно в аргументе Bundle, который имеет ключ с тем же именем.
Например, http://www.example.com/users/{id}будет соответствовать http://www.example.com/users/4.
Вы можете использовать .*подстановочный знак, чтобы соответствовать нулю или более символов.
NavController будет автоматически обрабатывать намерения ACTION_VIEW и искать подходящие глубокие ссылки.
Добавьте основанную на URI глубокую ссылку, используя <deepLink>
и манифест
Запуск:
adb shell am start -a android.intent.action.VIEW -d "http://www.example.com/urlTest"
Перейдите через приложение Google.(не Хроме)
поместить www.example.com/urlTest в строку поиска, и появится окно устранения неоднозначности.
Выберите навигационную кодовую метку
Стартует DeepLink и передаст urlTest

12. [Необязательно] Попробуйте самостоятельно
Есть еще одна часть приложения codelab, с которой вы можете поэкспериментировать, и это кнопка корзины покупок.
Сделал простейшую - работает
13. Поздравляем!
Вы знакомы с основными понятиями, лежащими в основе компонента «Навигация»!
В этой кодовой метке вы узнали о:
    Структура навигационного графика
    NavHostFragment и NavController
    Как перейти к конкретным направлениям
    Как ориентироваться по действию
    Как передавать аргументы между получателями, включая использование нового плагина safeargs
    Навигация с использованием меню, навигации внизу и ящиков навигации
    Навигация по глубокой ссылке
Вы можете продолжить исследовать это приложение или начать использовать навигацию в своем собственном приложении.

Есть еще много чего попробовать, в том числе:

Попадание мест назначения за пределы backstack (или любые манипуляции с backstack)
Вложенные навигационные графики
Условная навигация
Добавление поддержки для новых направлений

Для получения дополнительной информации о компоненте навигации ознакомьтесь с документацией.
Если вам интересно узнать о других компонентах архитектуры , попробуйте следующие кодовые метки:

Номер с кодовой меткой вида (LiveData, ViewModel и номер)
Android WorkManager Codelab
Android Paging Codelab
Компоненты Codelab, поддерживающие жизненный цикл Android (LiveData и ViewModel)
Android Persistance Codelab (Комната)

Room with a View Codelab (LiveData, ViewModel and Room)
Android WorkManager Codelab
Android Paging Codelab
Android Lifecycle-aware components Codelab (LiveData and ViewModel)
Android Persistence Codelab (Room)
27,06,2020 5:30

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