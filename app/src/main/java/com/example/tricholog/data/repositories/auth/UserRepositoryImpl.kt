package com.example.tricholog.data.repositories.auth

import com.example.tricholog.data.mapper.toFirestoreMap
import com.example.tricholog.domain.auth.AuthManager
import com.example.tricholog.domain.model.User
import com.example.tricholog.domain.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import android.util.Log


class UserRepositoryImpl @Inject constructor(
    private val firebaseFireStore: FirebaseFirestore,
    private val authManager: AuthManager
) : UserRepository {
    override suspend fun saveUser(user: User): Boolean {
        return try {
            firebaseFireStore.collection("users")
                .document(user.uid)
                .set(user.toFirestoreMap())
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getUser(): User? {
        val uid = authManager.getCurrentUserId() ?: return null
        try {
            val documentSnapshot = firebaseFireStore.collection("users")
                .document(uid)
                .get()
                .await()

            val user = documentSnapshot.toObject(User::class.java)
            user?.let {
                return it
            } ?: return null
        } catch (e: Exception) {
            return null
        }
    }
}