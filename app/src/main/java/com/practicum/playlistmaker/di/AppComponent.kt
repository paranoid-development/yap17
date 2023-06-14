package com.practicum.playlistmaker.di

import android.content.Context
import com.practicum.playlistmaker.di.viewModel.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    AppSubcomponents::class,
    ViewModelModule::class,
  ]
)
interface AppComponent {

  @Component.Factory
  interface Factory {
    fun create(@BindsInstance context: Context): AppComponent
  }

  fun mainComponent(): MainComponent.Factory
}