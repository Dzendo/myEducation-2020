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

package com.example.android.databinding.basicsample.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.android.databinding.basicsample.databinding.ActivityMainBinding

/**
 * Shows a menu. Показывает меню.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // The layout for this activity is a Data Binding layout so it needs to be inflated using
        // Макет для этого действия является макетом привязки данных, поэтому его необходимо раздуть с помощью
        // DataBindingUtil.
       // val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // The returned binding has references to all the Views with an ID.
        // Возвращаемая привязка содержит ссылки на все представления с идентификатором.
       /*
        binding.observableFieldsActivityButton.setOnClickListener {
        //    startActivity(Intent(this, ObservableFieldActivity::class.java))
            startActivity<ObservableFieldActivity>()    
        }
        binding.viewmodelActivityButton.setOnClickListener {
        //    startActivity(Intent(this, ViewModelActivity::class.java))
            startActivity<ViewModelActivity>()    
        }*/
    }

    fun onClick(view: View) = when (view){
        binding.observableFieldsActivityButton -> startActivity<ObservableFieldActivity>()
        binding.viewmodelActivityButton -> startActivity<ViewModelActivity>()
        else -> startActivity<MainActivity>()
    }
}
// AS
private inline fun <reified T: AppCompatActivity> AppCompatActivity.startActivity() =
        startActivity(Intent(this,T::class.java))
