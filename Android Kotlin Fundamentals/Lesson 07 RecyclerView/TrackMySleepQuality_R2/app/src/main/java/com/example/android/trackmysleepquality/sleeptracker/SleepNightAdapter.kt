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
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.TextItemViewHolder
import com.example.android.trackmysleepquality.database.SleepNight

/**
 * Цель этого адартеоа - составить список сна ночью и адаптировать его во что-то,
 * что переработчик может использовать для отображения на экране
 */

// первое что добавитьв XML: 
// (и recyclerview.widget.RecyclerView в layout\fragment_sleep_tracker.xml)
// с  app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager (пока)

// Сначала надо добавить ПЕРЕРАБОТЧИК: 
// 1. Создайте класс SleepNightAdapter. (пока от TextItemViewHolder)
class SleepNightAdapter: RecyclerView.Adapter<TextItemViewHolder>() {
    // 2. Определите источник данных. (Recycler не будет исп эти данные напрямую)
    var data = listOf<SleepNight>()
            // 12. add a custom setter to data that calls notifyDataSetChanged()
            // and tell Kotlin to save the new value by setting field = value.
        set(value) { // При приеме новых данных сразу известить RecyclerView
            field = value
            notifyDataSetChanged()  // запросит и перерисует новые данные (грубо - весь список)
        }
    // 3. Переопределить getItemCount() - размер данных
    override fun getItemCount(): Int = data.size
// 4. Функция должна извлечь элемент из списка данных и
// установить holder.textView.text в item.sleepQuality.toString(). (дает элемент для позиции)
    override fun onBindViewHolder(holder:  TextItemViewHolder, position: Int) {
    val item = data[position]
    holder.textView.text = item.sleepQuality.toString()  // просто номера пока
    }
    // 5. Для адаптера требуется этот метод, но мы не будем его здесь заполнять
    // 6. !! Добавлен в Utils.kt class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)
    // 7. Убедитесь, что код компилируется и запускается без ошибок. элементы еще не появятся на экране
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        // 11. In SleepNightAdapter, onCreateViewHolder(), inflate the text_item_view layout and return the ViewHolder.
        // задача - выдавать вид каждый раз когда просят сюда, parent: ViewGroup - какой тип надо
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.text_item_view, parent, false) as TextView
        return TextItemViewHolder(view)
    }


}
// 13. in SleepTrackerFragment, create a new SleepNightAdapter, and use binding to associate it with the RecyclerView:
//binding.sleepList.adapter = adapter 