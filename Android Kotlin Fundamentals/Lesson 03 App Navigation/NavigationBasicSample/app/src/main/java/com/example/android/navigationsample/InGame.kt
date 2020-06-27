/*
 * Copyright 2018, The Android Open Source Project
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

package com.example.android.navigationsample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

/**
 * Shows a question and four answers.
 * Показывает вопрос и четыре ответа.
 */
class InGame : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_in_game, container, false)

        // Сооружаются прослушивающая лямбда для назначения на  <CheckBox ниже -->
        val gameOverListener: (View) -> Unit = {
            Navigation.findNavController(view).navigate(R.id.action_in_game_to_gameOver)
            //  <action (в navigation)
            //      android:id="@+id/action_in_game_to_gameOver"
            //      app:destination="@id/game_over"
        }

        view.findViewById<View>(R.id.checkBox).setOnClickListener(gameOverListener)
        view.findViewById<View>(R.id.checkBox2).setOnClickListener(gameOverListener)
        view.findViewById<View>(R.id.checkBox4).setOnClickListener(gameOverListener)

        // <CheckBox который светит 42 километра
        //    android:id="@+id/checkBox3"
        view.findViewById<View>(R.id.checkBox3).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_in_game_to_resultsWinner)
            // <action  (в navigation)
            //       android:id="@+id/action_in_game_to_resultsWinner"
            //       app:destination="@id/results_winner"
        }

        return view
    }
}
