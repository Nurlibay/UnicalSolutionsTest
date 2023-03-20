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

    private val viewModel: SignInViewModel by viewModels()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }

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
                        val users = it.result
                        if (users.isNotEmpty()) {
                            users.forEach { user ->
                                if (user.id == provideDeviceId()) {
                                    navController.navigate(R.id.action_splashScreen_to_mainContainer)
                                    return@collectLatest
                                } else if (user.id != provideDeviceId() && users.last() == user) {
                                    navController.navigate(R.id.action_splashScreen_to_signInScreen)
                                }
                            }
                        } else {
                            navController.navigate(R.id.action_splashScreen_to_signInScreen)
                        }
                    }
                    else -> {
                    }
                }
            }
        }
    }
}
