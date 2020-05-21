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
 *
 */
// Этот файл будет содержать связывающие адаптеры, которые вы используете в приложении
package com.example.android.marsrealestatec

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.marsrealestatec.network.MarsProperty
import com.example.android.marsrealestatec.overview.MarsApiStatus
import com.example.android.marsrealestatec.overview.PhotoGridAdapter

//Шаг 3. Создайте адаптер привязки и вызовите Glide.
/*
Теперь у вас есть URL-адрес изображения для отображения, и пришло время начать работу с Glide для загрузки этого изображения.
На этом шаге вы используете адаптер привязки для получения URL-адреса из атрибута XML,
связанного с ImageView, и используете Glide для загрузки изображения.
Адаптеры привязки - это методы расширения, которые находятся между представлением и привязанными данными,
обеспечивая пользовательское поведение при изменении данных.
В этом случае пользовательским поведением является вызов Glide для загрузки изображения из URL в ImageView.
 */

// @BindingAdapter Аннотацию говорит связывание данных, которые вы хотите,
// чтобы это связывание адаптера выполняется, когда элемент XML имеет imageUrl атрибут.
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        // конечный Uriобъект использовал схему HTTPS
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        // чтобы загрузить изображение из Uri объекта в ImageView
        Glide.with(imgView.context)
                .load(imgUri)
                // Добавьте простые изображения загрузки и ошибок
                .apply(RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image))
                .into(imgView)
    }
}
// Шаг 4: Обновите макет и фрагменты
// app:imageUrl="@{viewModel.property.imgSrcUrl}"

// используйте BindingAdapter для инициализации PhotoGridAdapter со списком MarsProperty объектов.
// Использование a BindingAdapter для установки RecyclerView данных приводит к тому,
// что привязка данных автоматически соблюдает LiveData список MarsProperty объектов.
// Затем адаптер привязки вызывается автоматически при MarsProperty изменении списка.
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,
                     data: List<MarsProperty>?) {
    // Это говорит, RecyclerView когда новый список доступен.
    val adapter = recyclerView.adapter as PhotoGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("marsApiStatus")
fun bindStatus(statusImageView: ImageView,
               status: MarsApiStatus?) {
    when (status) {
        MarsApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        MarsApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        MarsApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}