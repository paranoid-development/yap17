package com.practicum.playlistmaker.di

import com.practicum.playlistmaker.player.domain.api.PlayerInteractor
import com.practicum.playlistmaker.player.domain.impl.PlayerInteractorImpl
import com.practicum.playlistmaker.search.domain.api.SearchInteractor
import com.practicum.playlistmaker.search.domain.impl.SearchInteractorImpl
import com.practicum.playlistmaker.settings.domain.SettingsInteractor
import com.practicum.playlistmaker.settings.domain.impl.SettingsInteractorImpl
import com.practicum.playlistmaker.sharing.domain.api.SharingInteractor
import com.practicum.playlistmaker.sharing.domain.impl.SharingInteractorImpl
import org.koin.dsl.module

val interactorModule = module {
    factory<PlayerInteractor> {
        PlayerInteractorImpl(trackPlayer = get())
    }

    single<SearchInteractor> {
        SearchInteractorImpl(
            sharedPreferencesSearchClient = get(),
            networkClient = get()
        )
    }

    single<SettingsInteractor> {
        SettingsInteractorImpl(settingsRepository = get())
    }

    single<SharingInteractor> { SharingInteractorImpl(externalNavigator = get()) }
}