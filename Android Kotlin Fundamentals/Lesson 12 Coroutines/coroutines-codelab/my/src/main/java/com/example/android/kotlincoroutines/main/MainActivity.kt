/*
 * Copyright (C) 2019 Google LLC
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

package com.example.android.kotlincoroutines.main

/**
 * Это начальное приложение использует потоки для увеличения счетчика через короткую задержку после нажатия на экран.
 * Он также получит новый заголовок из сети и отобразит его на экране.
 * Попробуйте сейчас, и вы увидите, что счетчик и сообщение изменится после небольшой задержки.
 *
 * В этой лаборатории кода вы конвертируете это приложение для использования сопрограмм.
 */
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.android.kotlincoroutines.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

/**
 * MainActivity отображает пользовательский интерфейс, регистрирует слушателей кликов и может отображать Snackbar.
 *   Она проходит события в MainViewModel и обновляет экран , основанный на LiveDataв MainViewModel.
 * MainViewModel обрабатывает события onMainViewClickedи будет общаться с MainActivity использованием LiveData.
 * Executors определяет, BACKGROUND,что может запускать вещи в фоновом потоке.
 * TitleRepository получает результаты из сети и сохраняет их в базе данных.
 *
 * MainDatabase реализует базу данных с использованием Room, которая сохраняет и загружает файл Title.
 * MainNetwork реализует сетевой API, который получает новый заголовок.
 *   Он использует Retrofit для получения заголовков.
 *   Retrofitнастроен на случайный возврат ошибок или фиктивных данных,
 *   но в остальном ведет себя так, как если бы он делал реальные сетевые запросы.
 * TitleRepository реализует единый API для извлечения или обновления заголовка
 *   путем объединения данных из сети и базы данных.
 * MainViewModelпредставляет состояние экрана и обрабатывает события.
 *   Он скажет репозиторию обновить заголовок, когда пользователь коснется экрана.
 * Поскольку сетевой запрос управляется UI-событиями, и мы хотим запустить на их основе сопрограмму,
 * естественным местом для начала использования сопрограмм является файл ViewModel.
 */

/**
 * Show layout.activity_main and setup data binding.
 * Показать макет.activity_main и привязка данных настройки.
 */
class MainActivity : AppCompatActivity() {

    /**
     * Inflate layout.activity_main and setup data binding.
     * Надуть макет.activity_main и привязка данных настройки.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        // Get MainViewModel by passing a database to the factory
        // Получить MainViewModel, передав базу данных на завод
        // используется спец утилита singleArgViewModelFactory из util\ViewModelHelpers.kt
        val database = getDatabase(this)
        val repository = TitleRepository(getNetworkService(), database.titleDao)
        val viewModel = ViewModelProvider(this, MainViewModel.FACTORY(repository))
                .get(MainViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Show a snackbar whenever the [ViewModel.snackbar] is updated a non-null value
        viewModel.snackbar.observe(this) { text ->
            text?.let {

                Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
                //context.snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
                //binding.root.toast(text)
                viewModel.onSnackbarShown()
            }
        }
        setContentView(binding.root)
    }
}
/*
kotlinx-coroutines-core - Основной интерфейс для использования сопрограмм в Kotlin
kotlinx-coroutines-android - Поддержка основного потока Android в сопрограммах
 */