package uz.nurlibaydev.unicalsolutionstest.presentation.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.nurlibaydev.unicalsolutionstest.data.repository.AuthRepository
import uz.nurlibaydev.unicalsolutionstest.utils.Resource
import javax.inject.Inject

/**
 *  Created by Nurlibay Koshkinbaev on 17/03/2023 19:36
 */

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository,
) : ViewModel() {

    private val _signupFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signupFlow: StateFlow<Resource<FirebaseUser>?> = _signupFlow

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _signupFlow.value = Resource.Loading
            val result = repository.signup(email, password)
            _signupFlow.value = result
        }
    }
}
