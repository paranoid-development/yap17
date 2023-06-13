package com.practicum.playlistmaker.main.ui.view_model

import SingleLiveEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmaker.main.ui.model.NavigationViewState

class MainViewModel() : ViewModel() {
    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MainViewModel()
            }
        }
    }

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