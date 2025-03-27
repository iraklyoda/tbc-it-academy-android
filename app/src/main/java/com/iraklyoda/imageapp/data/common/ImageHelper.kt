package com.iraklyoda.imageapp.data.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class ImageHelper(private val appContext: Context) {

    suspend fun compressUri(imageUri: Uri, quality: Int = 80): Bitmap? =
        withContext(Dispatchers.IO) {
            val bitmap = uriToBitmap(imageUri) // uriToBitmap will use appContext internally
            compressBitmap(bitmap, quality)
        }

    private fun uriToBitmap(uri: Uri): Bitmap? {
        return try {
            appContext.contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: Exception) {
            Log.e("ImageUtils", "Error converting Uri to Bitmap", e)
            null
        } catch (oom: OutOfMemoryError) {
            Log.e("ImageUtils", "OutOfMemoryError converting Uri to Bitmap", oom)
            null
        }
    }

    private suspend fun compressBitmap(bitmap: Bitmap?, quality: Int = 80): Bitmap? =
        withContext(Dispatchers.IO) { // Ensure execution on IO thread
            if (bitmap == null) return@withContext null // Early exit if input is null

            try {
                ByteArrayOutputStream().use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
                    val byteArray = outputStream.toByteArray()
                    BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                        ?: run {
                            Log.e(
                                "ImageHelper",
                                "BitmapFactory.decodeByteArray returned null after compression."
                            )
                            null
                        }
                }
            } catch (e: Exception) {
                Log.e("ImageHelper", "Error compressing Bitmap", e)
                null // Return null on error
            } catch (oom: OutOfMemoryError) {
                Log.e("ImageHelper", "OutOfMemoryError during Bitmap compression", oom)
                null // Return null on OOM
            }
        }
}