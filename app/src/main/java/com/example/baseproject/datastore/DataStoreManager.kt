package com.example.baseproject.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.example.baseproject.User
import com.example.baseproject.proto.UserDataPreferences
import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.coroutines.flow.first
import java.io.InputStream
import java.io.OutputStream

object UserDataPreferencesSerializer : Serializer<UserDataPreferences> {
    override val defaultValue: UserDataPreferences = UserDataPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserDataPreferences {
        try {
            return UserDataPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: UserDataPreferences,
        output: OutputStream
    ) = t.writeTo(output)
}

private const val DATA_STORE_FILE_NAME = "user_data.proto"

val Context.usersDataStore: DataStore<UserDataPreferences> by dataStore(
    fileName = DATA_STORE_FILE_NAME,
    serializer = UserDataPreferencesSerializer
)

@SuppressLint("StaticFieldLeak")
object UserDataPreferencesRepository {
    private var context: Context? = null

    fun init(context: Context) {
        this.context = context.applicationContext
    }

    private val userDataPreferences: DataStore<UserDataPreferences> by lazy {
        context?.usersDataStore ?: throw IllegalStateException("Context not initialized")
    }

    private suspend fun isUserSet(): Boolean {
        val preferences = userDataPreferences.data.first()
        return preferences.firstName.isNotEmpty()
    }

    suspend fun setUser(user: User) {
        userDataPreferences.updateData { preferences ->
            preferences.toBuilder()
                .setFirstName(user.firstName)
                .setLastName(user.lastName)
                .setEmailAddress(user.emailAddress)
                .build()
        }
    }

    suspend fun getUser(): User? {

        if (!isUserSet()) {
            return null
        }

        val preferences = userDataPreferences.data.first()
        return User(
            firstName = preferences.firstName,
            lastName = preferences.lastName,
            emailAddress = preferences.emailAddress
        )
    }
}

