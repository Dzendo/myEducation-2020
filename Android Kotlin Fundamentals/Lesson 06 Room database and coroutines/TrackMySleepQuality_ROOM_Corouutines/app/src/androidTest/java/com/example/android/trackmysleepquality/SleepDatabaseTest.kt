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

package com.example.android.trackmysleepquality

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 * Это не должно быть полным набором тестов. Для простоты большинство ваших образцов этого не делают
 * включите тесты. Однако при строительстве комнаты полезно убедиться, что она работает раньше
 * добавление пользовательского интерфейса.
 * При тестировании базы данных вам необходимо использовать все методы, определенные в DAO.
 * Для полного тестирования , добавить и выполнить тесты , чтобы осуществлять другие методы DAO.
 */
// фрагмент кода, который вы можете использовать повторно:
// AndroidJUnit4 - исполнитель тестов представляет собой программу
// которая устанавливает и выполняет тесты.
@RunWith(AndroidJUnit4::class)
class SleepDatabaseTest {

    private lateinit var sleepDao: SleepDatabaseDao
    private lateinit var db: SleepDatabase

    @Before  // создает в памяти SleepDatabase с SleepDatabaseDao
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        // Использование базы данных в памяти, поскольку информация, хранящаяся здесь, исчезает,
        // когда процесс убит.
        db = Room.inMemoryDatabaseBuilder(context, SleepDatabase::class.java)
                // Allowing main thread queries, just for testing.
                // Разрешение запросов к основному потоку только для тестирования.
                .allowMainThreadQueries()
                .build()
        sleepDao = db.sleepDatabaseDao
    }

    @After  // Когда тестирование завершено
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() {
        val night = SleepNight()
        sleepDao.insert(night)
        val tonight = sleepDao.getTonight()
        assertEquals(tonight?.sleepQuality, -1)
    }
}

/*
SleepDabaseTest это тестовый класс .
*В @RunWith аннотации указан исполнитель тестов,
 который представляет собой программу, которая устанавливает и выполняет тесты.

*Во время настройки выполняется функция, аннотированная с @Before,
 и она создает в памяти SleepDatabase с SleepDatabaseDao.
  «В памяти» означает, что эта база данных не сохраняется в файловой системе
   и будет удалена после выполнения тестов.

*Также при создании базы данных в памяти код вызывает другой метод,
 специфичный для теста allowMainThreadQueries.
  По умолчанию вы получаете сообщение об ошибке,
   если пытаетесь выполнить запросы в основном потоке.
    Этот метод позволяет запускать тесты в основном потоке,
     что следует делать только во время тестирования.

*В тестовом методе, помеченном как @Test, вы создаете, вставляете и извлекаете SleepNight,
 и утверждаете, что они одинаковы.
  Если что-то пойдет не так, бросьте исключение.
   В реальном тесте, вы бы несколько @Test методов.

*Когда тестирование завершено,
 функция, снабженная комментариями, @After выполняется для закрытия базы данных.

*Щелкните правой кнопкой мыши по тестовому файлу на панели Project и выберите Run 'SleepDatabaseTest' .

*После выполнения тестов на панели SleepDatabaseTest убедитесь, что все тесты пройдены.
 */

/*
Поскольку все тесты пройдены, теперь вы знаете несколько вещей:
Started running tests
Tests ran to completion.
База данных создается правильно.
Вы можете вставить SleepNight в базу данных.
Вы можете получить обратно SleepNight.
SleepNight Имеет правильное значение для качества.
 */