package uz.nurlibaydev.unicalsolutionstest.presentation.main.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.nurlibaydev.unicalsolutionstest.data.repository.ProfileImageRepository
import uz.nurlibaydev.unicalsolutionstest.utils.Resource
import javax.inject.Inject

/**
 *  Created by Nurlibay Koshkinbaev on 18/03/2023 18:49
 */

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileImageRepository,
) : ViewModel() {

    private val _addImageStorage = MutableStateFlow<Resource<Uri>?>(null)
    val addImageStorage: StateFlow<Resource<Uri>?> = _addImageStorage

    fun addImageToStorage(imageUri: Uri) {
        viewModelScope.launch {
            _addImageStorage.value = Resource.Loading
            val result = repository.addImageToFirebaseStorage(imageUri)
            _addImageStorage.value = result
        }
    }

    private val _addImageFireStore = MutableStateFlow<Resource<Boolean>?>(null)
    val addImageFireStore: StateFlow<Resource<Boolean>?> = _addImageFireStore

    fun addImageToFireStore(downloadUrl: Uri) {
        viewModelScope.launch {
            _addImageFireStore.value = Resource.Loading
            val result = repository.addImageUrlToFireStore(downloadUrl)
            _addImageFireStore.value = result
        }
    }

    private val _getImageFromDatabase = MutableStateFlow<Resource<String>?>(null)
    val getImageFromDatabase: StateFlow<Resource<String>?> = _getImageFromDatabase

    fun getImageFromDatabase() {
        viewModelScope.launch {
            _getImageFromDatabase.value = Resource.Loading
            val result = repository.getImageUrlFromFireStore()
            _getImageFromDatabase.value = result
        }
    }
}
