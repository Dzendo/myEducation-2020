package com.example.android.roomwordsample

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Каждый @Entity класс представляет таблицу SQLite.
// Аннотируйте объявление вашего класса, чтобы указать, что это объект.
// Вы можете указать имя таблицы, если хотите, чтобы оно отличалось от имени класса.
@Entity(tableName = "word_table")
data class Word(
    // Каждой сущности нужен первичный ключ. Для простоты каждое слово действует как собственный первичный ключ.
    @PrimaryKey
    // Указывает имя столбца в таблице, если вы хотите, чтобы оно отличалось от имени переменной-члена.
    @ColumnInfo(name = "word")
    val word: String)
// Каждое свойство, хранящееся в базе данных, должно иметь публичную видимость,
// что является значением по умолчанию Kotlin.

//  Вы можете автоматически генерировать уникальные ключи,
// @PrimaryKey(autoGenerate = true) val id: Int,

