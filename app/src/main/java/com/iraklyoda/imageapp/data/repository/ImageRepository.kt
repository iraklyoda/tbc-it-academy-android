package com.iraklyoda.imageapp.data.repository

import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.iraklyoda.imageapp.domain.common.Resource
import com.iraklyoda.imageapp.domain.common.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageRepositoryImpl @Inject constructor(
    private val firebaseStorage: FirebaseStorage
) : ImageRepository {

    private val storageRef = firebaseStorage.reference

    override fun uploadImage(imageUri: Uri): Flow<Resource<Uri>> = flow {
        emit(Resource.Loader(loading = true))
        try {

            val imageRef = storageRef.child("images/img_${System.currentTimeMillis()}.jpg")
            val uploadTask = imageRef.putFile(imageUri)
            val downloadUrl = imageRef.downloadUrl.await()

            emit(Resource.Success(downloadUrl))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Upload failed"))
        } finally {
            emit(Resource.Loader(loading = false))
        }
    }
}