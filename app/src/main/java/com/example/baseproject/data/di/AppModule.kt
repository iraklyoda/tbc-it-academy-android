package com.example.baseproject.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.baseproject.common.ApiHelper
import com.example.baseproject.data.local.AuthPreferencesRepository
import com.example.baseproject.data.local.db.AppDatabase
import com.example.baseproject.data.local.db.UserDao
import com.example.baseproject.data.remote.AuthRepository
import com.example.baseproject.data.remote.api.AuthService
import com.example.baseproject.data.remote.api.UserService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

private const val AUTH_PREFERENCES_NAME = "auth_preferences"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = AUTH_PREFERENCES_NAME)


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(
                Json {
                    ignoreUnknownKeys = true
                }.asConverterFactory("application/json".toMediaType())
            )
            .build()
    }

    @Provides
    fun provideApiHelper(): ApiHelper {
        return ApiHelper()
    }

    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        apiHelper: ApiHelper,
        authPreferencesRepository: AuthPreferencesRepository,
        authService: AuthService
    ): AuthRepository {
        return AuthRepository(
            apiHelper = apiHelper,
            authPreferencesRepository = authPreferencesRepository,
            authService = authService
        )
    }

    @Provides
    fun provideAuthPreferencesRepository(@ApplicationContext context: Context): AuthPreferencesRepository {
        return AuthPreferencesRepository(context.dataStore)
    }

    @Provides
    fun provideRoomDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "user_database"
        ).build()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

}