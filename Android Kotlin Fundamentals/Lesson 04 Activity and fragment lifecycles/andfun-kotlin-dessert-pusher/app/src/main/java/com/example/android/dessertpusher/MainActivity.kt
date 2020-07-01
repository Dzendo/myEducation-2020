/*
 * Copyright 2018, The Android Open Source Project
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

package com.example.android.dessertpusher

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleObserver
import com.example.android.dessertpusher.databinding.ActivityMainBinding
import timber.log.Timber

// adb shell am kill com.example.android.dessertpusher

// для onSaveInstanceState Bundle
const val KEY_REVENUE = "revenue_key"
const val KEY_DESSERT_SOLD = "dessert_sold_key"
const val KEY_TIMER_SECONDS = "timer_seconds_key"

class MainActivity : AppCompatActivity(), LifecycleObserver {

    private var revenue = 0
    private var dessertsSold = 0
    private lateinit var dessertTimer : DessertTimer

    // Contains all the views
    private lateinit var binding: ActivityMainBinding

    /** Dessert Data **/

    /**
     * Simple data class that represents a dessert. Includes the resource id integer associated with
     * the image, the price it's sold for, and the startProductionAmount, which determines when
     * the dessert starts to be produced.
     */
    data class Dessert(val imageId: Int, val price: Int, val startProductionAmount: Int)

    // Create a list of all desserts, in order of when they start being produced
    private val allDesserts = listOf(
            Dessert(R.drawable.cupcake, 5, 0),
            Dessert(R.drawable.donut, 10, 5),
            Dessert(R.drawable.eclair, 15, 20),
            Dessert(R.drawable.froyo, 30, 50),
            Dessert(R.drawable.gingerbread, 50, 100),
            Dessert(R.drawable.honeycomb, 100, 200),
            Dessert(R.drawable.icecreamsandwich, 500, 500),
            Dessert(R.drawable.jellybean, 1000, 1000),
            Dessert(R.drawable.kitkat, 2000, 2000),
            Dessert(R.drawable.lollipop, 3000, 4000),
            Dessert(R.drawable.marshmallow, 4000, 8000),
            Dessert(R.drawable.nougat, 5000, 16000),
            Dessert(R.drawable.oreo, 6000, 20000)
    )
    private var currentDessert = allDesserts[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("MainActivity","onCreate Called")
        // Use Data Binding to get reference to the views
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.dessertButton.setOnClickListener {
            onDessertClicked()
        }
        // Setup dessertTimer, passing in the lifecycle
        // Настройка времени десерта, проходящего в жизненном цикле
        dessertTimer = DessertTimer(this.lifecycle)
        //dessertTimer = DessertTimer()
        //dessertTimer.startTimer() // стартует один раз и НЕ восстанавливается

        // Восстановление после перезапуска:
        // If there is a savedInstanceState bundle, then you're "restarting" the activity
        // If there isn't a bundle, then it's a "fresh" start
       /* if (savedInstanceState != null) {
            revenue = savedInstanceState.getInt(KEY_REVENUE, 0)
            dessertsSold = savedInstanceState.getInt(KEY_DESSERT_SOLD, 0)
            dessertTimer.secondsCount = savedInstanceState.getInt(KEY_TIMER_SECONDS, 0)
            // Show the next dessert
            showCurrentDessert()
            Timber.i( "onCreate savedInstanceState Timber $revenue $dessertsSold ${dessertTimer.secondsCount}")
            // Как-то неустойчиво - иногда восстанавливавется с нуля
            //>adb shell am kill com.example.android.dessertclicker - прибить приложение в фоне
        }*/

        // Set the TextViews to the right values
        // Установите текстовые представления в правильные значения

        // Set the TextViews to the right values
        binding.revenue = revenue
        binding.amountSold = dessertsSold

        // Make sure the correct dessert is showing
        binding.dessertButton.setImageResource(currentDessert.imageId)
    }

    /**
     * Updates the score when the dessert is clicked. Possibly shows a new dessert.
     */
    private fun onDessertClicked() {

        // Update the score
        revenue += currentDessert.price
        dessertsSold++

        binding.revenue = revenue
        binding.amountSold = dessertsSold

        // Show the next dessert
        showCurrentDessert()
    }

    /**
     * Determine which dessert to show.
     */
    private fun showCurrentDessert() {
        var newDessert = allDesserts[0]
        for (dessert in allDesserts) {
            if (dessertsSold >= dessert.startProductionAmount) {
                newDessert = dessert
            }
            // The list of desserts is sorted by startProductionAmount. As you sell more desserts,
            // you'll start producing more expensive desserts as determined by startProductionAmount
            // We know to break as soon as we see a dessert who's "startProductionAmount" is greater
            // than the amount sold.
            else break
        }

        // If the new dessert is actually different than the current dessert, update the image
        if (newDessert != currentDessert) {
            currentDessert = newDessert
            binding.dessertButton.setImageResource(newDessert.imageId)
        }
    }

    /**
     * Menu methods
     */
    private fun onShare() {
        val shareIntent = ShareCompat.IntentBuilder.from(this)
                .setText(getString(R.string.share_text, dessertsSold, revenue))
                .setType("text/plain")
                .intent
        try {
            startActivity(shareIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.sharing_not_available),
                    Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareMenuButton -> onShare()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Called when the user navigates away from the app but might come back
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_REVENUE, revenue)
        outState.putInt(KEY_DESSERT_SOLD, dessertsSold)
        outState.putInt(KEY_TIMER_SECONDS, dessertTimer.secondsCount)
        Timber.i("onSaveInstanceState Called $revenue $dessertsSold ${dessertTimer.secondsCount}")
        //Timber.i("onSaveInstanceState Called $revenue $dessertsSold ")
        super.onSaveInstanceState(outState)
        //  Восстанавливать будем в onCreate или
        // Если действие воссоздается, onRestoreInstanceState() обратный вызов
        // вызывается после onStart(), также с пакетом
    }
    /** Lifecycle Methods **/
    override fun onStart() {
        super.onStart()
        // стартует каждый раз и восстанавливается - надо останавливать onStop
        //dessertTimer.startTimer()
        // при использовании Lifecycle не нужно указывать - он сам знает
        Timber.i("onStart Timber")
        //Log.i("MainActivity", "onStart Log")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume Called")
    }

    override fun onPause() {
        super.onPause()
        Timber.i("onPause Called")
    }
    /** Lifecycle Methods **/
    override fun onStop() {
        super.onStop()
        //dessertTimer.stopTimer()
        // при использовании Lifecycle не нужно указывать - он сам знает
        Timber.i("onStop Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy Called")
    }
    // выполняется когда создан аовтороно
    override fun onRestart() {
        super.onRestart()
        Timber.i("onRestart Called")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        revenue = savedInstanceState.getInt(KEY_REVENUE, 0)
        dessertsSold = savedInstanceState.getInt(KEY_DESSERT_SOLD, 0)
        dessertTimer.secondsCount = savedInstanceState.getInt(KEY_TIMER_SECONDS, 0)
        // Show the next dessert
        showCurrentDessert()
        Timber.i( "onRestoreInstanceState Timber $revenue $dessertsSold ${dessertTimer.secondsCount}")
        // Как-то неустойчиво - иногда восстанавливавется с нуля
        //>adb shell am kill com.example.android.dessertclicker - прибить приложение в фоне
        // Восстанавлиает после поворота но не засвечивает
        // переделать в LiveData
    }
}
