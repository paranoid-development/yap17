package com.practicum.playlistmaker.main.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.practicum.playlistmaker.main.ui.model.NavigationViewState
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

  private val navigationViewStateLiveData = SingleLiveEvent<NavigationViewState>()
  fun observeNavigationViewState(): LiveData<NavigationViewState> = navigationViewStateLiveData

  fun onSearchView() {
    navigationViewStateLiveData.postValue(NavigationViewState.Search)
  }

  fun onMediaLibraryView() {
    navigationViewStateLiveData.postValue(NavigationViewState.MediaLibrary)
  }

  fun onSettingsView() {
    navigationViewStateLiveData.postValue(NavigationViewState.Settings)
  }
}