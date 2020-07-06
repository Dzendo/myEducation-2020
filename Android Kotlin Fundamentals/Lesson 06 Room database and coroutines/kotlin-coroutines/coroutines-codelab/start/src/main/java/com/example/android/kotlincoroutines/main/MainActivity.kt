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
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.android.kotlincoroutines.R
import com.example.android.kotlincoroutines.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

/**
 * MainActivity отображает пользовательский интерфейс, регистрирует прослушиватели щелчков и может отображать Snackbar.
 * Он передает события MainViewModel и обновляет экран на основе LiveData в MainViewModel.
 *
 * Show layout.activity_main and setup data binding.
 * Показать макет.activity_main и привязка данных настройки.
 */
class MainActivity : AppCompatActivity() {
    //private lateinit var binding: ActivityMainBinding
    /**
     * Inflate layout.activity_main and setup data binding.
     * Надуть макет.activity_main и привязка данных настройки.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_main)
       //val binding = ActivityMainBinding.inflate(layoutInflater)
       // setContentView(binding.root)
        //setContentView(R.layout.activity_main)

        //val rootLayout: ConstraintLayout = findViewById(R.id.rootLayout)
        //val title: TextView = findViewById(R.id.title)
        //val taps: TextView = findViewById(R.id.taps)
        //val spinner: ProgressBar = findViewById(R.id.spinner)

        // Get MainViewModel by passing a database to the factory
        // Получить MainViewModel, передав базу данных на фабрику, которая лежит в нем же Static
        val database = getDatabase(this)  // titles_db из com\example\android\kotlincoroutines\main\MainDatabase.kt
        val repository = TitleRepository(getNetworkService(), database.titleDao)
        val viewModel = ViewModelProvider(this, MainViewModel.FACTORY(repository))
                .get(MainViewModel::class.java)
        // Для построения viewModel из MainViewModel вызывается MainViewModel.FACTORY(repository) - зачетно

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        /*
        // When rootLayout is clicked call onMainViewClicked in ViewModel
        // При разметке rootlayout кнопки вызова на главном экране нажал в модель представления
        binding.rootLayout.setOnClickListener {
            viewModel.onMainViewClicked()
        }

        // update the title when the [MainViewModel.title] changes
        // обновите заголовок, когда [MainViewModel.название] изменения
        viewModel.title.observe(this) { value ->
            value?.let {
                binding.title.text = it
            }
        }

        viewModel.taps.observe(this) { value ->
            binding.taps.text = value
        }
        */
        // show the spinner when [MainViewModel.spinner] is true
        viewModel.spinner.observe(this) { value ->
            value.let { show ->
                binding.spinner.visibility = if (show) View.VISIBLE else View.GONE
            }
        }

        // Show a snackbar whenever the [ViewModel.snackbar] is updated a non-null value
        viewModel.snackbar.observe(this) { text ->
            text?.let {
                Snackbar.make(binding.rootLayout, text, Snackbar.LENGTH_LONG).show()
                viewModel.onSnackbarShown()
            }
        }
    }
}
