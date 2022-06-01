package com.dicoding.story.api

import com.dicoding.story.data.DefaultResponse
import com.dicoding.story.data.GetStoryResponse
import com.dicoding.story.data.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @FormUrlEncoded
    @POST("register")
    fun userRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<DefaultResponse>

    @FormUrlEncoded
    @POST("login")
    fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("stories")
    fun getStories(
        @Header("Authorization") authToken: String,
        @Query("location") location: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<GetStoryResponse>

    @Multipart
    @POST("stories")
    fun uploadStory(
        @Header("Authorization") authToken: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Call<DefaultResponse>


}