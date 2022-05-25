package com.dicoding.story.data

import com.google.gson.annotations.SerializedName

data class StoryDetailData(
	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("photoUrl")
	val photoUrl: String,

	@field:SerializedName("CreatedAt")
	val createdAt: String,

	@field:SerializedName("lon")
	val lon: Double,

	@field:SerializedName("lat")
	val lat: Double
)



