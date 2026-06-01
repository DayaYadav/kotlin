package com.example.data.di

import com.example.core.util.CommonConstants.BASE_URL_POST
import com.example.data.network.ApiService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    fun provideokHttplogginInterceptor(): okhttp3.logging.HttpLoggingInterceptor {
        val logging = okhttp3.logging.HttpLoggingInterceptor()
        logging.setLevel(okhttp3.logging.HttpLoggingInterceptor.Level.BODY)
        return logging
    }

    @Provides
    fun provideOKHttpClient(): okhttp3.OkHttpClient {
        return okhttp3.OkHttpClient.Builder()
            .addInterceptor ( provideokHttplogginInterceptor() )
            .build()
    }
    @Provides
    fun provideRetrofit(): Retrofit
    {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_POST)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(provideOKHttpClient())
            .build()
    }


    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService
    {
        return retrofit.create(ApiService::class.java)
    }
}