package com.practicum.playlistmaker.main.ui.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.practicum.playlistmaker.media_library.ui.activity.MediaLibraryActivity
import com.practicum.playlistmaker.search.ui.activity.SearchActivity
import com.practicum.playlistmaker.settings.ui.activity.SettingsActivity
import javax.inject.Inject

class NavigationRouter @Inject constructor() {
    fun getSearch(activity: Activity) {
        val searchIntent = Intent(activity, SearchActivity::class.java)
        activity.startActivity(searchIntent)
    }

    fun getSettings(activity: Activity) {
        val settingsIntent = Intent(activity, SettingsActivity::class.java)
        activity.startActivity(settingsIntent)
    }

    fun getMediaLibrary(activity: Activity) {
        val mediaIntent = Intent(activity, MediaLibraryActivity::class.java)
        activity.startActivity(mediaIntent)
    }
}