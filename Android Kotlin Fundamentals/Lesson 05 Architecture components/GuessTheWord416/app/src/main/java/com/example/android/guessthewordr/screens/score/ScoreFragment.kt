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

package com.example.android.guessthewordr.screens.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android.guessthewordr.R
import com.example.android.guessthewordr.databinding.ScoreFragmentBinding

// adb shell am kill com.example.android.dessertpusher
/**
 * Fragment where the final score is shown, after the game is over
 * Фрагмент, где показан окончательный счет, после окончания игры
 * ScoreFragment это последний экран в игре, и он отображает окончательный счет игрока.
 * В этой кодовой метке вы добавляете реализацию для отображения этого экрана
 * и отображения итоговой оценки.
 * Этот фрагмент получает оценку, переданную из набора аргументов, и отображает ее.
 * Также есть кнопка для повторной игры, которая возвращает вас к GameFragment.
 *
 */
class ScoreFragment : Fragment() {

    //переменные класса для ScoreViewModel и ScoreViewModelFactory
    private lateinit var viewModel: ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory
    // Реализации Factory интерфейса отвечают за создание экземпляров ViewModels.

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class.
        // Раздуть представление и получить экземпляр класса привязки.
        val binding: ScoreFragmentBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.score_fragment,
                container,
                false
        )

        // Get args using by navArgs property delegate Udacity 05.21
        val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()
        //binding.scoreText.text = scoreFragmentArgs.score.toString()

        // binding.scoreText.text = ScoreFragmentArgs.fromBundle(arguments).score.toString()  // Udacity
        // binding.scoreText.text = arguments?.let { ScoreFragmentArgs.fromBundle(it).score.toString() }  // Udacity
        //binding.playAgainButton.setOnClickListener { viewModel.onPlayAgain() } // Codelabs

        // Передайте окончательный результат из набора аргументов в качестве параметра конструктора для ScoreViewModelFactory().
        viewModelFactory = ScoreViewModelFactory(scoreFragmentArgs.score)
       // viewModelFactory = ScoreViewModelFactory(ScoreFragmentArgs.fromBundle(arguments!!).score)
        // после инициализации viewModelFactory, инициализируем viewModel объект.
        // Это создаст ScoreViewModel объект, используя фабричный метод, определенный в viewModelFactory классе
        viewModel = ViewModelProvider(this, viewModelFactory)
                .get(ScoreViewModel::class.java)
        // установите текст scoreText представления в окончательную оценку, определенную в ScoreViewModel.
        // binding.scoreText.text = viewModel.score.toString()

        // Установите viewmodel для привязки данных
        // - это позволяет получить доступ к привязанному макету
        // для всех данных в ViewModel - это имя из data блока файла score_fragment.xml
        binding.scoreViewModel = viewModel
        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates
        // Укажите представление фрагмента в качестве владельца жизненного цикла привязки.
        // Это используется для того, чтобы привязка могла наблюдать за обновлениями данных в реальном времени
        binding.lifecycleOwner = viewLifecycleOwner

        // Add observer for score - Добавить наблюдателя для оценки
        // Не требуется при пряой привязке LiveData к XML
        /*viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
            binding.scoreText.text = newScore.toString()
        })*/
        // Кнопка опять сначала на Слушателе:
        // Navigates back to game when button is pressed - Переход обратно в игру при нажатии кнопки
        // не нужна при прямой связке ViewModel с Binding XML
        // Однако нужен обозреваватель поля playAgain <boolean> чтобы перейти в предыдцщему экрану
        // т.к. при LiveData нажатая кнопка play_again_button в XML прямо изменит поле в ViewModel
        // и это изменение поля надо перехватить обозревателем и перейти наконец назад
        viewModel.eventPlayAgain.observe(viewLifecycleOwner, Observer { playAgain ->
            if (playAgain) {
                findNavController().navigate(ScoreFragmentDirections.actionRestart())
                viewModel.onPlayAgainComplete() // чтобы не было повторного перехвата при ориентации
            }
        })
        return binding.root
    }
    // from Udacity less 5 N 3  нет в решении - это если примитивный слушатель кнопки прямо здесь
     //private fun onPlayAgain() {
     //   findNavController().navigate(ScoreFragmentDirections.actionRestart())
     //}
}

/*
В этой задаче вы реализованы ScoreFragment для использования ViewModel.
 Вы также узнали, как создать параметризованный конструктор
  для ViewModel использования ViewModelFactory интерфейса.
 */


