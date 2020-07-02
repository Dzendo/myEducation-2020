package com.example.android.guessworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.guessworld.databinding.MainActivityBinding

// adb shell am kill com.example.android.guessthewordr
// команда для Terminal AS чтобы выбить окончательно программу из памяти эмулятора
/**
 * Creates an Activity that hosts all of the fragments in the app
 * Создает действие, которое содержит все фрагменты в приложении
 * Этот файл содержит только код, сгенерированный по умолчанию -starter
 */
// на старте main только грузит код main_activity.XML с одним фрагментом,
// переводящим к navigation/main_navigation.xml - поэтому так просто
// все остальное в kotlin-fragments и fragments.xml связанных через navigation.xml
// СОВЕТ - k-fragments лучше подкаталог-пакет screens и в нем
// для каждого фрагмента в screens свои подкаталоги пакеты, т.к. много доп файлов к фрагменту
// Переделано в ViewBinding и на  <androidx.fragment.app.FragmentContainerView - Работает
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(MainActivityBinding.inflate(layoutInflater).root)
        //setContentView(R.layout.main_activity)
    }
}
// В main_activity.xml отылка на навигатор main_navigation.xml через фрагмент
// androidx.navigation.fragment.NavHostFragment - стандартный обработчик навигации
// из implementation "android.arch.navigation:navigation-fragment-ktx:2.2.2"
// Дальнейшее UI ходит все там в отдельном фрагменте - потоке
// обработчик навигации зовет фрагменты как у него нарисовано (сначала startовый)
// Вызванные им Фрагменты надувают свои XML создают ViewModel связывают их между собой
// ставят binding, LiveData, Observers, Listeners, и.т.д, и.т.п и в наконец
// Высвечивают свой экран-фрагмент и встают на реагирование на действия пользователя
// когда им надо, то они обращаются к NavHostFragment и просят его куда нибудь пойти
// обработчик навигации смотрит чего просят и куда идти по файлу navigation.xml
// тогда NavHostFragment выгружает просящий фрагмент и заггужает другой требуемый

/*
При выходе по navigation на Game фрагмент (play из Title, playAgain из Результата или др)
androidx.navigation.fragment.NavHostFragment - стандартный обработчик навигации
из implementation "android.arch.navigation:navigation-fragment-ktx:2.2.2"
Организует в своем потоке GAMEФрагмент, описанный в navigation.xml и зовет этот GameFragment.kt
GameFragment.kt Загружает game_fragment.xml, строит (м.б. через Factory) ViewModel
Устанавливает все ссылки (binding на xml, на ViewModel, binding.gameViewModel для доступа к полям
binding.lifecycleOwner для наблюдателей за LiveData), ставит наблюдателей, слушателей,
Прописывает и связывает необходимые функции для реакции UI, его построения и восстановления
И НАКОНЕЦ высвечивает на экран подготовленное game_fragment.xml и ВСТАЕТ на реагирование
впрочем многое реагирование при LiveData идет напрямую XML<-->ViewModel через ссылки отсюда
 */

/*class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}*/