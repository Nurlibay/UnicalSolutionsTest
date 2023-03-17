package uz.nurlibaydev.unicalsolutionstest.presentation.auth.signin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.nurlibaydev.unicalsolutionstest.R
import uz.nurlibaydev.unicalsolutionstest.databinding.ScreenSigninBinding

/**
 *  Created by Nurlibay Koshkinbaev on 17/03/2023 17:29
 */

@AndroidEntryPoint
class SignInScreen : Fragment(R.layout.screen_signin) {

    private val binding by viewBinding<ScreenSigninBinding>()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvSignUp.setOnClickListener {
                navController.navigate(SignInScreenDirections.actionSignInScreenToSignUpScreen())
            }
            btnSignIn.setOnClickListener {
                navController.navigate(SignInScreenDirections.actionSignInScreenToMainContainer())
            }
        }
    }
}
