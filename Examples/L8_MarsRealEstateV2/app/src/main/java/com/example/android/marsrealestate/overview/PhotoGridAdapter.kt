/*
 * Copyright 2018, The Android Open Source Project
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

package com.example.android.marsrealestate.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsrealestate.databinding.GridViewItemBinding
import com.example.android.marsrealestate.network.MarsProperty
// 12.2.2 Добавьте импорт для ListAdapter и MarsProperty. Не забудьте импортировать androidx.recyclerview.widget.ListAdapter.

// 12.2 Давайте реализуем Адаптер RecyclerView в нашем приложении, чтобы показать наш список фотографий марсохода.

// 12.2.1 В PhotoGridAdapter.kt создании класса «PhotoGridAdapter» , который простирается RecyclerView ListAdapter с DiffCallback.
// Пусть он использует обычай, PhotoGridAdapter.MarsPropertyViewHolder чтобы представить список <MarsProperty>объектов:
// 15.3.2 Добавьте OnClickListener параметр в PhotoGridAdapter объявление класса
class PhotoGridAdapter(val onClickListener: OnClickListener):
    ListAdapter<MarsProperty, PhotoGridAdapter.MarsPropertyViewHolder>(DiffCallback) {
//class PhotoGridAdapter : ListAdapter<MarsProperty, PhotoGridAdapter.MarsPropertyViewHolder>(DiffCallback) {
    // 12.2.3 Используйте Control-I, чтобы Android Studio изменила требования onCreateViewHolder() и onBindViewHolder() методы адаптера:
    // 12.2.6 Реализуем т.е заполним пустые onCreateViewHolder()и onBindViewHolder()методы:
    // onCreateViewHolder необходимо вернуть новый адаптер представления Mars, созданный раздуванием привязки макета
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoGridAdapter.MarsPropertyViewHolder =
         MarsPropertyViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    // перерабатывать элемент сетки функцией bind см ниже в классе MarsPropertyViewHolder.bind
    override fun onBindViewHolder(holder: PhotoGridAdapter.MarsPropertyViewHolder, position: Int) {
        //holder.bind(getItem(position))
         val marsProperty = getItem(position)
        // 15.3.3 настроить, onClickListener()чтобы передать marsProperty нажатие кнопки
        holder.itemView.setOnClickListener {
            onClickListener.onClick(marsProperty)
        }
         holder.bind(marsProperty)
    }
//12.2.4  Создайте DiffCallback сопутствующий объект и переопределите его два обязательных areItemsTheSame()метода:
    companion object DiffCallback: DiffUtil.ItemCallback<MarsProperty>(){
        override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean = oldItem === newItem // равны ссылки
        override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean = oldItem.id == newItem.id
    }
// 12.2.5 Создайте MarsPropertyViewHolder внутренний класс и реализуйте bind()метод, который включает в себя привязку к marsProperty:
    class MarsPropertyViewHolder(private var binding: GridViewItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(marsProperty: MarsProperty) {
            binding.property = marsProperty
            binding.executePendingBindings()  // не забывать сразу вызывать рисовку
        }
    }

// 15.3.1 внутренний OnClickListener класс с лямбдой в его конструкторе, который инициализирует соответствующую onClick функцию
    class OnClickListener(val clickListener: (marsProperty: MarsProperty) -> Unit) {
        fun onClick(marsProperty:MarsProperty) = clickListener(marsProperty)
    }
}
