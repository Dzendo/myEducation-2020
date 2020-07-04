package com.example.android.roomwordsample

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * WordDao это интерфейс; DAO должны быть интерфейсами или абстрактными классами.
 * @Dao Аннотацию идентифицирует его как класс DAO для комнаты.
 *
 * suspend fun insert(word: Word): Объявляет функцию приостановки для вставки одного слова.
 * @Insert Аннотаций это специальный метод DAO аннотация , где вы не должны предоставлять какой - либо SQL!
 *  (Есть также @Deleteи @Update аннотации для удаления и обновления строк, но вы не используете их в этом приложении.)
 *
 * onConflict = OnConflictStrategy.IGNORE: Выбранная стратегия onConflict игнорирует новое слово,
 *  если оно в точности совпадает с тем, которое уже есть в списке.
 *  Чтобы узнать больше о доступных конфликтных стратегиях, ознакомьтесь с документацией .
 *
 * suspend fun deleteAll(): Объявляет функцию приостановки для удаления всех слов.
 * Нет удобной аннотации для удаления нескольких сущностей, поэтому она помечена универсальным @Query.
 * @Query("DELETE FROM word_table"): @Query требует,
 *  чтобы вы предоставили SQL-запрос в виде строкового параметра для аннотации,
 *  что позволяет выполнять сложные запросы чтения и другие операции.
 *
 * fun getAlphabetizedWords(): List<Word>: Метод, позволяющий получить все слова и вернуть его List из Words.
 * @Query("SELECT * from word_table ORDER BY word ASC"):
 * Запрос, возвращающий список слов, отсортированных по возрастанию.
 */



@Dao
interface WordDao {
    @Query("SELECT * from word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): LiveData<List<Word>>
    // отслеживаете изменения данных с помощью Observer in MainActivity.

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()
}