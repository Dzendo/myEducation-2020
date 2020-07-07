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
 */

package com.example.android.trackrecycledview.sleeptracker
/*
Основная задача при реализации RecyclerView- создание адаптера.
У вас есть простой держатель для вида элемента и макет для каждого элемента.
Теперь вы можете создать адаптер.
Адаптер создает держатель представления и заполняет его данными для RecyclerView отображения.
 */
// 07.4 Шаг 1. Создайте прослушиватель кликов и запустите его из макета элемента

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackrecycledview.R
import com.example.android.trackrecycledview.database.SleepNight
import com.example.android.trackrecycledview.databinding.ListItemSleepNightBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// 07.5 Заголовок
private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1


// Стартовый код 07,3 Grid
class SleepNightAdapter(private val clickListener: SleepNightListener):
        //ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {
        ListAdapter<DataItem, RecyclerView.ViewHolder>(SleepNightDiffCallback()) {
// Измените конструктор SleepNightAdapter класса, чтобы получить val clickListener: SleepNightListener.
// Когда адаптер связывает его ViewHolder, ему необходимо предоставить этот прослушиватель щелчков.

//class SleepNightAdapter : ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {

    /* 07,5,5 Задача: использовать сопрограммы для манипулирования списком
    Это не имеет большого значения для короткого списка с одним заголовком,
    но вы не должны делать манипуляции со списком в addHeaderAndSubmitList() потоке пользовательского интерфейса.
    Представьте себе список с сотнями элементов, несколькими заголовками и логикой,
     чтобы решить, куда нужно вставить элементы. Эта работа принадлежит сопрограмме.
     */

    private val adapterScope = CoroutineScope(Dispatchers.Default)

   // override fun onBindViewHolder(holder: ViewHolder, position: Int) {
   override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       // val item = getItem(position)
        // holder.bind(item)
       // holder.bind(item!! , clickListener)  // для слушателя списка
       when (holder) {
           is ViewHolder -> {
               val nightItem = getItem(position) as DataItem.SleepNightItem
               holder.bind(nightItem.sleepNight, clickListener)
           }
       }
       // Как вариант переписал: по моему так можно если внутри нет заголовка
       //if (holder is ViewHolder)
       // (holder as ViewHolder).bind((getItem(position) as DataItem.SleepNightItem).sleepNight, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
    //override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //return ViewHolder.from(parent)
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }
    // Вместо того, чтобы использовать submitList(), предоставленный ListAdapter,
    // для отправки вашего списка, вы будете использовать эту функцию,
    // чтобы добавить заголовок, а затем отправить список.
    fun addHeaderAndSubmitList(list: List<SleepNight>?) {

        // запустите сопрограмму в, adapterScope чтобы управлять списком
        adapterScope.launch {

            //если переданный список есть null, верните только заголовок,
            // в противном случае присоедините заголовок к заголовку списка,
            // а затем отправьте список.
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.SleepNightItem(it) }
            }
            // Затем переключитесь в Dispatchers.Main контекст, чтобы отправить список
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
        // Ваш код должен собираться и запускаться, и вы не увидите никакой разницы.
    }
    // Откройте SleepTrackerFragment.kt и измените вызов submitList()на addHeaderAndSubmitList().
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.SleepNightItem -> ITEM_VIEW_TYPE_ITEM
            else ->  ITEM_VIEW_TYPE_ITEM
        }
    }

    // 07.5 Заголовок
    class TextViewHolder(view: View): RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.header, parent, false)
                return TextViewHolder(view)
            }
        }
    }


    class ViewHolder private constructor(val binding: ListItemSleepNightBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: SleepNight, clickListener: SleepNightListener) {
            binding.sleep = item
            binding.clickListener = clickListener  // для слушателя списка
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


/*class SleepNightDiffCallback : DiffUtil.ItemCallback<SleepNight>() {

    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem.nightId == newItem.nightId
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem == newItem
    }
}*/
class SleepNightDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }
    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

    // 07.4: Шаг 1. Создайте прослушиватель кликов и запустите его из макета элемента
    class SleepNightListener(val clickListener: (sleepId: Long) -> Unit) {
        fun onClick(night: SleepNight) = clickListener(night.nightId)
    }
