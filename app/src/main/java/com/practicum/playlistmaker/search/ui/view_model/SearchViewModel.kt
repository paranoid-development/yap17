package com.practicum.playlistmaker.search.ui.view_model

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmaker.player.domain.model.Track
import com.practicum.playlistmaker.search.creator.CreatorSearch
import com.practicum.playlistmaker.search.domain.api.SearchInteractor
import com.practicum.playlistmaker.search.ui.models.SearchStateInterface

class SearchViewModel(
    private val application: Application,
    private val searchInteractor: SearchInteractor,
): AndroidViewModel(application) {

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private val SEARCH_REQUEST_TOKEN = Any()
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
                SearchViewModel(
                    application = application,
                    searchInteractor = CreatorSearch.provideSearchInteractor(application),
                )
            }
        }
    }

    private val handler = Handler(Looper.getMainLooper())
    private var latestSearchText: String? = null
    private var isClickAllowed = true

    private val stateLiveData = MutableLiveData<SearchStateInterface>()
    fun observeState(): LiveData<SearchStateInterface> = stateLiveData

    private fun renderState(state: SearchStateInterface) {
        stateLiveData.postValue(state)
    }

    override fun onCleared() {
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
    }

    //Запускаем поиск, если пользователь 2 секунды не вводит текст
    fun onTextChanged(
        changedText: String,
        focus: Boolean) {

        if (latestSearchText == changedText || !focus || changedText.isNullOrEmpty()) return

        renderState(SearchStateInterface.changeTextSearch)

        this.latestSearchText = changedText

        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)

        val searchRunnable = Runnable { loadTracks(changedText) }

        val postTime = SystemClock.uptimeMillis() + SEARCH_DEBOUNCE_DELAY

        handler.postAtTime(
            searchRunnable,
            SEARCH_REQUEST_TOKEN,
            postTime,
        )
    }

    //Ограничение двойного нажатия на трек для открытия плеера
    fun clickDebounce(): Boolean {
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false

            val clickRunnable = Runnable { isClickAllowed = true }

            val postTime = SystemClock.uptimeMillis() + SEARCH_DEBOUNCE_DELAY

            handler.postAtTime(
                clickRunnable,
                SEARCH_REQUEST_TOKEN,
                postTime,
            )
        }
        return current
    }

    fun loadTracks(searchText: String) {
        if (searchText.isEmpty()) return
        renderState(SearchStateInterface.Loading)
        searchInteractor.loadTracks(
            searchText = searchText,
            onSuccess = { tracks -> renderState(SearchStateInterface.SearchTracks(tracks))
            },
            noData = { error -> renderState(SearchStateInterface.Error(error))
            },
            serverError = { error -> renderState(SearchStateInterface.Error(error))
            },
            noInternet = { error -> renderState(SearchStateInterface.Error(error))
            }
        )
    }

    fun visibleHistoryTrack() {
        val historyTracks = tracksHistoryFromJson()
        renderState(SearchStateInterface.HistoryTracks(historyTracks))
    }

    fun clickButtonClearHistory() {
        searchInteractor.clearHistory()
        visibleHistoryTrack()
    }

    fun clearSearchText() {
        visibleHistoryTrack()
    }

    private fun tracksHistoryFromJson(): List<Track>{
        return searchInteractor.tracksHistoryFromJson()
    }

    fun onTrackClick(track: Track, position: Int) {
        if (clickDebounce()){
            searchInteractor.addTrack(track, position)
        }
    }
}