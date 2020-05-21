/*
 * Copyright 2018, The Android Open Source Project
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

package com.example.android.navigation

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        // Раздуть макет для этого фрагмента
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_won, container, false)
        // Add OnClick Handler for Next Match button
        // Добавить обработчик OnClick для кнопки Next Match
        binding.nextMatchButton.setOnClickListener{view: View->
            view.findNavController()
        //          .navigate(R.id.action_gameWonFragment_to_gameFragment)}
        // передайте GameWonFragmentDirections.actionGameWonFragmentToGameFragment()в качестве navigate()аргумента метода:
                    .navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())}

        // Добавляем для поимки переданных параметров из Game
        val args = GameWonFragmentArgs.fromBundle(requireArguments()) // arguments!!
        Toast.makeText(context, "NumCorrect: ${args.numCorrect}, NumQuestions: ${args.numQuestions}", Toast.LENGTH_LONG).show()

        setHasOptionsMenu(true) // для менюшки передачи в дпугое приложение результатов
        return binding.root

    }
    // Creating our Share Intent
    private fun getShareIntent() : Intent {
        val args = GameWonFragmentArgs.fromBundle(requireArguments()) // arguments!!
        // более простой способ через API используя shrecompact:
        return ShareCompat.IntentBuilder.from(requireActivity())
                .setText(getString(R.string.share_success_text,args.numCorrect,args.numQuestions))
                .setType("text/plain")
                .intent
        /*  Более старый и затратный способ - работает
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")   // тип аргументов приложения для вызова
                .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_success_text, args.numCorrect, args.numQuestions))
        return shareIntent
         */
    }
    // Starting an Activity with our new Intent вызывается ниже из onOptionsItemSelected
    private fun shareSuccess() {
        startActivity(getShareIntent())
    }

    // Showing the Share Menu Item Dynamically
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.winner_menu, menu)
        // check if the activity resolves - Проверяем есть ли такая активити для обработки
        if (null == getShareIntent().resolveActivity(requireActivity().packageManager)) {  // activity!!
            // если нечем обрабатывать то скрываем значок
            // hide the menu item if it doesn't resolve
            menu.findItem(R.id.share)?.isVisible = false
        }
    }
    // Sharing from the Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }
}
