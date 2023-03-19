package uz.nurlibaydev.unicalsolutionstest.presentation.auth.signup

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
import uz.nurlibaydev.unicalsolutionstest.databinding.ScreenSignupBinding
import uz.nurlibaydev.unicalsolutionstest.utils.Constants.MIN_PASSWORD_REQUIRED_LENGTH
import uz.nurlibaydev.unicalsolutionstest.utils.Resource
import uz.nurlibaydev.unicalsolutionstest.utils.provideDeviceId
import uz.nurlibaydev.unicalsolutionstest.utils.showMessage

/**
 *  Created by Nurlibay Koshkinbaev on 17/03/2023 17:30
 */

@AndroidEntryPoint
class SignUpScreen : Fragment(R.layout.screen_signup) {

    private val binding by viewBinding<ScreenSignupBinding>()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private val viewModel: SignUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvSignIn.setOnClickListener {
                navController.navigate(SignUpScreenDirections.actionSignUpScreenToSignInScreen())
            }
            btnSignUp.setOnClickListener {
                if (validate()) {
                    viewModel.signUp(etEmail.text.toString(), etPassword.text.toString())
                    setupObserverSignUp()
                }
            }
        }
    }

    private fun setupObserverSignUp() = binding.apply {
        lifecycleScope.launch {
            viewModel.signupFlow.collectLatest {
                when (it) {
                    Resource.Loading -> showLoading(true)
                    is Resource.Failure -> {
                        showLoading(false)
                        showMessage(it.exception.toString())
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        viewModel.addUserToDb(
                            provideDeviceId(),
                            etName.text.toString(),
                            etEmail.text.toString(),
                            etPassword.text.toString(),
                        )
                        setupObserverAddUser()
                    }
                    else -> {
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun setupObserverAddUser() {
        lifecycleScope.launch {
            viewModel.addUserFlow.collectLatest {
                when (it) {
                    Resource.Loading -> showLoading(true)
                    is Resource.Failure -> {
                        showLoading(false)
                        showMessage(it.exception.toString())
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        showMessage(it.result)
                        navController.navigate(SignUpScreenDirections.actionSignUpScreenToMainContainer())
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
        tilName.isEnabled = !isLoading
        tilEmail.isEnabled = !isLoading
        tilPassword.isEnabled = !isLoading
        btnSignUp.isEnabled = !isLoading
    }

    private fun validate(): Boolean {
        binding.apply {
            return if (etEmail.text.toString().isNotEmpty() && etName.text.toString().isNotEmpty() &&
                etPassword.length() >= MIN_PASSWORD_REQUIRED_LENGTH
            ) {
                true
            } else if (etPassword.length() < MIN_PASSWORD_REQUIRED_LENGTH) {
                tilPassword.error = getString(R.string.password_length_condition)
                false
            } else if (etName.text.isNullOrEmpty()) {
                tilPassword.error = getString(R.string.enter_full_name)
                false
            } else {
                false
            }
        }
    }
}
