package uz.nurlibaydev.unicalsolutionstest.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import uz.nurlibaydev.unicalsolutionstest.BuildConfig

/**
 *  Created by Nurlibay Koshkinbaev on 17/03/2023 17:18
 */

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
