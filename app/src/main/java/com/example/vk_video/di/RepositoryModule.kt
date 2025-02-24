package com.example.vk_video.di

import com.example.vk_video.data.network.VideoRepositoryImpl
import com.example.vk_video.domain.VideoRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<VideoRepository> {
        VideoRepositoryImpl(
            get()
        )
    }
}
