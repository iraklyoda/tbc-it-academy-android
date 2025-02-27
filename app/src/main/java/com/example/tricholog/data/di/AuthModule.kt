package com.example.tricholog.data.di

import com.example.tricholog.data.auth.FirebaseAuthManager
import com.example.tricholog.domain.auth.AuthManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindAuthManager(firebaseAuthManager: FirebaseAuthManager): AuthManager
}