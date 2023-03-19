package uz.nurlibaydev.unicalsolutionstest.data.pref

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.nurlibaydev.unicalsolutionstest.utils.SharedPreference
import javax.inject.Inject
import javax.inject.Singleton

/**
 *  Created by Nurlibay Koshkinbaev on 19/03/2023 16:43
 */

@Singleton
class SharedPref @Inject constructor(
    @ApplicationContext context: Context,
    sharedPreferences: SharedPreferences,
) : SharedPreference(context, sharedPreferences) {

    var isSigned: Boolean by Booleans(false)
}
