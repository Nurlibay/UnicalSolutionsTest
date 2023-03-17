package uz.nurlibaydev.unicalsolutionstest.presentation.main.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.nurlibaydev.unicalsolutionstest.R
import uz.nurlibaydev.unicalsolutionstest.databinding.ScreenProfileBinding

/**
 *  Created by Nurlibay Koshkinbaev on 17/03/2023 18:06
 */

@AndroidEntryPoint
class ProfileScreen : Fragment(R.layout.screen_profile) {

    private val binding by viewBinding<ScreenProfileBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
        }
    }
}
