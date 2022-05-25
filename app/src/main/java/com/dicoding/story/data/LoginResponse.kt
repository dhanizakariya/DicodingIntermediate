package com.dicoding.story.data

import com.google.gson.annotations.SerializedName

data class LoginResponse (
        @SerializedName("error")
        val error: Boolean,
        @SerializedName("message")
        val message: String,
        @SerializedName("loginResult")
        val loginResult: LoginResult
)

data class LoginResult(
        @SerializedName("userID")
        val userID: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("token")
        val token: String,
)