// 07.5 Заголовок
sealed class DataItem {
    abstract val id: Long
    // SleepNightItem, который является оберткой вокруг a SleepNight,
    // поэтому он принимает единственное значение с именем sleepNight.
    // Чтобы сделать его частью запечатанного класса, сделайте так, чтобы он расширялся DataItem
    data class SleepNightItem(val sleepNight: SleepNight): DataItem(){
        override val id = sleepNight.nightId
    }
    // Второй класс Header для представления заголовка.
    // Поскольку заголовок не имеет фактических данных, вы можете объявить его как object.
    // Это означает, что будет только один случай Header.
    // Опять же, пусть это продлится DataItem
    object Header: DataItem(){
        override val id = Long.MIN_VALUE
    }
    // Создайте макет для заголовка в новом файле ресурсов макета с именем header.xml,
    // который отображает TextView
}

    /*
    Внутри SleepNightListener класса добавьте onClick()функцию.
    Когда щелкает представление, отображающее элемент списка, оно вызывает эту onClick() функцию.
    (Вы установите android:onClick свойство представления позже для этой функции.)

    Добавьте аргумент функции night типа SleepNight для onClick().
    Представление знает, какой элемент он отображает, и эта информация должна быть передана для обработки щелчка.

    предоставьте clickListener обратный вызов в конструкторе SleepNightListener и назначьте его onClick()
    Предоставление лямбды, которая обрабатывает щелчок по имени,
    clickListener помогает отслеживать его при передаче между классами.
    clickListener Обратный вызов требуется только night.nightId для доступа к данным из базы данных.
    Далее занести его в  list_item_sleep_night.xml.
     */



/* Окончательный вариант разработки 07,2 со всеми комментариями:
// Окончательный текст решения 05,1: все комменарии внизу
//class SleepNightAdapter: RecyclerView.Adapter<SleepNightAdapter.ViewHolder>() {
class SleepNightAdapter : ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {

    /*  Это при Diff становится не нужным
    var data =  listOf<SleepNight>()
        set(value) {
            field = value
            notifyDataSetChanged()  // неэффективен
            // говорит, RecyclerView что весь список является потенциально недействительным.
            // RecyclerView имеет класс, DiffUtil который называется для расчета различий между двумя списками.
        }

    override fun getItemCount() = data.size
    */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //val item = data[position]
        val item = getItem(position)
        holder.bind(item)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemSleepNightBinding) : RecyclerView.ViewHolder(binding.root){

        // при привязке данных перенесла inline поля ниже
        fun bind(item: SleepNight) {
            // Шаг 2. Обновите SleepNightAdapter:
            // Внутри bind()присвойте sleep item, потому что вам нужно сообщить объекту привязки о вашем новом SleepNight:
            binding.sleep = item
            binding.executePendingBindings()
            /*
            Этот вызов является оптимизацией, которая запрашивает привязку данных для немедленного выполнения любых ожидающих привязок.
            Всегда полезно вызывать executePendingBindings() при использовании связывающих адаптеров в a RecyclerView,
             поскольку это может немного ускорить изменение размеров представлений.
             */

            /* Это все не нужно при создании адаптеров
            val res = itemView.context.resources
            binding.sleepLength.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
            binding.qualityString.text = convertNumericQualityToString(item.sleepQuality, res)
            binding.qualityImage.setImageResource(when (item.sleepQuality) {
                0 -> R.drawable.ic_sleep_0
                1 -> R.drawable.ic_sleep_1
                2 -> R.drawable.ic_sleep_2
                3 -> R.drawable.ic_sleep_3
                4 -> R.drawable.ic_sleep_4
                5 -> R.drawable.ic_sleep_5
                else -> R.drawable.ic_sleep_active
            })*/
        }
        // для Создайте адаптеры привязки
        // Вы возьмете код , который вычисляет значения binding.sleepLength, binding.quality и binding.qualityImage,
        // и использовать его внутри адаптера вместо этого
        // В Kotlin вы можете написать адаптер привязки в качестве функции расширения для класса представления, который получает данные.

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
               /* не нужна с привязкой данных
                val view = layoutInflater
                        .inflate(R.layout.list_item_sleep_night, parent, false)
                return ViewHolder(view)
                */
                val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class SleepNightDiffCallback : DiffUtil.ItemCallback<SleepNight>() {
    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem.nightId == newItem.nightId
    }

    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem == newItem
        // эта проверка на равенство проверит все поля, потому что SleepNight это класс данных
    }
}
 07,2 */

// Шаг 2. Используйте submitList (), чтобы обновлять список
// Open SleepTrackerFragment.kt. и в create adapter.submitList(it)

