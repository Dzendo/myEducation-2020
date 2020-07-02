package com.example.android.guessworld.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel for the final screen showing the score
 * ViewModel для финального экрана, показывающего результат
 */
// Вы передадите значение оценки во время ViewModel инициализации, используя шаблон фабричного метода .
// Этот класс будет ViewModel для фрагмента партитуры.
// М.Б ScoreViewModelFactoty берет в себя аргумент из Bundle и создает ScoreViewModel(finalScore: Int)
// передавая ему этот аргумент, т.о. не надо разбирать bundle ????
class ScoreViewModel(finalScore: Int) : ViewModel() {
    // The final score для сохранения окончательного результата. - переехал в init c LiveData
    // var score = finalScore
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score
    // Этот объект используется для сохранения LiveData события
    // для перехода от экрана счета к экрану игры
    private val _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain: LiveData<Boolean>
        get() = _eventPlayAgain

    init {
        Log.i("ScoreViewModel", "Final score is $finalScore")
        _score.value = finalScore // берет из переданного в аргументах
    }
    // методы для установки и сброса события, _eventPlayAgain: В фрагменте наблюдатель eventPlayAgain
    fun onPlayAgain() {
        _eventPlayAgain.value = true
    }
    fun onPlayAgainComplete() {
        _eventPlayAgain.value = false
    }
}

// При этой созданной ViewModel для экрана можно быть уверенным что при крахе UI (поворот, интрнет итд)
// фрагмент будет пересоздан андроидом пустой, вызовет ScoreFragment : Fragment()
// Он котлином надует макет xml и возьмет отсюда все данные которые никуда не делись
// кроме этого iewModel будет создан/вызван ScoreViewModelFactoty который передаст ему bundle

/*
В этом задании вы меняете счет на LiveData объект в ScoreViewModel и присоединяете к нему наблюдателя.
 Это задание похоже на то, что вы делали, когда добавляли LiveData в GameViewModel.
Вы вносите эти изменения ScoreViewModel для полноты,
 чтобы все данные в вашем приложении использовались LiveData.
 */