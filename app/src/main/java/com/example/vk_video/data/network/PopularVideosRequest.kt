package com.example.vk_video.data.network

data class PopularVideosRequest(
    val page: Int = 1,
    val perPage: Int = 15
)
