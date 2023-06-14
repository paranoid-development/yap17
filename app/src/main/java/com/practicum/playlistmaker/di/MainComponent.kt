package com.practicum.playlistmaker.di

import com.practicum.playlistmaker.main.ui.activity.MainActivity
import dagger.Subcomponent

@Subcomponent
interface MainComponent {

  @Subcomponent.Factory
  interface Factory {
    fun create(): MainComponent
  }

  fun inject(activity: MainActivity)
}