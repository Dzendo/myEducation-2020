/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.databinding.twowaysample.ui

import android.content.Context
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.InverseMethod
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.android.databinding.twowaysample.R


/**
 * The [EditText] that controls the number of sets is using two-way Data Binding. Applying a
 * 2-way expression to the `android:text` attribute of the EditText triggers an update on every
 * keystroke. This is an alternative implementation that uses a [View.OnFocusChangeListener]
 * instead.
 * [EditText], который управляет количеством наборов, использует двустороннюю привязку данных.
 * Применение 2-стороннее выражение для атрибута "android:text" EditText запускает обновление для каждого
 * нажатие клавиши.
 * Это альтернативная реализация, которая использует [View.OnFocusChangeListener] вместо.
 *
 * `numberOfSetsAttrChanged` creates a listener that triggers when the focus is lost
 * `numberOfSetsAttrChanged` создает прослушиватель, который срабатывает, когда фокус теряется
 *
 * `hideKeyboardOnInputDone` (in a different file) will clear focus when the `Done` action on
 * the keyboard is dispatched, triggering `numberOfSetsAttrChanged`.
 * "скрыть клавиатуру при вводе Done" (в другом файле) очистит фокус, когда действие "Done" на
 * * клавиатура отправляется, вызывая `numberOfSetsAttrChanged`.
 *
 */
object NumberOfSetsBindingAdapters {

    /**
     * Needs to be used with [NumberOfSetsConverters.setArrayToString].
     * * * Необходимо использовать с преобразователями [количество наборов.set ArrayToString].
     */
    @BindingAdapter("numberOfSets")
    @JvmStatic fun setNumberOfSets(view: EditText, value: String) {
        view.setText(value)
    }

    /**
     * Called when the [InverseBindingListener] of the `numberOfSetsAttrChanged` binding adapter
     * is notified of a change.
     * Вызывается, если [обратная привязка слушателя] в `numberOfSetsAttrChanged` обязательный адаптер
     * уведомляется об изменении.
     *
     * Used with the inverse method of [NumberOfSetsConverters.setArrayToString], which is
     * [NumberOfSetsConverters.stringToSetArray].
     * Используется с обратным методом [NumberOfSetsConverters.setArrayToString], который является
     * [NumberOfSetsConverters.stringToSetArray].
     */
    @InverseBindingAdapter(attribute = "numberOfSets")
    @JvmStatic fun getNumberOfSets(editText: EditText): String {
        return editText.text.toString()
    }

    /**
     * That this Binding Adapter is not defined in the XML. "AttrChanged" is a special
     * suffix that lets you manage changes in the value, using two-way Data Binding.
     * Что этот адаптер привязки не определен в XML. ""Attrchange" - это специальный продукт.
     * суффикс, позволяющий управлять изменениями значения с помощью двусторонней привязки данных.
     *
     * Note that setting a [View.OnFocusChangeListener] overrides other listeners that might be set
     * with `android:onFocusChangeListener`. Consider supporting both in the same binding adapter
     * with `requireAll = false`. See [android.databinding.adapters.CompoundButtonBindingAdapter]
     * for an example.
     * Обратите внимание, что установка [View.OnFocusChangeListener] переопределяет другие прослушиватели, которые могут быть установлены
     * с помощью "android:onFocusChangeListener". Рассмотрите возможность поддержки обоих в одном и том же адаптере привязки
     * с `requireAll = false'. См. [android.привязка данных.адаптеры.Соединительный Адаптер CompoundButton]
     * для примера.
     * Это доделывает адаптер numberOfSets для двухсторонней апивязке -- обязательный специальный адапрет
     */
    @BindingAdapter("numberOfSetsAttrChanged")
    @JvmStatic fun setListener(view: EditText, listener: InverseBindingListener?) {
        view.onFocusChangeListener = View.OnFocusChangeListener { focusedView, hasFocus ->
            val textView = focusedView as TextView
            if (hasFocus) {
                // Delete contents of the EditText if the focus entered.
                // Удалить содержимое редактируемого текста, если фокус введен.
                textView.text = ""
            } else {
                // If the focus left, update the listener
                // Если фокус ушел, обновите прослушиватель
                listener?.onChange()
            }
        }
    }

    /* This sample showcases the NumberOfSetsConverters below, but note that they could be used also like:
      В этом примере показано количество преобразователей наборов ниже,
       но обратите внимание, что они могут быть использованы так же: */

@BindingAdapter("numberOfSets_alternative")
@JvmStatic fun setNumberOfSets_alternative(view: EditText, value: Array<Int>) {
    view.setText(String.format(
            view.resources.getString(R.string.sets_format,
                    value[0] + 1,
                    value[1])))
}

@InverseBindingAdapter(attribute = "numberOfSets_alternative")
@JvmStatic fun getNumberOfSets_alternative(editText: EditText): Array<Int> {
    if (editText.text.isEmpty()) {
        return arrayOf(0, 0)
    }

    return try {
        arrayOf(0, editText.text.toString().toInt()) // First item is not passed Первый пункт не пройден
    } catch (e: NumberFormatException) {
        arrayOf(0, 0)
    }
}
}

/**
* Converters for the number of sets attribute.
* Преобразователи для атрибута количество наборов.
*/
object NumberOfSetsConverters {

/**
 * Used with `numberOfSets` to convert from array to String.
 * Используется с `numberOfSets для преобразования из массива в строку.
 */
//Это инвертированный МЕТОД а не адаптер, т.е. fun stringToSetArray является инвертированным к fun setArrayToString
@InverseMethod("stringToSetArray")
@JvmStatic fun setArrayToString(context: Context, value: Array<Int>): String {
    return context.getString(R.string.sets_format, value[0] + 1, value[1])
}

/**
 * This is the Inverse Method used in `numberOfSets`, to convert from String to array.
 * Это обратный метод, используемый в `numberOfSets`, чтобы преобразовать строку в массив.
 *  numberOfSets="@={NumberOfSetsConverters.setArrayToString(context, viewmodel.numberOfSets)}"
 * Note that Context is passed Обратите внимание, что контекст передается
 */

@JvmStatic fun stringToSetArray(unused: Context, value: String): Array<Int> {
    // Converts String to long  Преобразует строку в длинную
    if (value.isEmpty()) {
        return arrayOf(0, 0)
    }

    return try {
        arrayOf(0, value.toInt()) // First item is not passed Первый пункт не пройден
    } catch (e: NumberFormatException) {
        arrayOf(0, 0)
    }
}
}
