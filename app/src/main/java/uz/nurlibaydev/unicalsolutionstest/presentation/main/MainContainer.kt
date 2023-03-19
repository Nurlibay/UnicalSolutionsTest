package uz.nurlibaydev.unicalsolutionstest.presentation.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import uz.nurlibaydev.unicalsolutionstest.R
import uz.nurlibaydev.unicalsolutionstest.data.pref.SharedPref
import javax.inject.Inject

/**
 *  Created by Nurlibay Koshkinbaev on 17/03/2023 17:28
 */

@AndroidEntryPoint
class MainContainer : Fragment(R.layout.screen_container) {

    @Inject
    lateinit var pref: SharedPref

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref.isSigned = true
    }
}
