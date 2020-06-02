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

package com.google.samples.apps.sunfloweras

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ShareCompat
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.samples.apps.sunfloweras.data.Plant
import com.google.samples.apps.sunfloweras.databinding.FragmentPlantDetailBinding
import com.google.samples.apps.sunfloweras.utilities.InjectorUtils
import com.google.samples.apps.sunfloweras.viewmodels.PlantDetailViewModel

/**
 * A fragment representing a single Plant detail screen.
 * Фрагмент, представляющий собой один экран детализации растений.
 */
class PlantDetailFragment : Fragment() {

    // Это видимо объявление для получения переданного навигатором от предылущего фрагмента аргумента
    // Запись через by мне непонятна но здорово и коротко - разобрать
    private val args: PlantDetailFragmentArgs by navArgs()

    // Это создание/восстановлениен ссылки на viewModel фрагмента через его Factory
    // Запись через by мне непонятна но здорово и коротко - разобрать
    private val plantDetailViewModel: PlantDetailViewModel by viewModels {
        InjectorUtils.providePlantDetailViewModelFactory(requireActivity(), args.plantId)
    }
        // обычное штатное создание фрагмента
    override fun onCreateView(
        inflater: LayoutInflater,   // Ссылка на создаватель ??
        container: ViewGroup?,      // Ссылка на контейнер ??
        savedInstanceState: Bundle? // Переданные аргументы Bundle (Kitty)
    ): View? {  // возврат должен быть View? который андроид засветит на экран (неважно откуда я его возьму)

            // Здесь работаем с XML через технологию  binding ==> грузим через нее Указанный XML + .apply{лямбда}
            // Функция apply работает почти так же, как with, но возвращает объект, переданный в аргументе.
            // .apply Полезна в тех случаях, когда требуется создание экземпляра, у которого следует инициализировать некоторые свойства.
            // Часто в этих случаях мы просто повторяем имя экземпляра. Обратить внимание что Эта APPLY идет почти до низа - все в ней
        val binding = FragmentPlantDetailBinding.inflate(inflater, container, false    // взяз образец из Home AS OK
                //val binding = DataBindingUtil.inflate<FragmentPlantDetailBinding>(        // более длинная запись исходная
                //  inflater, R.layout.fragment_plant_detail, container, false
          // с тем же успехом можно var binding=.... ; binding = binding.apply  или binding.viewModel = plantDetailViewModel итд
        ).apply {
            viewModel = plantDetailViewModel         // в простраиваемый фрагмент впихиваем viewModel точнее в ее binding XML
            lifecycleOwner = viewLifecycleOwner      // ставится  lifecycle но как и где используется - разбирать
           /* callback = object : Callback {           // Это вызов от кнопки(+) в data binding XML Почему она callback interface- неясно - add
                override fun add(plant: Plant?) {    // Если нажали плюсик на FAB, то ...   (plant - ссылка на растение)
                    plant?.let {
                        hideAppBarFab(fab)             // Это местная функция внизу - скрыть FAB кнопку + после нажатия
                        plantDetailViewModel.addPlantToGarden() // функция добавить растение в садик
                        Snackbar.make(root, R.string.added_plant_to_garden, Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
            }*/


            // ЭТО ВСЕ ВНУТРИ .apply{}

            var isToolbarShown = false

            // scroll change listener begins at Y = 0 when image is fully collapsed
            // Scroll change listener начинается с Y = 0, когда изображение полностью свернуто
            plantDetailScrollview.setOnScrollChangeListener(
                NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->

                    // User scrolled past image to height of toolbar and the title text is
                    // underneath the toolbar, so the toolbar should be shown.
                    // Пользователь прокрутил мимо изображения на высоту панели инструментов, и текст заголовка
                    // под панелью инструментов, поэтому панель инструментов должна быть показана.
                    val shouldShowToolbar = scrollY > toolbar.height    // видимо BOOLEAN

                    // The new state of the toolbar differs from the previous state; update
                    // appbar and toolbar attributes.
                    // Новое состояние панели инструментов отличается от предыдущего состояния ==>
                    //  обновить атрибуты панели приложений и панели инструментов.
                    if (isToolbarShown != shouldShowToolbar) {
                        isToolbarShown = shouldShowToolbar

                        // Use shadow animator to add elevation if toolbar is shown
                        // Используйте shadow animator для добавления высоты, если отображается панель инструментов
                        appbar.isActivated = shouldShowToolbar

                        // Show the plant name if toolbar is shown
                        // Показать название растения, если отображается панель инструментов
                        toolbarLayout.isTitleEnabled = shouldShowToolbar
                    }
                }
            )       // конец хитрой обработки прокрутки вниз-вверх plantDetailScrollview.setOnScrollChangeListener(

            // toolbar - это вся горизонтальная полоса вверху и со <-- слева и с action_share справа
            toolbar.setNavigationOnClickListener { view ->      // Это правда возврат назад на экран откуда пришли
                view.findNavController().navigateUp()           // но не понимаю как ловит только стрелочку - типа поле toolbar
            }
// ЭТО НЕ ЕСТЬ ХОРОШО - НЕСТАНДАРТНО - НАДО БЫ СТРЕЛОЧКУ ВОЗВРАТ НАЗАТ ИЗ ЛЕКЦИЙ но это полное перестроение фрагмента
// т.к. и все остальное запутано дальше некуда - скролим - убирать итд
            toolbar.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_share -> {
                        createShareIntent()         // функция см. внизу файла позвать SHARE
                        true
                    }
                    else -> false
                }
            }
        }       // это конец .apply к binding
        setHasOptionsMenu(true)     // Установить меню - реально только для action_share справа

        return binding.root     // и вот этот binding наконец-то передаем (возращаем) на высветку
    }

    // Helper function for calling a share functionality.
    // Should be used when user presses a share button/menu item.
    // Вспомогательная функция для вызова функции общего доступа.
    // Должен использоваться, когда пользователь нажимает кнопку share/пункт меню.
    //@Suppress("DEPRECATION")
    private fun createShareIntent() {
        val shareText = getString(R.string.share_text_plant,plantDetailViewModel.plant.value)
       // val shareText = plantDetailViewModel.plant.value?.let { plant ->
       //     getString(R.string.share_text_plant, plant.name) }?:""
/*  Это Я переписал в Элвис - проверил - работает - ниже исходный текст можно еще короче??
            if (plant == null) {
                ""
            } else {
                getString(R.string.share_text_plant, plant.name)
            }
        }*/
        // Создаем гандбольный мячик и напихиваем в него Intent
        val shareIntent = ShareCompat.IntentBuilder.from(requireActivity())
            .setText(shareText)
            .setType("text/plain")
            .createChooserIntent()
            .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        // бросаем мячик системе с запросом "кто обработает новый документ"
        startActivity(shareIntent)
    }

    // FloatingActionButtons anchored to AppBarLayouts have their visibility controlled by the scroll position.
    // FloatingActionButtons привязанный к AppBarLayouts имеет свою видимость, контролируемую положением прокрутки.
    // We want to turn this behavior off to hide the FAB when it is clicked.
    // Мы хотим отключить это поведение, чтобы скрыть FAB при нажатии на него.
    //
    // This is adapted from Chris Banes' Stack Overflow answer: https://stackoverflow.com/a/41442923
    // Это адаптировано из ответа Stackoverflow Криса Бэйнса:  https://stackoverflow.com/a/41442923
   /* private fun hideAppBarFab(fab: FloatingActionButton) {    // в принципе не требуется т.к. стоит GONE в XML
        val params = fab.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as FloatingActionButton.Behavior
        behavior.isAutoHideEnabled = false
        fab.hide()
    }*/

    // Это должно быть возврат назад судя по названию  а это по-моему + т.е. добавление растения
    /*interface Callback {
        fun add(plant: Plant?)
    }*/
    // Вместо этого надо бы ставить LiveData и вообще возвращаться в мой садик - здесь больше делать нечего
}
