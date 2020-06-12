/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunfloweras.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.samples.apps.sunfloweras.R
import com.google.samples.apps.sunfloweras.HomeViewPagerFragmentDirections
import com.google.samples.apps.sunfloweras.data.PlantAndGardenPlantings
import com.google.samples.apps.sunfloweras.databinding.ListItemGardenPlantingBinding
import com.google.samples.apps.sunfloweras.viewmodels.PlantAndGardenPlantingsViewModel

// вызывается - создается в GardenFragment для RecycledView см. читать лекцию 07.1-5
// адаптер, который адаптирует данные из Room базы данных во что-то, что RecyclerView умеет отображать без изменения ViewModel
// Адаптер создает держатель представления и заполняет его данными для RecyclerView отображения.
// Адаптер должен сообщить RecyclerView, когда data изменился, потому RecyclerView что ничего не знает о данных.
// Он знает только о держателях вида, которые ему дает адаптер. binding.sleepList.adapter = adapter в фрагменте
class GardenPlantingAdapter :
    ListAdapter<PlantAndGardenPlantings, GardenPlantingAdapter.ViewHolder>(
        GardenPlantDiffCallback()   // класс в этом же модуле ниже для эффективного перерисовывания только тех что изменились
    ) {

    // вызывается, когда RecyclerView необходим держатель представления для представления элемента.
    //  07.1: основы RecyclerView Refactor> Extract> Function .
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return from(parent) // см ниже в  companion object
    }
    companion object {
        //  Android Kotlin Fundamentals 07.2.6: DiffUtil and data binding with RecyclerView
        // AS 09.06.2020 6. Task: Use DataBinding with RecyclerView
        private fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding =  ListItemGardenPlantingBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding)
            /*return ViewHolder(  // создать экземпляр LayoutInflater
                    DataBindingUtil.inflate( // Создатель макета знает, как создавать представления из XML-макетов
                            LayoutInflater.from(parent.context), // context Содержит информацию о том , как правильно надуть вид
                            R.layout.list_item_garden_planting, parent, false  // MaskedCardView
                    )
            )*/
        }
    }

    // RecyclerView повторяет представления держателей, что означает, что он использует их повторно.
// Поскольку представление прокручивается за пределы экрана,
// RecyclerView повторно используется представление для представления, которое собирается прокрутить на экран.
//  Функция onBindViewHolder() вызывается RecyclerView для отображения данных для одного элемента списка в заданном положении.
//  Таким образом, метод onBindViewHolder() принимает два аргумента: держатель представления и положение данных для привязки.
//  Для этого приложения держатель - TextItemViewHolder, а позиция - это позиция в списке.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    // bind это функция внизу файла которая ставит viewModel = PlantAndGardenPlantingsViewModel(plantings)
    // Создается AS Refactor> Extract> Function . в классе class ViewHolder
    }

    // A ViewHolder описывает представление элемента и метаданные о его месте в RecyclerView.
    // RecyclerView полагается на эту функциональность, чтобы правильно позиционировать представление при прокрутке списка
    // и делать интересные вещи, такие как анимированные представления, когда элементы добавляются или удаляются в Adapter.
    // конкретно здесь ставится перехват нажатия на карточку - проверять перехват RecyclerView
    class ViewHolder(
            // получить доступ к представлениям, хранящимся в ViewHolder, это можно сделать с помощью itemView
        private val binding: ListItemGardenPlantingBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        // TODO: convert to data binding if applicable -- преобразование в привязку данных, если это применимо
        init {
            // наверно это реагирование на нажатие на карточке -> переход к деталям растения на 2-ой экран
            // т.е. переопределяется data переменная setClickListener из layout/list_item_garden_planting.xml
            binding.setClickListener { view ->      // т.е. при нажатии на любую часть item карточки
                binding.viewModel?.plantId?.let { plantId -> //кстати берется из PlantAndGardenPlantingsViewModel
                    navigateToPlant(plantId, view)  // функция ниже см
                }
            }
        }
        // наверно это реагирование на нажатие на карточке -> переход к деталям растения на 2-ой экран
        // берется из  android:id="@+id/action_view_pager_fragment_to_plant_detail_fragment"
        // проверять по уроку навигации 3,0
        private fun navigateToPlant(plantId: String, view: View) {
            val direction = HomeViewPagerFragmentDirections
                .actionViewPagerFragmentToPlantDetailFragment(plantId)
            view.findNavController().navigate(direction)
        }
        //  Android Kotlin Fundamentals 07.2.6: DiffUtil and data binding with RecyclerView
        // 7. Task: Create binding adapters
        // TODO: convert to data binding if applicable -- преобразование в привязку данных, если это применимо
        fun bind(plantings: PlantAndGardenPlantings) {
            with(binding) {
                viewModel = PlantAndGardenPlantingsViewModel(plantings)
                executePendingBindings()
// Оценивает ожидающие привязки, обновляя все представления, к которым привязаны выражения
// измененные переменные. Это <b > должно быть запущено в потоке пользовательского интерфейса.
            }
        }
    }
}
// Android Kotlin 07.2: DiffUtil и привязка данных с помощью RecyclerView
//RecyclerView имеет класс, DiffUtil который называется для расчета различий между двумя списками.
// DiffUtil берет старый список и новый список и выясняет, что отличается.
// Он находит элементы, которые были добавлены, удалены или изменены.
// Затем он использует алгоритм, называемый алгоритмом разницы Юджина У. Майерса,
// чтобы определить минимальное количество изменений, которые нужно сделать из старого списка, чтобы создать новый список.
//
//Когда DiffUtil выясняется, что изменилось, RecyclerView можно использовать эту информацию для обновления только тех элементов,
// которые были изменены, добавлены, удалены или перемещены, что гораздо эффективнее, чем повторение всего списка.
private class GardenPlantDiffCallback : DiffUtil.ItemCallback<PlantAndGardenPlantings>() {
// Эти функции генерируются как обязательные для DiffUtil:
    override fun areItemsTheSame(
        oldItem: PlantAndGardenPlantings,
        newItem: PlantAndGardenPlantings
    ): Boolean {
        return oldItem.plant.plantId == newItem.plant.plantId
    // DiffUtil использует этот тест, чтобы определить, был ли элемент добавлен, удален или перемещен.
    }

