package com.dicoding.story.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.story.api.ApiClient
import com.dicoding.story.data.GetStoryResponse
import com.dicoding.story.data.ListStoryItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private var dataStory = MutableLiveData<ArrayList<ListStoryItem>>()

    fun story(token: String) {
        ApiClient.apiInstances
            .getStories(token,0,5,20)
            .enqueue(object : Callback<GetStoryResponse> {
                override fun onResponse(
                    call: Call<GetStoryResponse>,
                    response: Response<GetStoryResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        dataStory.postValue(response.body()!!.listStory)

                    }
                }

                override fun onFailure(call: Call<GetStoryResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }

    fun getStory(): LiveData<ArrayList<ListStoryItem>> {
        return dataStory
    }
}



