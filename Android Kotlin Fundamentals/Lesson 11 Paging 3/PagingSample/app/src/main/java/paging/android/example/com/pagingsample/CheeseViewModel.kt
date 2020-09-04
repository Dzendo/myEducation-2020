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

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

/**
 * A simple [AndroidViewModel] that provides a [Flow]<[PagingData]> of delicious cheeses.
 * Простая [модель представления Android], которая обеспечивает [поток]<[данные подкачки] > вкусных сыров.
 */
class CheeseViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = CheeseDb.get(app).cheeseDao()

    /**
     * We use the Kotlin [Flow] property available on [Pager]. Java developers should use the
     * RxJava or LiveData extension properties available in `PagingRx` and `PagingLiveData`.
     * Мы используем свойство Kotlin [Flow], доступное на [пейджере]. Разработчики Java должны использовать
     * * RxJava или видео свойства расширения данных, имеющихся в `подкачки` и `жить подкачки данных
     */
    val allCheeses = Pager(
        PagingConfig(
            /**
             * A good page size is a value that fills at least a few screens worth of content on a
             * large device so the User is unlikely to see a null item.
             * You can play with this constant to observe the paging behavior.
             * Хороший размер страницы-это значение, которое заполняет по крайней мере несколько экранов контента на одной странице.
             * большое устройство, поэтому пользователь вряд ли увидит нулевой элемент.
             * Вы можете играть с этой константой, чтобы наблюдать за поведением подкачки.
             *
             *
             * It's possible to vary this with list device size, but often unnecessary, unless a
             * user scrolling on a large device is expected to scroll through items more quickly
             * than a small device, such as when the large device uses a grid layout of items.
             * Это можно варьировать в зависимости от размера устройства списка, но часто не требуется, если только
             * пользовательская прокрутка на большом устройстве, как ожидается, будет прокручивать элементы быстрее
             * чем маленькое устройство,например, когда большое устройство использует сетку расположения элементов.
             */
            pageSize = 60,

            /**
             * If placeholders are enabled, PagedList will report the full size but some items might
             * be null in onBind method (PagedListAdapter triggers a rebind when data is loaded).
             * Если заполнители включены, PagedList будет сообщать полный размер, но некоторые элементы могут быть
             * быть нулевым в методе onBind (PagedListAdapter запускает повторную привязку при загрузке данных).
             *
             * If placeholders are disabled, onBind will never receive null but as more pages are
             * loaded, the scrollbars will jitter as new pages are loaded. You should probably
             * disable scrollbars if you disable placeholders.
             * Если заполнители отключены, onBind никогда не получит null, но по мере увеличения количества страниц
             * при загрузке полосы прокрутки будут дрожать при загрузке новых страниц. Наверное, так и должно быть
             * отключите полосы прокрутки, если вы отключили заполнители.
             */
            enablePlaceholders = true,

            /**
             * Maximum number of items a PagedList should hold in memory at once.
             * Максимальное количество элементов, которые PagedList должен держать в памяти одновременно.
             *
             * This number triggers the PagedList to start dropping distant pages as more are loaded.
             * Это число запускает PagedList, чтобы начать отбрасывать удаленные страницы по мере загрузки новых.
             */
            maxSize = 200
        )
    ) {
        dao.allCheesesByName()
    }.flow

    fun insert(text: CharSequence) = ioThread {
        dao.insert(Cheese(id = 0, name = text.toString()))
    }

    fun remove(cheese: Cheese) = ioThread {
        dao.delete(cheese)
    }
    fun count() = ioThread { dao  .getCount() }
}
