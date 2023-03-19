package uz.nurlibaydev.unicalsolutionstest.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.nurlibaydev.unicalsolutionstest.utils.Constants.SHARED_PREFS
import javax.inject.Singleton

/**
 *  Created by Nurlibay Koshkinbaev on 19/03/2023 16:48
 */

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
    }
}
