package com.zivkesten.xtrodestest.data.api

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitService {

    private val logging: HttpLoggingInterceptor
        get() = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

    private val client: OkHttpClient
        get() = OkHttpClient.Builder().addInterceptor(logging).build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://base_url/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .client(client)
        .build()
}
