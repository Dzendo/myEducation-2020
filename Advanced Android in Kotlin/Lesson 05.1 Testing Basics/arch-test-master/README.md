# Android Architecture Blueprints v2
<p align="center">
<img src="https://github.com/googlesamples/android-architecture/wiki/images/aab-logov2.png" alt="Illustration by Virginia Poltrack"/>
</p>

Android Architecture Blueprints is a project to showcase different architectural approaches to developing Android apps.
 In its different branches you'll find the same app (a TODO app) implemented with small differences.
Android Architecture Blueprints-это проект, демонстрирующий различные архитектурные подходы к разработке приложений для Android.
 В разных его ветвях вы найдете одно и то же приложение (приложение TODO), реализованное с небольшими различиями.

In this branch you'll find:
В этой ветке вы найдете:

*   Kotlin **[Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)** for background operations.
*   A single-activity architecture,
    using the **[Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started)**
    to manage fragment operations.
*   A presentation layer that contains a fragment (View) and a **ViewModel** per screen (or feature).
*   Reactive UIs using **LiveData** observables and **Data Binding**.
*   A **data layer** with a repository and two data sources (local using Room and remote) that are queried with one-shot operations
    (no listeners or data streams).
*   Two **product flavors**, `mock` and `prod`, [to ease development and testing]
    (https://android-developers.googleblog.com/2015/12/leveraging-product-flavors-in-android.html) (except in the Dagger branch).
*   A collection of unit, integration and e2e **tests**, including "shared" tests that can be run on emulator/device or Robolectric.

* Котлин **[сопрограммы](https://kotlinlang.org/docs/reference/coroutines-overview.html)** для фоновых операций.
* Архитектура с одним действием, использующая **[навигационный компонент]
    (https://developer.android.com/guide/navigation/navigation-getting-started)** для управления операциями фрагментов.
* Слой презентации, содержащий фрагмент (представление) и модель представления** на каждый экран (или объект).
* Реактивная интерфейсов с использованием **Данн** наблюдаемых и **привязка данных**.
* Уровень данных** с хранилищем и двумя источниками данных (локальным с использованием комнаты и удаленным),
    которые запрашиваются с помощью одноразовых операций (без прослушивателей или потоков данных).
* Два **ароматизатора продукта**, `макет` и `пародия`, [чтобы облегчить разработку и testing]
    (https://android-developers.googleblog.com/2015/12/leveraging-product-flavors-in-android.html) (за исключением ветви Кинжала).
* Набор модульных, интеграционных и e2e **тестов**, включая "общие" тесты,
    которые могут быть запущены на эмуляторе/устройстве или Robolectric.

## Variations
## Изменения

This project hosts each sample app in separate repository branches.
    For more information, see the `README.md` file in each branch.
В этом проекте каждый пример приложения размещается в отдельных ветвях репозитория.
    Для получения дополнительной информации см. `README.md-досье в каждом отделении.
### Stable samples - Kotlin
|     Sample     | Description |
| ------------- | ------------- |
| [master](https://github.com/googlesamples/android-architecture/tree/master) | The base for the rest of the branches. <br/>Uses Kotlin, Architecture Components, coroutines, Data Binding, etc. and uses Room as source of truth, with a reactive UI. |
| [dagger-android](https://github.com/googlesamples/android-architecture/tree/dagger-android)<br/>[[compare](https://github.com/googlesamples/android-architecture/compare/dagger-android#files_bucket)] | A simple Dagger setup that uses `dagger-android` and removes the two flavors. |
| [usecases](https://github.com/googlesamples/android-architecture/tree/usecases)<br/>[[compare](https://github.com/googlesamples/android-architecture/compare/usecases#files_bucket)] | Adds a new domain layer that uses UseCases for business logic. |

### Old samples - Kotlin and Java
### Старые образцы - Kotlin и Java

Blueprints v1 had a collection of samples that are not maintained anymore, but can still be useful.
    See [all project branches](https://github.com/googlesamples/android-architecture/branches).
У Blueprints v1 была коллекция образцов, которые больше не поддерживаются, но все еще могут быть полезны.
    См. раздел [все ветви проекта](https://github.com/googlesamples/android-architecture/branches).

## Why a to-do app?
## Почему приложение to-do?

<img align="right" src="https://github.com/googlesamples/android-architecture/wiki/images/todoapp.gif" alt="A demo illustraating the UI of the app" width="288" height="512" style="display: inline; float: right"/>

The app in this project aims to be simple enough that you can understand it quickly,
    but complex enough to showcase difficult design decisions and testing scenarios.
    For more information, see the [app's specification]
    (https://github.com/googlesamples/android-architecture/wiki/To-do-app-specification).
Приложение в этом проекте стремится быть достаточно простым, чтобы вы могли быстро понять его,
    но достаточно сложным, чтобы продемонстрировать сложные проектные решения и сценарии тестирования.
 Дополнительные сведения см. В разделе [спецификация приложения]
 (https://github.com/googlesamples/android-architecture/wiki/To-do-app-specification).

## What is it not?
## Что это не так?

*   A UI/Material Design sample. The interface of the app is deliberately kept simple to focus on architecture. Check out [Plaid](https://github.com/android/plaid) instead.
*   A complete Jetpack sample covering all libraries. Check out [Android Sunflower](https://github.com/googlesamples/android-sunflower) or the advanced [Github Browser Sample](https://github.com/googlesamples/android-architecture-components/tree/master/GithubBrowserSample) instead.
*   A real production app with network access, user authentication, etc. Check out the [Google I/O app](https://github.com/google/iosched), [Santa Tracker](https://github.com/google/santa-tracker-android) or [Tivi](https://github.com/chrisbanes/tivi) for that.

* Образец дизайна пользовательского интерфейса/материала.
    Интерфейс приложения намеренно упрощен, чтобы сосредоточиться на архитектуре.
    Проверьте [плед](https://github.com/android/plaid) вместо этого.
* Полный образец реактивного ранца, охватывающий все библиотеки.
    Проверьте [Android Sunflower](https://github.com/googlesamples/android-sunflower)
    или продвинутый [браузер Github Sample]
    (https://github.com/googlesamples/android-architecture-components/tree/master/GithubBrowserSample)
    вместо этого.
* Реальное производственное приложение с доступом к сети, аутентификацией пользователя и т. д.
    Проверьте [приложение ввода-вывода Google]
    (https://github.com/google/iosched),
    [Санта-трекер](https://github.com/google/santa-tracker-android)
    или [Тиви](https://github.com/chrisbanes/tivi) за это.

## Who is it for?
## Для кого он предназначен?

*   Intermediate developers and beginners looking for a way to structure their app in a testable and maintainable way.
*   Advanced developers looking for quick reference.

* Промежуточные разработчики и новички ищут способ структурировать свое приложение тестируемым и ремонтопригодным способом.
* Расширенный разработчиков для быстрого ознакомления.

## Opening a sample in Android Studio
## Открытие образца в Android Studio

To open one of the samples in Android Studio, begin by checking out one of the sample branches,
 and then open the root directory in Android Studio.
  The following series of steps illustrate how to open the [usecases](tree/usecases/) sample.
Чтобы открыть один из примеров в Android Studio, начните с проверки одной из ветвей примера,
 а затем откройте корневой каталог в Android Studio.
  Следующая серия шагов иллюстрирует, как открыть образец [use cases](три/use cases/).

Clone the repository:
Клонирование репозитория:

```
git clone git@github.com:googlesamples/android-architecture.git
```
This step checks out the master branch. If you want to change to a different sample:
Этот шаг проверяет главную ветвь. Если вы хотите перейти на другой образец:

```
git checkout usecases
```

**Note:** To review a different sample, replace `usecases` with the name of sample you want to check out.
****Примечание:** чтобы просмотреть другой образец, замените "варианты использования" на имя образца,
    который вы хотите проверить.

Finally open the `android-architecture/` directory in Android Studio.
Наконец откройте каталог `android-architecture/` в Android Studio.

### License


```
Copyright 2019 Google, Inc.

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements. See the NOTICE file distributed with this work for
additional information regarding copyright ownership. The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License. You may obtain a copy of
the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations under
the License.
```
