package com.dicoding.story.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class GetStoryResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("listStory")
    val listStory: ArrayList<ListStoryItem>
)

data class ListStoryItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("photoUrl")
    val photoUrl: String,
    @SerializedName("CreatedAt")
    val createdAt: String,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("lat")
    val lat: Double
)


