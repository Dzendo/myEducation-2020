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

package com.example.android.codelabs.navigation

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions


/**
 * Fragment used to show how to navigate to another destination
 * Фрагмент, используемый для того, чтобы показать, как перейти к другому пункту назначения
 */
class HomeFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO STEP 5 - Set an OnClickListener, using Navigation.createNavigateOnClickListener()
        // TODO Шаг 5-Установите OnClickListener, используя навигацию.создание навигации OnClickLis
       /* val button = view.findViewById<Button>(R.id.navigate_destination_button)
        button?.setOnClickListener {
            findNavController().navigate(R.id.flow_step_one_dest, null)
        }*/

        // удобный метод Navigation.createNavigateOnClickListener(@IdRes destId: int, bundle: Bundle).
        // Этот метод создаст OnClickListener для перехода к заданному месту назначения
        // с набором аргументов, которые будут переданы получателю.
        //val button = view.findViewById<Button>(R.id.navigate_destination_button)
        //button?.setOnClickListener(
        //Navigation.createNavigateOnClickListener(R.id.flow_step_one_dest, null)
        //)

         //TODO END STEP 5
        // Добавить пользовательский переход вместо простого того что наверху
        //TODO STEP 6 - Set NavOptions
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }
        view.findViewById<Button>(R.id.navigate_destination_button)?.setOnClickListener {
            findNavController().navigate(R.id.flow_step_one_dest, null, options)
        }
        //TODO END STEP 6

        //TODO STEP 7.2 - Update the OnClickListener to navigate using an action
        // работает но лучше ниже
        //view.findViewById<Button>(R.id.navigate_action_button)?.setOnClickListener(
        //        Navigation.createNavigateOnClickListener(R.id.next_action, null)
        //)
        // Вызов перехода с использованием Класс Directions утилиты navArgs
        // Note the usage of curly braces since we are defining the click listener lambda
        view.findViewById<Button>(R.id.navigate_action_button)?.setOnClickListener {
            //val flowStepNumberArg = 1
            //val action = HomeFragmentDirections.nextAction(flowStepNumberArg)
            val action = HomeFragmentDirections.nextAction()  // арумент = 1 по умролчанию из графа
            findNavController().navigate(action)
        }
        // Обратите внимание, что в вашем графике навигации XML вы можете указать defaultValue для каждого аргумента.
        // Если вы этого не сделаете, вы должны передать аргумент в действие, как показано:
        //HomeFragmentDirections.nextAction(flowStepNumberArg)
        //TODO END STEP 7.2
    }
    // добавление меню наверх - значка корзины
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }
    // Вызывать навигацию будет сам по имени меню shopping_cart равному имени фрагмента в навигации
    // при этих условиях больше ничего не надо
}
