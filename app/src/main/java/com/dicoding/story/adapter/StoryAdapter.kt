package com.dicoding.story.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dicoding.story.data.StoryDetailData
import com.dicoding.story.data.StoryResponse
import com.dicoding.story.databinding.StoryItemBinding

class StoryAdapter() : RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {
    private val data = ArrayList<StoryDetailData>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: StoryDetailData)
    }


    inner class StoryViewHolder(private val _binding: StoryItemBinding) :
        RecyclerView.ViewHolder(_binding.root){
            fun bind(story: StoryDetailData){
                _binding.apply {
                    Glide.with(itemView)
                        .load(story.photoUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(imgStory)
                    tvName.text = story.name
                    tvDescription.text = story.description

                    Log.e("img_Url",imgStory.toString())
                    Log.e("tv_name",tvName.toString())
                }
            }
        }

    fun setList(story: StoryDetailData) {
        data.clear()
        data.addAll(listOf(story))
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = StoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}