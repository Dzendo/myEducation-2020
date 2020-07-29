package com.example.android.guessworld.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

// LiveData является наблюдаемым классом держателя данных, который учитывает жизненный цикл.

// эти объявления нужны для вибратора:
private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
private val NO_BUZZ_PATTERN = longArrayOf(0)

/**
 * ViewModel containing all the logic needed to run the game
 * ViewModel никогда не должен содержать ссылок на фрагменты, действия или представления,
 * поскольку действия, фрагменты и представления не сохраняются после изменений конфигурации.
 * ViewModel Должен быть связан с контроллером интерфейса.
 * Чтобы связать их, вы создана ссылка на эту ViewModel контроллер UI внутри GameFragment.
 */
class GameViewModel : ViewModel() {

    // These are the three different types of buzzing in the game. Buzz pattern is the number of
    // milliseconds each interval of buzzing and non-buzzing takes.
    // Это три различных типа жужжания в игре. Узор жужжания - это количество
    // миллисекунды занимает каждый интервал жужжания и отсутствия жужжания.
    enum class BuzzType(val pattern: LongArray) {
        CORRECT(CORRECT_BUZZ_PATTERN),
        GAME_OVER(GAME_OVER_BUZZ_PATTERN),
        COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
        NO_BUZZ(NO_BUZZ_PATTERN)
    }

    companion object { // courlera 05.03 udacity 05.21 для таймера и жужжалки
        // These represent different important times - Они представляют собой разные важные времена
        // This is when the game is over - Это когда игра заканчивается
        const val DONE = 0L
        // This is the time when the phone will start buzzing each second
        // Это время, когда телефон начнет гудеть каждую секунду
        private const val COUNTDOWN_PANIC_SECONDS = 10L
        // This is the number of milliseconds in a second - Это число миллисекунд в секунду
        // Countdown time interval - // Интервал времени обратного отсчета через сколько светить
        const val ONE_SECOND = 1000L
        // This is the total time of the game - Это общее время игры т.е. 1 мин = 60 000 мсек
        const val COUNTDOWN_TIME = 60000L
    }
    // Чтобы сохранить время обратного отсчета таймера,
    // добавьте MutableLiveData переменную-член с именем _currentTime
    // и вспомогательное свойство currentTime.
    private val _currentTime = MutableLiveData<Long>()
    private val currentTime: LiveData<Long>
                get() = _currentTime
    // Вы устраните ошибку инициализации на следующем шаге
    private val timer: CountDownTimer       // Это стандартный класс Android

