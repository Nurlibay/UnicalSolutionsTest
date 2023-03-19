package uz.nurlibaydev.unicalsolutionstest.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.nurlibaydev.unicalsolutionstest.data.repository.ProfileImageRepository
import uz.nurlibaydev.unicalsolutionstest.data.repository.impl.ProfileImageRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseFireStore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideProfileImageRepository(
        storage: FirebaseStorage,
        db: FirebaseFirestore,
        auth: FirebaseAuth,
    ): ProfileImageRepository = ProfileImageRepositoryImpl(
        storage = storage,
        db = db,
        auth = auth,
    )
}
