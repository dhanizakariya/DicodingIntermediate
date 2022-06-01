package com.dicoding.story.api

import com.dicoding.story.BuildConfig
import com.dicoding.story.data.UserData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {
    private const val BASE_URL = "https://story-api.dicoding.dev/v1/"
    private lateinit var userData: UserData

    private val loggingInterceptor =
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

    var client: OkHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiInstances = retrofit.create(ApiServices::class.java)


}
