package com.iraklyoda.imageapp.presentation.screen.preview

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.iraklyoda.imageapp.databinding.FragmentPreviewBinding
import com.iraklyoda.imageapp.presentation.BaseFragment
import com.iraklyoda.imageapp.presentation.screen.preview.image_picker.ImagePickerBottomSheetFragment
import com.iraklyoda.imageapp.presentation.utils.collectLatest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreviewFragment : BaseFragment<FragmentPreviewBinding>(FragmentPreviewBinding::inflate) {

    private val previewViewModel: PreviewViewModel by viewModels()

    private val imagePickerBottomSheetFragment: ImagePickerBottomSheetFragment by lazy {
        ImagePickerBottomSheetFragment()
    }

    override fun start() {
    }

    override fun listeners() {
        setAddImageBtnListener()
        imagePickerFragmentListener()
    }

    override fun observers() {
        uiStateObserver()
    }

    private fun uiStateObserver() {
        collectLatest(flow = previewViewModel.state) { state ->

            state.imageBitmap?.let {
                Glide.with(requireContext()).load(it).into(binding.ivPreview)
            }
        }
    }

    private fun imagePickerFragmentListener() {
        setFragmentResultListener("imageResult") { _, bundle ->
            val byteArray = bundle.getByteArray("bitmap")
            val imageUri = bundle.getString("imageUri")
            val bitmap = byteArray?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }
            Log.d("bitmapIraklyoda", bitmap.toString())
            bitmap?.let {
                onImageSelected(bitmap = bitmap)
            }

            imageUri?.let {
                Glide.with(requireContext()).load(it).into(binding.ivPreview)
            }
        }
    }

    private fun setAddImageBtnListener() {
        binding.btnAddImg.setOnClickListener {
            imagePickerBottomSheetFragment.show(
                parentFragmentManager,
                imagePickerBottomSheetFragment.tag
            )
        }
    }

    private fun onImageSelected(bitmap: Bitmap?) {
        previewViewModel.onEvent(
            PreviewEvent.SetImage(
                bitmap = bitmap
            )
        )
    }


}