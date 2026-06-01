package com.example.data.di

import com.example.data.network.ApiService
import com.example.data.repository.PostRepositoryImpl
import com.example.domain.repository.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    fun provideRepository(apiService: ApiService): PostRepository {
        return PostRepositoryImpl(apiService)
    }
}