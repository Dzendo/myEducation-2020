/*
 * Copyright 2018, The Android Open Source Project
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

package com.example.android.navigationsample

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation

/**
 * Shows a static leaderboard with three users.
 * Показывает статическую таблицу лидеров с тремя пользователями.
 * Показывает через RecyclerView массив лидеров Стандартным LinerLayout
 * При этом через Адаптер RecyclerView.Adapter<MyAdapter.ViewHolder>
 * Навигируется переход к карточке при нажатии на строчку RecyclerView
 */
class Leaderboard : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_leaderboard, container, false)

        // адаптер объявлен классом в этом же файле внизу
        val viewAdapter = MyAdapter(arrayOf("Flo", "Ly", "Jo"))

        view.findViewById<RecyclerView>(R.id.leaderboard_list).run {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            // используйте этот параметр для повышения производительности, если вы знаете, что изменения
            // in content не изменяют размер макета RecyclerView
            setHasFixedSize(true)

            // specify an viewAdapter (see also next example)
            // укажите viewAdapter (см. Также следующий пример)
            adapter = viewAdapter
        }
        return view
    }

}

class MyAdapter(private val myDataset: Array<String>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    // Укажите ссылку на представления для каждого элемента данных
    // Сложные элементы данных могут нуждаться в более чем одном представлении для каждого элемента, и
    // вы предоставляете доступ ко всем представлениям для элемента данных в держателе представлений.
    // Каждый элемент данных в этом случае является просто строкой, которая отображается в текстовом представлении.
    class ViewHolder(val item: View) : RecyclerView.ViewHolder(item)


    // Create new views (invoked by the layout manager)
    // Создание новых представлений (вызывается менеджером компоновки)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        // create a new view
        // создать новый вид
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_view_item, parent, false)


        return ViewHolder(itemView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    // Заменить содержимое представления (вызывается диспетчером компоновки)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        // - получить элемент из вашего набора данных в этой позиции
        // - заменить содержимое представления этим элементом
        holder.item.findViewById<TextView>(R.id.user_name_text).text = myDataset[position]

        holder.item.findViewById<ImageView>(R.id.user_avatar_image)
                .setImageResource(listOfAvatars[position])

        // Ставится слушатель на строку RecyclerView
        holder.item.setOnClickListener {
      // формируется мячик для передачи в параметре user_profile
            val bundle = bundleOf("userName" to myDataset[position])
    // вызывается навигация для перехода с передачей параметров строки
            Navigation.findNavController(holder.item).navigate(
                    R.id.action_leaderboard_to_userProfile,
                bundle)
            // <action
            //    android:id="@+id/action_leaderboard_to_userProfile"
            //    app:destination="@id/user_profile"
           /* <fragment приемник аргумента
            android:id="@+id/user_profile"
            android:name="com.example.android.navigationsample.UserProfile"
            android:label="fragment_user_profile"
            tools:layout="@layout/fragment_user_profile">
                <argument android:name="userName"
                android:defaultValue="name"/>
                <deepLink app:uri="www.example.com/user/{userName}" />
            </fragment>*/
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    // Возвращает размер набора данных (вызывается менеджером компоновки)
    override fun getItemCount() = myDataset.size
}

private val listOfAvatars = listOf(
        R.drawable.avatar_1_raster, R.drawable.avatar_2_raster, R.drawable.avatar_3_raster)
