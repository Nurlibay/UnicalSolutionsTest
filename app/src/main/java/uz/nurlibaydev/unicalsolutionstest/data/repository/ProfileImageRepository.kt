package uz.nurlibaydev.unicalsolutionstest.data.repository

import android.net.Uri
import uz.nurlibaydev.unicalsolutionstest.utils.Resource

typealias AddImageToStorageResponse = Resource<Uri>
typealias AddImageUrlToFireStoreResponse = Resource<Boolean>
typealias GetImageUrlFromFireStoreResponse = Resource<String>

interface ProfileImageRepository {
    suspend fun addImageToFirebaseStorage(imageUri: Uri): AddImageToStorageResponse

    suspend fun addImageUrlToFireStore(downloadUrl: Uri): AddImageUrlToFireStoreResponse

    suspend fun getImageUrlFromFireStore(): GetImageUrlFromFireStoreResponse
}