    /*
    Этот Transformations.map()метод предоставляет способ выполнять
     манипуляции с данными в источнике LiveData и возвращать LiveData объект результата
     */
    // Transformations.map() для преобразования одного LiveData объекта в другой
    // The String version of the current time этот вид вставляется в xml
    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }


    // The current word для LiveData + _Инкапсуляция
    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word


    // The current score для LiveData + _Инкапсуляция
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score
    // Так делается чтобы умирающий фрагмент не затер текущий счет в ViewModel, а только читал
    // и никто другой из классов не мог затереть текущий счет коме view Model - проще отлаживать

    // Event which triggers the end of the game - Событие, которое запускает окончание игры
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    // Event that triggers the phone to buzz using different patterns, determined by BuzzType
    // Событие, которое вызывает гудение телефона с использованием различных шаблонов, определяемых типом гудения
    private val _eventBuzz = MutableLiveData<BuzzType>()
    val eventBuzz: LiveData<BuzzType>
        get() = _eventBuzz

    init {
        _word.value = ""
        _score.value = 0
        _eventGameFinish.value = false   // есть только в Udacity
        // Эти методы должны быть в init блоке,
        // потому что вы должны сбрасывать список слов при ViewModel создании,
        // а не каждый раз, когда создается фрагмент
        resetList()
        nextWord()
        //Чтобы помочь вам лучше понять, как ViewModel работает жизненный цикл
        Log.i("GameViewModel", "GameViewModel created!")

        // Это стандартный класс Android - создается на 1 мин и обратные вызовы 1 сек
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
                if (millisUntilFinished / ONE_SECOND <= COUNTDOWN_PANIC_SECONDS) {
                    _eventBuzz.value = BuzzType.COUNTDOWN_PANIC
                }
            }

            override fun onFinish() {
                _currentTime.value = DONE
                _eventGameFinish.value = true
                // onGameFinish() //Codelabs
            }
        }
        // DateUtils.formatElapsedTime(newTime) // Позже
        timer.start()
    }  // конец init

    // Переменные определены для текущего слова и текущей оценки.
    // The current word- Текущее слово Переменная для текущего слова угадать.
    //var word = ""
    // The current score- Текущий счет Переменная для текущей оценки.
    //var score = 0



    // The list of words - the front of the list is the next word to guess
    // Список слов - в начале списка находится следующее слово, которое нужно угадать
    // Переменная для изменяемого списка всех слов, которые вам нужно угадать.
    // Этот список создается и сразу же перетасовывается,
    // используя resetList()каждый раз новый порядок слов.
    private lateinit var wordList: MutableList<String>

    /**
     * Resets the list of words and randomizes the order
     * Сбрасывает список слов и рандомизирует порядок
     * Метод, который создает и перемешивает список слов.
     */
    private fun resetList() {
        // wordList представляет собой образец список слов , которые будут использоваться в игре.
        wordList = mutableListOf(
                /*  "queen",
                  "hospital",
                  "basketball",
                  "cat",
                  "change",
                  "snail",
                  "soup",
                  "calendar",
                  "sad",
                  "desk",
                  "guitar",
                  "home",
                  "railway",
                  "zebra",
                  "jelly",
                  "car",
                  "crow",
                  "trade",
                  "bag",
                  "roll",
                  "bubble"
                 */
                "королева",
                "больница",
                "баскетбол",
                "кошка",
                "изменение",
                "улитка",
                "суп",
                "календарь",
                "грустный",
                "рабочий стол",
                "гитара",
                "домашний",
                "железная дорога",
                "зебра",
                "желе",
                "машина",
                "ворона",
                "торговля",
                "сумка",
                "рулон",
                "пузырь"
        )
        // Перемешивается список
        wordList.shuffle()
    }

    /**
     *  Callback called when the ViewModel is destroyed
     * ViewModel Разрушается, когда соответствующий фрагмент отдельно, или когда активность закончена.
     * Прямо перед ViewModel уничтожением вызывается onCleared()обратный вызов для очистки ресурсов.
     */
    override fun onCleared() {
        super.onCleared()
        // чтобы отслеживать GameViewModel жизненный цикл
        Log.i("GameViewModel", "GameViewModel destroyed!")
        timer.cancel()
    }


    /**
     * Moves to the next word in the list
     * Переход к следующему слову в списке
     * Способ перехода к следующему слову угадать.
     * Если в вашем изменяемом списке слов все еще есть слова,
     * удалите текущее слово, а затем установите
     */
    private fun nextWord() {
        if (wordList.isEmpty()) //{ gameFinished() }
            //_eventGameFinish.value = true
            // resetList() - с бесконечеыми вопросами и выходом по таймеру
            onGameFinish() // Coursera
        else {
            //Select and remove a word from the list - Выберите и удалите слово из списка
            _word.value = wordList.removeAt(0)
        }
    }
    /*private fun nextWord() {  // переделка для таймера Udacity 05.21
        if (wordList.isEmpty()) {
            resetList()   // Shuffle the word list, if the list is empty
        }
            //Select and remove a word from the list - Выберите и удалите слово из списка
            _word.value = wordList.removeAt(0)
    }*/
    /** Methods for updating the UI **/
    /** Methods for buttons presses  Методы для кнопки нажимает **/
    /** onSkip()Метод является обработчиком щелчка для Скип кнопки.
     *  Это уменьшает счет на 1, затем отображает следующее слово, используя nextWord()метод.
     *  Методы, когда вы нажимаете кнопки Пропустить / Получить .
     *  Они изменяют счет, а затем переходят к следующему слову в вашем worldList
     */
    fun onSkip() {
        _score.value = (score.value)?.minus(1)
        nextWord()
    }
    /** onCorrect()Метод является обработчиком щелчка для Got It кнопки.
     *  Этот метод реализован аналогично onSkip()методу.
     *  Разница лишь в том, что этот метод добавляет 1 к баллу вместо вычитания.
     *  Методы, когда вы нажимаете кнопки Пропустить / Получить .
     *  Они изменяют счет, а затем переходят к следующему слову в вашем worldList
     */
    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        _eventBuzz.value = BuzzType.CORRECT
        nextWord()
    }
    /** Method for the game completed event - Метод для завершенного события игры **/
    fun onGameFinish() {
        _currentTime.value = DONE
        _eventBuzz.value = BuzzType.GAME_OVER
        _eventGameFinish.value = true
    }
    /** Method for the game completed event - Метод для завершенного события игр**/
    // чтобы не повторялось при повороте т.е не срабатывала ненужно лайфдата
    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }
    fun onBuzzComplete() {
        _eventBuzz.value = BuzzType.NO_BUZZ
    }
}
/*
 Отличная работа!
 Ваше приложение использует LiveData для запуска события завершения игры,
 чтобы сообщить от GameViewModel фрагмента игры, что список слов пуст.
 Затем фрагмент игры перемещается к фрагменту партитуры.
 */


/**
 *Во время изменений конфигурации, таких как поворот экрана,
 *  контроллеры пользовательского интерфейса, такие как фрагменты, воссоздаются.
 *  Однако ViewModel случаи выживают.
 *  Если вы создаете ViewModel экземпляр с использованием ViewModel класса,
 *  каждый раз при повторном создании фрагмента создается новый объект.
 *  Вместо этого создайте ViewModel экземпляр, используя ViewModelProvider.
 *
 *  Важное замечание:
 *  Всегда используйте ViewModelProvider для создания ViewModel объектов,
 *  а не для непосредственного создания экземпляра экземпляра ViewModel.
 *
 */
/**
 * Напоминание : поскольку действия, фрагменты и представления приложения
 * не сохраняются после изменений конфигурации,
 * они ViewModel не должны содержать ссылок на действия, фрагменты или представления приложения.
 */
