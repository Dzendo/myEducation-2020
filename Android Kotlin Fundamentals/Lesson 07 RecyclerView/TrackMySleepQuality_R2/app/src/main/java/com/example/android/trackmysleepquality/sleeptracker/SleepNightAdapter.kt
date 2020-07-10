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

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.TextItemViewHolder
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
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
//class SleepNightAdapter: RecyclerView.Adapter<TextItemViewHolder>() {
//  7.8.13. Обновите SleepNightAdapter для использования ViewHolder:
class SleepNightAdapter: RecyclerView.Adapter<SleepNightAdapter.ViewHolder>() {
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
    // 7.8.15. Обновление на BindViewHolder.
    //Измените onBindViewHolder его holder: ViewHolder на параметр и
    // обновите представления, ViewHolder чтобы отображать ресурсы значков вместо цветов.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = data[position]
    val res = holder.itemView.context.resources
    holder.sleepLength.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
    holder.quality.text = convertNumericQualityToString(item.sleepQuality, res)

    holder.qualityImage.setImageResource(when (item.sleepQuality) {
        0 -> R.drawable.ic_sleep_0
        1 -> R.drawable.ic_sleep_1
        2 -> R.drawable.ic_sleep_2
        3 -> R.drawable.ic_sleep_3
        4 -> R.drawable.ic_sleep_4
        5 -> R.drawable.ic_sleep_5
        else -> R.drawable.ic_sleep_active
    })
    when {
        item.sleepQuality <= 1 ->  holder.quality.setTextColor(Color.RED) // red
        item.sleepQuality >= 4 ->  holder.quality.setTextColor(Color.GREEN) // green
        else -> holder.quality.setTextColor(Color.BLACK) // black
    }
    // 7.8.16. Запустите приложение, и вы должны увидеть список в стиле!
}
    // 5. Для адаптера требуется этот метод, но мы не будем его здесь заполнять
    // 6. !! Добавлен в Utils.kt class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)
    // 7. Убедитесь, что код компилируется и запускается без ошибок. элементы еще не появятся на экране
    // 7.8.14. Измените тип возвращаемого значения onCreateViewHolder () на ViewHolderи надуйте list_item_sleep_nightвместо text_item_view:
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // 11. In SleepNightAdapter, onCreateViewHolder(), inflate the text_item_view layout and return the ViewHolder.
        // задача - выдавать вид каждый раз когда просят сюда, parent: ViewGroup - какой тип надо
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.list_item_sleep_night, parent, false)
        return ViewHolder(view)
    }

    // 7.8.11. Внутри класса SleepNightAdapter создайте ViewHolder класс, который расширяется RecyclerView.ViewHolder.
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // 7.8.12. Используйте findViewById для поиска просмотров.
        val sleepLength: TextView = itemView.findViewById(R.id.sleep_length)
        val quality: TextView = itemView.findViewById(R.id.quality_string)
        val qualityImage: ImageView = itemView.findViewById(R.id.quality_image)

    }
}
// 13. in SleepTrackerFragment, create a new SleepNightAdapter, and use binding to associate it with the RecyclerView:
//binding.sleepList.adapter = adapter


//<!-- 7.8.1. В list_item_sleep_night, добавьте представления ConstraintLayout для построения дизайна ниже -->
//<!-- 7.8.2. Добавить Добавить ImageView и установить его id в quality_image: -->
//<!-- 7.8.3. Добавьте TextView справа от quality_image и установите для его id значение sleep_length: -->
//<!--7.8.4. Добавьте TextView справа от quality_image и установите для его id значение sleep_length: -->

//<!-- 7.8.11. Внутри класса SleepNightAdapter создайте ViewHolder класс, который расширяется RecyclerView.ViewHolder.-->
//<!-- 7.8.12. Используйте findViewById для поиска просмотров. -->
//<!-- 7.8.13. Обновите SleepNightAdapter для использования ViewHolder: -->
//<!-- 7.8.14. Измените тип возвращаемого значения onCreateViewHolder () на ViewHolder и надуйте list_item_sleep_nightвместо text_item_view: -->
//<!-- 7.8.15. Обновление на BindViewHolder. чтобы отображать ресурсы значков вместо цветов.-->
//<!-- 7.8.16. Запустите приложение, и вы должны увидеть список в стиле! -->