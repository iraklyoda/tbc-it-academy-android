package com.iraklyoda.imageapp.domain.common.use_case

import android.net.Uri
import com.iraklyoda.imageapp.domain.common.Resource
import com.iraklyoda.imageapp.domain.common.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import java.io.InputStream
import javax.inject.Inject

interface UploadImageUseCase {
    suspend operator fun invoke(imageUri: Uri): Flow<Resource<Uri>>
}

class UploadImageUseCaseImpl @Inject constructor(
    private val imageRepository: ImageRepository
) : UploadImageUseCase {
    override suspend fun invoke(imageUri: Uri): Flow<Resource<Uri>> {
        return imageRepository.uploadImage(imageUri = imageUri)
    }
}