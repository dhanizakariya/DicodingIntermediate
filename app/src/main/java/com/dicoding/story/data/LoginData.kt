package com.dicoding.story.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginData {
    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null

}