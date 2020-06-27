package com.example.android.codelabs.navigation

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

// Создал AS по заданию 12. [Необязательно] Попробуйте самостоятельно
class ShoppingFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Раздуть макет для этого фрагмента
        return inflater.inflate(R.layout.shopping_fragment, container, false)
    }
}