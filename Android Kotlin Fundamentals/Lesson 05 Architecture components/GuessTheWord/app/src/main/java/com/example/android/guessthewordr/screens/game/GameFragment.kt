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

package com.example.android.guessthewordr.screens.game

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.android.guessthewordr.R
import com.example.android.guessthewordr.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 * Фрагмент, где идет игра
 * Это основной фрагмент, где происходит большинство действий в игре:
 */
/*
При выходе по navigation на этот фрагмент (play из Title, playAgain из Результата или др)
androidx.navigation.fragment.NavHostFragment - стандартный обработчик навигации
из implementation "android.arch.navigation:navigation-fragment-ktx:2.2.2"
Организует в своем потоке Фрагмент, описанный в navigation.xml и зовет этот GameFragment.kt
GameFragment.kt Загружает game_fragment.xml, строит (м.б. через Factory) ViewModel
Устанавливает все ссылки (binding на xml, на ViewModel, binding.gameViewModel для доступа к полям
binding.lifecycleOwner для наблюдателей за LiveData), ставит наблюдателей, слушателей,
Прописывает и связывает необходимые функции для реакции UI, его построения и восстановления
И НАКОНЕЦ высвечивает на экран подготовленное game_fragment.xml и ВСТАЕТ на реагирование
впрочем многое реагирование при LiveData идет напрямую XML<-->ViewModel через ссылки отсюда
 */
class GameFragment : Fragment() {

    private lateinit var binding: GameFragmentBinding

    //ViewModel Должен быть связан с контроллером интерфейса.
    // Чтобы связать их, вы создаете ссылку на ViewModel контроллер UI внутри.
    private lateinit var viewModel: GameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate view and obtain an instance of the binding class
        // Раздуть представление и получить экземпляр класса привязки
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )

