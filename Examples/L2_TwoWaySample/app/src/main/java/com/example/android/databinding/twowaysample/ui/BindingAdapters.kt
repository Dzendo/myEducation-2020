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
import android.os.Build
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.example.android.databinding.twowaysample.R


/**
 * Втыкнутое для @{} должно уметь отдать в розетку <-(get)
 * Втыкнутое для @={} должно уметь отдать в розетку И забрать из розетки ->(set)
 * и/Или должно иметь пометку инвертированный метод
 *
 * AS Адаптеры @BindingAdapter("property") имя property появляется СЛЕВА в файле XML? т.е Это во что втыкаем
 * AS @InverseMethod("property") property тоже СЛЕВА, но что отдает втыкнутому
 * Чтобы завершить двустороннюю обработку синтаксиса " @ = {}", также необходимо определить адаптер привязки
 * для соответствующего синтетического атрибута с суффиксом "Attr Changed"
 *
 *
 * A collection of [BindingAdapter]s for different UI-related tasks.
 * Коллекция [Binding Adapter]s для различных задач, связанных с пользовательским интерфейсом.
 *
 * In Kotlin you can write the Binding Adapters in the traditional way:
 * В Kotlin вы можете написать адаптеры привязки традиционным способом:
 *
 * ```
 * @BindingAdapter("property")
 * @JvmStatic fun propertyMethod(view: ViewClass, parameter1: Param1, parameter2: Param2...)
 * ```
 *
 * Or using extension functions: Или с помощью функций расширения:
 *
 * ```
 * @BindingAdapter("property")
 * @JvmStatic fun ViewClass.propertyMethod(parameter1: Param1, parameter2: Param2...)
 * ```
 *
 * See [EditText.clearTextOnFocus].
 *
 * Also, keep in mind that @JvmStatic is only necessary if you define the methods inside a class or
 * object. Consider moving the Binding Adapters to the top level of the file.
 * Кроме того, имейте в виду, что @JvmStatic необходим только в том случае, если вы определяете методы внутри класса или
 * объект. Рассмотрите возможность перемещения адаптеров привязки на верхний уровень файла.
 */
object BindingAdapters {

    /**
     * When defined in an [EditText], this [BindingAdapter] will clear the text on focus and
     * set the previous value if the user doesn't enter one. When the focus leaves, it calls
     * the listener that was passed as an argument.
     * При определении в [EditText] этот [адаптер привязки] очистит текст при фокусировке и
     * установите Предыдущее значение, если пользователь его не вводит. Когда фокус уходит, он зовет
     * слушатель, который был передан в качестве аргумента.
     *
     * Note that `android:selectAllOnFocus="true"` does something similar but not exactly the same.
     * Обратите внимание, что `android:selectAllOnFocus="true"` делает что-то похожее, но не совсем то же самое.
     *
     * @see [clearTextOnFocus] for a version without a listener.
     * @ @см. [clear Text OnFocus] для версии без прослушивателя.
     */
    @BindingAdapter("clearOnFocusAndDispatch")
    @JvmStatic fun clearOnFocusAndDispatch(view: EditText, listener: View.OnFocusChangeListener?) {
        view.onFocusChangeListener = View.OnFocusChangeListener { focusedView, hasFocus ->
            val textView = focusedView as TextView
            if (hasFocus) {
                // Delete contents of the EditText if the focus entered.
                // Удалить содержимое редактируемого текста, если фокус введен.
                view.setTag(R.id.previous_value, textView.text)
                textView.text = ""
            } else {
                if (textView.text.isEmpty()) {
                    val tag: CharSequence? = textView.getTag(R.id.previous_value) as CharSequence
                    textView.text = tag ?: ""
                }
                // If the focus left, update the listener
                // Если фокус ушел, обновите прослушиватель
                listener?.onFocusChange(focusedView, hasFocus)
            }
        }
    }

    /**
     * Clears the text on focus.
     * Очищает текст при фокусировке.
     *
     * This method is using extension functions. It's equivalent to:
     * Этот метод использует функции расширения. Это эквивалентно:
     * ```
     * @JvmStatic fun clearTextOnFocus(view: EditText, enabled: Boolean)...
     * ```
     */
    @BindingAdapter("clearTextOnFocus")
    @JvmStatic fun EditText.clearTextOnFocus(enabled: Boolean) {
        if (enabled) {
            clearOnFocusAndDispatch(this, null)
        } else {
            this.onFocusChangeListener = null
        }
    }

    /**
     * Hides keyboard when the [EditText] is focused.
     * Скрывает клавиатуру, когда [EditText] сфокусирован.
     *
     * Note that there can only be one [TextView.OnEditorActionListener] on each [EditText] and
     * this [BindingAdapter] sets it.
     * Обратите внимание, что может быть только один [TextView.OnEditorActionListener] на каждом [EditText] и
     * этот [адаптер привязки] устанавливает его.
     */
    @BindingAdapter("hideKeyboardOnInputDone")
    @JvmStatic fun hideKeyboardOnInputDone(view: EditText, enabled: Boolean) {
        if (!enabled) return
        val listener = TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                view.clearFocus()
                val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE)
                        as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
            false
        }
        view.setOnEditorActionListener(listener)
    }

    /*
     * Instead of having if-else statements in the XML layout, you can create your own binding
     * adapters, making the layout easier to read.
     * Вместо того чтобы иметь операторы if-else в XML-макете, вы можете создать свою собственную привязку
     * адаптеры, облегчающие чтение макета.
     *
     * Instead of * Вместо
     *
     * `android:visibility="@{viewmodel.isStopped ? View.INVISIBLE : View.VISIBLE}"`
     *
     * you use: вы пользуетесь:
     *
     * `android:invisibleUnless="@{viewmodel.isStopped}"`
     *
     */

    /**
     * Makes the View [View.INVISIBLE] unless the condition is met.
     * Представление [представление.Невидимый], если условие не будет выполнено.
     */
    @Suppress("unused")
    @BindingAdapter("invisibleUnless")
    @JvmStatic fun invisibleUnless(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    /**
     * Makes the View [View.GONE] unless the condition is met.
     * Делает вид [Вид.Ушел], если не будет выполнено условие.
     */
    @Suppress("unused")
    @BindingAdapter("goneUnless")
    @JvmStatic fun goneUnless(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    /**
     * In [ProgressBar], [ProgressBar.setMax] must be called before [ProgressBar.setProgress].
     * В [ProgressBar], [ProgressBar.setMax] должен быть вызван перед [ProgressBar.метод setprogress].
     * By grouping both attributes in a BindingAdapter we can make sure the order is met.
     * Группируя оба атрибута в адаптере привязки, мы можем убедиться, что порядок соблюден.
     *
     * Also, this showcases how to deal with multiple API levels.
     * Кроме того, это демонстрирует, как работать с несколькими уровнями API.
     */
    @BindingAdapter(value=["android:max", "android:progress"], requireAll = true)
    @JvmStatic fun updateProgress(progressBar: ProgressBar, max: Int, progress: Int) {
        progressBar.max = max
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress(progress, false)
        } else {
            progressBar.progress = progress
        }
    }

    @BindingAdapter("loseFocusWhen")
    @JvmStatic fun loseFocusWhen(view: EditText, condition: Boolean) {
        if (condition) view.clearFocus()
    }
}
