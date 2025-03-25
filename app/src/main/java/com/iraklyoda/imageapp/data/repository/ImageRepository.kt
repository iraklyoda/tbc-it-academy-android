package com.iraklyoda.imageapp.data.repository

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.iraklyoda.imageapp.domain.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageRepository @Inject constructor(
    private val firebaseStorage: FirebaseStorage
) {

    private val storageRef = firebaseStorage.reference

    fun uploadBitmap(bitmap: Bitmap): Flow<Resource<Uri>> = flow {
        emit(Resource.Loader(true))
        try {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
            val data = byteArrayOutputStream.toByteArray()
            val imageRef = storageRef.child("images/${System.currentTimeMillis()}.jpg")
            val uploadTask = imageRef.putBytes(data).await()
            val downloadUrl = imageRef.downloadUrl.await()
            emit(Resource.Success(downloadUrl))

        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Upload failed")) // Emit error state
        } finally {
            emit(Resource.Loader(false)) // Emit loading complete state
        }
    }
}