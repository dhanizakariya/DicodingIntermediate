package com.dicoding.story.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dicoding.story.GlideApp
import com.dicoding.story.data.ListStoryItem
import com.dicoding.story.databinding.StoryItemBinding
import com.dicoding.story.ui.StoryDetailActivity

class StoryAdapter(private val data: ArrayList<ListStoryItem>) :
    RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    inner class StoryViewHolder(private val _binding: StoryItemBinding) :
        RecyclerView.ViewHolder(_binding.root) {
        fun bind(story: ListStoryItem) {
            _binding.apply {
                GlideApp.with(itemView)
                    .load(story.photoUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(imgStory)
                tvName.text = story.name
                tvDescription.text = story.description
            }
            _binding.root.setOnClickListener {
                val intent = Intent(itemView.context, StoryDetailActivity::class.java)
                intent.putExtra(StoryDetailActivity.EXTRA_NAME, story.name)
                intent.putExtra(StoryDetailActivity.EXTRA_DESCRIPTION, story.description)
                intent.putExtra(StoryDetailActivity.EXTRA_PHOTO, story.photoUrl)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(_binding.imgStory, "image"),
                        Pair(_binding.tvName, "name"),
                        Pair(_binding.tvDescription, "desc"),
                    )
                itemView.context.startActivity(intent, optionsCompat.toBundle())

            }

        }
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