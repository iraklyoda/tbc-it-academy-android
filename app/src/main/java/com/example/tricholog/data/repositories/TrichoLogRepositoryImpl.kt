package com.example.tricholog.data.repositories

import android.util.Log
import com.example.tricholog.data.mapper.toTrichoLogDomain
import com.example.tricholog.data.model.TrichoLogDto
import com.example.tricholog.domain.auth.AuthManager
import com.example.tricholog.domain.common.Resource
import com.example.tricholog.domain.error.ApiError
import com.example.tricholog.domain.model.TrichoLog
import com.example.tricholog.domain.repository.TrichoLogRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TrichoLogRepositoryImpl @Inject constructor(
    private val firebaseFireStore: FirebaseFirestore,
    authManager: AuthManager
) : TrichoLogRepository {
    private lateinit var logsRef: CollectionReference

    private val uid = authManager.getCurrentUserId()

    override suspend fun saveTrichoLog(trichoLog: TrichoLog): Flow<Resource<Boolean, ApiError>> {

        return flow {
            emit(Resource.Loading)
            try {
                uid?.let {
                    logsRef = firebaseFireStore.collection("users").document(uid)
                        .collection("logs")
                    logsRef.add(trichoLog)
                    emit(Resource.Success(true))
                } ?: emit(Resource.Error(ApiError.UnauthorizedError))
            } catch (exception: Exception) {
                emit(Resource.Error(ApiError.UnknownError))
            }
        }

    }

    override suspend fun getTrichoLogs(): Flow<Resource<List<TrichoLog>, ApiError>> {
        return flow {
            emit(Resource.Loading)
            try {
                uid?.let {
                    logsRef = firebaseFireStore.collection("users").document(uid)
                        .collection("logs")
                    val logsSnapshot = logsRef.get().await()
                    val logs = logsSnapshot.documents.mapNotNull { document ->
                        document.toObject(TrichoLogDto::class.java)
                    }.sortedBy { it.createdAt }

                    emit(Resource.Success(logs.map { it.toTrichoLogDomain() }))
                } ?: emit(Resource.Error(ApiError.UnauthorizedError))
            } catch (e: Exception) {
                Log.d("Firebase Error", "${e.message}")
                emit(Resource.Error(ApiError.HttpError(code = 400)))
            }
        }
    }
}