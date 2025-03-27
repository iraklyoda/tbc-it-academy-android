package com.iraklyoda.imageapp.presentation.screen.preview.image_picker

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.iraklyoda.imageapp.R
import com.iraklyoda.imageapp.databinding.FragmentImagePickerBottomSheetBinding
import com.iraklyoda.imageapp.presentation.utils.collect
import com.iraklyoda.imageapp.presentation.utils.collectLatest
import com.iraklyoda.imageapp.presentation.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ImagePickerBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentImagePickerBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var pickMediaLauncher: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var uploadMediaLauncher: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var takePicturePreviewLauncher: ActivityResultLauncher<Uri?>
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String?>

    // Camera image Uri
    private var imageUri: Uri? = null

    private val imagePickerViewModel: ImagePickerViewModel by viewModels()

    private fun setActivityResults() {
        pickMediaLauncher =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                uri?.let {
                    onImageSuccess(imageUri = uri)
                }
            }

        takePicturePreviewLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
            if (success) {
                // Picture was taken successfully and saved to imageUri
                Log.d("CameraFragment", "Image capture successful. Uri: $imageUri")
                // Load the image from the Uri into the ImageView
                imageUri?.let { uri ->
                    onImageSuccess(imageUri = uri)
                }
            } else {
                imageUri = null // Reset Uri if capture failed
            }
        }

        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                launchCamera()
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    requireView().showSnackbar(message = getString(R.string.camera_permission_required))
                } else {
                    showSettingsDialog()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityResults()
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

    // Observers

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
                is ImagePickerUiEvent.DismissDialog -> onImageSuccess(imageUri = event.imageUri)
                is ImagePickerUiEvent.ShowError -> event.error?.let { binding.root.showSnackbar(it) }
                is ImagePickerUiEvent.LaunchMediaPicker -> launchImagePicker()
                is ImagePickerUiEvent.LaunchCamera -> checkCameraPermission()
                is ImagePickerUiEvent.LaunchMediaUploader -> launchUploadImage()
            }
        }
    }

    // Listeners

    private fun setPickPictureBtnListener() {
        binding.btnPickGallery.setOnClickListener {
            imagePickerViewModel.onEvent(ImagePickerEvent.PickImageClicked)
        }
    }

    private fun setTakeImageBtnListener() {
        binding.btnTakeImage.setOnClickListener {
            imagePickerViewModel.onEvent(ImagePickerEvent.TakeAPictureClicked)
        }
    }

    private fun setUploadImageBtnListener() {
        binding.btnUpload.setOnClickListener {
            imagePickerViewModel.onEvent(ImagePickerEvent.UploadImageClicked)
        }
    }


    private fun launchImagePicker() {
        pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun launchCamera() {
        val context = requireContext()
        val photoFile = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "photo_${System.currentTimeMillis()}.jpg"
        )
        imageUri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            photoFile
        )

        takePicturePreviewLauncher.launch(imageUri) // Change to use TakePicture()
    }
    private fun launchUploadImage() {
        pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
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

    private fun onImageSuccess(imageUri: Uri? = null) {
        val bundle = Bundle().apply {
            putString(IMAGE_URI_KEY, imageUri.toString())
        }
        setFragmentResult(IMAGE_PICKER_REQUEST_KEY, bundle)
        dismiss()
    }

    companion object {
        const val IMAGE_URI_KEY = "IMAGE_URI"
        const val IMAGE_PICKER_REQUEST_KEY = "IMAGE_RESULT"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}