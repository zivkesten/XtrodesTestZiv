package com.zivkesten.xtrodestest.data.repository

import com.zivkesten.xtrodestest.data.api.GetApiService
import javax.inject.Inject

class RemoteRepository @Inject constructor(private val apiService: GetApiService) {

    suspend fun getContent(): String {
        return apiService.get()
    }
}
