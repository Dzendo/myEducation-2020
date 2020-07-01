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

package com.example.android.guessworld.screens.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.guessworld.screens.title.TitleFragmentDirections
import com.example.android.guessworld.R
import com.example.android.guessworld.databinding.TitleFragmentBinding

/**
 * Fragment for the starting or title screen of the app
 * Фрагмент для начального или титульного экрана приложения
 * Фрагмент заголовка - это первый экран, который отображается при запуске приложения.
 * Обработчик щелчка установлен на кнопку « Воспроизвести» , чтобы перейти к экрану игры.
 */
class TitleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        // Раздуть макет для этого фрагмента
        // Устананавливаем привязку и ставим слушатель на кнопку - перемещение по навигации
        /*val binding: TitleFragmentBinding = DataBindingUtil.inflate(
                inflater, R.layout.title_fragment, container, false)*/
        // Переделка во ViewBinding
        val binding: TitleFragmentBinding = TitleFragmentBinding.inflate(
            inflater,  container, false)

        binding.playGameButton.setOnClickListener {
            findNavController().navigate(TitleFragmentDirections.actionTitleToGame())
            // берет из main_navigation.xml android:id="@+id/action_title_to_game"
            // точнее из сгенерированного navigation.safeargs.kotlin
            // если не применять binding то применяем navigation.xml + фунцию которая ниже:
            // fun actionTitleToGame(): NavDirections = ActionOnlyNavDirections(R.id.action_title_to_game)
        }
        return binding.root
    }
}
// Это обычное использование navigation фрагмента с XML для экрана
// Единственное что применено это binding из сгенерированных файлов
// здесь нет данных и поэтому нет ViewModel и LiveData
