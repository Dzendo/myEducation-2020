/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.dessertclicker

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import timber.log.Timber

/**
 * This is a class representing a timer that you can start or stop. The secondsCount outputs a count of
 * how many seconds since it started, every one second.
 * Это класс, представляющий таймер, который можно запустить или остановить. Отсчет секунд выводит количество
 * сколько секунд прошло с момента его начала, каждую секунду.
 *
 * -----
 *
 * Handler and Runnable are beyond the scope of this lesson. This is in part because they deal with
 * threading, which is a complex topic that will be covered in a later lesson.
 * Обработчик и Runnable выходят за рамки этого урока. Отчасти это объясняется тем, что они имеют дело с
 * многопоточность-это сложная тема, которая будет рассмотрена в следующем уроке.
 *
 * If you want to learn more now, you can take a look on the Android Developer documentation on
 * threading:
 * Если вы хотите узнать больше сейчас, вы можете взглянуть на документацию разработчика Android на
 * нарезка резьбы:
 *
 * https://developer.android.com/guide/components/processes-and-threads
 *
 */
//class DessertTimer {
class DessertTimer(lifecycle: Lifecycle) : LifecycleObserver {
    //Конструктор берет Lifecycle объект, который является жизненным циклом, который наблюдает таймер.
    //Определение класса реализует LifecycleObserver интерфейс.

    // The number of seconds counted since the timer started
    // Количество секунд, отсчитанных с момента запуска таймера
    var secondsCount = 0

    /**
     * [Handler] is a class meant to process a queue of messages (known as [android.os.Message]s)
     * or actions (known as [Runnable]s)
     * [Handler] - это класс, предназначенный для обработки очереди сообщений (известный как [android.ос.Сообщение]s)
     * или действия (известные как [Runnable] s)
     */
    private var handler = Handler(Looper.myLooper()!!)
    private lateinit var runnable: Runnable
    init {
        // метод для подключения объекта жизненного цикла, переданного от владельца (действия)
        // к этому классу (наблюдателю)
        lifecycle.addObserver(this)
    }
    //  Все события жизненного цикла находятся в Lifecycle.Event классе.
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startTimer() {
        // Create the runnable action, which prints out a log and increments the seconds counter
        // Создайте запускаемое действие, которое выводит журнал и увеличивает счетчик секунд
        runnable = Runnable {
            secondsCount++
            Timber.i("Timer is at : $secondsCount")
            // postDelayed re-adds the action to the queue of actions the Handler is cycling
            // through. The delayMillis param tells the handler to run the runnable in
            // 1 second (1000ms)
            // postDelayed повторно добавляет действие в очередь действий, циклически выполняемых обработчиком
            // через. В delayMillis парам говорит обработчик для выполнения Runnable в
            // 1 секунда (1000 мс)
            handler.postDelayed(runnable, 1000)
        }


        // This is what initially starts the timer
        // Это то, что изначально запускает таймер
        handler.postDelayed(runnable, 1000)

        // Note that the Thread the handler runs on is determined by a class called Looper.
        // In this case, no looper is defined, and it defaults to the main or UI thread.
        // Обратите внимание, что поток, на котором работает обработчик, определяется классом Looper.
        // В этом случае петлитель не определен,
        // и по умолчанию он используется для основного потока
        // или потока пользовательского интерфейса.
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopTimer() {
        // Removes all pending posts of runnable from the handler's queue, effectively stopping the
        // timer
        // Удаляет все отложенные сообщения runnable из очереди обработчика,
        // эффективно останавливая работу обработчика.
        // таймер.
        handler.removeCallbacks(runnable)
    }
}