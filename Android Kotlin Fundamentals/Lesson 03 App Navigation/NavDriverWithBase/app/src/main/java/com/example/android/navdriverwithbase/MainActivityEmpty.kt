package com.example.android.navdriverwithbase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import com.example.android.navdriverwithbase.databinding.ActivityMainEmptyBinding

class MainActivityEmpty : AppCompatActivity() {
    private lateinit var binding : ActivityMainEmptyBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainEmptyBinding.inflate(layoutInflater)
        setContentView(binding.root)    //  viewBinding = true без layout
        //navController = this.findNavController(R.id.mainActivityEmpty)// Переделать в Binding если возможно
        //NavigationUI.setupWithNavController(binding.navdrawerNavView, navController) // Запускает в работу переход по пунктам шторки по именам
        //NavigationUI.setupActionBarWithNavController(this,navController, binding.navdrawerLayout)
        //override fun onSupportNavigateUp(): Boolean = NavigationUI.navigateUp(navController, binding.navdrawerLayout)
    }
    private inline fun <reified T: AppCompatActivity> AppCompatActivity.startActiv() =
                        startActivity(Intent(this,T::class.java))

    fun onClickButton(view: View) = when (view) {
        binding.buttonBottom -> startActiv<MainActivityBottom>()
        binding.buttonBasic ->  startActiv<MainActivityBasic>()
        binding.buttonNda -> startActiv<MainActivityNDA>()
        else -> startActiv<MainActivityEmpty>()
    }


}