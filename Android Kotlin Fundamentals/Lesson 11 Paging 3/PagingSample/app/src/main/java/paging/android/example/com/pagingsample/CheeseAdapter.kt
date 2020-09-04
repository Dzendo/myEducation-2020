/*
 * Copyright (C) 2017 The Android Open Source Project
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

package paging.android.example.com.pagingsample

import androidx.recyclerview.widget.DiffUtil
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter

/**
 * A simple PagedListAdapter that binds Cheese items into CardViews.
 * <p>
 * PagedListAdapter is a RecyclerView.Adapter base class which can present the content of PagedLists
 * in a RecyclerView. It requests new pages as the user scrolls, and handles new PagedLists by
 * computing list differences on a background thread, and dispatching minimal, efficient updates to
 * the RecyclerView to ensure minimal UI thread work.
 * <p>
 * If you want to use your own Adapter base class, try using a PagedListAdapterHelper inside your
 * adapter instead.
 *
 * Простой адаптер постраничного списка, который связывает элементы сыра в представления карт.
 * <P>в
 * PagedListAdapter-это RecyclerView.Базовый класс адаптера, который может представлять содержимое выгружаемых списков
 * в RecyclerView. Он запрашивает новые страницы по мере прокрутки пользователем и обрабатывает новый список страниц с помощью
 * вычисление различий списков в фоновом потоке и отправка минимальных эффективных обновлений в фоновый поток.
 * RecyclerView для обеспечения минимальной работы потока пользовательского интерфейса.
 * <P>в
 * Если вы хотите использовать свой собственный базовый класс адаптера, попробуйте использовать помощник адаптера PagedList внутри вашего устройства.
 * вместо адаптера.
 *
 * @see androidx.paging.PagedListAdapter
 * @see androidx.paging.AsyncPagedListDiffer
 */
class CheeseAdapter : PagingDataAdapter<Cheese, CheeseViewHolder>(diffCallback) {
    override fun onBindViewHolder(holder: CheeseViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheeseViewHolder =
            CheeseViewHolder(parent)

    companion object {
        /**
         * This diff callback informs the PagedListAdapter how to compute list differences when new
         * PagedLists arrive.
         * <p>
         * When you add a Cheese with the 'Add' button, the PagedListAdapter uses diffCallback to
         * detect there's only a single item difference from before, so it only needs to animate and
         * rebind a single view.
         *
         * Этот обратный вызов diff информирует адаптер PagedList о том, как вычислить различия в списках при создании нового
         * * Появляются списки постраничных сообщений.
         * <P>в
         * Когда вы добавляете сыр с помощью кнопки "Добавить", PagedListAdapter использует diffCallback для
         * обнаружьте, что есть только одно отличие элемента от предыдущего, поэтому ему нужно только анимировать и
         * повторная привязка одного вида.
         *
         * @see DiffUtil
         */
        private val diffCallback = object : DiffUtil.ItemCallback<Cheese>() {
            override fun areItemsTheSame(oldItem: Cheese, newItem: Cheese): Boolean =
                    oldItem.id == newItem.id

            /**
             * Note that in kotlin, == checking on data classes compares all contents, but in Java,
             * typically you'll implement Object#equals, and use it to compare object contents.
             * Обратите внимание, что в kotlin == проверка классов данных сравнивает все содержимое, но в Java,
             * обычно вы реализуете Object#equals и используете его для сравнения содержимого объекта.
             */
            override fun areContentsTheSame(oldItem: Cheese, newItem: Cheese): Boolean =
                    oldItem == newItem
        }
    }
}
