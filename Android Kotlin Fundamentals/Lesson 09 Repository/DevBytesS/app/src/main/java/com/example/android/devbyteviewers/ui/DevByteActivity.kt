/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.devbyteviewers.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.devbyteviewers.R

/**
 * This is a single activity application that uses the Navigation library. Content is displayed
 * by Fragments.
 * Это единственное приложение активности, которое использует библиотеку навигации. Содержимое отображается фрагментами.
 */
class DevByteActivity : AppCompatActivity() {

    /**
     * Called when the activity is starting.  This is where most initialization
     * should go
     * Вызывается, когда начинается действие. Именно сюда должна идти большая часть инициализации
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dev_byte_viewer)
    }
}
/*
Это стартовое приложение поставляется с большим количеством кода,
в частности со всеми сетевыми модулями и модулями пользовательского интерфейса
 */
