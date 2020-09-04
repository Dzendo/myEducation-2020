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

package com.example.android.codelabs.paging.ui

// 7. Запросить и кэшировать PagingData в ViewModel.

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android.codelabs.paging.data.GithubRepository
import com.example.android.codelabs.paging.model.Repo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

/**
 * ViewModel for the [SearchRepositoriesActivity] screen.
 * The ViewModel works with the [GithubRepository] to get the data.
 */
@ExperimentalCoroutinesApi
class SearchRepositoriesViewModel(private val repository: GithubRepository) : ViewModel() {
    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<Repo>>? = null

    fun searchRepo(queryString: String): Flow<PagingData<Repo>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<Repo>> = repository.getSearchResultStream(queryString)
                .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
        // Flow<PagingData>имеет удобный cachedIn()метод,
        // который позволяет нам кэшировать содержимое Flow<PagingData>в CoroutineScope.
        // Поскольку мы находимся в a ViewModel,
        // мы будем использовать androidx.lifecycle.viewModelScope.
    }
}
/*

  Теперь посмотрим, какие изменения мы внесли SearchRepositoriesViewModel:

Добавлены новые элементы запроса Stringи результатов поиска Flow.
Обновлен searchRepo()метод с использованием ранее описанных функций.
Удалены queryLiveData и, repoResult поскольку их назначение покрывается Paging 3.0 и Flow.
Удален, listScrolled()поскольку библиотека подкачки справится с этим за нас.
Удален, companion object потому что VISIBLE_THRESHOLD больше не нужен.
Удалено repoLoadStatus, поскольку в Paging 3.0 также есть механизм для отслеживания статуса загрузки,
 как мы увидим на следующем шаге.
 */


/*
Из нашего SearchRepositoriesViewModel выставляем repoResult: LiveData<RepoSearchResult>.
 Роль repoResult заключается в том, чтобы быть кешем в памяти для результатов поиска,
  который сохраняет изменения конфигурации.
   С Paging 3.0 мы не должны превратить нашу Flow в LiveData больше.
    Вместо этого у него SearchRepositoriesViewModel будет частный Flow<PagingData<Repo>>член,
     который служит той же цели, что repoResult и ..

Вместо того, чтобы использовать LiveData объект для каждого нового запроса, мы можем просто использовать String.
 Это поможет нам гарантировать, что всякий раз, когда мы получим новый поисковый запрос,
  который совпадает с текущим запросом, мы просто вернем существующий Flow.
   Нам нужно только позвонить, repository.getSearchResultStream()если новый поисковый запрос отличается.
 */

/*

import androidx.lifecycle.*
import com.example.android.codelabs.paging.data.GithubRepository
import com.example.android.codelabs.paging.model.RepoSearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

/**
 * ui - классы, относящиеся к отображению Activity с RecyclerView.
 * ViewModel for the [SearchRepositoriesActivity] screen.
 * The ViewModel works with the [GithubRepository] to get the data.
 */
@ExperimentalCoroutinesApi
class SearchRepositoriesViewModel(private val repository: GithubRepository) : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    private val queryLiveData = MutableLiveData<String>()
    @FlowPreview
    val repoResult: LiveData<RepoSearchResult> = queryLiveData.switchMap { queryString ->
        liveData {
            val repos = repository.getSearchResultStream(queryString).asLiveData(Dispatchers.Main)
            emitSource(repos)
        }
    }

    /**
     * Search a repository based on a query string.
     * поиск репозитория на основе строки запроса.
     */
    fun searchRepo(queryString: String) {
        queryLiveData.postValue(queryString)
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            val immutableQuery = queryLiveData.value
            if (immutableQuery != null) {
                viewModelScope.launch {
                    repository.requestMore(immutableQuery)
                }
            }
        }
    }
}
*/

/*
SearchRepositoriesViewModel запрашивает данные GithubRepository и предоставляет их SearchRepositoriesActivity.
 Потому что мы хотим , чтобы убедиться , что мы не запрос данных несколько раз по изменению конфигурации
  (например , поворот), мы преобразуя Flow для LiveData в ViewModel использовании liveData()метода строителя.
   Таким образом, LiveData последний список результатов кэшируется в памяти,
    и при SearchRepositoriesActivity воссоздании его содержимое LiveData будет отображаться на экране.
 */