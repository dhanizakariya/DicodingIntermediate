package com.dicoding.story.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.story.api.ApiClient
import com.dicoding.story.data.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    var userLogin = MutableLiveData<LoginResponse>()

    fun getLoginResponse(): MutableLiveData<LoginResponse> {
        return userLogin
    }

    fun login(email: String, password: String) {
        ApiClient.apiInstances
            .userLogin(email, password)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        val user = response.body()
                        userLogin.postValue(response.body())
                        Log.e("token", user!!.loginResult.token)
                        Log.e("email", user.loginResult.name)
                    } else {
                        userLogin.postValue(response.body())
                        val fail = response.body()
                        Log.e("message", fail?.message.toString())
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("Login Failed", t.message.toString())
                }
            })

    }
}