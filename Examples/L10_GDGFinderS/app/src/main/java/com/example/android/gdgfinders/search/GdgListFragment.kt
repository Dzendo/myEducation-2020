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


package com.example.android.gdgfinders.search

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.android.gdgfinders.R
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.gdgfinders.databinding.FragmentGdgListBinding
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.chip.Chip

private const val LOCATION_PERMISSION_REQUEST = 1

private const val LOCATION_PERMISSION = "android.permission.ACCESS_FINE_LOCATION"

class GdgListFragment : Fragment() {


    private val viewModel: GdgListViewModel by lazy {
        ViewModelProvider(this).get(GdgListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentGdgListBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        // Позволяет привязке данных наблюдать живые данные с жизненным циклом этого фрагмента
        binding.setLifecycleOwner(this)

        // Giving the binding access to the OverviewViewModel
        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        val adapter = GdgListAdapter(GdgClickListener { chapter ->
            val destination = Uri.parse(chapter.website)
            startActivity(Intent(Intent.ACTION_VIEW, destination))
        })

        // Sets the adapter of the RecyclerView
        // Устанавливает адаптер RecyclerView
        binding.gdgChapterList.adapter = adapter

        viewModel.showNeedLocation.observe(viewLifecycleOwner, object: Observer<Boolean> {
            override fun onChanged(show: Boolean?) {
                // Snackbar is like Toast but it lets us show forever
                // Закусочная похожа на тост, но она позволяет нам показать себя навсегда
                if (show == true) {
                    Snackbar.make(
                        binding.root,
                        "No location. Enable location in settings (hint: test with Maps) then check app permissions!",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        })

        // ЧИПСЫ:
        // Создайте наблюдателя для определения ChipGroup и добавления Chips:

        // 1. Добавить наблюдателя на viewModel.regionList.
        // наблюдателя viewModel.regionList и переопределите onChanged().
        // Когда список регионов, предоставленных моделью представления, изменяется, чипы должны быть воссозданы
        viewModel.regionList.observe(viewLifecycleOwner, object: Observer<List<String>> {
            override fun onChanged(data: List<String>?) {
                data ?: return  //  немедленно вернуться , если поставляемое в комплекте dataесть null

                // 2. Создать chipGroupи inflatorпеременные.
                // chipGroup которая вызывается для кэширования regionList.
                val chipGroup = binding.regionList
                // новый layoutInflator для надувания чипсов chipGroup.context
                val inflator = LayoutInflater.from(chipGroup.context)
                // Очистите и перестройте свой проект, чтобы избавиться от ошибки привязки данных.

                // 3. создайте переменную children для хранения всех фишек.
                // Присвойте ему функцию отображения на переданном входе, data чтобы создать и вернуть каждый чип.
                // Создайте Chip для каждого regionsListэлемента.
                //Используйте map функцию, чтобы перебрать regionsList и создать Chip для каждого элемента
                val children = data.map { regionName ->
                    // для каждого regionName создайте и надуйте фишку
                        val chip = inflator.inflate(R.layout.region, chipGroup, false) as Chip
                        chip.text = regionName
                        chip.tag = regionName
                        // TODO: Click listener goes here.
                        // добавьте прослушиватель щелчков для ЧИПСОВ.
                        chip.setOnCheckedChangeListener { button, isChecked ->
                            // Когда chipнажата кнопка, установите ее состояние на checked
                            // Вызов onFilterChanged()в viewModel, который запускает последовательность событий,
                            // которая извлекает результат для этого фильтра
                            viewModel.onFilterChanged(button.tag as String, isChecked)


                        }
                        chip
                }
                // 4. Удалите виды, которые уже есть chipGroup.
                // В конце lamba удалите все текущие представления из chipGroup, затем добавьте все фишки из children в chipGroup.
                // (Вы не можете обновить чипы, поэтому вы должны удалить и воссоздать содержимое chipGroup.
                chipGroup.removeAllViews()

                // 5. Наконец, итерируйте по списку, children чтобы добавить каждого chip к chipGroup:
                for (chip in children) {
                    chipGroup.addView(chip)
                }
            }
        })
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestLastLocationOrStartLocationUpdates()
    }

    /**
     * Show the user a dialog asking for permission to use location.
     * Покажите пользователю диалоговое окно с запросом разрешения на использование location.
     */
    private fun requestLocationPermission() {
        requestPermissions(arrayOf(LOCATION_PERMISSION), LOCATION_PERMISSION_REQUEST)
    }

    /**
     * Request the last location of this device, if known, otherwise start location updates.
     * Запросите последнее местоположение этого устройства, если оно известно, в противном случае запустите обновление местоположения.
     *
     * The last location is cached from the last application to request location.
     * Последнее местоположение кэшируется из последнего приложения для запроса местоположения.
     */
    private fun requestLastLocationOrStartLocationUpdates() {
        // if we don't have permission ask for it and wait until the user grants it
        // если у нас нет разрешения, попросите его и подождите, пока пользователь его не предоставит
        if (ContextCompat.checkSelfPermission(requireContext(), LOCATION_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission()
            return
        }

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location == null) {
                startLocationUpdates(fusedLocationClient)
            } else {
                viewModel.onLocationUpdated(location)
            }
        }
    }

    /**
     * Start location updates, this will ask the operating system to figure out the devices location.
     * Начните обновление местоположения, это попросит операционную систему определить местоположение устройств.
     */
    private fun startLocationUpdates(fusedLocationClient: FusedLocationProviderClient) {
        // if we don't have permission ask for it and wait until the user grants it
        // если у нас нет разрешения, попросите его и подождите, пока пользователь его не предоставит
        if (ContextCompat.checkSelfPermission(requireContext(), LOCATION_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission()
            return
        }


        val request = LocationRequest().setPriority(LocationRequest.PRIORITY_LOW_POWER)
        val callback = object: LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                val location = locationResult?.lastLocation ?: return
                viewModel.onLocationUpdated(location)
            }
        }
        fusedLocationClient.requestLocationUpdates(request, callback, null)
    }

    /**
     * This will be called by Android when the user responds to the permission request.
     * Это будет вызвано Android, когда пользователь ответит на запрос разрешения
     *
     * If granted, continue with the operation that the user gave us permission to do.
     * Если это разрешено, продолжайте выполнять операцию, на которую пользователь дал нам разрешение.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            LOCATION_PERMISSION_REQUEST -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestLastLocationOrStartLocationUpdates()
                }
            }
        }
    }
}


