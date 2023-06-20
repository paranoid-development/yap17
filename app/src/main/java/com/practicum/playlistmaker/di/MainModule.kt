package com.practicum.playlistmaker.di

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.practicum.playlistmaker.search.ui.activity.HISTORY_TRACKS_SHARED_PREF
import dagger.Module
import dagger.Provides

@Module
class MainModule {

  @Provides
  fun provideSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences(
      HISTORY_TRACKS_SHARED_PREF,
      AppCompatActivity.MODE_PRIVATE
    )
  }
}