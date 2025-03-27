package com.iraklyoda.imageapp.domain.common.repository

import android.net.Uri
import com.iraklyoda.imageapp.domain.common.Resource
import kotlinx.coroutines.flow.Flow
import java.io.InputStream

interface ImageRepository {
    fun uploadImage(inputStream: InputStream): Flow<Resource<Uri>>
}