package uz.nurlibaydev.unicalsolutionstest.presentation.auth.signin

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.nurlibaydev.unicalsolutionstest.R
import uz.nurlibaydev.unicalsolutionstest.data.pref.SharedPref
import uz.nurlibaydev.unicalsolutionstest.databinding.ScreenSigninBinding
import uz.nurlibaydev.unicalsolutionstest.utils.Constants
import uz.nurlibaydev.unicalsolutionstest.utils.Resource
import uz.nurlibaydev.unicalsolutionstest.utils.provideDeviceId
import uz.nurlibaydev.unicalsolutionstest.utils.showMessage
import javax.inject.Inject

/**
 *  Created by Nurlibay Koshkinbaev on 17/03/2023 17:29
 */

@AndroidEntryPoint
class SignInScreen : Fragment(R.layout.screen_signin) {

    private val binding by viewBinding<ScreenSigninBinding>()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private val viewModel: SignInViewModel by viewModels()

    @Inject
    lateinit var pref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (pref.isSigned) {
            navController.navigate(SignInScreenDirections.actionSignInScreenToMainContainer())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllUsers()
        observerUsers()
        setupObserver()
        binding.apply {
            tvSignUp.setOnClickListener {
                navController.navigate(SignInScreenDirections.actionSignInScreenToSignUpScreen())
            }
            btnSignIn.setOnClickListener {
                if (validate()) {
                    viewModel.login(etEmail.text.toString(), etPassword.text.toString())
                }
            }
        }
    }

    private fun observerUsers() {
        lifecycleScope.launch {
            viewModel.users.collectLatest {
                when (it) {
                    is Resource.Failure -> {
                        showLoading(false)
                        showMessage(it.exception.toString())
                    }
                    Resource.Loading -> {
                        showLoading(true)
                    }
                    is Resource.Success -> {
                        it.result.forEach { user ->
                            if (user.id == provideDeviceId()) {
                                navController.navigate(SignInScreenDirections.actionSignInScreenToMainContainer())
                            }
                        }
                        showLoading(false)
                    }
                    else -> {
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            viewModel.loginFlow.collectLatest {
                when (it) {
                    Resource.Loading -> showLoading(true)
                    is Resource.Failure -> {
                        showLoading(false)
                        showMessage(it.exception.toString())
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        navController.navigate(SignInScreenDirections.actionSignInScreenToMainContainer())
                    }
                    else -> {
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) = binding.apply {
        progressBar.isVisible = isLoading
        tilEmail.isEnabled = !isLoading
        tilPassword.isEnabled = !isLoading
        btnSignIn.isEnabled = !isLoading
    }

    private fun validate(): Boolean {
        binding.apply {
            return if (etEmail.text.toString().isNotEmpty() &&
                etPassword.length() >= Constants.MIN_PASSWORD_REQUIRED_LENGTH
            ) {
                true
            } else if (etPassword.length() < Constants.MIN_PASSWORD_REQUIRED_LENGTH) {
                tilPassword.error = getString(R.string.password_length_condition)
                false
            } else {
                false
            }
        }
    }
}
