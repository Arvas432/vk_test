package com.example.vk_video.di

import android.content.Context
import android.net.ConnectivityManager
import com.example.vk_video.data.network.NetworkClient
import com.example.vk_video.data.network.impl.RetrofitNetworkClient
import com.example.vk_video.domain.api.PexelsApiService
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    factory { Gson() }

    single<ConnectivityManager> {
        androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    single<NetworkClient> {
        RetrofitNetworkClient(
            get(),
            get()
        )
    }
    single { createPexelsApiService() }
}
fun createPexelsApiService(): PexelsApiService {
    return Retrofit.Builder()
        .baseUrl("https://api.pexels.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PexelsApiService::class.java)
}