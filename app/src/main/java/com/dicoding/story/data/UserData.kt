package com.dicoding.story.data

import com.google.gson.annotations.SerializedName

data class UserData(
    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("token")
    val token: String
)