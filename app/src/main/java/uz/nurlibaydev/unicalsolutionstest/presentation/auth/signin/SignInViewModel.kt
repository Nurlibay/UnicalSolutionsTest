package uz.nurlibaydev.unicalsolutionstest.presentation.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.nurlibaydev.unicalsolutionstest.data.models.User
import uz.nurlibaydev.unicalsolutionstest.data.repository.AuthRepository
import uz.nurlibaydev.unicalsolutionstest.utils.Resource
import javax.inject.Inject

/**
 *  Created by Nurlibay Koshkinbaev on 17/03/2023 19:28
 */

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: AuthRepository,
) : ViewModel() {

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow: StateFlow<Resource<FirebaseUser>?> = _loginFlow

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginFlow.value = Resource.Loading
            val result = repository.login(email, password)
            _loginFlow.value = result
        }
    }

    private val _users = MutableStateFlow<Resource<List<User>>?>(Resource.Loading)
    val users: StateFlow<Resource<List<User>>?> = _users

    fun getAllUsers(deviceId: String) {
        viewModelScope.launch {
            _users.value = Resource.Loading
            val result = repository.getAllUsers(deviceId)
            _users.value = result
        }
    }
}
