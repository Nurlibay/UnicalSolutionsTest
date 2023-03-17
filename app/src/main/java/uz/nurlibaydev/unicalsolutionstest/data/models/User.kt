package uz.nurlibaydev.unicalsolutionstest.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *  Created by Nurlibay Koshkinbaev on 17/03/2023 18:29
 */

@Parcelize
data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val imageUrl: String = "",
) : Parcelable
