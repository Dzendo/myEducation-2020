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

import androidx.paging.PagingSource
import com.example.android.codelabs.paging.api.GithubService
import com.example.android.codelabs.paging.api.IN_QUALIFIER
import com.example.android.codelabs.paging.model.Repo
import retrofit2.HttpException
import java.io.IOException

/**
 * PagingSource Реализация определяет источник данных и как извлечь данные из этого источника.
 * PagingData Объект запрашивает данные из PagingSource в ответ на погрузочных подсказки,
 * которые генерируются как прокрутке в RecyclerView.
 *
 * Для построения PagingSource вам необходимо определить следующее:
 *
 * - Тип ключа подкачки - в нашем случае Github API использует номера индекса для страниц,
 *   отсчитываемые от 1, поэтому тип равен Int.
 * - Тип загружаемых данных - в нашем случае мы загружаем Repo элементы.
 * - Откуда берутся данные - откуда берутся данные GithubService.
 *   Наш источник данных будет специфическим для определенного запроса, поэтому нам нужно убедиться,
 *   что мы также передаем информацию о запросе GithubService.
 */


// GitHub page API is 1 based: https://developer.github.com/v3/#pagination
private const val GITHUB_STARTING_PAGE_INDEX = 1

class GithubPagingSource(
        private val service: GithubService,
        private val query: String
) : PagingSource<Int, Repo>() {
    //  ​​будет вызываться для запуска асинхронной загрузки.
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        // Ключ загружаемой страницы . Если это первый вызов нагрузки, LoadParams.key будет null.
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        // Размер загрузки - требуемое количество элементов для загрузки.
        val apiQuery = query + IN_QUALIFIER
        return try {
            // Это заменит использование RepoSearchResult в нашем приложении
            val response = service.searchRepos(apiQuery, position, params.loadSize)
            val repos = response.items
            // LoadResult.Page, если результат был успешным.
            LoadResult.Page(
                    data = repos,
                    prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (repos.isEmpty()) null else position + 1
            )
            // При построении LoadResult.Page, пройдите null по nextKey или prevKey
            // если список не может быть загружен в соответствующем направлении .
            // Так , например, в нашем случае, мы могли бы считать , что если ответ сети был успешным ,
            // но список был пуст, у нас нет каких - либо данные, оставшиеся для загрузки;
            // так что nextKey может быть null.
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
            // LoadResult.Error, в случае ошибки.
        }
    }
}
