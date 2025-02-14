package com.example.baseproject.data.di

import com.example.baseproject.data.remote.api.StatisticsService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://run.mocky.io/v3/"

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                json().asConverterFactory("application/json".toMediaType())
            )
            .build()
    }

    private fun json() = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    fun provideStatisticsService(retrofit: Retrofit): StatisticsService {
        return retrofit.create(StatisticsService::class.java)
    }

}