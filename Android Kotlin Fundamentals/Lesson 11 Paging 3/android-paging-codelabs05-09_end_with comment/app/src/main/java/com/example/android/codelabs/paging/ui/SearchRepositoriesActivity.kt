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

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.android.codelabs.paging.Injection
import com.example.android.codelabs.paging.databinding.ActivitySearchRepositoriesBinding
import com.example.android.codelabs.paging.model.RepoSearchResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

/**
 * Приложение следует архитектуре, рекомендованной в «Руководстве по архитектуре приложения».
 *
 * ui - классы, относящиеся к отображению Activity с RecyclerView.
 * Приложение позволяет искать на GitHub репозитории, в названии или описании которых содержится определенное слово.
 * Список репозиториев отображается в порядке убывания количества звездочек, а затем в алфавитном порядке по имени.
 *
 * api - вызовы API Github с использованием Retrofit.
 * data - класс репозитория, отвечающий за запуск запросов API и кеширование ответов в памяти.
 * model - модельRepo данных, которая также является таблицей в базе данных Room; и RepoSearchResultкласс,
 *  который используется пользовательским интерфейсом для наблюдения
 *  как за данными результатов поиска, так и за сетевыми ошибками.
 * ui - классы, относящиеся к отображению Activityс RecyclerView.
 */
@FlowPreview
@ExperimentalCoroutinesApi
class SearchRepositoriesActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchRepositoriesBinding
    private lateinit var viewModel: SearchRepositoriesViewModel
    private val adapter = ReposAdapter()

    // Мы также хотим гарантировать, что всякий раз, когда пользователь ищет новый запрос, предыдущий запрос отменялся.
    // Для этого наш SearchRepositoriesActivity может содержать ссылку на новый,
    // Job которая будет отменяться каждый раз, когда мы ищем новый запрос.
    private var searchJob: Job? = null
    // Отменить предыдущее поисковое задание.
    //Начать новую работу в lifecycleScope.
    //Звоните viewModel.searchRepo.
    //Собери PagingData результат.
    //Пропустите PagingData к ReposAdapter позвонив по телефону adapter.submitData(pagingData).
    private fun search(query: String) {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchRepo(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchRepositoriesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // get the view model и в нее передается provideGithubRepository()
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory())
                .get(SearchRepositoriesViewModel::class.java)

        // add dividers between RecyclerView's row items
        // добавить разделители между элементами строки Recyclerview
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(decoration)
        //setupScrollListener()

        initAdapter()
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        search(query)
        // if (viewModel.repoResult.value == null) {
        //    viewModel.searchRepo(query)
        //}
        initSearch(query)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, binding.searchRepo.text.trim().toString())
    }

    @FlowPreview
    private fun initAdapter() {
        binding.list.adapter = adapter
       /* Это все не нужно на шаге 9
        viewModel.repoResult.observe(this,Observer { result ->
            when (result) {
                is RepoSearchResult.Success -> {
                    showEmptyList(result.data.isEmpty())
                    adapter.submitList(result.data)
                }
                is RepoSearchResult.Error -> {
                    Toast.makeText(
                            this,
                            "\uD83D\uDE28 Wooops $result.message}",
                            Toast.LENGTH_LONG
                    ).show()
                }
            }
        })

        */
    }

    private fun initSearch(query: String) {
        binding.searchRepo.setText(query)

        binding.searchRepo.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
        binding.searchRepo.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
        /*
        // Добавить на шаге 9:
        // 'dataRefreshFlow: Flow<Boolean>' is deprecated.
        // dataRefreshFlow is now redundant with the information passed from loadStateFlow and getItemCount,
        // and will be removed in a future alpha version
        lifecycleScope.launch {
            @OptIn(ExperimentalPagingApi::class)
            adapter.dataRefreshFlow.collect {
                binding.list.scrollToPosition(0)
            }
        }*/
        // Добавить на шаге 9: Вариант 2 из решения
        // Scroll to top when the list is refreshed from network.
        lifecycleScope.launch {
            adapter.loadStateFlow
                    // Only emit when REFRESH LoadState for RemoteMediator changes.
                    .distinctUntilChangedBy { it.refresh }
                    // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                    .filter { it.refresh is LoadState.NotLoading }
                    .collect { binding.list.scrollToPosition(0) }
        }
    }

    private fun updateRepoListFromInput() {
        binding.searchRepo.text.trim().let {
            if (it.isNotEmpty()) {
                binding.list.scrollToPosition(0)
                //viewModel.searchRepo(it.toString())
                search(it.toString())
                // Функция поиска должна быть вызвана SearchRepositoriesActivity в onCreate()методе
                // В updateRepoListFromInput()
            }
        }
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            binding.emptyList.visibility = View.VISIBLE
            binding.list.visibility = View.GONE
        } else {
            binding.emptyList.visibility = View.GONE
            binding.list.visibility = View.VISIBLE
        }
    }
/* Почему-то выброшено на шаге 9:
    private fun setupScrollListener() {
        val layoutManager = binding.list.layoutManager as LinearLayoutManager
        binding.list.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }
*/
    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = "Android"
    }
}

/*
С точки зрения удобства использования у нас есть следующие проблемы:

У пользователя нет информации о состоянии загрузки списка:
 он видит пустой экран при поиске нового репозитория или просто резкий конец списка,
  пока загружаются другие результаты для того же запроса.
Пользователь не может повторить неудачный запрос.

С точки зрения реализации у нас есть следующие проблемы:

Список неограниченно растет в памяти, тратя память по мере прокрутки пользователя.
Мы должны преобразовать наши результаты из Flow в, LiveData чтобы кэшировать их, увеличивая сложность нашего кода.
Если бы нашему приложению нужно было отображать несколько списков,
 мы бы увидели, что для каждого списка нужно написать много шаблонов.
Давайте узнаем, как библиотека Paging может помочь нам в решении этих проблем и какие компоненты она включает.
 */
