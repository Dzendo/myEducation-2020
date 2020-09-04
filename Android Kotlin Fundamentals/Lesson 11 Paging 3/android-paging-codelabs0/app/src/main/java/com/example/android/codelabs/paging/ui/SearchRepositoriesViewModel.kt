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
/*
SearchRepositoriesViewModel запрашивает данные GithubRepository и предоставляет их SearchRepositoriesActivity.
 Потому что мы хотим , чтобы убедиться , что мы не запрос данных несколько раз по изменению конфигурации
  (например , поворот), мы преобразуя Flow для LiveData в ViewModel использовании liveData()метода строителя.
   Таким образом, LiveData последний список результатов кэшируется в памяти,
    и при SearchRepositoriesActivity воссоздании его содержимое LiveData будет отображаться на экране.
 */