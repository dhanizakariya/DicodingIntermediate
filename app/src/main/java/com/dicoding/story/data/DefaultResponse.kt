package com.dicoding.story.data

import com.google.gson.annotations.SerializedName

data class DefaultResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)
