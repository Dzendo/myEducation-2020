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
// Добавьте адаптер сетки фотографий
package com.example.android.marsrealestatec.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsrealestatec.databinding.GridViewItemBinding
import com.example.android.marsrealestatec.network.MarsProperty

//еперь у fragment_overview макета есть время, RecyclerView пока у grid_view_item макета есть один ImageView.
// На этом этапе вы связываете данные с RecyclerView помощью RecyclerView адаптера.

//class PhotoGridAdapter : ListAdapter<MarsProperty,
//        PhotoGridAdapter.MarsPropertyViewHolder>(DiffCallback) {

class PhotoGridAdapter( private val onClickListener: OnClickListener) :
        ListAdapter<MarsProperty,
                PhotoGridAdapter.MarsPropertyViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsPropertyViewHolder {
        // Метод должен возвращать новый , созданное надувание и использование из родительского контекста.
        return MarsPropertyViewHolder(GridViewItemBinding.inflate(
                LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MarsPropertyViewHolder, position: Int) {
// Здесь вы звоните , getItem()чтобы получить MarsProperty объект , связанный с текущим в RecyclerView положении,
// а затем передать это имущество к bind()методу в MarsPropertyViewHolder.
        val marsProperty = getItem(position)

        // Сделайте фотографию кликабельной, добавив onClickListener элемент сетки
        holder.itemView.setOnClickListener {
            onClickListener.onClick(marsProperty)
        }

        holder.bind(marsProperty)
    }
    companion object DiffCallback : DiffUtil.ItemCallback<MarsProperty>() {
        override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            //Ссылочной оператор равенства Использовать Котлин ( в ===), который возвращается,
            // true если ссылки на объект для oldItem и newItem те же.
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem.id == newItem.id
        }
    }
// нужна GridViewItemBinding переменная для привязки MarsProperty к макету, поэтому передайте переменную в MarsPropertyViewHolder.
// Поскольку базовый ViewHolder класс требует представления в своем конструкторе, вы передаете ему привязку корневого представления
    class MarsPropertyViewHolder(private var binding:
                                 GridViewItemBinding):
            RecyclerView.ViewHolder(binding.root) {
        // метод, который принимает MarsProperty объект в качестве аргумента и устанавливает binding.property для этого объекта.
        // Вызывать executePendingBindings() после установки свойства, которое приводит к немедленному выполнению обновления
        fun bind(marsProperty: MarsProperty) {
            binding.property = marsProperty  // no ОШИБКА
            binding.executePendingBindings()
        }

    }
    // Шаг 3: Настройте прослушиватели щелчков в сетевом адаптере и фрагмент
    class OnClickListener(val clickListener: (marsProperty:MarsProperty) -> Unit) {
        fun onClick(marsProperty:MarsProperty) = clickListener(marsProperty)
    }

}