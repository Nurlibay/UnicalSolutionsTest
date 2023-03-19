package uz.nurlibaydev.unicalsolutionstest.data.repository.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import uz.nurlibaydev.unicalsolutionstest.data.models.User
import uz.nurlibaydev.unicalsolutionstest.data.repository.AuthRepository
import uz.nurlibaydev.unicalsolutionstest.utils.Constants.USERS
import uz.nurlibaydev.unicalsolutionstest.utils.Resource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFireStore: FirebaseFirestore,
) : AuthRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun signup(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun addUserToDb(deviceId: String, name: String, email: String, password: String):
        Resource<String> {
        val user = User(deviceId, name, firebaseAuth.currentUser?.email!!)
        return try {
            firebaseFireStore.collection(USERS).document(user.id).set(user).await()
            Resource.Success("User added to FireStore")
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getAllUsers(): Resource<List<User>> {
        val users = mutableListOf<User>()
        return try {
            firebaseFireStore.collection(USERS).get()
                .addOnSuccessListener {
                    val result = it.documents.map { doc ->
                        doc.toObject(User::class.java)!!
                    }
                    users.addAll(result)
                }.await()
            Resource.Success(users)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}
