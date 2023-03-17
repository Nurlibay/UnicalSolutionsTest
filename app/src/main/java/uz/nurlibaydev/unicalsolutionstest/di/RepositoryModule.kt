package uz.nurlibaydev.unicalsolutionstest.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.nurlibaydev.unicalsolutionstest.data.repository.AuthRepository
import uz.nurlibaydev.unicalsolutionstest.data.repository.impl.AuthRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}
