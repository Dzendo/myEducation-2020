/*
 * Copyright 2018, The Android Open Source Project
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
 *
 */

package com.example.android.devbyteviewer.repository
// Repository - Хранилище обеспечит единое представление наших данных из нескольких источников
// Repository обеспечивает логику обновления данных, хотя ему по барабану что это за данные и откуда они идут

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.devbyteviewer.database.VideosDatabase
import com.example.android.devbyteviewer.database.asDomainModel
import com.example.android.devbyteviewer.domain.Video
import com.example.android.devbyteviewer.network.Network
import com.example.android.devbyteviewer.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


    /* 14. Задача: создать хранилище подключается вызовы из DevByteViewModel,
В этой задаче вы создаете репозиторий для управления автономным кэшем, который вы реализовали в предыдущей задаче.
Ваша Room база данных не имеет логики для управления автономным кешем, она имеет только методы для вставки и извлечения данных.
Хранилище будет иметь логику для извлечения результатов сети и поддержания базы данных в актуальном состоянии.
 */
/*
A Repository- это обычный класс, который имеет один (или несколько) методов,
которые загружают данные без указания источника данных как части основного API.
Поскольку это просто обычный класс, нет необходимости в аннотации для определения репозитория.
Репозиторий скрывает сложность управления взаимодействиями между базой данных и сетевым кодом.

Вы можете определить любой API, который имеет смысл для ваших данных.
Сделайте, чтобы ваш репозиторий принимал параметр конструктора VideosDatabase,
таким образом у вас не будет никаких зависимостей от Context в вашем репозитории
(так называемое внедрение зависимостей).
 */

    /**
     * Repository for fetching devbyte videos from the network and storing them on disk
     * Репозиторий для извлечения devbytes видео из сети и хранения их на диске
     * Передайте VideosDatabase объект в качестве параметра конструктора класса для доступа к Dao методам
     */
// Шаг 1: Добавить репозиторий: - Создайте класс репозитория:
    class VideosRepository(private val database: VideosDatabase) {

        // Шаг 2: Получить данные из базы данных
        // объект для чтения списка воспроизведения видео из базы данных
        //  Этот LiveData объект автоматически обновляется при обновлении базы данных.
        //  Прикрепленный фрагмент или действие обновляется новыми значениями.
        //val videos: LiveData<List<DevByteVideo>> = database.videoDao.getVideos()
        //  method returns a list of database objects, not a list of DevByteVideo objects,
        // Чтобы исправить ошибку, используйте Transformations.map
        // для преобразования списка объектов базы данных в список объектов домена.
        // Используйте asDomainModel() функцию преобразования.
        val videos: LiveData<List<Video>> =  // from the DB
                Transformations.map(database.videoDao.getVideos()) {
            it.asDomainModel()
        }
        // Обновление : Transformations.map метод использует функцию преобразования
        // для преобразования одного LiveData объекта в другой LiveData.
        // Преобразования рассчитываются только тогда, когда активная активность или фрагмент наблюдают
        // за возвращенным LiveData свойством.

        // Room может вернуть LiveData объектов базы данных, вызванных DatabaseVideo
        // с использованием getVideos() метода, который вы написали в VideoDao.
        // Таким образом, вам нужно преобразовать список DatabaseVideo в список Video.
        // Вы уже написали функцию расширения Kotlin для этого уже на предыдущем шаге под названием asDomainModel().

        // Transformations.map идеально подходит для отображения выходных данных одной LiveData на другой тип.
        // Если вам нужно еще одно обновление Transformations.map(), вы можете посмотреть видео,
        // в котором мы его использовали, на уроке «Архитектура приложения».

        /**
         * Refresh the videos stored in the offline cache.
         * Обновите видео, хранящиеся в автономном кэше.
         *
         * This function uses the IO dispatcher to ensure the database insert database operation
         * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
         * function is now safe to call from any thread including the Main thread.
         *
         * Эта функция использует диспетчер ввода-вывода для обеспечения работы базы данных insert database
         * происходит на диспетчере ввода-вывода. При переключении на диспетчер ввода-вывода с помощью `с контекстом " это
         * функция теперь безопасна для вызова из любого потока, включая основной поток.
         *
         */
        // Этот метод будет API, используемый для обновления автономного кэша.
        // Поскольку refreshVideos() выполняет операцию с базой данных, она должна вызываться из сопрограммы.
        // Определите refreshVideos() функцию для обновления автономного кэша:
        suspend fun refreshVideos() {
            // переключите контекст сопрограммы Dispatchers.IO на выполнение сетевых операций и операций с базой данных.
            withContext(Dispatchers.IO) {
                // выберите список воспроизведения видео DevByte из сети, используя экземпляр службы Retrofit DevByteNetwork.
                // Используйте await() функцию, чтобы приостановить сопрограмму, пока не будет доступен список воспроизведения.
                // Получите данные из сети и затем поместите их в базу данных
                val playlist = Network.devbytes.getPlaylist().await()
                // сохраните список воспроизведения в Room базе данных.
                database.videoDao.insertAll(*playlist.asDatabaseModel())
                //  В refreshVideos(), выполните сетевой вызов getPlaylist()и используйте await()функцию,
                //  чтобы сообщить сопрограмме о приостановке, пока данные не станут доступны.
                //  Затем позвоните, insertAll()чтобы вставить плейлист в базу данных.
                // database.videoDao.insertAll(*playlist.asDatabaseModel()) - Udacity
                // Обратите внимание, что звездочка * является оператором спреда.
                // Это позволяет передавать массив в функцию, которая ожидает переменные .
            }
        // из функции refreshVideos() ничего возвращать не надо: просто читаем из инета и сохраняем в Room
        }
    }

/*
Примечание.
Базы данных на Android хранятся в файловой системе или на диске, и для сохранения они должны выполнять дисковый ввод-вывод.
Дисковый ввод-вывод, или чтение и запись на диск, происходит медленно и всегда блокирует текущий поток до завершения операции.
Из-за этого вам нужно запустить дисковый ввод-вывод в диспетчере ввода-вывода.
Этот диспетчер предназначен для разгрузки блокирующих задач ввода-вывода с использованием общего пула потоков
.withContext(Dispatchers.IO) { ... }
 */

// Шаг 2: Получить данные из базы данных
/*
На этом этапе вы создаете LiveData объект для чтения списка воспроизведения видео из базы данных.
Этот LiveData объект автоматически обновляется при обновлении базы данных.
Прикрепленный фрагмент или действие обновляется новыми значениями.
В VideosRepository классе объявите LiveData объект
 */


// Теперь вы создали репозиторий для своего приложения.
// В следующей задаче вы используете простую стратегию обновления,
// чтобы поддерживать локальную базу данных в актуальном состоянии.

/*
A Repository- это обычный класс, который имеет один (или несколько) методов,
которые загружают данные без указания источника данных как части основного API.
Поскольку это просто обычный класс, нет необходимости в аннотации для определения репозитория.
Репозиторий скрывает сложность управления взаимодействиями между базой данных и сетевым кодом.
 */


