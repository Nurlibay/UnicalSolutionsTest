package uz.nurlibaydev.unicalsolutionstest.utils

import android.annotation.SuppressLint
import android.provider.Settings
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showMessage(text: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(requireContext(), text, duration).show()
}

@SuppressLint("HardwareIds")
fun Fragment.provideDeviceId(): String {
    return Settings.Secure.getString(
        requireActivity().contentResolver,
        Settings.Secure.ANDROID_ID,
    )
}
