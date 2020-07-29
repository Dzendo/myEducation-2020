Android Data Binding Advanced Sample
=============================================

This sample showcases the following features Этот пример демонстрирует следующие особенности
of the [Data Binding library](https://developer.android.com/topic/libraries/data-binding/index.html)
with an app that shows a workout timer. с приложением, которое показывает таймер тренировки.

 * Two-way Data Binding
 * Alternatives to Two-way Data Binding
 * Binding adapters with multiple parameters
 * Animations with Binding Adapters
 * Binding converters and inverse converters
 * Data Binding with ViewModels and Kotlin
 * No UI framework calls in activity
 * Testing

 * Двусторонняя привязка данных
 * Альтернативы двусторонней привязке данных
 * Привязка адаптеров с несколькими параметрами
 * Анимация с адаптерами привязки
 * Связывающие преобразователи и обратные преобразователи
 * Привязка данных с помощью ViewModels и Kotlin
 * Нет вызовов UI framework в действии
 * Тестирование

Features Особенности
--------

![Screenshot](https://github.com/googlesamples/android-databinding/blob/master/TwoWaySample/screenshots/screenshot2way.png)

## Two-way Data Binding Двусторонняя привязка данных

Two-way data binding is used twice in this sample: with a simple case (the toggle start/pause
button) and with a more complex feature (the number of sets input).
Двусторонняя привязка данных используется в этом примере дважды:
в простом случае (переключение start / pause кнопка) и с более сложной функцией (количество входных наборов).

### Simple two-way case Простой двухсторонний случай

In the layout, two-way is indicated with the `@={}` syntax:
В макете двухстороннее обозначается синтаксисом' @ = {} `

```xml
<ToggleButton
    android:checked="@={viewmodel.timerRunning}" />
```

Note the difference with one-way syntax which doesn't have the equals sign (`@{}`).
Обратите внимание на разницу с односторонним синтаксисом, который не имеет знака равенства (`@{}`).

This layout expression binds the `checked` attribute to `timerRunning` in the ViewModel.
This means that if the property in the ViewModel changes (for example, when the timer finishing),
the button will automatically show the new status.
Это выражение компоновки связывает `проверено` атрибут `таймер` в ViewModel.
Это означает, что если свойство в ViewModel изменяется (например, при завершении таймера),
кнопка автоматически покажет новый статус.

At the same time, if a user action modifies the `checked` attribute on the button, the ViewModel
will receive this signal, dispatching the event accordingly.
В то же время, если пользовательское действие изменяет атрибут "checked" на кнопке, то ViewModel
получит этот сигнал, отправляя событие соответствующим образом.

Two Way Data Binding requires the ViewModel property to be decorated with a
Двусторонняя привязка данных требует, чтобы свойство ViewModel было украшено
 [`@Bindable`](https://developer.android.com/reference/android/databinding/Bindable.html) annotation:

```kotlin
var timerRunning: Boolean
    @Bindable get() {
        return state == TimerStates.STARTED
    }
    set(value) {
        // These methods take care of calling notifyPropertyChanged()
        if (value) startButtonClicked() else pauseButtonClicked()
    }
```

**Getter**: When the `state` property changes in the ViewModel, `notifyPropertyChanged(BR.timerRunning)` must be
called to indicate that the UI should be updated with the new value obtained in the getter.
** Getter**: когда свойство 'state' изменяется в ViewModel, ' inotifypropertychanged(BR.таймер работает) ' должно быть
вызывается для указания на то, что пользовательский интерфейс должен быть обновлен новым значением, полученным в геттере.

**Setter**: User actions invoke the setter. The corresponding methods will only call `notifyPropertyChanged`
when the state changes, to avoid infinite loops.
** Сеттер**: действия пользователя вызывают сеттер. Соответствующие методы будут вызывать только ' notifyPropertyChanged`
когда состояние меняется, чтобы избежать бесконечных циклов.

### Complex two-way case Сложный двусторонний случай

The text attribute of an `EditText` is much more complicated to manage than the checked/unchecked
nature of a toggle button. On top of that, the requirement for this input view is to show the text
in a particular format (`Sets: %d/%d`) so the two-way syntax is similar but it includes a converter:
Текстовый атрибут " EditText` гораздо сложнее в управлении, чем проверенный/непроверенный
природа кнопки-тумблера. Кроме того, требование для этого вида ввода состоит в том, чтобы показать текст
в определенном формате ('Sets: %d/%d`) так что двусторонний синтаксис похож, но он включает в себя конвертер:

```xml
<EditText
    numberOfSets="@={NumberOfSetsConverters.setArrayToString(viewmodel.numberOfSets)}" />
```

`EditText` doesn't have a `numberOfSets` attribute so this implies there's a corresponding binding adapter.

In `NumberOfSetsBindingAdapters.kt`:

```kotlin
@BindingAdapter("numberOfSets")
@JvmStatic fun setNumberOfSets(view: EditText, value: String) {
    view.setText(value)
}
```

This sets the value from the ViewModel in the view.
Это устанавливает значение из ViewModel в представлении.

To complete the two-way syntax "@={}" handling, it's also required to define a Binding Adapter
for a corresponding synthetic attribute with the "AttrChanged" suffix:
Чтобы завершить двустороннюю обработку синтаксиса " @ = {}", также необходимо определить адаптер привязки
для соответствующего синтетического атрибута с суффиксом "Attr Changed" :

```kotlin
@BindingAdapter(value = ["numberOfSetsAttrChanged"])
@JvmStatic fun setListener(view: EditText, listener: InverseBindingListener?) {
    view.onFocusChangeListener = View.OnFocusChangeListener { focusedView, hasFocus ->
        val textView = focusedView as TextView
        if (hasFocus) {
            // Delete contents of the EditText if the focus entered.
            textView.text = ""
        } else {
            // If the focus left, update the listener
            listener?.onChange()
        }
    }
}
```

In this adapter you normally set the listeners that will detect changes in the view. Note that it
includes an InverseBindingListener which needs to be called when we want to tell the data binding
system that there's been a change. This, in turn, triggers calls to the InverseBindingAdapter:
В этом адаптере вы обычно устанавливаете прослушиватели, которые будут обнаруживать изменения в представлении.
Обратите внимание, что это включает в себя слушатель обратной привязки, который должен быть вызван,
когда мы хотим сообщить о привязке данных система, в которой произошли изменения.
Это, в свою очередь, запускает вызовы адаптера обратной привязки:

```kotlin
@InverseBindingAdapter(attribute = "numberOfSets")
@JvmStatic fun getNumberOfSets(editText: EditText): String {
    return editText.text.toString()
}
```

See `NumberOfSetsBindingAdapters.kt` for alternatives and an example on how to use converters
with two-way data binding.
См. Раздел "Количество Наборов Адаптеров Привязки".КТ для альтернативы и примера по использованию преобразователей
с двусторонней привязкой данных.

## Alternatives two-way data binding Альтернативы двусторонней привязке данных

Two-way data binding is an advanced feature that can be complicated. Instead of using the library's
component to automate and remove boilerplate, beginners can opt for a more verbose but easier to
understand approach, using one-way data binding.
Двусторонняя привязка данных-это продвинутая функция, которая может быть сложной. Вместо того чтобы пользоваться библиотекой
компонент для автоматизации и удаления шаблонной формы, новички могут выбрать более подробный, но более простой в использовании
поймите подход, использующий одностороннюю привязку данных.

The `EditText` that manages the initial timer value has the following attributes:
"EditText`, управляющий начальным значением таймера, имеет следующие атрибуты:

```xml
<EditText
    android:text="@{Converter.fromTenthsToSeconds(viewmodel.timePerWorkSet)}"
    clearOnFocusAndDispatch="@{() -> viewmodel.timePerWorkSetChanged(setWorkTime.getText())}"
    />
```

Similarly to the previous section, the backing property in the ViewModel needs to be converted
before displaying and changes are sent to the ViewModel using a custom listener.
Как и в предыдущем разделе, необходимо преобразовать свойство backing в ViewModel
перед отображением изменения отправляются в ViewModel с помощью пользовательского прослушивателя.

See `BindingAdapters.kt` for the multiple binding adapters applied in this view and `Converter.kt`
for the logic that converts between formats.
Привязки Адаптеров Видеть.kt` для нескольких адаптеров привязки, применяемых в этом представлении,
и ' конвертер.КТ` для логики, которая преобразует между форматами.

## Binding adapters with multiple parameters Привязка адаптеров с несколькими параметрами

The progress bars in the sample need to be updated whenever either `progress` or the `max` property
changes. The Binding Adapter for this case looks like:
Индикаторы выполнения в образце должны обновляться всякий раз, когда либо` прогресс`, либо свойство` Макс'
изменения. Адаптер привязки для этого случая выглядит следующим образом:

```kotlin
@BindingAdapter(value=["android:max", "android:progress"], requireAll = true)
@JvmStatic fun updateProgress(progressBar: ProgressBar, max: Int, progress: Int) ...

```

See `BindingAdapters.kt` for this example and `AnimationBindingAdapters.kt` for more.

## Animations with binding adapters Анимация с адаптерами привязки

Animators are also elements that can be bound to data and they usually involve verbose code for
setup and execution. Data Binding lets you move this code out of the activities and fragments to
a more convenient and isolated location: a binding adapter.
Аниматоры также являются элементами, которые могут быть привязаны к данным, и они обычно включают подробный код для
настройка и выполнение. Привязка данных позволяет переместить этот код из действий и фрагментов в
более удобное и изолированное место: связующий адаптер.

For animations, two binding adapters are created in `AnimationBindingAdapters.kt`. They control
the background color and some Constraint Layout parameters, directly bound to properties in the
ViewModel.
Для анимации два адаптера привязки создаются в разделе "адаптеры привязки анимации".КТ'. Они контролируют
цвет фона и некоторые параметры компоновки ограничений, непосредственно связанные со свойствами в
модель представления.

## Binding converters and inverse converters Связующие преобразователи и обратные преобразователи

Binding converters allow you to convert data to a format required by the binding adapter.
Конвертеры привязки позволяют конвертировать данные в формат, необходимый адаптеру привязки.

See `Converter.kt` for one-way converters and `NumberOfSetsBindingAdapters.kt` for two-way
converters.

## Data Binding with ViewModels and Kotlin Привязка данных с помощью ViewModels и Kotlin

ViewModels are a perfect fit for data binding because they expose data and state to the view and
they survive configuration changes such as rotations.
ViewModels идеально подходят для привязки данных, потому что они предоставляют данные и состояние для представления и
они выдерживают изменения конфигурации, такие как вращения.

Common mistakes when using Kotlin with the Data Binding Library include:
Распространенные ошибки при использовании Kotlin с библиотекой привязки данных включают в себя:
 * Forgetting the `@JvmStatic` annotation in Binding Adapters inside an object or class.
 * Забывание аннотации "@JvmStatic " в адаптерах привязки внутри объекта или класса.
Alternatively you can place the functions in the top level of a file so it generates a public static
method.
В качестве альтернативы вы можете разместить функции на верхнем уровне файла,
чтобы он генерировал общедоступную статику метод.
 * Annotation parameters syntax
 * Синтаксис параметров аннотаций

## No UI framework calls in activity

One of the important features of data binding is that is frees activities and fragments from making
the UI calls, moving them to the layouts and Binding Adapters. However, not all
framework calls need to be moved.
Одной из важных особенностей привязки данных является то, что она освобождает действия и фрагменты от создания
пользовательский интерфейс вызывает их, перемещая в макеты и привязывая адаптеры. Впрочем, не все
вызовы фреймворка должны быть перемещены.
In this sample, the activity is responsible for ViewModel creation, binding and
managing the Shared Preferences, but there are no UI calls.
В этом примере действие отвечает за создание ViewModel, привязку и
управление общими настройками, но нет никаких вызовов пользовательского интерфейса.

This sample uses a relatively complex `IntervalTimerViewModel` that is exposing the data, receiving
events and holding state for a relatively complex screen. There are multiple advantages to this:
it makes very clear where a piece of code belongs to, it prevents activities from holding state,
and it generates very reusable code (binding adapters).
В этом примере используется относительно сложная "модель просмотра интервального таймера", которая предоставляет данные, получая
события и состояние удержания для относительно сложного экрана. У этого есть множество преимуществ:
это очень ясно показывает, к чему относится фрагмент кода, он предотвращает удержание активности в состоянии,
и он генерирует очень многоразовый код (привязка адаптеров).

## Testing Тестирование

There are no special considerations necessary regarding testing and the Data Binding Library.
There is a UI test class that checks part of the interaction and a unit test class that
verifies some logic in the ViewModel.
Нет никаких особых соображений, необходимых для тестирования и библиотеки привязки данных.
Существует тестовый класс пользовательского интерфейса, который проверяет часть взаимодействия,
и модульный тестовый класс, который проверяет некоторую логику в ViewModel.

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
