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

    private val _signupFlow = MutableStateFlow<Resource<FirebaseUser>?>(Resource.Loading)
    val signupFlow: StateFlow<Resource<FirebaseUser>?> = _signupFlow

    private val _addUserFlow = MutableStateFlow<Resource<String>?>(Resource.Loading)
    val addUserFlow: StateFlow<Resource<String>?> = _addUserFlow

    init {
        if (repository.currentUser != null) {
            _signupFlow.value = Resource.Success(repository.currentUser!!)
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _signupFlow.value = Resource.Loading
            val result = repository.signup(email, password)
            _signupFlow.value = result
        }
    }

    fun addUserToDb(deviceId: String, name: String, email: String, password: String) {
        viewModelScope.launch {
            _addUserFlow.value = Resource.Loading
            val result = repository.addUserToDb(deviceId, name, email, password)
            _addUserFlow.value = result
        }
    }
}
