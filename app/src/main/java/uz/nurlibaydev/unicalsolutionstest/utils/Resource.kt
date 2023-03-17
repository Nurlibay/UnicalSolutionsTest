package uz.nurlibaydev.unicalsolutionstest.utils

/**
 *  Created by Nurlibay Koshkinbaev on 17/03/2023 18:36
 */

sealed class Resource<out R> {
    data class Success<out R>(val result: R) : Resource<R>()
    data class Failure(val exception: Exception) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}
