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

package com.example.android.databinding.twowaysample.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.ObservableInt
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.databinding.twowaysample.BR
import com.example.android.databinding.twowaysample.R
import com.example.android.databinding.twowaysample.data.IntervalTimerViewModel
import com.example.android.databinding.twowaysample.data.IntervalTimerViewModelFactory
import com.example.android.databinding.twowaysample.databinding.IntervalTimerBinding

// Константа - имя сохраняемых настроек Preferences
const val SHARED_PREFS_KEY = "timer"

/** Весь пример двухсторонней привязки создан на [Observable] [ViewModel] а не на LiveData полях - так вот устаревши
 * This activity only takes care of binding a ViewModel to the layout. All UI calls are delegated
 * to the Data Binding library or Binding Adapters ([BindingAdapters]).
 * Это действие заботится только о привязке ViewModel к макету. Все вызовы пользовательского интерфейса делегируются
 * в библиотеку привязки данных или адаптеры привязки ([адаптеры привязки]).
 *
 * Note that not all calls to the framework are removed, activities are still responsible for non-UI
 * interactions with the framework, like Shared Preferences or Navigation.
 * Обратите внимание, что не все вызовы фреймворка удаляются, действия по-прежнему ответственны за отсутствие пользовательского интерфейса
 * взаимодействие с фреймворком, например общие предпочтения или навигация.
 */
class MainActivity : AppCompatActivity() {

    // построен от /util/ObservableViewModel.kt который от  ViewModel(), Observable через IntervalTimerViewModelFactory.kt
    // IntervalTimerViewModelFactory передает параметр IntervalTimerViewModel(DefaultTimer) из util/TimerWrapper.kt
    private val intervalTimerViewModel: IntervalTimerViewModel
            by lazy {
                ViewModelProvider(this, IntervalTimerViewModelFactory)
                        .get(IntervalTimerViewModel::class.java)
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: IntervalTimerBinding = DataBindingUtil.setContentView(
                this, R.layout.interval_timer)
        val viewmodel = intervalTimerViewModel
        binding.viewmodel = viewmodel

        /* Save the user settings whenever they change
        Сохраняйте пользовательские настройки всякий раз, когда они изменяются*/
        observeAndSaveTimePerSet(  // функция ниже
                viewmodel.timePerWorkSet, R.string.prefs_timePerWorkSet)
        observeAndSaveTimePerSet(  // функция ниже
                viewmodel.timePerRestSet, R.string.prefs_timePerRestSet)

        /* Number of sets needs a different Количество наборов нужно различное  */
        observeAndSaveNumberOfSets(viewmodel)  // функция ниже
        // AS стартуется двойной слушатель : первый при старте программы и второй здесь

        if (savedInstanceState == null) {
            /* If this is the first run, restore shared settings
            Если это первый запуск, восстановите общие настройки*/
            restorePreferences(viewmodel)  // функция ниже
           // observeAndSaveNumberOfSets(viewmodel)  // функция ниже дублирующий слушатель - ОШИБКА
        }
    }

        // https://developer.android.com/guide/topics/ui/settings/use-saved-values
    private fun observeAndSaveTimePerSet(timePerWorkSet: ObservableInt, prefsKey: Int) {
        timePerWorkSet.addOnPropertyChangedCallback(  // переопределяется в ObservableViewModel - родительском для intervalTimerViewModel
                // это просто Observable.OnPropertyChangedCallback присоединяющий к PropertyChangeRegistry(стандарт) Callback
                object : Observable.OnPropertyChangedCallback() {
// @SuppressLint("NewApi") – это аннотация, используемая инструментом Android Lint.
// Линт расскажет вам, когда что-то в вашем коде не является оптимальным или может упасть.
// Указывает, что Lint должен игнорировать указанные предупреждения для аннотированного элемента.
            @SuppressLint("CommitPrefEdits")
            override fun onPropertyChanged(observable: Observable?, p1: Int) {
                Log.d("saveTimePerWorkSet", "Saving time-per-set--------${getString(prefsKey)}--preference = ${(observable as ObservableInt).get()} ")
                val sharedPref =  // получаем объект Preferences
                        getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE) ?: return
                // Берем редактор Preferences и записываем туда пару ключ-Int
                sharedPref.edit().apply {
                    putInt(getString(prefsKey), observable.get())
                    commit() // используйте apply() вместо commit()
                    // Если вы не используете значение, возвращаемое из commit() и вы используете commit() из основного потока,
                }
            }
        })
    }
    // восстановления значений 100 = 10.0 50 = 5 5=5 это значения по умолчанию
    private fun restorePreferences(viewModel: IntervalTimerViewModel) {
        val sharedPref =  // получаем объект Preferences
                getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE) ?: return

        var wasAnythingRestored = false
        val timePerWorkSetKey = getString(R.string.prefs_timePerWorkSet)
        if (sharedPref.contains(timePerWorkSetKey)) {
            viewModel.timePerWorkSet.set(sharedPref.getInt(timePerWorkSetKey, 100))
            wasAnythingRestored = true
        }
        val timePerRestSetKey = getString(R.string.prefs_timePerRestSet)
        if (sharedPref.contains(timePerRestSetKey)) {
            viewModel.timePerRestSet.set(sharedPref.getInt(timePerRestSetKey, 50))
            wasAnythingRestored = true
        }
        val numberOfSetsKey = getString(R.string.prefs_numberOfSets)
        if (sharedPref.contains(numberOfSetsKey)) {
            viewModel.numberOfSets = arrayOf(0, sharedPref.getInt(numberOfSetsKey, 5))
            wasAnythingRestored = true
        }
        if (wasAnythingRestored) Log.d("saveTimePerWorkSet",
   "Preferences restored $numberOfSetsKey = ${viewModel.numberOfSets[1]} $timePerWorkSetKey = ${viewModel.timePerWorkSet.get()} $timePerRestSetKey = ${viewModel.timePerRestSet.get()}")
        viewModel.stopButtonClicked()
    }
    //************* какая-то ошибка при старте верхнего таймера - периодически сам сораняет 10-15 сек и перемудрено с массивом из двух!! цифр и
    // Заморочился с массивом из 2 элементов ==> в XML ошибка двусторонней привязки
    private fun observeAndSaveNumberOfSets(viewModel: IntervalTimerViewModel) {
        viewModel.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            @SuppressLint("CommitPrefEdits")
            override fun onPropertyChanged(observable: Observable?, p1: Int) {
                if (p1 == BR.numberOfSets) {
                    Log.d("saveTimePerWorkSet", "Saving --------${getString(R.string.prefs_numberOfSets)}----- number of sets preference  ${viewModel.numberOfSets[1]}")
                    val sharedPref =  // получаем объект Preferences
                            getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE) ?: return
                    // Берем редактор Preferences и записываем туда пару ключ-Int - первый элемент массива
                    sharedPref.edit().apply {
                        putInt(getString(R.string.prefs_numberOfSets), viewModel.numberOfSets[1])
                        commit()  // используйте apply() вместо commit()
                        // Если вы не используете значение, возвращаемое из commit() и вы используете commit() из основного потока,
                    }
                }
            }
        })
    }
}

// На мой взгляд надо переписывать все сохранения Preferences по документации без использования addOnPropertyChangedCallback
