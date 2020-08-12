/*
 * Copyright 2019, The Android Open Source Project
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


package com.example.android.gdgfinders.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddGdgViewModel : ViewModel() {

    /**
     * Request a toast by setting this value to true.
     * Запросите тост, установив это значение в true.
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     * Это личное, потому что мы не хотим выставлять установку этого значения на фрагмент.
     */
    private var _showSnackbarEvent = MutableLiveData<Boolean?>()

    /**
     * If this is true, immediately `show()` a toast and call `doneShowingSnackbar()`.
     * Если это правда, немедленно "покажите ()" тост и вызовите " готово показывать снэк-бар ()".
     */
    val showSnackBarEvent: LiveData<Boolean?>
        get() = _showSnackbarEvent

    /**
     * Call this immediately after calling `show()` on a toast.
     * все это сразу же после вызова "show ()" на тосте.
     *
     * It will clear the toast request, so if the user rotates their phone it won't show a duplicate toast.
     * Он очистит запрос тоста, поэтому, если пользователь вращает свой телефон, он не будет показывать дубликат тоста.
     */
    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = null
    }

    fun onSubmitApplication() {
        _showSnackbarEvent.value = true

    }
}
