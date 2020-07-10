package com.example.android.trackrecycledview.sleepdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
//import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.trackrecycledview.R
import com.example.android.trackrecycledview.database.SleepDatabase
import com.example.android.trackrecycledview.databinding.FragmentSleepDetailBinding
//import com.example.android.trackrecycledview.sleepdetail.SleepDetailFragmentArgs
//import com.example.android.trackrecycledview.sleepdetail.SleepDetailFragmentDirections


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SleepDetailFragment.*OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SleepDetailFragment.*newInstance] factory method to
 * create an instance of this fragment.
 * Простой подкласс [*фрагмент].
 * Действия, содержащие этот фрагмент, должны реализовывать
 * [Детализация Сна.Интерфейс OnFragmentInteractionListener]
 * для обработки событий взаимодействия.
 * Используйте детализацию сна [Sleep DetailFragment.newinstance метод] метод фабрики, чтобы
 * создайте экземпляр этого фрагмента.
 *
 */
// Обратите внимание на настройку привязки данных, модель представления и наблюдателя для навигации.
class SleepDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        // Получить ссылку на объект привязки и раздуть представления фрагментов.
        val binding: FragmentSleepDetailBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sleep_detail, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = SleepDetailFragmentArgs.fromBundle(requireArguments())  // AS (arguments!!)

        // Create an instance of the ViewModel Factory.
        // Создайте экземпляр фабрики ViewModel.
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = SleepDetailViewModelFactory(arguments.sleepNightKey, dataSource)

        // Get a reference to the ViewModel associated with this fragment.
        // Получить ссылку на ViewModel, связанную с этим фрагментом.
        val sleepDetailViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(SleepDetailViewModel::class.java)

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        // Чтобы использовать модель представления с привязкой данных, необходимо явным образом
        // дайте объекту привязки ссылку на него.
        binding.sleepDetailViewModel = sleepDetailViewModel

        binding.lifecycleOwner = viewLifecycleOwner // AS this

        // Add an Observer to the state variable for Navigating when a Quality icon is tapped.
        // Добавьте наблюдателя в переменную состояния для навигации при нажатии значка качества.
        // AS this
        sleepDetailViewModel.navigateToSleepTracker.observe(viewLifecycleOwner) {
            if (it == true) { // Observed state is true.
                this.findNavController().navigate(
                        SleepDetailFragmentDirections.actionSleepDetailFragmentToSleepTrackerFragment())
                // Reset state to make sure we only navigate once, even if the device
                // has a configuration change.
                // Сбросить состояние, чтобы убедиться, что мы перемещаемся только один раз, даже если устройство
                // имеет изменение конфигурации.
                sleepDetailViewModel.doneNavigating()
            }
        }

        return binding.root
    }


}