/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// используется в fragment_garden.xml (не светить значек фильтра) и использовался в fragment_plant_detail.xml (светить значек +)
/*
чтобы использовать такую функцию надо:
 binding.hasPlantings = !result.isNullOrEmpty()

<data>
        <variable
                name="hasPlantings"
                type="boolean" />
    </data>

 app:isGone="@{!hasPlantings}"
 */

package com.google.samples.apps.sunfloweras.adapters

import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.samples.apps.sunfloweras.R
// из PlantListFragment для RecyclerView 07,1-5
//  Android Kotlin Fundamentals 07.2.7: DiffUtil and data binding with RecyclerView
// TODO: 7. Task: Create binding adapters

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
    }
}

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

// был для FAB отключил AS для +-
/*@BindingAdapter("isGone")
fun bindIsGone(view: FloatingActionButton, isGone: Boolean?) {
    if (isGone == null || isGone) {
        view.hide()
    } else {
        view.show()
    }
}*/

@BindingAdapter("renderHtml")
fun bindRenderHtml(view: TextView, description: String?) {
    if (description != null) {
        view.text = HtmlCompat.fromHtml(description, FROM_HTML_MODE_COMPACT)
        view.movementMethod = LinkMovementMethod.getInstance()
    } else {
        view.text = ""
    }
}

@BindingAdapter("wateringText")
fun bindWateringText(textView: TextView, wateringInterval: Int) {
    val resources = textView.context.resources
    val quantityString = resources.getQuantityString(R.plurals.watering_needs_suffix,
        wateringInterval, wateringInterval)

    textView.text = quantityString
}
/*
BindingAdapter применяется к методам, которые используются для манипулирования значениями how с помощью выражений
* установите взгляды. Самый простой пример - иметь открытый статический метод, который принимает представление
* и значение для установки:
* <p><pre>
 *<code>@BindingAdapter("android:bufferType")
 * public static void setBufferType(TextView view, TextView.BufferType bufferType) {
 *     view.setText(view.getText(), bufferType);
 * }</code></pre>
* В приведенном выше примере, когда android:bufferType используется в TextView, метод
* вызывается setBufferType.
* <P>в
* Также можно взять ранее установленные значения, если старые значения перечислены первыми:
* <p><pre>
 *<code>@BindingAdapter("android:onLayoutChange")
 * public static void setOnLayoutChangeListener(View view, View.OnLayoutChangeListener oldValue,
 *                                              View.OnLayoutChangeListener newValue) {
 *     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
 *         if (oldValue != null) {
 *             view.removeOnLayoutChangeListener(oldValue);
 *         }
 *         if (newValue != null) {
 *             view.addOnLayoutChangeListener(newValue);
 *         }
 *     }
 * }</code></pre>
* Если адаптер привязки может также принимать несколько атрибутов, он будет вызываться только тогда, когда все атрибуты будут совпадать.
* атрибуты, связанные с адаптером привязки, имеют связанные с ними выражения привязки.
* Это полезно, когда существуют необычные взаимодействия между атрибутами. Например:
<p><pre>
 *<code>@BindingAdapter({"android:onClick", "android:clickable"})
 * public static void setOnClick(View view, View.OnClickListener clickListener,
 *                               boolean clickable) {
 *     view.setOnClickListener(clickListener);
 *     view.setClickable(clickable);
 * }</code></pre>
* Порядок параметров должен совпадать с порядком атрибутов в значениях в таблице.
* BindingAdapter.
* <P>в
* Адаптер привязки может дополнительно принять класс, расширяющий компонент DataBinding, в качестве первого
* параметр также. Если это так, то ему будет передано значение, переданное во время привязки, либо
* непосредственно в методе надувания или косвенно, используя значение из
* {@link DataBindingUtil#getdefaultкомпонент()}.
* <P>в
* Если адаптер привязки является методом экземпляра, то сгенерированный компонент DataBinding Component будет иметь
* геттер для получения экземпляра класса BindingAdapter, который будет использоваться для вызова метода.
 */