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

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.trackmysleepquality.databinding.ActivityMainBinding

//import com.example.android.trackmysleepquality.databinding.ActivityMainBinding

// Стартовая версия с ScrollView без recycled
// Зато с DataBinding, ViewModel 2 шт приложения и фрагмента, полой ROOM и записью в нее
//

/**
 * This is the toy app for lesson 6 of the
 * Android App Development in Kotlin course on Udacity(https://www.udacity.com/course/???).
 * Это игрушечное приложение для урока 6 из
 * Разработка приложений для Android в курсе Kotlin по Udacity(https://www.udacity.com/course/???).
 *
 * The SleepQualityTracker app is a demo app that helps you collect information about your sleep.
 * - Start time, end time, quality, and time slept
 * Приложение Sleep Quality Tracker - это демонстрационное приложение,
 * которое помогает вам собирать информацию о вашем сне.
 * - Время начала, Время окончания, качество и время сна
 *
 * This app demonstrates the following views and techniques:
 * - Room database, DAO, and Coroutines
 * Это приложение демонстрирует следующие виды и методы:
 * - База данных номеров, DAO и сопрограммы
 *
 * It also uses and builds on the following techniques from previous lessons:
 * - Transformation map
 * - Data Binding in XML files
 * - ViewModel Factory
 * - Using Backing Properties to protect MutableLiveData
 * - Observable state LiveData variables to trigger navigation
 *
 * Он также использует и развивает следующие методы из предыдущих уроков:
 * - Карта трансформации
 * - Привязка данных в XML-файлах
 * - Модель Представления Заводе
 * - Использование резервных свойств для защиты изменяемых данных в реальном времени
 * - Наблюдаемое состояние живые переменные данных для запуска навигации
 */

/**
 * This main activity is just a container for our fragments,
 * where the real action is.
 * Эта основная деятельность - всего лишь контейнер для наших фрагментов,
 * где происходит настоящее действие.
 */
class MainActivity : AppCompatActivity() {
    //private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)

        //setContentView(R.layout.activity_main)
    }
}
// https://www.sqlite.org/lang.html

/*
* Пользовательский поток выглядит следующим образом:

* Пользователь открывает приложение и отображается экран отслеживания сна.
* Пользователь нажимает кнопку Пуск . Это записывает время начала и отображает его.
*  Кнопка « Пуск» отключена, а кнопка « Стоп» включена.
* Пользователь нажимает кнопку Стоп . Это записывает время окончания и открывает экран качества сна.
* Пользователь выбирает значок качества сна.
*  Экран закроется, и на экране отслеживания отобразится время окончания сна и качество сна.
*  Кнопка « Стоп» отключена, а кнопка « Пуск» включена. Приложение готово для еще одной ночи.
* Кнопка Очистить активна, когда в базе данных есть данные.
*  Когда пользователь нажимает кнопку « Очистить» , все его данные стираются
*  без возможности восстановления - нет «Вы уверены?» сообщение.
 */


/*
8. Резюме
Использование ViewModel, ViewModelFactory и связывание создать архитектуру пользовательского интерфейса для приложения данные.
Чтобы обеспечить бесперебойную работу пользовательского интерфейса,
 используйте сопрограммы для длительных задач, таких как все операции с базами данных.
Сопрограммы асинхронные и неблокирующие.
 Они используют suspend функции, чтобы сделать асинхронный код последовательным.
Когда сопрограмма вызывает функцию, отмеченную значком suspend, вместо блокировки до тех пор,
 пока эта функция не вернется как обычный вызов функции, она приостанавливает выполнение до тех пор,
  пока результат не будет готов. Затем он возобновляет с того места, где остановился с результатом.
Разница между блокировкой и приостановкой заключается в том,
 что если поток заблокирован, никакой другой работы не происходит.
  Если поток приостановлен, выполняется другая работа до тех пор, пока результат не станет доступен.
Чтобы запустить сопрограмму, вам нужна работа, диспетчер и область действия:

По сути, работа - это все, что можно отменить.
 У каждого сопрограммы есть работа, и вы можете использовать ее, чтобы отменить сопрограмму.
Диспетчер отсылает сопрограммы для работы на различных потоках.
 Dispatcher.Main выполняет задачи в главном потоке и
 Dispatcher.IO предназначен для разгрузки блокирующих задач ввода-вывода в общий пул потоков.
Область действия объединяет информацию, включая задание и диспетчер,
 для определения контекста, в котором выполняется сопрограмма. Области отслеживания сопрограмм.
Чтобы реализовать обработчики кликов, которые запускают операции с базой данных, следуйте этой схеме:

Запустите сопрограмму, которая выполняется в основном потоке или потоке пользовательского интерфейса,
 поскольку результат влияет на пользовательский интерфейс.
Вызовите функцию приостановки для выполнения длительной работы,
 чтобы не блокировать поток пользовательского интерфейса во время ожидания результата.
Длительная работа не имеет ничего общего с пользовательским интерфейсом,
 поэтому переключитесь на контекст ввода-вывода.
  Таким образом, работа может выполняться в пуле потоков, который оптимизирован и отведен для таких операций.
Затем вызовите функцию базы данных, чтобы сделать работу.
Используйте Transformations карту для создания строки из LiveData объекта каждый раз, когда объект изменяется.
 */