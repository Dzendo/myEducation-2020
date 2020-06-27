package com.example.colormyviews_view_binding

import android.R.color.*
import android.graphics.Color.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.colormyviews_view_binding.databinding.ActivityMainBinding
//import kotlinx.android.synthetic.main.activity_main.*

//  viewBinding  true - Можно включать вместе с dataBinding  true
// При этом обернутые в layout - будут dataBinding, не обернутые будут viewBinding
// не обернутые с tools:viewBindingIgnore="true" будут без Binding
// если применять этот стандарт то один и тот же текст годится и для viewBinding и dataBindin
// .idea\modules\ColorMyViews_view_binding.iml
// build\generated\data_binding_base_class_source_out\debug\out\com\example\colormyviews_view_binding\databinding\ActivityMainBinding.java

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // setContentView(R.layout.activity_main)
        // setListeners() // Отключил и поставил в XML android:onClick="makeColored"
    }

    fun makeColored(view: View)  = binding.apply {
        when (view) {
            // Boxes using Color class colors for background
            boxOneText -> view.setBackgroundColor(BLUE)
            boxTwoText -> view.setBackgroundColor(GRAY)

            // Boxes using Android color resources for background
            boxThreeText -> view.setBackgroundResource(holo_green_light)
            boxFourText -> view.setBackgroundResource(holo_green_dark)
            boxFiveText -> view.setBackgroundResource(holo_green_light)

            // Boxes using custom colors for background
            redButton -> boxThreeText.setBackgroundResource(R.color.my_red)
            yellowButton -> boxFourText.setBackgroundResource(R.color.my_yellow)
            greenButton -> boxFiveText.setBackgroundResource(R.color.my_green)

            labelText -> labelText.setBackgroundResource(R.color.white)
            infoText -> infoText.setBackgroundResource(R.color.white)

            else -> view.setBackgroundColor(DKGRAY)
        }
    }

    fun makeColored_findById(view: View) = when (view.id) {
        // Boxes using Color class colors for background
        R.id.box_one_text -> view.setBackgroundColor(BLUE)
        R.id.box_two_text -> view.setBackgroundColor(GRAY)

        // Boxes using Android color resources for background
        R.id.box_three_text -> view.setBackgroundResource(holo_green_light)
        R.id.box_four_text -> view.setBackgroundResource(holo_green_dark)
        R.id.box_five_text -> view.setBackgroundResource(holo_green_light)

        // Boxes using custom colors for background
        R.id.red_button -> binding.boxThreeText.setBackgroundResource(R.color.my_red)
        R.id.yellow_button -> binding.boxFourText.setBackgroundResource(R.color.my_yellow)
        R.id.green_button -> binding.boxFiveText.setBackgroundResource(R.color.my_green)

        R.id.label_text -> binding.labelText.setBackgroundResource(R.color.white)
        R.id.info_text -> binding.infoText.setBackgroundResource(R.color.white)

        else -> view.setBackgroundColor(DKGRAY)
    }

    private fun setListeners() = binding.apply {
        val clickableViews: List<View> =
            listOf(
                boxOneText,
                boxTwoText,
                boxThreeText,
                boxFourText,
                boxFiveText,
                root,
                redButton,
                yellowButton,
                greenButton,
                labelText,
                infoText,
            )
        // synthetic
        //listOf(box_one_text, box_two_text, box_three_text, box_four_text, box_five_text,
        //       constraint_layout,red_button, yellow_button, green_button)

        for (item in clickableViews) {
            item.setOnClickListener { makeColored(it) }
        }
    }

  
}
/*
Привязка к представлению - это функция, которая позволяет легче писать код, взаимодействующий с представлениями.
Как только привязка вида включена в модуле, он генерирует класс привязки для каждого файла макета XML, присутствующего в этом модуле.
Экземпляр класса привязки содержит прямые ссылки на все представления, имеющие идентификатор в соответствующем макете.
В большинстве случаев вид привязки заменяет findViewById.

Более быстрая компиляция: привязка представления не требует обработки аннотаций, поэтому время компиляции сокращается.
Простота использования:
для привязки просмотра не требуются файлы тегов XML со специальными тегами, поэтому они быстрее применяются в ваших приложениях.
После включения привязки вида в модуле она автоматически применяется ко всем макетам этого модуля.

ограничения по сравнению с привязкой данных:
Привязка вида не поддерживает переменные макета или выражения макета,
   поэтому ее нельзя использовать для объявления содержимого динамического интерфейса непосредственно из файлов макета XML.
Привязка вида не поддерживает двустороннюю привязку данных .

buildFeatures {
        viewBinding true
    }
<LinearLayout
        ...
        tools:viewBindingIgnore="true" >

    Использовать привязку вида во фрагментах:

private var _binding: ResultProfileBinding? = null
// This property is only valid between onCreateView and
// onDestroyView.
private val binding get() = _binding!!

override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {
    _binding = ResultProfileBinding.inflate(inflater, container, false)
    val view = binding.root
    return view
}

override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
}

binding.name.text = viewModel.name
binding.button.setOnClickListener {viewModel.userClicked ()}
 */