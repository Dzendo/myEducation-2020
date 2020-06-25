Android Data Binding Basic Sample
=============================================

This sample showcases the following features of the
Этот образец демонстрирует следующие особенности
[Data Binding library](https://developer.android.com/topic/libraries/data-binding/index.html):

* Layout variables and expressions
* Observability through Observable Fields, LiveData and Observable classes
* Binding Adapters, Binding Methods and Binding Converters
* Seamless integration with ViewModels

* Переменные макета и выражения
* Наблюдаемость через наблюдаемые поля, живые данные и наблюдаемые классы
* Адаптеры привязки, методы привязки и конвертеры привязки
* Бесшовная интеграция с ViewModels

It shows common bad practices and their solutions in two different screens.
Он показывает общие плохие практики и их решения на двух разных экранах.

Features Особенности
--------

![Screenshot](https://github.com/googlesamples/android-databinding/blob/master/BasicSample/screenshots/screenshotbasic.png)

### Layout variables and expressions Переменные и выражения компоновки

With Data Binding you can write less boilerplate and repetitive code. It moves UI operations out
of the activities and fragments to the XML layout.
С привязкой данных вы можете писать меньше шаблонного и повторяющегося кода.
Он перемещает операции пользовательского интерфейса из действий и фрагментов в XML-макет.

For example, instead of setting text on a TextView in an activity:
Например, вместо установки текста в TextView в activity:

```java
TextView textView = findViewById(R.id.name);
textView.setText(user.name);
```

You assign the attribute to a variable, in the XML layout:

```xml
<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="@{user.name}" />
```

See `ObservableFieldActivity.kt`, `ObservableFieldProfile.kt` and `observable_field_profile.xml`
for a simple example. для простого примера.

### Observability Наблюдаемость

In order to update the UI automatically when the data changes, Data Binding lets you bind attributes
with observable objects. You can choose between three mechanisms to achieve this: Observable fields,
LiveData and Observable classes.
Чтобы автоматически обновлять пользовательский интерфейс при изменении данных,
привязка данных позволяет привязывать атрибуты с наблюдаемыми объектами.
Вы можете выбрать один из трех механизмов для достижения этой цели:
наблюдаемые поля, Живые данные и наблюдаемые классы.

#### Observable fields Наблюдаемые поля

Types like ObservableBoolean, ObservableInt and the generic ObservableField replace the
corresponding primitives to make them observable. Setting a new value on one of the Observable
fields will update the layout automatically.
Типами логическое наблюдаемых, наблюдаемыми Инт и generic наблюдаемый поле заменить
соответствующие примитивы, чтобы сделать их наблюдаемыми.
Установка нового значения на одном из наблюдаемых объектов поля автоматически обновят макет.

```kotlin
class ProfileObservableFieldsViewModel : ViewModel() {

    val likes = ObservableInt(0)

    fun onLike() {
        likes.increment()  // Equivalent to set(likes.get() + 1)
    }
}
```

In this example, when `onLike` is called, the number of likes is incremented
and the UI is updated. There is no need to notify that the property changed.
В этом примере, когда вызывается "on Like", количество лайков увеличивается
и пользовательский интерфейс обновляется.
Нет необходимости уведомлять об изменении объекта.

#### LiveData

LiveData is an observable from Живые данные-это наблюдаемые из
[Android Architecture Components](https://developer.android.com/topic/libraries/architecture)
that is lifecycle-aware. это осознание жизненного цикла.

The advantages over Observable Fields are that LiveData supports
Преимущества перед наблюдаемыми полями заключаются в том, что живые данные поддерживают
[Transformations](https://developer.android.com/reference/android/arch/lifecycle/Transformations)
and it's compatible with other components and libraries, like Room and WorkManager.
и он совместим с другими компонентами и библиотеками, такими как Room and Work Manager

```kotlin
class ProfileLiveDataViewModel : ViewModel() {
    private val _likes =  MutableLiveData(0)
    val likes: LiveData<Int> = _likes // Expose an immutable LiveData

    fun onLike() {
        _likes.value = (_likes.value ?: 0) + 1
    }
}
```

It requires an extra step done on the binding:
Это требует дополнительного шага, сделанного на привязке:


```kotlin

binding.lifecycleOwner = this  // use viewLifecycleOwner when assigning a fragment
```

#### Observable classes Наблюдаемые классы

For maximum flexibility and control, you can implement a fully observable class and decide when
to update certain properties. This lets you create dependencies between properties and it's
useful to dispatch partial UI updates, for example avoiding
potential glitches (UI elements updating almost at the same time).

Для обеспечения максимальной гибкости и контроля вы можете реализовать полностью наблюдаемый класс
и решить, когда для обновления определенных свойств.
Это позволяет создавать зависимости между свойствами и его
полезно отправлять частичные обновления пользовательского интерфейса, например избегая
потенциальные глюки (элементы пользовательского интерфейса обновляются почти одновременно).

```kotlin
class ProfileObservableViewModel : ObservableViewModel() {
    val likes = ObservableInt(0)

    fun onLike() {
        likes.increment()
        notifyPropertyChanged(BR.popularity)
    }

    @Bindable
    fun getPopularity(): Popularity {
        return likes.get().let {
            when {
                it > 9 -> Popularity.STAR
                it > 4 -> Popularity.POPULAR
                else -> Popularity.NORMAL
            }
        }
    }
}
```

In this example, when `onLike` is called, the number of likes is incremented and the
`popularity` property is notified of a potential change (`popularity` depends on `likes`).
`getPopularity` is called by the library, returning a possible new value.

В этом примере, когда вызывается "on Like", количество лайков увеличивается и число лайков увеличивается.
свойство "популярность" уведомляется о потенциальном изменении ("популярность" зависит от "лайков").
"get Popularity" вызывается библиотекой, возвращая возможное новое значение.

See `ProfileObservableFieldsViewModel.kt` for a complete example.

#### Binding adapters Связующие адаптеры

Binding adapters let you customize or create layout attributes. For example, you can create
an `app:progressTint` attribute for progress bars where you change the color of the
progress indicator depending on an external value.

Адаптеры привязки позволяют настраивать или создавать атрибуты макета. Например, вы можете создать
электронное приложение:атрибут прогрессировать по полосы прогресса, в котором можно изменить цвет
индикатор прогресса в зависимости от внешнего значения.

```kotlin
    @BindingAdapter("app:progressTint")
    @JvmStatic fun tintPopularity(view: ProgressBar, popularity: Popularity) {

        val color = getAssociatedColor(popularity, view.context)
        view.progressTintList = ColorStateList.valueOf(color)
    }
```

The binding is created in the XML layout with:

```xml
    <ProgressBar
        app:progressTint="@{viewmodel.popularity}" />
```

Using binding adapters lets you move UI calls from the activity to static methods, improving
encapsulation.
Использование адаптеров привязки позволяет перемещать вызовы пользовательского интерфейса из действия в статические методы, улучшая
инкапсуляция.

You can also use multiple attributes in a Binding Adapter, see `viewmodel_profile.xml` for a complete
example.
Вы также можете использовать несколько атрибутов в адаптере привязки, см. раздел ' viewmodel_profile.xml` для полного
пример.

#### Binding methods and binding converters Методы связывания и преобразователи связывания

Binding methods and binding converters let you reduce code if your binding adapters
are very simple. You can read about them in the
Методы привязки и конвертеры привязки позволяют сократить код, если ваши адаптеры привязки
быть очень простым. Вы можете прочитать о них в разделе
[official guide](https://developer.android.com/topic/libraries/data-binding/index.html#attribute_setters).

For example, if an attribute's value needs to be passed to a method in the class:
Например, если значение атрибута должно быть передано методу в классе:

```kotlin
@BindingAdapter("app:srcCompat")
@JvmStatic fun srcCompat(view: ImageView, @DrawableRes drawableId: Int) {
    view.setImageResource(drawable)
}
```

You can replace this with a Binding Method which can be added to any class in the project:
Вы можете заменить его методом привязки, который может быть добавлен в любой класс проекта:

```kotlin
@BindingMethods(
        BindingMethod(type = ImageView::class,
                attribute = "app:srcCompat",
                method = "setImageDrawable"))
```

#### Binding Converters (use with caution) Конвертеры привязки (используйте с осторожностью)

In this sample we show a View depending on whether a number is zero. There are many options to do
this. We're showing two, one in the `observable_field_profile.xml`
and, the recommended way, in `viewmodel_profile.xml`.

В этом примере мы показываем представление в зависимости от того, является ли число нулем.
Есть много вариантов, чтобы сделать этот.
Мы показываем два, один в файле observable_field_profile.XML`
и, рекомендуемый способ, в ' viewmodel_profile.XML`.

The goal is to bind the view's visibility to the number of likes, but this won't work:
Цель состоит в том, чтобы привязать видимость представления к количеству лайков, но это не сработает:

```xml
android:visibility="@{viewmodel.likes}"  <!-- Doesn't work as expected -->
```

The number of likes is an integer and the visibility attribute takes an integer
(`VISIBLE`, `GONE` and `INVISIBLE` are 0, 4 and 8 respectively), so doing this would build,
but the result would not be the expected.
Число лайков является целым числом, а атрибут видимости принимает целое число
("Видимый", "ушедший" и "невидимый" равны 0, 4 и 8 соответственно), так что это будет строить,
но результат оказался бы совсем не таким, как ожидалось.

A possible solution is: Возможное решение таково:

```xml
android:visibility="@{viewmodel.likes == 0 ? View.GONE : View.VISIBLE}"/
```

But it adds a relatively complex expression to the layout.
Но это добавляет относительно сложное выражение к макету.

Instead, you can create and import a utils class:
Вместо этого вы можете создать и импортировать класс utils:

```xml
<data>
        <import type="com.example.android.databinding.basicsample.util.ConverterUtil" />
        ...
</data>
```

and use it from the View like so: и использовать его с точки зрения вот так:

```xml
android:visibility="@{ConverterUtil.isZero(viewmodel.likes)}"  <!-- don't do this either -->
```

`isZero` returns a boolean and `visibility` takes an integer so in order to convert
from boolean we can also define a BindingConversion:
"is Zero" возвращает логическое значение, а " visibility` принимает целое число, поэтому для преобразования
из boolean мы также можем определить преобразование привязки:

```kotlin
    @BindingConversion
    @JvmStatic fun booleanToVisibility(isVisible: Boolean): Int {  // Risky! applies everywhere
        return if (isVisible) View.VISIBLE else View.GONE
    }
```

This conversion is unsafe because this binding conversion is not restricted to our case: it will
convert all booleans to visibility integers when the attribute takes an integer.
Это преобразование небезопасно, потому что это связывающее преобразование не ограничено нашим случаем: оно будет
преобразуйте все логические значения в целые числа видимости, когда атрибут принимает целое число.

Solution: As with every BindingConversion and BindingMethod, you can replace it with a Binding
Adapter, which normally is much simpler:
Решение: как и в случае любого преобразования привязки и метода привязки, вы можете заменить его привязкой
Адаптер, который обычно гораздо проще:

```kotlin
    @BindingAdapter("app:hideIfZero")  // Recommended solution
    @JvmStatic fun hideIfZero(view: View, number: Int) {
        view.visibility = if (number == 0) View.GONE else View.VISIBLE
    }
```

and as shown in in `viewmodel_profile.xml`: и как показано в разделе in ' viewmodel_profile.XML`:

```xml
app:hideIfZero="@{viewmodel.likes}"
```

This defines a new custom attribute `hideIfZero` that can't be used accidentally.
Это определяет новый пользовательский атрибут "скрыть, если ноль", который не может быть использован случайно.

As a rule of thumb it's preferable to create your our custom attributes using Data Binding adapters
instead of adding logic to your binding expressions.
Как правило предпочтительнее создавать свои собственные атрибуты с помощью адаптеров привязки данных
вместо того, чтобы добавлять логику к вашим связывающим выражениям.

Sample app Пример приложения
----------

This app shows a user's profile using two different screens to showcase different Data Binding
features:
Это приложение показывает профиль пользователя с помощью двух разных экранов
для демонстрации различных Привязок данных особенности:

  * Main activity: Shows how a Data Binding layout lets you access Views without `findViewById`.
  * Основное действие: показывает, как макет привязки данных позволяет получить доступ к представлениям без `findViewById`.

  * Observable field activity: In this screen the user can give "likes" to the profile and the UI
  reacts automatically to changes. However, the activity  holds the logic that receives the user
  click and the actual profile data, which is not testable.
  Also, likes are reset when the user rotates the device and the layout contains documented common
  bad practices.
  * Наблюдаемая активность поля: на этом экране пользователь может поставить "лайки" профилю и пользовательскому интерфейсу
  автоматически реагирует на изменения. Однако действие содержит логику, которая получает пользователя
  нажмите и получите фактические данные профиля, которые не поддаются тестированию.
  Кроме того, лайки сбрасываются, когда пользователь поворачивает устройство и макет содержит документированные общие
  плохая практика.

  * ViewModel activity: Using a ViewModel from the
  [Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html)
  fixes the rotation problem and moves logic out of the activity. Also, the use of binding
  adapters changes the responsibility of the activity which is no longer the "view" and
  solely responsible for dealing with the lifecycle. Two ViewModels are suggested in
  `ProfileObservableViewModel.kt`: one based on observable fields and another implementing the
  observable interface.
  * Активность ViewModel: использование ViewModel из
  [Компоненты архитектуры](https://developer.android.com/topic/libraries/architecture/index.html)
  исправляет проблему вращения и выводит логику из действия. Кроме того, использование привязки
  адаптеры изменяют ответственность за деятельность, которая больше не является "представлением" и
  исключительно ответственный за работу с жизненным циклом. Две модели представления предлагаются в
  ''Профиль Observable ViewModel.kt`: один основан на наблюдаемых полях, а другой реализует
  наблюдаемый интерфейс.


License
--------

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
