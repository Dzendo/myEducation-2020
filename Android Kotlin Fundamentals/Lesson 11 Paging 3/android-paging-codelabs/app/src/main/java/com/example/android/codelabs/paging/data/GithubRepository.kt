/*
 * Copyright (C) 2018 The Android Open Source Project
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

package com.example.android.codelabs.paging.data


import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.android.codelabs.paging.api.GithubService
import com.example.android.codelabs.paging.model.Repo
import kotlinx.coroutines.flow.Flow

/**
 * Repository class that works with local and remote data sources.
 * Класс репозитория, работающий с локальными и удаленными источниками данных.
 */
class GithubRepository(private val service: GithubService) {

    /**
     * Search repositories whose names match the query, exposed as a stream of data that will emit
     * every time we get more data from the network.
     * Поиск репозиториев, имена которых совпадают с запросом, выставляется в виде потока данных, который будет выдавать
     * каждый раз, когда мы получаем больше данных из сети.
     */
    // Независимо от того, какой PagingData конструктор вы используете, вам необходимо передать следующие параметры:
    // PagingConfig. Этот класс устанавливает параметры, касающиеся того, как загружать контент,
    // Функция , которая определяет , как создать PagingSource .
    // В нашем случае мы будем создавать новый GithubPagingSource для каждого нового запроса.

    fun getSearchResultStream(query: String): Flow<PagingData<Repo>> {
        Log.d("GithubRepository", "New query: $query")
        return Pager(
                config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
                pagingSourceFactory = { GithubPagingSource(service, query) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }
}
/*
Поток - это асинхронная последовательность значений

Flow генерирует значения по одному (вместо всех сразу), которые могут генерировать значения из асинхронных операций,
 таких как сетевые запросы, вызовы базы данных или другой асинхронный код.
 Он поддерживает сопрограммы во всем своем API, поэтому вы также можете преобразовывать поток с помощью сопрограмм!

 */
