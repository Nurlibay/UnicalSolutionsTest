package uz.nurlibaydev.unicalsolutionstest.utils

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showMessage(text: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(requireContext(), text, duration).show()
}
