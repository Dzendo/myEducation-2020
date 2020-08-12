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

package com.example.android.databinding.basicsample.util

import androidx.databinding.BindingConversion
import android.view.View

/**
 * In order to show a View only when it has more than 0 likes, we pass this expression to its
 * visibilty property:
 * Чтобы показать вид только тогда, когда он имеет больше 0 лайков, мы передаем это выражение его владельцу.
 * свойство видимости:
 *
 * `android:visibility="@{ConverterUtil.isZero(viewmodel.likes)}"`
 *
 * This converts "likes" (an Int) into a Boolean. See [BindingConverters] for the conversion
 * from Boolean to a visibility integer.
 * Это преобразует "нравится" (Int) в логическое значение. См. [привязка конвертеры для преобразования
 * от логического до целого числа видимости.
 */
/*
Если View перед отображением переменную, связанную с объектом, необходимо отформатировать,
перевести или изменить каким-либо образом, можно использовать Converter объект.
 */
object ConverterUtil {
    @JvmStatic fun isZero(number: Int): Boolean {
        return number == 0
    }
}

/**
 * The number of likes is an integer and the visibility attribute takes an integer
 * (VISIBLE, GONE and INVISIBLE are 0, 4 and 8 respectively), so we use this converter.
 * * Количество лайков является целым числом, а атрибут видимости принимает целое число
 * (VISIBLE, GONE и INVISIBLE равны 0, 4 и 8 соответственно), поэтому мы используем этот конвертер.
 *
 * There is no need to specify that this converter should be used. [BindingConversion]s are
 * applied automatically.
 * Нет необходимости указывать, что этот конвертер должен использоваться. [[Обязательного обращения]с
 * применяется автоматически.
 */
object BindingConverters{

    @BindingConversion
    @JvmStatic fun booleanToVisibility(isNotVisible: Boolean): Int {
        return if (isNotVisible) View.GONE else View.VISIBLE
    }
}

