package com.example.android.roomwordsample

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// WordViewModel который получает в Application качестве параметра и расширяет AndroidViewModel.
class WordViewModel(application: Application) : AndroidViewModel(application) {

    // Добавлена закрытая переменная-член для хранения ссылки на хранилище.
    private val repository: WordRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    // Использование живых данных и кэширование того, что возвращает Get Alphabetized Words, имеет несколько преимуществ:
    // - Мы можем поставить наблюдателя на данные (вместо опроса на предмет изменений) и только обновлять данные.
    //   пользовательский интерфейс, когда данные действительно изменяются.
    // - Репозиторий полностью отделен от пользовательского интерфейса через ViewModel.

    // Добавлена открытая LiveData переменная-член для кэширования списка слов.
    val allWords: LiveData<List<Word>>

    // Создан init блок, который получает ссылку на WordDao из WordRoomDatabase.
    init {
        // В init блоке, построенном на WordRepository основе WordRoomDatabase.
        val wordsDao = WordRoomDatabase.getDatabase(application, viewModelScope).wordDao()
        repository = WordRepository(wordsDao)
        allWords = repository.allWords
        // инициализировали allWordsLiveData с помощью репозитория.
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     * Запуск новой сопрограммы для вставки данных неблокирующим способом
     */
    // метод- обертка, который вызывает метод репозитория insert()
    fun insert(word: Word) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }
}
/*
Таким образом, реализация insert()инкапсулируется из пользовательского интерфейса.
Мы не хотим, чтобы вставка блокировала основной поток, поэтому мы запускаем новую сопрограмму
и вызываем вставку репозитория, которая является функцией приостановки.
Как уже упоминалось, ViewModels имеют сопрограммированную область видимости,
основанную на их жизненном цикле viewModelScope, который мы здесь используем.
 */

/*
Предупреждение : не сохраняйте ссылку на контекст, который имеет более короткий жизненный цикл,
чем ваша ViewModel! Примеры:

Activity
Fragment
View

Сохранение ссылки может привести к утечке памяти, например, ViewModel имеет ссылку на уничтоженную активность!
Все эти объекты могут быть уничтожены операционной системой и воссозданы при изменении конфигурации,
и это может происходить много раз в течение жизненного цикла модели представления.

Если вам нужен контекст приложения (жизненный цикл которого длится столько же, сколько и приложение),
используйте AndroidViewModel, как показано в этом коде.

Важно:ViewModel не переживайте процесс приложения, убиваемый в фоновом режиме, когда ОС требуется больше ресурсов.
Для данных пользовательского интерфейса, которые должны пережить смерть процесса из-за нехватки ресурсов,
вы можете использовать модуль « Сохраненное состояние» для ViewModels.

 */