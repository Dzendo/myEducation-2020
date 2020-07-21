package com.example.android.aboutme

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.example.android.aboutme.databinding.ActivityMainBinding


//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val myName: MyName = MyName("My Dzen DO Binding")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView (this, R.layout.activity_main)
        binding.myName = myName
        binding.doneButton.setOnClickListener { addNickname() }
        binding.nicknameText.setOnClickListener { updateNickname() }
    }
    private fun addNickname() {
        binding.apply {
            //nicknameText.text = nicknameEdit.text.toString()
            myName?.nickname = nicknameEdit.text.toString()
            invalidateAll()   // обновить экран
            nicknameEdit.visibility = View.GONE
            doneButton.visibility = View.GONE
            nicknameText.visibility = View.VISIBLE
            // скрыть клавиатуру после завершения ввода
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(nicknameEdit.windowToken, 0)
        }
    }
    private fun updateNickname () {
        binding.apply {
        nicknameEdit.visibility = View.VISIBLE
        doneButton.visibility = View.VISIBLE
        // Set the focus to the edit text.
        nicknameEdit.requestFocus()
        // Show the keyboard.
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(nicknameEdit, 0)
        }
    }
}

/*
Это устраняет необходимость вызова любого из кода Java,
<TextView
    android:text="@{viewmodel.userName}" />

<TextView android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="@{user.firstName, default=my_default}"/>

Работа с наблюдаемыми объектами данных
Библиотека привязки данных предоставляет классы и методы для простого наблюдения за изменениями.
Вам не нужно беспокоиться об обновлении пользовательского интерфейса при изменении базового источника данных.
Вы можете сделать свои переменные или их свойства наблюдаемыми.
Библиотека позволяет сделать объекты, поля или коллекции видимыми.
 */