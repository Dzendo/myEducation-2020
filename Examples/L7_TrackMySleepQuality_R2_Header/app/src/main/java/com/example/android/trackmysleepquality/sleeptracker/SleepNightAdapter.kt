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
 */

package com.example.android.trackmysleepquality.sleeptracker


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Цель этого адаптера - составить список сна ночью и адаптировать его во что-то,
 * что переработчик может использовать для отображения на экране
 */
//23,6 Затем, чтобы выяснить, какой тип представления вернуть, добавьте проверку,
// чтобы увидеть, какой тип элемента находится в списке:
private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

//class SleepNightAdapter(private val clickListener: SleepNightListener):
//    ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {
//23,3 Обновите объявление SleepNightAdapter для поддержки любого типа держателя представления.
//class SleepNightAdapter:
//    ListAdapter<SleepNight, RecyclerView.ViewHolder>(SleepNightDiffCallback())
//23,4 Теперь давайте обновимся, ListAdapter чтобы содержать список DataItem вместо списка SleepNight:
class SleepNightAdapter(private val clickListener: SleepNightListener):
    ListAdapter<DataItem, RecyclerView.ViewHolder>(SleepNightDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)


  //  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
  //      holder.bind(clickListener, getItem(position))
//}
    // 23,8 В onBindViewHolder()вам нужно разворачивать , DataItem чтобы пропускать SleepNight к ViewHolder
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
      when (holder) {
          is ViewHolder -> {
              val nightItem = getItem(position) as DataItem.SleepNightItem
              holder.bind(clickListener, nightItem.sleepNight)
          }
      }
    }
// Измените тип возврата onCreateViewHolder на RecyclerView.ViewHolder и обновите его,
// чтобы он возвращал правильный ITEM_VIEW_TYPE.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }

    //23,9 Вам понадобится способ конвертировать List<SleepNight>в List<DataItem>.
    // Вместо того, чтобы использовать submitList(), предоставленный ListAdapter,
    // для отправки вашего списка, вы будете использовать эту функцию,
    // чтобы добавить заголовок, а затем отправить список.
    fun addHeaderAndSubmitList(list: List<SleepNight>?) {
        // запустите сопрограмму в, adapterScope чтобы управлять списком
        adapterScope.launch {
            //если переданный список есть null, верните только заголовок,
            // в противном случае присоедините заголовок к заголовку списка,
            // а затем отправьте список.
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.SleepNightItem(it) }
            }
            // Затем переключитесь в Dispatchers.Main контекст, чтобы отправить список
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
        // Ваш код должен собираться и запускаться, и вы не увидите никакой разницы.
    }

    class ViewHolder private constructor(val binding: ListItemSleepNightBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: SleepNightListener, item: SleepNight) {
            binding.sleep = item
            binding.clickListener = clickListener
            binding.executePendingBindings()  // попоросить привязку выполнить обновление сразу
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
    // 23,2 Добавьте класс TextHolder внутри SleepNightAdapter класса.
    class TextViewHolder(view: View): RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.header, parent, false)
                return TextViewHolder(view)
            }
        }
    }
//23,6 Затем, чтобы выяснить, какой тип представления вернуть, добавьте проверку,
// чтобы увидеть, какой тип элемента находится в списке:
    // Затем переопределите getItemViewType и верните правильный тип представления элемента.
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.SleepNightItem -> ITEM_VIEW_TYPE_ITEM
        }
    }


}
// Умное обновление изменившихся элементов на экране, а не всех
//23,5 Обновите ваш DiffCallback для обработки DataItem вместо SleepNight:
class SleepNightDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}
// Вызывается из XML при нажатии на элемент списка RecyclerView через лямбду
class SleepNightListener(val clickListener: (sleepId: Long) -> Unit) {
    fun onClick(night: SleepNight) = clickListener(night.nightId)
}
// 23.1. Внизу SleepNightAdapter добавьте запечатанный класс с именем DataItem:
sealed class DataItem {
    data class SleepNightItem(val sleepNight: SleepNight): DataItem() {
        override val id = sleepNight.nightId
    }

    object Header: DataItem() {
        override val id = Long.MIN_VALUE
    }

    abstract val id: Long
}
/*
sleeptracker\class SleepNightAdapter
layout\fragment_sleep_tracker.xml
layout\list_item_sleep_night.xml
sleeptracker\BindingUtils.kt
Util.kt
sleeptracker\SleepTrackerFragment.kt
 */