// Важное замечание:
// *  Всегда используйте ViewModelProvider для создания ViewModel объектов,
// *  а не для непосредственного создания экземпляра экземпляра ViewModel.
// *  без ViewModelProvider будет создаваться кажкый раз НОВЫЙ экземпляр что не надо
        Log.i("GameFragment", "Called ViewModelProvider")
        //viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java) // Deprecate
        // Get the viewModel - Получить ссылку на viewModel существующую (или новую если не существует)
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        // ссылка на провайдер восстанавливает ссылку на ту же viewModel после разрушения фрагмента

        /**** Шаг 1: Добавьте привязку данных для GameViewModel ***/
        /* <!-- 1. добавьте переменную привязки в XML данных типа GameViewModel--> */
        // Шаг 1 пункт 2 для прямой привязки с XML: В GameFragment файле передайте GameViewModel в привязку данных.
        // Set the viewmodel for databinding - this allows the bound layout access
        // to all the data in the ViewModel
        // Установите viewmodel для привязки данных
        // - это позволяет получить доступ к привязанному макету
        // для всех данных в ViewModel
        binding.gameViewModel = viewModel
        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates
        // Укажите представление фрагмента в качестве владельца жизненного цикла привязки.
        // Это используется для того, чтобы привязка могла наблюдать за обновлениями данных в реальном времени
       // binding.lifecycleOwner = viewLifecycleOwner // Udacity
        binding.lifecycleOwner = this // Codelabs

        // Шаг 2: Используйте привязки слушателя для обработки событий прямо в XML через Listener bindings

        /*  Эти слушатели становятся не нужны:
        binding.correctButton.setOnClickListener {
            viewModel.onCorrect()
            //updateWordText() // отсутстует в тексте решения
            //updateScoreText() // отсутстует в тексте решения
        }
        binding.skipButton.setOnClickListener {
            viewModel.onSkip()
            //updateWordText() // отсутстует в тексте решения Udacity
            //updateScoreText() // отсутстует в тексте решения
        }
        binding.endGameButton.setOnClickListener { onEndGame() }
        */
        /** Setting up LiveData observation relationship **/
        /** Настройка отношения наблюдения за живыми данными **/
        // Не требуется при пряой привязке LiveData к XML
        /*viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
            binding.scoreText.text = newScore.toString()
        })*/
        /** Setting up LiveData observation relationship **/
        /** Настройка отношения наблюдения за живыми данными **/
        // Наблюдатель, которого вы только что создали, получает событие,
        // когда LiveData изменяются данные, содержащиеся в наблюдаемом объекте.
        // Не требуется при пряой привязке LiveData к XML
       /* viewModel.word.observe(viewLifecycleOwner, Observer { newWord ->
            binding.wordText.text = newWord
        })*/
        // Когда значение score или word изменяется,
        // score или, word отображаемое на экране,
        // теперь обновляется автоматически.

        /* не нужен т.к. есть  Tranformations.map для преобразования текущего времени в форматированную строку.
        viewModel.currentTime.observe(this, Observer { newTime ->
            binding.timerText.text = DateUtils.formatElapsedTime(newTime)

        })*/

        // Observer for the Game finished event - Наблюдатель за игрой законченного
        //viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer<Boolean> { hasFinished ->
        //    if (hasFinished) gameFinished() // Coursera
        //})
        // Этот наблюдатель все равно здесь нужен, т.к. xml изменит ТОЛЬКО переменную "конецигры"
        // А здесь надо ее прослушать чтобы перейти к фрагменту результатов через navigation
        // Это (переход) дело фрагмента, а не ViewModel, т.к. это UI, а не данные
        viewModel.eventGameFinish.observe(viewLifecycleOwner) { hasFinished ->
            if (hasFinished) gameFinished() // Udacity
        }

        /** Buzzes when triggered with different buzz events */
        /* Жужжит при срабатывании с различными событиями жужжания */
        viewModel.eventBuzz.observe(viewLifecycleOwner) { buzzType ->
            if (buzzType != GameViewModel.BuzzType.NO_BUZZ) {
                buzz(buzzType.pattern)
                viewModel.onBuzzComplete()
            }
        }


        //updateScoreText()
        //updateWordText()
        return binding.root  // нет в Udacity
    }

    /** Methods for updating the UI Методы обновления пользовательского интерфейса **/
    /** При LIVEDATA теперь не требуются
    private fun updateWordText() {
        binding.wordText.text = viewModel.word.value
   }
    private fun updateScoreText() {
        binding.scoreText.text = viewModel.score.value.toString()
    }
    Они вам больше не нужны,
    потому что текстовые представления обновляются
    методами LiveData наблюдателя.
    */

    /**
     * Called when game is finished
     * Метод, который вызывается, чтобы закончить игру.
     * Это передает ваш текущий счет в ScoreFragment с помощью SafeArgs ?? где?.
     */
    private fun gameFinished() {
        Toast.makeText(activity, "Game has just finished", Toast.LENGTH_SHORT).show()
        val action = GameFragmentDirections.actionGameToScore(viewModel.score.value?:0)
        //val action = GameFragmentDirections.actionGameToScore()
        //action.setScore(score)  // Udacity - ошибка нет в исходнике
        //action.score = viewModel.score.value?:0
        //NavHostFragment.findNavController(this).navigate(action)
        // Переходим по actionGameToScore к фрагменту(экрану) Score с передачей туда аргумента score:
        findNavController(this).navigate(action)
        viewModel.onGameFinishComplete() // чтобы не повторялся Тоаст - при повороте
    }
    /**
     * Given a pattern, this method makes sure the device buzzes
     * Учитывая шаблон, этот метод гарантирует, что устройство гудит
     */
    private fun buzz(pattern: LongArray) {
        val buzzer = activity?.getSystemService<Vibrator>()
        buzzer?.let {
            // Vibrate for 500 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                //deprecated in API 26
                @Suppress("DEPRECATION")
                buzzer.vibrate(pattern, -1)
            }
        }
    }

    /** Methods for buttons presses **/
    // не нужны - нигде не вызываются но так вызывать из фрагмента UI для изменения данных в ViewModelи
   /* private fun onSkip() {
        viewModel.onSkip()
    }
    private fun onCorrect() {
        viewModel.onCorrect()
    }
    private fun onEndGame() {
        gameFinished()
    }*/
}

/**
 * Зачем использовать viewLifecycleOwner?
 * Представления фрагментов разрушаются, когда пользователь удаляется от фрагмента,
 * даже если сам фрагмент не уничтожен.
 * По сути, это создает два жизненных цикла:
 * жизненный цикл фрагмента и жизненный цикл представления фрагмента.
 * Ссылка на жизненный цикл фрагмента вместо жизненного цикла представления фрагмента
 * может вызвать незначительные ошибки при обновлении представления фрагмента.
 * Поэтому при настройке наблюдателей, которые влияют на вид фрагмента, вы должны:
 *
 * 1. Настройте наблюдателей в onCreateView()
 * 2. Пройдите viewLifecycleOwner к наблюдателям
 */
