package com.example.android.roomwordsample

import androidx.lifecycle.LiveData

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
// Объявляет DAO как частное свойство в конструкторе. Пройдите в ДАО
// вместо всей базы данных, потому что вам нужен только доступ к DAO

class WordRepository(private val wordDao: WordDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    // Room выполняет все запросы в отдельном потоке.
    // Observed LiveData уведомляет наблюдателя, когда данные изменились.
    val allWords: LiveData<List<Word>> = wordDao.getAlphabetizedWords()

    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}