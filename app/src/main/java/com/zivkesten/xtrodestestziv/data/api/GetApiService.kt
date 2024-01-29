package com.zivkesten.xtrodestest.data.api

import retrofit2.http.GET

interface GetApiService {
    @GET("BigString/")
    suspend fun get(): String
}
