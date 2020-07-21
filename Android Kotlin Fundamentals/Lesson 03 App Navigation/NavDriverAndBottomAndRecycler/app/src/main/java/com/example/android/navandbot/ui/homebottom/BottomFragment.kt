package com.example.android.navandbot.ui.homebottom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.navandbot.R



class BottomFragment : Fragment() {

    private lateinit var bottomViewModel: BottomViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        bottomViewModel =
                ViewModelProvider(this).get(BottomViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_bottom, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        bottomViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}