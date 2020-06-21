package com.example.android.navigation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.FragmentTitleBinding

// Ctrl/O - Медоды для переопределения
class TitleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        // Раздуть макет для этого фрагмента - простейший вариант Kitty
       // return inflater.inflate(R.layout.fragment_title, container, false)

        //val binding = DataBindingUtil.inflate<FragmentTitleBinding>(inflater,
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_title,container,false)
       

        //The complete onClickListener with Navigation using createNavigateOnClickListener 1-ый вариант
        // binding.playButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_titleFragment_to_gameFragment))

        //The complete onClickListener with Navigation - 2-ый вариант
        binding.playButton.setOnClickListener { view: View ->
            view.findNavController()
                  .navigate(R.id.action_titleFragment_to_gameFragment) }
        // теперь используя идентификатор действия эквивалентным методом из соответствующего NavDirection класса 3-ый вариант
             //       .navigate(TitleFragmentDirections.actionTitleFragmentToGameFragment())}

        setHasOptionsMenu(true)     // Установка меню три точки для этого фрагмент
        Log.i("TitleFragment", "onCreateView called")
        return binding.root
    }
    // overflow_menu - Создание меню три точки
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        //inflater?.inflate(R.menu.options_menu, menu)  // так в занятии но там MenuInflater?
        inflater.inflate(R.menu.options_menu, menu)
    }
    // Ctrl/O - Медоды для переопределения
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
      //        NavigationUI.onNavDestinationSelected(item!!,  // так в занятии, но там MenuItem?
                requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i("TitleFragment", "onAttach called")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("TitleFragment", "onCreate called")
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i("TitleFragment", "onActivityCreated called")
    }
    override fun onStart() {
        super.onStart()
        Log.i("TitleFragment", "onStart called")
    }
    override fun onResume() {
        super.onResume()
        Log.i("TitleFragment", "onResume called")
    }
    override fun onPause() {
        super.onPause()
        Log.i("TitleFragment", "onPause called")
    }
    override fun onStop() {
        super.onStop()
        Log.i("TitleFragment", "onStop called")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("TitleFragment", "onDestroyView called")
    }
    override fun onDetach() {
        super.onDetach()
        Log.i("TitleFragment", "onDetach called")
    }
}