package com.dicoding.story.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.story.R
import com.dicoding.story.adapter.StoryAdapter
import com.dicoding.story.data.StoryDetailData
import com.dicoding.story.databinding.ActivityMainBinding
import com.dicoding.story.preference.Preference
import com.dicoding.story.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var preference: Preference
    private lateinit var adapter: StoryAdapter
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preference = Preference(this)

        adapter = StoryAdapter()

        adapter.setOnItemClickCallback(object : StoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: StoryDetailData) {
                Intent(this@MainActivity, StoryDetailActivity::class.java).also {
                    it.putExtra(StoryDetailActivity.EXTRA_NAME, data.name)
                    it.putExtra(StoryDetailActivity.EXTRA_DESCRIPTION, data.description)
                    it.putExtra(StoryDetailActivity.EXTRA_PHOTO, data.photoUrl)
                    startActivity(it)
                }
            }
        })

        recycleViewConfig()
        startViewModel()
    }

    private fun startViewModel() {
        mainViewModel.getStory().observe(this) {
            if (it != null) {
                adapter.setList(it.listStory)
            }
        }

    }

    private fun recycleViewConfig() {
        binding.apply {
            rvStory.layoutManager = LinearLayoutManager(this@MainActivity)
            rvStory.setHasFixedSize(true)
            rvStory.adapter = adapter

            val token = preference.getString(Preference.PREF_TOKEN)
            getStory(token!!)
        }

        binding.addStory.setOnClickListener {
            startActivity(Intent(this, AddStoryActivity::class.java))
            finish()
        }
    }

    private fun getStory(token: String) {
        mainViewModel.story(token)
        Log.e("token", token)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                preference.clear()
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
                finish()
                true
            }
            else -> true
        }

    }
}