package com.example.tricholog.data.repositories.auth

import android.util.Log
import com.example.tricholog.data.mapper.FirebaseAuthExceptionMapper
import com.example.tricholog.domain.common.Resource
import com.example.tricholog.domain.error.AuthError
import com.example.tricholog.domain.model.User
import com.example.tricholog.domain.repository.AuthRepository
import com.example.tricholog.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userRepository: UserRepository
) : AuthRepository {

    override suspend fun login(email: String, password: String): Flow<Resource<Boolean, AuthError>> {
        return flow {
            emit(Resource.Loading)
            try {
                val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                authResult.user?.let {
                    emit(Resource.Success(true))
                } ?: emit(Resource.Error(AuthError.UnknownError))
            } catch (exception: Exception) {
                emit(Resource.Error(FirebaseAuthExceptionMapper.mapException(exception)))
            }
        }
    }

    override suspend fun signUp(email: String, username: String, password: String): Flow<Resource<Boolean, AuthError>> = flow {
        emit(Resource.Loading)
        try {
            val authResult =
                firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            val uid = authResult.user?.uid
            uid?.let {

                userRepository.saveUser(User(
                    uid = uid,
                    email = email,
                    username = username
                ))

                emit(Resource.Success(true))
            } ?: emit(Resource.Error(AuthError.UnknownError))

        } catch (exception: Exception) {
            emit(Resource.Error(FirebaseAuthExceptionMapper.mapException(exception)))
        }
    }

    override suspend fun logout() {
        try {
            firebaseAuth.signOut()
        } catch (e: Exception) {
            Log.e("Auth", "Logout failed: ${e.message}")
        }
    }
}