package uz.nurlibaydev.unicalsolutionstest.data.repository

import com.google.firebase.auth.FirebaseUser
import uz.nurlibaydev.unicalsolutionstest.utils.Resource

/**
 *  Created by Nurlibay Koshkinbaev on 17/03/2023 18:37
 */

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Resource<FirebaseUser>
    suspend fun signup(email: String, password: String): Resource<FirebaseUser>
    suspend fun addUserToDb(name: String, email: String, password: String): Resource<String>
    fun logout()
}
