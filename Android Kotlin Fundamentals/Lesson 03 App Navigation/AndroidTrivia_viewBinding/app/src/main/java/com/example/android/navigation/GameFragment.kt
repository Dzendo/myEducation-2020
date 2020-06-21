/*
 * Copyright 2018, The Android Open Source Project
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

package com.example.android.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameBinding
import com.example.android.navigation.GameFragmentDirections
//build\generated\source\navigation-args\debug\com\example\android\navigation\GameFragmentDirections.java
import kotlin.math.min

class GameFragment : Fragment() {
    data class Question(
            val text: String,
            val answers: List<String>)

    // The first answer is the correct one.  We randomize the answers before showing the text.
    // All questions must have four answers.  We'd want these to contain references to string
    // resources so we could internationalize. (Or better yet, don't define the questions in code...)
    // Первый ответ-правильный. Мы рандомизируем ответы, прежде чем показывать текст.
    // На все вопросы должно быть четыре ответа. Мы бы хотели, чтобы они содержали ссылки на строку
    // ресурсы, чтобы мы могли интернационализироваться. (Или еще лучше, не определяйте вопросы в коде...)
    private val questions: MutableList<Question> = mutableListOf(
            Question(text = "What is (Что такое) Android Jetpack?",
                    answers = listOf("!!All of these (Все это)", "Tools", "Documentation", "Libraries")),
            Question(text = "What is  the base class for (Родительский класс) layouts?",
                    answers = listOf("!!ViewGroup", "ViewSet", "ViewCollection", "ViewRoot")),
            Question(text = "What layout do you use for complex screens? (для сложных экранов)",
                    answers = listOf("!!ConstraintLayout", "GridLayout", "LinearLayout", "FrameLayout")),
            Question(text = "What do you use to push structured data (поместить структурированные данные) into a layout?",
                    answers = listOf("!!Data binding", "Data pushing", "Set text", "An OnClick method")),
            Question(text = "What method do you use to inflate (раздувания) layouts in fragments?",
                    answers = listOf("!!onCreateView()", "onActivityCreated()", "onCreateLayout()", "onInflateLayout()")),
            Question(text = "What's the build system (система сборки) for Android?",
                    answers = listOf("!!Gradle", "Graddle", "Grodle", "Groyle")),
            Question(text = "Which class do you use to create a vector drawable (прорисовки)?",
                    answers = listOf("!!VectorDrawable", "AndroidVectorDrawable", "DrawableVector", "AndroidVector")),
            Question(text = "Which one of these is an Android (навигационным компонентом) navigation component?",
                    answers = listOf("!!NavController", "NavCentral", "NavMaster", "NavSwitcher")),
            Question(text = "Which XML element lets you register an activity with the launcher activity?(зарегистрировать действие с помощью действия запуска)",
                    answers = listOf("!!intent-filter", "app-registry", "launcher-registry", "app-launcher")),
            Question(text = "What do you use to mark a layout for data binding (обозначения макета привязки данных)?",
                    answers = listOf("!!<layout>", "<binding>", "<data-binding>", "<dbinding>"))
    )

    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = min((questions.size + 1) / 2, 3)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        // Раздуть макет для этого фрагмента
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
                inflater, R.layout.fragment_game, container, false)

        // Shuffles the questions and sets the question index to the first question.
        // Перетасовывает вопросы и устанавливает индекс вопроса на первый вопрос.
        randomizeQuestions()

        // Bind this fragment class to the layout
        // Привязать этот фрагмент класса к макету т.е к dataBinding game в XML
        binding.game = this

        // Set the onClickListener for the submitButton
        // // Установить onClickListener на кнопку Submit
        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
               // Do nothing if nothing is checked (id == -1)
            val answerIndex = when (binding.questionRadioGroup.checkedRadioButtonId) {
                    R.id.firstAnswerRadioButton  ->  0
                    R.id.secondAnswerRadioButton ->  1
                    R.id.thirdAnswerRadioButton  ->  2
                    R.id.fourthAnswerRadioButton ->  3
                    else -> -1
            }
            if (answerIndex == -1) returnTransition
                // The first answer in the original question is always the correct one, so if our
                // answer matches, we have the correct answer.
                // Первый ответ в исходном вопросе всегда является правильным, так что если наш
                // ответ совпадает, у нас есть правильный ответ.
            if (answers[answerIndex] != currentQuestion.answers[0])
                // Game over! A wrong answer sends us to the gameOverFragment.
                // Игра окончена! Неправильный ответ отсылает нас к gameOverFragment.
                // view.findNavController().navigate(R.id.action_gameFragment_to_gameOverFragment)
                // NavDirection классы генеритуются safe-args-gradle-plugin
                // Using directions to navigate to the GameOverFragment  теперь с плагином
            view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment())

                    // Advance to the next question
                    // Переходим к следующему вопросу
            if (++questionIndex >= numQuestions)
                    // We've won!  Navigate to the gameWonFragment.
                    // Мы победили! Перейдите к gameWonFragment.
                    // view.findNavController().navigate(R.id.action_gameFragment_to_gameWonFragment)
                    // Using directions to navigate to the GameWonFragment теперь с плагином
            view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment(numQuestions,questionIndex))
                            //.navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment()) // без Bundle

            currentQuestion = questions[questionIndex]
            setQuestion()
            binding.invalidateAll()
        }
        return binding.root
    }

    // randomize the questions and set the first question
    // рандомизируйте вопросы и задайте первый вопрос
    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    // Sets the question and randomizes the answers.  This only changes the data, not the UI.
    // Calling invalidateAll on the FragmentGameBinding updates the data.
    // Задает вопрос и рандомизирует ответы.
    // Это изменяет только данные, а не пользовательский интерфейс.
    // Вызов invalidateAll на FragmentGameBinding обновляет данные.
    private fun setQuestion() {
        currentQuestion = questions[questionIndex]
        // randomize the answers into a copy of the array
        // рандомизируйте ответы в копию массива
        answers = currentQuestion.answers.toMutableList()
        // and shuffle them
        // и перетасовать их
        answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_android_trivia_question, questionIndex + 1, numQuestions)
    }
}
