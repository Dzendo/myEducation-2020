package com.example.android.guessworld.screens.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// ViewModelFactory Конкретизирует ViewModel объекты, с или без параметров конструктора.
// ViewModelProvider.Factory это интерфейс,
// который вы можете использовать для создания ViewModel объекта.
// ViewModelFactory класс для создания экземпляра и возврата ViewModel объекта
// Этот класс будет отвечать за создание экземпляра ScoreViewModel объекта.
class ScoreViewModelFactory (private val finalScore: Int) : ViewModelProvider.Factory {
// Добавьте параметр конструктора для окончательной оценки.

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            return ScoreViewModel(finalScore) as T
            //  верните вновь созданный ScoreViewModel объект
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
/*
Примечание :
 в этом приложении нет необходимости добавлять ViewModelFactory для ScoreViewModel,
  потому что вы можете назначить оценку непосредственно viewModel.score переменной.
   Но иногда вам нужны данные прямо при viewModel инициализации.
 */