    override fun areContentsTheSame(
        oldItem: PlantAndGardenPlantings,
        newItem: PlantAndGardenPlantings
    ): Boolean {
        return oldItem.plant == newItem.plant
    //  Эта проверка на равенство проверит все поля, потому что plant это класс данных.
    //  Data классы автоматически определяют equals и несколько других методов для вас.
        //  Если между oldItem и есть различия newItem, этот код сообщает, DiffUtil что элемент обновлен
    }
}
/*
Для отображения ваших данных в a RecyclerView вам нужны следующие части:

Данные для отображения.
RecyclerViewЭкземпляр определяется в файле макета, чтобы действовать в качестве контейнера для представлений.
Макет для одного элемента данных.
    Если все элементы списка выглядят одинаково, вы можете использовать один и тот же макет для всех них, но это не обязательно.
     Макет элемента должен быть создан отдельно от макета фрагмента, чтобы можно было создать один элемент за раз и заполнить данными.
Менеджер по верстке. app:layoutManager="androidx.recyclerview.widget.StaggeredGridLayoutManager
    Менеджер по расположению управляет организацией (расположением) компонентов пользовательского интерфейса в представлении.
Держатель вида.
    Держатель вида расширяет ViewHolder класс. Он содержит информацию о представлении для отображения одного элемента из макета элемента.
    Держатели представлений также добавляют информацию, которая RecyclerView используется для эффективного перемещения представлений по экрану.
Адаптер
    Адаптер соединяет ваши данные с RecyclerView. Он адаптирует данные так, чтобы их можно было отображать в ViewHolder.
    А RecyclerView использует адаптер, чтобы выяснить, как отображать данные на экране.
 */
/*
app:layoutManager="androidx.recyclerview.widget.StaggeredGridLayoutManager
LayoutManager, который раскладывает дочерние элементы в шахматном порядке формирования сетки.
Он поддерживает горизонтальную и вертикальную компоновку, а также возможность компоновки детей в обратном направлении.

Расположенные в шахматном порядке сетки, вероятно, имеют пробелы по краям компоновки.
Чтобы избежать этих пробелов, StaggeredGridLayoutManager может смещать пяди независимо или перемещать элементы между пядями.
Ты можешь контролируйте это поведение через setGapStrategy(int).
 */

/*
Поскольку RecyclerView данные ничего не знают, Adapter необходимо сообщить, RecyclerView когда эти данные изменятся.
Используйте notifyDataSetChanged() для уведомления о Adapter том, что данные изменились. - НЕ НАШЕЛ ГДЕ
 */