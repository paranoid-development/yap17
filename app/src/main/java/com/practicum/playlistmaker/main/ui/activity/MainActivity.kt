package com.practicum.playlistmaker.main.ui.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmaker.App
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.di.MainComponent
import com.practicum.playlistmaker.di.viewModel.ViewModelFactory
import com.practicum.playlistmaker.main.ui.model.NavigationViewState
import com.practicum.playlistmaker.main.ui.router.NavigationRouter
import com.practicum.playlistmaker.main.ui.view_model.MainViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
  //Переменные для работы с UI
  lateinit var buttonSearch: Button
  lateinit var buttonMedia: Button
  lateinit var buttonSettings: Button

  @Inject
  lateinit var viewModelFactory: ViewModelFactory
  private lateinit var mainViewModel: MainViewModel

  @Inject
  lateinit var router: NavigationRouter

  @Inject
  lateinit var sharedPreferences: SharedPreferences

  lateinit var mainComponent: MainComponent

  override fun onCreate(savedInstanceState: Bundle?) {
    mainComponent = (application as App).appComponent.mainComponent().create()
    mainComponent.inject(this)

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    initViews()

    setListeners()

    mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

    mainViewModel.observeNavigationViewState().observe(this) {
      navigation(it)
    }
  }

  private fun navigation(state: NavigationViewState) {
    when (state) {
      is NavigationViewState.Search -> router.getSearch(this)
      is NavigationViewState.Settings -> router.getSettings(this)
      is NavigationViewState.MediaLibrary -> router.getMediaLibrary(this)
    }
  }

  private fun initViews() {
    //Ссылки на кнопки на главном экране
    buttonSearch = findViewById(R.id.buttonSearch)
    buttonMedia = findViewById(R.id.buttonMedia)
    buttonSettings = findViewById(R.id.buttonSettings)
  }

  private fun setListeners() {
    buttonSearch.setOnClickListener() {
      mainViewModel.onSearchView()
    }

    buttonMedia.setOnClickListener() {
      mainViewModel.onMediaLibraryView()
    }

    buttonSettings.setOnClickListener() {
      mainViewModel.onSettingsView()
    }
  }
}