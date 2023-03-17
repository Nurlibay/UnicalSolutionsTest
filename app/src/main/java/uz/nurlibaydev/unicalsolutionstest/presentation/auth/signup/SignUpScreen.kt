package uz.nurlibaydev.unicalsolutionstest.presentation.auth.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.nurlibaydev.unicalsolutionstest.R
import uz.nurlibaydev.unicalsolutionstest.databinding.ScreenSignupBinding

/**
 *  Created by Nurlibay Koshkinbaev on 17/03/2023 17:30
 */

@AndroidEntryPoint
class SignUpScreen : Fragment(R.layout.screen_signup) {

    private val binding by viewBinding<ScreenSignupBinding>()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvSignIn.setOnClickListener {
                navController.navigate(SignUpScreenDirections.actionSignUpScreenToSignInScreen())
            }
        }
    }
}
