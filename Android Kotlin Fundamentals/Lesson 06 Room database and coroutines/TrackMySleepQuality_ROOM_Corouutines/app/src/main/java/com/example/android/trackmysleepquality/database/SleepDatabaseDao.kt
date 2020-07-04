/*
 * Copyright 2019, The Android Open Source Project
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

package com.example.android.trackmysleepquality.database

import androidx.lifecycle.LiveData
import androidx.room.*

/*
В этой задаче вы определяете объект доступа к данным (DAO).
 В Android DAO предоставляет удобные методы для вставки, удаления и обновления базы данных
 Думайте о DAO как об определении пользовательского интерфейса для доступа к вашей базе данных
 */
// (Примечание. Вы можете вызывать функции как угодно.)
/*
Мы возвращаем это как живые данные, так что когда у нас есть все по ночам
нам не нужно получать их снова каждый раз, когда происходят изменения
как только будет ROOM
 */
// Через Factory цепляется в ViewModel чтобы там переопределиться и вызываться через SleepDatabase

@Dao  // Все DAO должны быть аннотированы @Dao ключевым словом
interface SleepDatabaseDao {
// Room сгенерирует весь необходимый код для операций SleepNight в/из базу данных
// аннотации: @Insert, @Delete, и @Update. Для всего остального есть @Query аннотация

    @Insert  // Вставьте новые ночи.
    fun insert(night: SleepNight)

    @Update  // Обновите существующую ночь, чтобы обновить время окончания и рейтинг качества.
    fun update(night: SleepNight)

    // Для оставшейся функциональности нет удобной аннотации,
    // поэтому вы должны использовать @Query аннотацию и предоставлять запросы SQLite.

    // Получить конкретную ночь на основе его ключа.
    @Query ("SELECT * from daily_sleep_quality_table WHERE nightId = :key")
    fun get(key: Long): SleepNight?
    // Выберите все столбцы из daily_sleep_quality_table
    // WHERE nightId соответствует: key аргумент.
    // Обратите внимание :key. Вы используете двоеточие в запросе для ссылки на аргументы в функции.


    // Удалить все записи в базе данных.
    @Query("DELETE FROM daily_sleep_quality_table")
    fun clear()

    /*
    Чтобы получить «сегодня вечером» из базы данных, напишите запрос SQLite,
     который возвращает первый элемент списка результатов, упорядоченный nightIdв порядке убывания.
      Используйте LIMIT 1 для возврата только одного элемента.
     */
    // Получите самую последнюю ночь.
    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
    fun getTonight(): SleepNight?
    //  Сделайте SleepNight возвращаемое getTonight() значение nullable,
    //  чтобы функция могла обрабатывать случай, когда таблица пуста.
    //  (Таблица пуста в начале и после очистки данных.)

    // Получите все ночи , чтобы вы могли показать их. отдает LiveData!!! да еще и от LIST ночей
    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC")
    fun getAllNights(): LiveData<List<SleepNight>>
    /*
    Запрос SQLite должен возвращать все столбцы из daily_sleep_quality_table, упорядоченные в порядке убывания.
     Давай getAllNights()вернем список SleepNight как LiveData. обновляет Room это LiveData для нас,
     и нам не нужно указывать для этого наблюдателя.
     Эта LiveData!!! да еще и от LIST ночей LiveData<List<SleepNight>> всегда будет иметь
     последнее - актуальное значение и его можно просто отображать - само изменится
     а если и прослушивать то будешь в курсе об изменениях (если надо, но необязательно)
     */
}