package com.example.android.navdriverwithbase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivityEmpty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_empty)
    }
    private inline fun <reified T: AppCompatActivity> AppCompatActivity.startActiv() =
                        startActivity(Intent(this,T::class.java))
    fun buttonBasic(view: View) =  startActiv<MainActivityBasic>()
    fun buttonNDA(view: View)   =  startActiv<MainActivityNDA>()
    //fun buttonBasic(view: View) =  startActivity(Intent(this, MainActivityBasic::class.java))
    //fun buttonNDA(view: View)   =  startActivity(Intent(this, MainActivityNDA::class.java))

}