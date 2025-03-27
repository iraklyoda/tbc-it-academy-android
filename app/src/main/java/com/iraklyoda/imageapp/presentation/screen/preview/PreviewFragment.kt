package com.iraklyoda.imageapp.presentation.screen.preview

import android.graphics.Bitmap
import android.util.Log
import androidx.core.net.toUri
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.iraklyoda.imageapp.R
import com.iraklyoda.imageapp.databinding.FragmentPreviewBinding
import com.iraklyoda.imageapp.presentation.BaseFragment
import com.iraklyoda.imageapp.presentation.screen.preview.image_picker.ImagePickerBottomSheetFragment
import com.iraklyoda.imageapp.presentation.utils.collect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreviewFragment : BaseFragment<FragmentPreviewBinding>(FragmentPreviewBinding::inflate) {

    private val previewViewModel: PreviewViewModel by viewModels()

    override fun start() {
    }

    override fun listeners() {
        setAddImageBtnListener()
        imagePickerFragmentListener()
    }

    override fun observers() {
        observePreviewUiEvents()
        observePreviewState()
    }

    private fun observePreviewUiEvents() {
        collect(flow = previewViewModel.uiEvents) { event ->
            when (event) {
                is PreviewUiEvent.OpenImagePickerBottomSheet -> showImagePickerBottomSheet()
            }
        }
    }

    private fun observePreviewState() {
        collect(flow = previewViewModel.state) { state ->
            Log.d("IMAGE_TAKEN", "IN PREVIEW FRAGMENT OBSERVER ${state.imageBitmap}")
            state.imageBitmap?.let { updatePreviewImage(bitmap = it) }
        }
    }

    private fun imagePickerFragmentListener() {
        setFragmentResultListener(ImagePickerBottomSheetFragment.IMAGE_PICKER_REQUEST_KEY) { _, bundle ->
            val imageUri = bundle.getString(ImagePickerBottomSheetFragment.IMAGE_URI_KEY)
            Log.d("IMAGE_TAKEN", "IN PREVIEW FRAGMENT ${imageUri?.toUri()}")
            imageUri?.let {
                previewViewModel.onEvent(PreviewEvent.ImageSelected(imageUri = imageUri.toUri()))
            }
        }
    }

    private fun setAddImageBtnListener() {
        binding.btnAddImg.setOnClickListener {
            previewViewModel.onEvent(PreviewEvent.AddImageClicked)
        }
    }

    private fun showImagePickerBottomSheet() {
        ImagePickerBottomSheetFragment().show(
            parentFragmentManager,
            "ImagePickerBottomSheet"
        )
    }

    private fun updatePreviewImage(bitmap: Bitmap) {
        Glide.with(requireContext())
            .load(bitmap)
            .placeholder(R.drawable.baseline_image_24)
            .into(binding.ivPreview)
    }
}