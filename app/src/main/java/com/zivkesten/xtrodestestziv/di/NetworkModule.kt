package com.zivkesten.xtrodestest.di

import com.zivkesten.xtrodestest.data.api.GetApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://5eca2e0038df960016511592.mockapi.io/ziv/") // Correct base URL
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    @Provides
    fun provideBlogApiService(retrofit: Retrofit): GetApiService {
        return retrofit.create(GetApiService::class.java)
    }
}
