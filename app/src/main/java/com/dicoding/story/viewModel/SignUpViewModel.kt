package com.dicoding.story.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.story.api.ApiClient
import com.dicoding.story.data.DefaultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {

    var signupUser = MutableLiveData<DefaultResponse>()

    fun getRegisterResponse(): MutableLiveData<DefaultResponse> {
        return signupUser
    }

    fun register(name: String, email: String, password: String) {
        ApiClient.apiInstances
            .userRegister(name, email, password)
            .enqueue(object : Callback<DefaultResponse> {

                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>,
                ) {
                    if (response.isSuccessful) {
                        signupUser.postValue(response.body())
                        val user = response.body()
                        Log.e("message", user!!.message)
                    } else {
                        signupUser.postValue(response.body())
                        val fail = response.body()
                        Log.e("message", fail?.message.toString())
                    }
                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Log.e("Failure", t.message.toString())
                }
            })
    }
}