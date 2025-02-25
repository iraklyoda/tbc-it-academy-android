package com.example.tricholog.data.repositories.auth

import android.util.Log.d
import com.example.tricholog.data.common.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun login(email: String, password: String): Flow<Resource<Boolean, Exception>> {
        return flow {
            emit(Resource.Loading)
            try {
                val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                authResult.user?.let {
                    emit(Resource.Success(true))
                }
            } catch (exception: Exception) {
                emit(Resource.Error(exception))
            }
        }
    }

    override suspend fun signUp(email: String, password: String): Flow<Resource<Boolean, Exception>> = flow {
        emit(Resource.Loading)
        try {
            val authResult =
                firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            d("FirebaseAuthProgress", "User: ${authResult.user}")

            authResult.user?.let {
                emit(Resource.Success(true))
            }

        } catch (exception: Exception) {
            emit(Resource.Error(exception))
        }
    }


    override suspend fun logout() {

    }
}