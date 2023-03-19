package uz.nurlibaydev.unicalsolutionstest.presentation.main.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.nurlibaydev.unicalsolutionstest.R
import uz.nurlibaydev.unicalsolutionstest.databinding.ScreenProfileBinding
import uz.nurlibaydev.unicalsolutionstest.utils.Resource
import uz.nurlibaydev.unicalsolutionstest.utils.provideDeviceId
import uz.nurlibaydev.unicalsolutionstest.utils.showMessage

/**
 *  Created by Nurlibay Koshkinbaev on 17/03/2023 18:06
 */

@AndroidEntryPoint
class ProfileScreen : Fragment(R.layout.screen_profile) {

    private val binding by viewBinding<ScreenProfileBinding>()
    private val viewModel: ProfileViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            fabAddPhoto.setOnClickListener {
                pickImageFromGallery()
            }
            viewModel.getImageFromDatabase(provideDeviceId())
            observerGetImage()
        }
    }

    private fun observerStorage() {
        lifecycleScope.launch {
            viewModel.addImageStorage.collectLatest {
                when (it) {
                    Resource.Loading -> showLoading(true)
                    is Resource.Failure -> {
                        showLoading(false)
                        showMessage(it.exception.toString())
                    }
                    is Resource.Success -> {
                        viewModel.addImageToFireStore(provideDeviceId(), it.result)
                        observerFireStore()
                        showMessage("Image uploaded to Storage successfully!")
                        showLoading(false)
                    }
                    else -> {
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun observerFireStore() {
        lifecycleScope.launch {
            viewModel.addImageFireStore.collectLatest {
                when (it) {
                    Resource.Loading -> showLoading(true)
                    is Resource.Failure -> {
                        showLoading(false)
                        showMessage(it.exception.toString())
                    }
                    is Resource.Success -> {
                        showMessage("Image link added tp firestore!")
                        showLoading(false)
                    }
                    else -> {
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun observerGetImage() {
        lifecycleScope.launch {
            viewModel.getImageFromDatabase.collectLatest {
                when (it) {
                    Resource.Loading -> showLoading(true)
                    is Resource.Failure -> {
                        showLoading(false)
                        showMessage(it.exception.toString())
                    }
                    is Resource.Success -> {
                        Glide
                            .with(this@ProfileScreen)
                            .load(it.result)
                            .centerCrop()
                            .into(binding.imgProfile)
                        showMessage("Image got from storage!")
                        showLoading(false)
                    }
                    else -> {
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) = binding.apply {
        progressBar.isVisible = isLoading
        fabAddPhoto.isEnabled = !isLoading
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        private const val IMAGE_PICK_CODE = 1000
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            binding.imgProfile.setImageURI(data?.data)
            viewModel.addImageToStorage(data?.data!!)
            observerStorage()
        }
    }
}
