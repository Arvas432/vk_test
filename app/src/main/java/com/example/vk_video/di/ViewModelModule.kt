package com.example.vk_video.di

import com.example.vk_video.ui.theme.viewmodel.VideoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {VideoViewModel(get())}
}