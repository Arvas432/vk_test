package com.example.vk_video.domain

sealed class VideoState {
    object Start : VideoState()
    object Loading : VideoState()
    data class Content(val videos: List<Video>, val canLoadMore: Boolean) : VideoState()
    data class Error(val errorType: ErrorType) : VideoState()
    object PageLoading : VideoState()
}