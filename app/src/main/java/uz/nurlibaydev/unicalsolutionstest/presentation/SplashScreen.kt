package uz.nurlibaydev.unicalsolutionstest.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.nurlibaydev.unicalsolutionstest.R
import uz.nurlibaydev.unicalsolutionstest.presentation.auth.signin.SignInViewModel
import uz.nurlibaydev.unicalsolutionstest.utils.Resource
import uz.nurlibaydev.unicalsolutionstest.utils.provideDeviceId
import uz.nurlibaydev.unicalsolutionstest.utils.showMessage

/**
 *  Created by Nurlibay Koshkinbaev on 20/03/2023 03:51
 */

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreen : Fragment(R.layout.screen_splash) {

    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private val viewModel: SignInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerUsers()
        viewModel.getAllUsers()
    }

    private fun observerUsers() {
        lifecycleScope.launch {
            viewModel.users.collectLatest {
                when (it) {
                    is Resource.Failure -> {
                        showMessage(it.exception.toString())
                    }
                    Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        it.result.forEachIndexed { index, user ->
                            if (user.id == provideDeviceId()) {
                                navController.navigate(SplashScreenDirections.actionSplashScreenToMainContainer())
                            }
                            if (it.result.size - 1 == index && user.id != provideDeviceId()) {
                                navController.navigate(SplashScreenDirections.actionSplashScreenToSignInScreen())
                            }
                        }
                        if (it.result.isEmpty()) {
                            navController.navigate(SplashScreenDirections.actionSplashScreenToSignInScreen())
                        }
                    }
                    else -> {
                    }
                }
            }
        }
    }
}
