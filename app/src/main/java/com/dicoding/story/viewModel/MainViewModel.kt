package com.dicoding.story.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.story.api.ApiClient
import com.dicoding.story.data.StoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    val listStory = MutableLiveData<StoryResponse>()

    fun story(token: String) {
        ApiClient.apiInstances
            .getStories(token)
            .enqueue(object : Callback<StoryResponse> {
                override fun onResponse(
                    call: Call<StoryResponse>,
                    response: Response<StoryResponse>
                ) {
                    if (response.isSuccessful) {
                        val story = response.body()
                        listStory.postValue(response.body())
                        Log.e("url", story!!.listStory.photoUrl)
                    }
                }

                override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                    Log.d("Login Failed", t.message.toString())
                }

            })
    }

    fun getStory(): MutableLiveData<StoryResponse> {
        return listStory
    }
}



