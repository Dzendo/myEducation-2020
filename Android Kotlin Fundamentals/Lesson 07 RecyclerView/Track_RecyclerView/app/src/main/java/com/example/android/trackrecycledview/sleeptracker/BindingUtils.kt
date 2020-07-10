package com.example.android.trackrecycledview.sleeptracker

/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.trackrecycledview.R
import com.example.android.trackrecycledview.convertDurationToFormatted
import com.example.android.trackrecycledview.convertNumericQualityToString
import com.example.android.trackrecycledview.database.SleepNight

// Шаг 1. Создайте адаптеры привязки
// Эта функция будет вашим адаптером для расчета и форматирования продолжительности сна.
@BindingAdapter("sleepDurationFormatted")
fun TextView.setSleepDurationFormatted(item: SleepNight?) {
    // привяжите данные к представлению, как вы это делали в ViewHolder.bind()
    // Вызов , convertDurationToFormatted() а затем установить text из TextView в форматированный текст
    item?.let {
        text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, context.resources)
    }
}

// Чтобы сообщить привязке данных об этом адаптере привязки, аннотируйте функцию с помощью
@BindingAdapter("sleepQualityString")
fun TextView.setSleepQualityString(item: SleepNight?) {
    item?.let {
        text = convertNumericQualityToString(item.sleepQuality, context.resources)
    }
}

// Третий адаптер привязки устанавливает изображение в представлении изображения
@BindingAdapter("sleepImage")
fun ImageView.setSleepImage(item: SleepNight?) {
    item?.let {
        setImageResource(when (item.sleepQuality) {
            0 -> R.drawable.ic_sleep_0
            1 -> R.drawable.ic_sleep_1
            2 -> R.drawable.ic_sleep_2

            3 -> R.drawable.ic_sleep_3

            4 -> R.drawable.ic_sleep_4
            5 -> R.drawable.ic_sleep_5
            else -> R.drawable.ic_sleep_active
        })
    }
}

/*
9. Резюме
DiffUtil:

RecyclerViewимеет класс, DiffUtilкоторый называется для расчета различий между двумя списками.
DiffUtilимеет класс, ItemCallBackкоторый вы расширили, чтобы выяснить разницу между двумя списками.
В ItemCallbackклассе, вы должны переопределить areItemsTheSame()и areContentsTheSame()методы.
ListAdapter:

Чтобы получить некоторые списки управления бесплатно, вы можете использовать ListAdapter класс вместо RecyclerView.Adapter.
Однако, если вы используете, ListAdapter вы должны написать свой собственный адаптер для других макетов,
поэтому эта кодовая метка показывает вам, как это сделать.
Чтобы открыть меню намерений в Android Studio, поместите курсор на любой элемент кода и нажмите Alt+Enter.
Это меню особенно полезно для рефакторинга кода и создания заглушек для реализации методов.
Меню является контекстно-зависимым, поэтому вам нужно разместить курсор точно, чтобы получить правильное меню.
Привязка данных:

Используйте привязку данных в макете элемента, чтобы связать данные с представлениями.
Связующие адаптеры:

Вы ранее использовали Transformations для создания строк из данных. Если вам нужно связать данные разных или сложных типов, предоставьте связывающие адаптеры, чтобы помочь их использовать.
Чтобы объявить адаптер привязки, определите метод, который принимает элемент и представление, и аннотируйте метод с помощью @BindingAdapter. В Kotlin вы можете написать адаптер привязки как функцию расширения для View. Передайте имя свойства, которое адаптер адаптирует. Например:
@BindingAdapter("sleepDurationFormatted")
В макете XML установите app свойство с тем же именем, что и адаптер привязки. Перейдите в переменную с данными. Например:
.app:sleepDurationFormatted="@{sleep}"
 */