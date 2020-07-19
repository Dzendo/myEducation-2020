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
 *
 */

package com.example.android.marsrealestate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.marsrealestate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    /**
     * STANDART FINALY CODELABS INTERNET + RecyclerView 15.07.2020
     * Our MainActivity is only responsible for setting the content view that contains the Navigation Host.
     *  Наша основная деятельность связана только с настройкой представления контента, содержащего Навигационный Хост.
     * N:\2020_GCAAD\Android Kotlin Fundamentals\Lesson 08 Connecting to the internet\MarsRealEstateFinal\app\build\generated\data_binding_base_class_source_out\debug\out\com\example\android\marsrealestate\databinding
     * <androidx.fragment.app.FragmentContainerView/>
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
        //val binding: ActivityMainBinding = DataBindingUtil.setContentView(
        //    this, R.layout.activity_main)
        //val binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(binding.root)

    }
}
