package com.dicoding.story.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dicoding.story.GlideApp
import com.dicoding.story.data.ListStoryItem
import com.dicoding.story.databinding.StoryItemBinding

class StoryAdapter(private val list: ArrayList<ListStoryItem>) :
    RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {
    private val data = ArrayList<ListStoryItem>()
    private var onItemClickCallback: OnItemClickCallback? = null

    inner class StoryViewHolder(private val _binding: StoryItemBinding) :
        RecyclerView.ViewHolder(_binding.root) {
        fun bind(story: ListStoryItem) {
            _binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(story)
            }
            _binding.apply {
                GlideApp.with(itemView)
                    .load(story.photoUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(imgStory)
                tvName.text = story.name
                tvDescription.text = story.description
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ListStoryItem)
    }


    fun setList(story: ArrayList<ListStoryItem>) {
        data.clear()
        data.addAll(story)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        return StoryViewHolder(
            StoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}