package com.iraklyoda.imageapp.presentation.screen.preview.image_picker

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.iraklyoda.imageapp.R
import com.iraklyoda.imageapp.databinding.FragmentImagePickerBottomSheetBinding
import com.iraklyoda.imageapp.presentation.utils.collect
import com.iraklyoda.imageapp.presentation.utils.collectLatest
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class ImagePickerBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentImagePickerBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var uploadMedia: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var takePicturePreviewLauncher: ActivityResultLauncher<Void?>
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String?>

    private val imagePickerViewModel: ImagePickerViewModel by viewModels()

    private fun setActivityResults() {
        pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                uri?.let {
                    compressUri(imageUri = uri)?.let { bitmap ->
                        onImageSuccess(bitmap = bitmap)
                    }
                }
            }

        uploadMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                uri?.let {
                    compressUri(imageUri = uri)?.let { bitmap ->
                        imagePickerViewModel.onEvent(ImagePickerEvent.UploadImage(bitmap = bitmap))
                    }
                }
            }

        takePicturePreviewLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
                bitmap?.let {
                    compressBitmap(bitmap)?.let { bitmap ->
                        onImageSuccess(bitmap = bitmap)
                    }
                }
            }

        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                launchCamera()
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    Snackbar.make(
                        requireView(),
                        "Camera permission required",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    showSettingsDialog()
                }
            }
        }
    }

    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> launchCamera()

            else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImagePickerBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActivityResults()
        listeners()
        observers()
    }

    private fun listeners() {
        setPickPictureBtnListener()
        setTakeImageBtnListener()
        setUploadImageBtnListener()
    }

    private fun observers() {
        observeUploadState()
        observeImagePickerEvents()
    }

    private fun observeUploadState() {
        collectLatest(flow = imagePickerViewModel.state) { state ->
            binding.loaderInclude.loaderContainer.isVisible = state.loading

            listOf(binding.btnUpload, binding.btnTakeImage, binding.btnPickGallery)
                .forEach { it.isInvisible = state.loading }
        }
    }

    private fun observeImagePickerEvents() {
        collect(flow = imagePickerViewModel.uiEvents) { event ->
            when (event) {
                is ImagePickerUiEvent.DismissDialog -> {
                    onImageSuccess(imageUri = event.imageUri)
                }

                is ImagePickerUiEvent.ShowError -> {
                    Toast.makeText(requireContext(), event.error, Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

    private fun setPickPictureBtnListener() {
        binding.btnPickGallery.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun setTakeImageBtnListener() {
        binding.btnTakeImage.setOnClickListener {
            checkCameraPermission()
        }
    }

    private fun setUploadImageBtnListener() {
        binding.btnUpload.setOnClickListener {
            uploadMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun launchCamera() {
        takePicturePreviewLauncher.launch(null)
    }

    private fun showSettingsDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.permission_needed))
            .setMessage(getString(R.string.camera_access_is_required_to_take_photos_please_grant_the_permission))
            .setPositiveButton(getString(R.string.grant)) { dialog, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", requireContext().packageName, null)
                }
                startActivity(intent)
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun onImageSuccess(bitmap: Bitmap? = null, imageUri: Uri? = null) {
        val bundle = Bundle().apply {
            putByteArray("bitmap", bitmap?.let { bitmapToByteArray(it) })
            putString("imageUri", imageUri.toString())
        }
        setFragmentResult("imageResult", bundle)
        dismiss()
    }

    // Image Format Handling

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    private fun compressUri(imageUri: Uri): Bitmap? {
        val bitmap = uriToBitmap(imageUri)
        return compressBitmap(bitmap)
    }

    private fun uriToBitmap(uri: Uri): Bitmap? {
        return try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            Log.e("ImagePicker", "Error converting Uri to Bitmap", e)
            null
        }
    }

    private fun compressBitmap(bitmap: Bitmap?): Bitmap? {
        if (bitmap == null) return null
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        val byteArray = outputStream.toByteArray()
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}