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

package com.example.android.trackrecycledview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.trackrecycledview.databinding.ActivityMainBinding

// Мой вариант с стартового 7 со всеми комметариямии - работает правильно

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
        //setContentView(R.layout.activity_main)
        /*
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_main)
       val binding = ActivityMainBinding.inflate(layoutInflater)
       setContentView(binding.root)
         */
    }
}
/*
Для отображения ваших данных в a RecyclerView вам нужны следующие части:

1. Data to display. - Данные для отображения.
2. RecyclerView Экземпляр определяется в файле макета - layout file, чтобы действовать в качестве контейнера для представлений.
3. A layout for one item of data. - Макет для одного элемента данных.
    Если все элементы списка выглядят одинаково, вы можете использовать один и тот же макет для всех них, но это не обязательно.
    Макет элемента должен быть создан отдельно от макета фрагмента, чтобы можно было создать один элемент за раз и заполнить данными.
4. A layout manager. - Менеджер по верстке.
    Менеджер по расположению управляет организацией (расположением) компонентов пользовательского интерфейса в представлении.
5. A view holder. -  Держатель вида расширяет ViewHolder класс.
    Он содержит информацию о представлении для отображения одного элемента из макета элемента.
    Держатели представлений также добавляют информацию, которая RecyclerView используется
    для эффективного перемещения представлений по экрану.
6. An adapter. - Адаптер
    Адаптер соединяет ваши данные с RecyclerView.
    Он адаптирует данные так, чтобы их можно было отображать в ViewHolder.
    А RecyclerView использует адаптер, чтобы выяснить, как отображать данные на экране.
7. Соеденить все это в правильной комбинации
   Data(LIVE) -> An adapter ->  RecyclerView(XML) + layout manager(list,grid...) + item of data (xml) + view holder +  
   class SleepNightListener - отдельно для реагирования на щелчки по элементам
   А еще в это вмешивается ViewModel, Room, LiveData,...@BindingAdapter,
   классы An adapter + view holder в файле SleepNightAdapter.kt и там же SleepNightListener и вся морока с заголовком
   
 */

/*
4. Задача: сделать элементы кликабельными
В этой задаче вы обновляете RecyclerView функцию реагирования на нажатия пользователя,
показывая экран подробностей для элемента, который коснулся.

Получение щелчков и их обработка - это задача, состоящая из двух частей:
 во-первых, вам необходимо прослушать и получить щелчок и определить, какой элемент был нажат.
 Затем вам нужно ответить на щелчок действием.

Итак, что является лучшим местом для добавления прослушивателя кликов для этого приложения?

В SleepTrackerFragment хостах много мнений,
и поэтому прослушивания нажмите события на уровне фрагмента не покажут вам ,какой элемент был щелкнули.
Он даже не скажет вам, был ли элемент нажат или один из других элементов пользовательского интерфейса.

Прислушиваясь к RecyclerView уровню, трудно точно определить, на какой пункт в списке нажал пользователь.

Лучший способ получить информацию об одном элементе, по которому щелкнули,
 - это ViewHolder объект, поскольку он представляет один элемент списка.
Хотя ViewHolder это отличное место для прослушивания щелчков, обычно это не то место, где нужно их обрабатывать.
Итак, что является лучшим местом для обработки кликов?

В Adapter пунктах отображаются данные в представлениях, чтобы вы могли обрабатывать щелчки в адаптере.
Однако задача адаптера - адаптировать данные для отображения, а не работать с логикой приложения.
Обычно вы должны обрабатывать щелчки в ViewModel, потому что у них ViewModel есть доступ
к данным и логике для определения того, что должно произойти в ответ на щелчок.

Совет: Существуют другие шаблоны для реализации прослушивателей RecyclerViews щелчков,
но тот, с которым вы работаете в этой кодовой метке, легче объяснить и проще реализовать.
Работая над приложениями для Android, вы столкнетесь с различными шаблонами использования прослушивателей кликов в RecyclerViews.
Все шаблоны имеют свои преимущества.
 */

/* 07.4
7. Резюме
Чтобы элементы RecyclerView реагировали на клики,
прикрепите прослушиватели кликов к элементам списка в ViewHolder и обрабатывайте клики в ViewModel.

Чтобы сделать элементы в RecyclerView ответ на клики, вам необходимо сделать следующее:

Создайте класс слушателя, который принимает лямбду и назначает ее onClick() функции.
class SleepNightListener(val clickListener: (sleepId: Long) -> Unit) {
   fun onClick(night: SleepNight) = clickListener(night.nightId)
}

Установите прослушиватель щелчка на представлении.
android:onClick="@{() -> clickListener.onClick(sleep)}"

Передайте слушатель щелчка конструктору адаптера в держатель представления и добавьте его в объект привязки.
class SleepNightAdapter(val clickListener: SleepNightListener):
       ListAdapter<DataItem, RecyclerView.ViewHolder>(SleepNightDiffCallback()
holder.bind(getItem(position)!!, clickListener)
binding.clickListener = clickListener

Во фрагменте, который показывает представление рециркулятора, где вы создаете адаптер,
определите прослушиватель щелчков, передав лямбду адаптеру.
val adapter = SleepNightAdapter(SleepNightListener { nightId ->
      sleepTrackerViewModel.onSleepNightClicked(nightId)
})

Реализуйте обработчик щелчков в модели представления.
Для кликов по элементам списка это обычно вызывает переход к фрагменту детали.
 */

/* 07.5
8. Резюме
Заголовка , как правило , элемент , который перекрывает ширину списка и действует в качестве заголовка или сепаратора. Список может иметь один заголовок для описания содержимого элемента или несколько заголовков для группировки элементов и отдельных элементов друг от друга.
A RecyclerView может использовать несколько держателей вида для размещения разнородного набора элементов; например, заголовки и элементы списка.
Один из способов добавления заголовков состоит в том, чтобы изменить ваш адаптер для использования другого ViewHolder, проверяя индексы, где должен отображаться ваш заголовок. AdapterОтвечает за отслеживание заголовка.
Другой способ добавить заголовки - изменить набор данных (список) для вашей сетки данных, что вы и сделали в этой кодовой метке.
Вот основные шаги для добавления заголовка:

Абстрагируйте данные в вашем списке, создав объект, DataItem который может содержать заголовок или данные.
Создайте держатель вида с макетом для заголовка в адаптере.
Обновите адаптер и его методы для использования любого вида RecyclerView.ViewHolder.
В onCreateViewHolder(), верните правильный тип держателя представления для элемента данных.
Обновление SleepNightDiffCallback для работы с DataItem классом.
Создайте addHeaderAndSubmitList()функцию, которая использует сопрограммы для добавления заголовка в набор данных, а затем вызывает submitList().
Реализуйте, GridLayoutManager.SpanSizeLookup()чтобы сделать заголовок только трех пролетов шириной.
 */
