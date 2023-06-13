package com.practicum.playlistmaker.di

import android.content.Context
import com.google.gson.Gson
import com.practicum.playlistmaker.search.data.network.NetworkClient
import com.practicum.playlistmaker.search.data.network.NetworkClientImpl
import com.practicum.playlistmaker.search.data.network.iTunesSearchAPI
import com.practicum.playlistmaker.search.data.sharedpreferences.SharedPreferencesSearchClient
import com.practicum.playlistmaker.search.data.sharedpreferences.SharedPreferencesSearchClientImpl
import com.practicum.playlistmaker.settings.data.sharedpreferences.SharedPrefSettingsClient
import com.practicum.playlistmaker.settings.data.sharedpreferences.SharedPrefSettingsClientImpl
import com.practicum.playlistmaker.sharing.data.ExternalNavigator
import com.practicum.playlistmaker.sharing.data.impl.ExternalNavigatorImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single<iTunesSearchAPI> {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(iTunesSearchAPI::class.java)
    }

    single<NetworkClient> {
        NetworkClientImpl(api = get())
    }

    factory { Gson() }

    single {
        androidContext().getSharedPreferences("tracks_shared_pref", Context.MODE_PRIVATE)
    }

    single<SharedPreferencesSearchClient> {
        SharedPreferencesSearchClientImpl(
            sharedPref = get(),
            gson = get()
        )
    }

    single<SharedPrefSettingsClient> {
        SharedPrefSettingsClientImpl(sharedPref = get())
    }

    single<ExternalNavigator> { ExternalNavigatorImpl(androidContext()) }
}