package com.iraklyoda.imageapp.domain.common.repository

import android.net.Uri
import com.iraklyoda.imageapp.domain.common.Resource
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    fun uploadImage(imageUri: Uri): Flow<Resource<Uri>>
}