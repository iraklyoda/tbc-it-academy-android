package com.example.tricholog.data.di

import com.example.tricholog.data.repositories.article.ArticleRepositoryImpl
import com.example.tricholog.data.repositories.auth.AuthRepositoryImpl
import com.example.tricholog.data.repositories.auth.UserRepositoryImpl
import com.example.tricholog.domain.repository.ArticleRepository
import com.example.tricholog.domain.repository.AuthRepository
import com.example.tricholog.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindArticleRepository(articleRepositoryImpl: ArticleRepositoryImpl): ArticleRepository
}