/*****************************************************************
// Адаптер для адаптации данных приложения которые будут использоваться в RecyclerView
// адаптировать SleepNight к чему-то, что RecyclerView может использовать
    //class SleepNightAdapter: RecyclerView.Adapter<TextItemViewHolder>() {
    // : расширение делает его адаптером с держателем TextItemViewHolder - базовый
    class SleepNightAdapter: RecyclerView.Adapter<SleepNightAdapter.ViewHolder>() {
    // ViewHolder - объект, который содержит представления и через него работает RecyclerView
    var data = listOf<SleepNight>()  // это данные, которые адаптер адаптирует для исп в RecyclerView
        // 2. добавить пользовательский сеттер на data что вызовы
        set(value) {
            field = value
            notifyDataSetChanged()
            // сообщает адаптерц когда данные поменялись для перерисовки
        }
    /*  Примечание.
     При notifyDataSetChanged()вызове RecyclerView перерисовывается весь список, а не только измененные элементы.
     Это просто, и пока работает. Вы улучшите этот код позже в этой серии программных кодов.
     */

    override fun getItemCount(): Int {
        // надо знать насколько велика полоса прокрутки = размер дата
        return data.size

    }
    //override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
     override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Как RecyclerView нарисовать элемент: TextItemViewHolder - стандартный тип, позиция в списке
        // Этот метод вызывается только для элементов, которые находятся на экране
        val item = data[ position]  // переменную для хранения данных.
        // Шаг 6: Узнайте, как перерабатываются держатели
        // Less 7 6. Переработка ViewHolder
        // Обновление, onBindViewHolder чтобы показать низкое качество сна в красном.
        /*if (item.sleepQuality <= 2) {
            holder.textView.setTextColor(Color.RED)
        } else {
            holder.textView.setTextColor(Color.BLACK)
        }*/
        // holder.textView.text = item.sleepQuality.toString()  // даст сразу попробовать
        /*  Этот кусок текста перенесен в  ViewHolder ниже в функцию bind
        val res = holder.itemView.context.resources
        holder.sleepLength.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
        holder.quality.text = convertNumericQualityToString(item.sleepQuality, res)

        holder.qualityImage.setImageResource(when (item.sleepQuality) {
            0 -> R.drawable.ic_sleep_0
            1 -> R.drawable.ic_sleep_1
            2 -> R.drawable.ic_sleep_2
            3 -> R.drawable.ic_sleep_3
            4 -> R.drawable.ic_sleep_4
            5 -> R.drawable.ic_sleep_5
            else -> R.drawable.ic_sleep_active
        })*/
        holder.bind(item)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { //  TextItemViewHolder { // ViewHolder {
      /*  Этот кусок текста перенесен в Компаньон объект ViewHolder ниже в функцию from
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
                .inflate(R.layout.list_item_sleep_night, parent, false) // as TextView          // text_item_view  // list_item_sleep_night
        // Обычно всегда надо надувать с  parent, false
        return   ViewHolder(view) //  TextItemViewHolder(view)
        // Вызвал коструктор и вернул ему надутый view

       */
        return ViewHolder.from(parent)
    }
    // Об остальном позаботится RecyclerView через Adapter

// На этот адаптер SleepNightAdapter.ViewHolder меняем стандартный TextItemViewHolder
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        /* получите ссылки на views.
        Вам нужна ссылка на представления, которые ViewHolder будут обновлены.
        Каждый раз, когда вы связываете это ViewHolder, вам нужно получить доступ к изображению и обоим текстовым представлениям.
        (Вы преобразуете этот код для использования привязки данных позже.)
         */
        val sleepLength:TextView = itemView.findViewById(R.id.sleep_length) // позже через привязку данных
        val quality: TextView = itemView.findViewById(R.id.quality_string)   // позже через привязку данных
        val qualityImage: ImageView = itemView.findViewById(R.id.quality_image)

    fun bind(item: SleepNight) {
        val res = itemView.context.resources
        sleepLength.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
        quality.text = convertNumericQualityToString(item.sleepQuality, res)
        qualityImage.setImageResource(when (item.sleepQuality) {
            0 -> R.drawable.ic_sleep_0
            1 -> R.drawable.ic_sleep_1
            2 -> R.drawable.ic_sleep_2
            3 -> R.drawable.ic_sleep_3
            4 -> R.drawable.ic_sleep_4
            5 -> R.drawable.ic_sleep_5
            else -> R.drawable.ic_sleep_active
        })
    }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                        .inflate(R.layout.list_item_sleep_night, parent, false)

                return ViewHolder(view)
            }
        }
    }
}
*********************************************************************/
// 3. теперь надо рассказать фрагменту об этом адаптере в SleepTrackerFragment
// В RecyclerView нужно знать о адаптере , чтобы использовать , чтобы получить держатели просмотра.


