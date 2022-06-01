package com.dicoding.story.preference

import android.content.Context
import android.content.SharedPreferences

class Preference(context: Context) {
    private val prefName = "StoryAppDb"
    private val sharedPref: SharedPreferences
    val editor: SharedPreferences.Editor

    init {
        sharedPref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        editor = sharedPref.edit()
    }

    fun put(key: String, value: String) {
        editor.putString(key, value)
            .apply()
    }

    fun getString(key: String): String? {
        return sharedPref.getString(key, null)
    }

    fun put(key: String, value: Boolean) {
        editor.putBoolean(key, value)
            .apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPref.getBoolean(key, false)
    }

    fun clear() {
        editor.clear()
            .apply()
    }

    companion object {
        const val PREF_TOKEN = "TOKEN"
        const val STATE_KEY = "STATE KEY"
    }
}