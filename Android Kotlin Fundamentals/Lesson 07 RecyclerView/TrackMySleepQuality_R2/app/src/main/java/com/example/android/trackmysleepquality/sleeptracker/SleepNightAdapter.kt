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
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding

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
class SleepNightAdapter: ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {
    // 2. Определите источник данных. (Recycler не будет исп эти данные напрямую)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = getItem(position)
// 9.1 Реорганизовать логику в onBind()отдельную функцию с именем bind () :
    holder.bind(item)
    // 7.8.16. Запустите приложение, и вы должны увидеть список в стиле!
}
    // 5. Для адаптера требуется этот метод, но мы не будем его здесь заполнять
    // 6. !! Добавлен в Utils.kt class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)
    // 7. Убедитесь, что код компилируется и запускается без ошибок. элементы еще не появятся на экране
    // 7.8.14. Измените тип возвращаемого значения onCreateViewHolder () на ViewHolderи надуйте list_item_sleep_nightвместо text_item_view:
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // 10.1 рефакторинг, инкапсулировав логику для создания ViewHolder в метод с именем from (),
        // который мы затем поместим в объект-компаньон в классе ViewHolder
        return ViewHolder.from(parent)
    }

    // 7.8.11. Внутри класса SleepNightAdapter создайте ViewHolder класс, который расширяется RecyclerView.ViewHolder.
    class ViewHolder private constructor(val binding: ListItemSleepNightBinding)
        : RecyclerView.ViewHolder(binding.root) {

        // 9.1.1 В новой bind()функции конвертируйте holder параметр в получатель.
        // 9.1.2 Вырежьте всю bind() функцию и вставьте ее в ViewHolder класс,
        // затем удалите ее View Holder и private модификаторы.
        // 9.1.3. Вырежьте строку val res = holder.itemView.context.resources из onBindViewHolder()
        // и вставьте ее в функцию bind (), затем удалите параметр res из bind ().
        // 9.1.4 Создайте и запустите приложение и убедитесь, что оно работает точно так же, как и раньше.
        fun bind(item: SleepNight) {
            binding.sleep = item
            binding.executePendingBindings()  // попоросить привязку выполнить сразу
        }
        companion object {
            //10.1 Инкапсулируйте логику для создания ViewHolder.
            //10.2 Переместить from код в companion object.
            //10.3 Измените ViewHolder объявление класса на private constructor:
            //10.4 Наконец, измените оператор возврата в onBindViewHolder
            // на return ViewHolder.from(parent).
            //10.5 Запустите приложение и убедитесь, что оно работает точно так же, как и до рефакторинга.
            fun from(parent: ViewGroup): ViewHolder {
                // 11. In SleepNightAdapter, onCreateViewHolder(), inflate the text_item_view layout and return the ViewHolder.
                // задача - выдавать вид каждый раз когда просят сюда, parent: ViewGroup - какой тип надо
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}
class SleepNightDiffCallback : DiffUtil.ItemCallback<SleepNight>() {
    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem.nightId == newItem.nightId
    }

    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
       return oldItem == newItem  // проверит все поля т.к. data class SleepNight
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

// 9. и 10. Рефакторинг кода с выносом Bind и From в class ViewHolder и его инкапсулирование
// После этого адаптер просто адаптирует (зовет) class ViewHolder с его методами