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

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.kotlincoroutines.fakes.MainNetworkCompletableFake
import com.example.android.kotlincoroutines.fakes.MainNetworkFake
import com.example.android.kotlincoroutines.fakes.TitleDaoFake
import com.example.android.kotlincoroutines.main.utils.MainCoroutineScopeRule
import com.example.android.kotlincoroutines.main.utils.getValueForTest
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * В этом setup методе новый экземпляр MainViewModel создается с использованием фальшивых тестов
 * - это фальшивые реализации сети и базы данных, представленные в начальном коде,
 * чтобы помочь в написании тестов без использования реальной сети или базы данных.
 */

class MainViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
// InstantTaskExecutorRule является правилом JUnit, которое настраивает LiveData выполнение каждой задачи синхронно

    lateinit var subject: MainViewModel

    @Before
    fun setup() {
        subject = MainViewModel(
                TitleRepository(
                        MainNetworkFake("OK"),
                        TitleDaoFake("initial")
                ))
    }

    /**
     * новый тест, который гарантирует, что касания обновляются через одну секунду после нажатия основного вида:
     */


    @Test
    fun whenMainClicked_updatesTaps() {
        // TODO: Write this 6.
        subject.onMainViewClicked()
        Truth.assertThat(subject.taps.getValueForTest()).isEqualTo("0 taps")
        coroutineScope.advanceTimeBy(1000)
        Truth.assertThat(subject.taps.getValueForTest()).isEqualTo("1 taps")
    }
    @Test
    fun whenRefreshTitleSuccess_insertsRows() = runBlockingTest {
        val titleDao = TitleDaoFake("title")
        val subject = TitleRepository(
                MainNetworkFake("OK"),
                titleDao
        )

        subject.refreshTitle()
        Truth.assertThat(titleDao.nextInsertedOrNull()).isEqualTo("OK")
    }
    @Test(expected = TitleRefreshError::class)
    fun whenRefreshTitleTimeout_throws() = runBlockingTest {
        val network = MainNetworkCompletableFake()
        val subject = TitleRepository(
                network,
                TitleDaoFake("title")
        )

        launch {
            subject.refreshTitle()
        }

        advanceTimeBy(5_000)
    }
}

/*
InstantTaskExecutorRule является правилом JUnit, которое настраивает LiveData выполнение каждой задачи синхронно
MainCoroutineScopeRule это пользовательское правило в этой кодовой базе,
 которое настраивает Dispatchers.Main использование TestCoroutineDispatcher from kotlinx-coroutines-test.
 Это позволяет тестам выдвигать виртуальные часы для тестирования и
 позволяет коду использовать Dispatchers.Main в модульных тестах.
 */
/*
При вызове onMainViewClicked запускается только что созданная нами сопрограмма.
Этот тест проверяет, что текст нажатий остается «0 нажатий» сразу после onMainViewClicked вызова,
затем через 1 секунду он обновляется до «1 нажатий» .

Этот тест использует виртуальное время для управления выполнением сопрограммы, запущенной onMainViewClicked.
MainCoroutineScopeRuleПозволяет приостановить, возобновить или контролировать выполнение сопрограмм,
которые запускаются на Dispatchers.Main.
Здесь мы вызываем, advanceTimeBy(1_000)что заставит главного диспетчера немедленно выполнить сопрограммы,
которые должны возобновиться через 1 секунду.

Этот тест является полностью детерминированным, что означает, что он всегда будет выполняться одинаково.
И, поскольку он полностью контролирует выполнение сопрограмм, запущенных на Dispatchers.Main нем,
ему не нужно ждать одну секунду для установки значения.
 */