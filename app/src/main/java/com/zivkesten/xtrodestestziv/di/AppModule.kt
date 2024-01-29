package com.zivkesten.xtrodestest.di

import com.zivkesten.xtrodestest.data.api.GetApiService
import com.zivkesten.xtrodestest.data.repository.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBlogRepository(apiService: GetApiService): RemoteRepository {
        return RemoteRepository(apiService)
    }
}
