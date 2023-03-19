package uz.nurlibaydev.unicalsolutionstest.data.repository.impl

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import uz.nurlibaydev.unicalsolutionstest.data.repository.AddImageToStorageResponse
import uz.nurlibaydev.unicalsolutionstest.data.repository.AddImageUrlToFireStoreResponse
import uz.nurlibaydev.unicalsolutionstest.data.repository.GetImageUrlFromFireStoreResponse
import uz.nurlibaydev.unicalsolutionstest.data.repository.ProfileImageRepository
import uz.nurlibaydev.unicalsolutionstest.utils.Constants.CREATED_AT
import uz.nurlibaydev.unicalsolutionstest.utils.Constants.IMAGES
import uz.nurlibaydev.unicalsolutionstest.utils.Constants.IMAGE_URL
import uz.nurlibaydev.unicalsolutionstest.utils.Constants.USERS
import uz.nurlibaydev.unicalsolutionstest.utils.Resource
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileImageRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage,
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth,
) : ProfileImageRepository {
    override suspend fun addImageToFirebaseStorage(imageUri: Uri): AddImageToStorageResponse {
        return try {
            val downloadUrl = storage.reference.child(IMAGES).child(UUID.randomUUID().toString())
                .putFile(imageUri).await()
                .storage.downloadUrl.await()
            Resource.Success(downloadUrl)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun addImageUrlToFireStore(deviceId: String, downloadUrl: Uri): AddImageUrlToFireStoreResponse {
        return try {
            db.collection(USERS).document(deviceId).update(
                mapOf(
                    IMAGE_URL to downloadUrl,
                    CREATED_AT to FieldValue.serverTimestamp(),
                ),
            ).await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun getImageUrlFromFireStore(deviceId: String): GetImageUrlFromFireStoreResponse {
        return try {
            val imageUrl = db.collection(USERS).document(deviceId).get().await().getString(IMAGE_URL)
            Resource.Success(imageUrl!!)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
}
