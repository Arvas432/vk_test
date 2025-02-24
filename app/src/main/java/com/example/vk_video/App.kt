package com.example.vk_video

import android.app.Application
import com.example.vk_video.di.dataModule
import com.example.vk_video.di.repositoryModule
import com.example.vk_video.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(dataModule, repositoryModule, viewModelModule)
        }
    }
}