/* Задача 6 шаг 1
На этом этапе вы реорганизуете код и перемещаете все функциональные возможности держателя представления в ViewHolder.
Цель этого рефакторинга не в том, чтобы изменить то, как приложение выглядит для пользователя,
а в том, чтобы разработчикам было проще и безопаснее работать с кодом.
К счастью, в Android Studio есть инструменты, которые помогут.
 */

/*
8. Резюме
Отображение списка или сетки данных является одной из наиболее распространенных задач пользовательского интерфейса в Android. RecyclerViewразработан, чтобы быть эффективным даже при отображении очень больших списков.
RecyclerView выполняет только ту работу, которая необходима для обработки или рисования элементов, которые в данный момент видны на экране.
Когда элемент прокручивается за пределы экрана, его виды перерабатываются. Это означает, что элемент заполнен новым содержимым, которое прокручивается на экран.
Модель адаптера в программной инженерии помогает объектный работать вместе с другим API. RecyclerViewиспользует адаптер для преобразования данных приложения во что-то, что оно может отображать, без необходимости изменения того, как приложение хранит и обрабатывает данные.
Для отображения ваших данных в a RecyclerViewвам нужны следующие части:

RecyclerView
Чтобы создать экземпляр RecyclerView, определите <RecyclerView>элемент в файле макета.
LayoutManager
A RecyclerViewиспользует LayoutManagerдля организации расположения элементов в RecyclerView, например, размещения их в сетке или в линейном списке.

В <RecyclerView>файле макета установите app:layoutManagerатрибут для менеджера макета (например, LinearLayoutManagerили GridLayoutManager).

Вы также можете установить LayoutManagerдля RecyclerViewпрограммно. (Этот метод описан в более позднем коде.)
Макет для каждого элемента
Создайте макет для одного элемента данных в файле макета XML.
Адаптер
Создайте адаптер, который подготавливает данные и способ их отображения в ViewHolder. Свяжите адаптер с RecyclerView.

При RecyclerViewзапуске он будет использовать адаптер, чтобы выяснить, как отображать данные на экране.

Адаптер требует, чтобы вы реализовали следующие методы:
- getItemCount()чтобы вернуть количество элементов.
- onCreateViewHolder()вернуть ViewHolderэлемент в списке.
- onBindViewHolder()адаптировать данные к представлениям для элемента в списке.

ViewHolder
A ViewHolderсодержит информацию о представлении для отображения одного элемента из макета элемента.
onBindViewHolder()Метод адаптера адаптирует данные взглядов. Вы всегда отменяете этот метод. Обычно onBindViewHolder()раздувает макет для элемента и помещает данные в представления в макете.
Поскольку RecyclerViewданные ничего не знают, Adapterнеобходимо сообщить, RecyclerViewкогда эти данные изменятся. Используйте notifyDataSetChanged()для уведомления о Adapterтом, что данные изменились.

 */
/* 7,1 функции:
Из пользовательского ввода приложение создает список SleepNight объектов. Каждый SleepNight объект представляет собой одну ночь сна, его продолжительность и качество.
SleepNightAdapterАдаптирует список SleepNight объектов во что - то RecyclerView можно использовать и дисплей.
SleepNightAdapterАдаптер производит ViewHolders которые содержат взгляды, данные и метаинформацию для представления ресайклера для отображения данных.
RecyclerView использует SleepNightAdapter для определения количества элементов для отображения ( getItemCount()). RecyclerView использует onCreateViewHolder() и onBindViewHolder() для привязки держателей представления к данным для отображения.
 */

/* зачача 07,2 7 Задача: создать связующие адаптеры
В предыдущей кодовой метке вы использовали Transformations класс для получения LiveData и генерации форматированных строк для отображения в текстовых представлениях.
Однако если вам необходимо связать разные типы или сложные типы, вы можете предоставить адаптеры связывания, чтобы помочь связыванию данных использовать эти типы.
Адаптеры привязки - это адаптеры, которые принимают ваши данные и превращают их во что-то, что привязка данных может использовать для привязки представления, например текста или изображения.

Вы собираетесь реализовать три адаптера привязки, один для качественного изображения и один для каждого текстового поля.
Таким образом, чтобы объявить адаптер привязки, вы определяете метод, который принимает элемент и представление, и комментируете его @BindingAdapter.
В теле метода вы реализуете преобразование.
В Kotlin вы можете написать адаптер привязки в качестве функции расширения для класса представления, который получает данные.
 */