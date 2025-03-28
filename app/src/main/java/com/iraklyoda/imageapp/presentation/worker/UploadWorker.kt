package com.iraklyoda.imageapp.presentation.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.iraklyoda.imageapp.domain.common.use_case.UploadImageUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay

@HiltWorker
class UploadWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val uploadImageUseCase: UploadImageUseCase
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        const val KEY_IMAGE_URI = "IMAGE_URI"
        const val KEY_UPLOAD_URL = "UPLOAD_URL"
        const val KEY_ERROR_MESSAGE = "ERROR_MESSAGE"
        const val KEY_WORKER_LOG = "KEY_WORKER_LOG"
    }

    override suspend fun doWork(): Result {
        Log.d(KEY_WORKER_LOG, "doWork Started")
        delay(200)
        return Result.success()

//        val imageUriString = inputData.getString(KEY_IMAGE_URI)
//            ?: return Result.failure(workDataOf(KEY_ERROR_MESSAGE to "Image URI missing"))
//
//        val imageUri = imageUriString.toUri()
//
//
//
//        return try {
//            uploadImageUseCase(imageUri).collect { resource ->
//                when (resource) {
//                    is Resource.Success -> {
//                        Log.d(KEY_WORKER_LOG, "doWork Sucess")
//                        Result.success(
//                            workDataOf(KEY_UPLOAD_URL to resource.data)
//                        )
//                    }
//
//                    is Resource.Error -> {
//                        Log.d(KEY_WORKER_LOG, "doWork ERROR")
//                        Result.failure(
//                            workDataOf(KEY_ERROR_MESSAGE to resource.errorMessage)
//                        )
//                    }
//                    else -> {
//                        Log.d(KEY_WORKER_LOG, "Iraklaa")
//                    }
//                }
//            }
//            Log.d(KEY_WORKER_LOG, "doWork ERROR")
//            Result.failure(workDataOf(KEY_ERROR_MESSAGE to "Unknown error")) // Fallback
//        } catch (e: Exception) {
//            Log.d(KEY_WORKER_LOG, "DOWORK ERROR ${e.message}")
//            Result.failure(workDataOf(KEY_ERROR_MESSAGE to e.message))
//        }
//    }

    }
}