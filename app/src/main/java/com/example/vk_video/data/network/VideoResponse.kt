package com.example.vk_video.data.network

import com.example.vk_video.domain.VideoApiModel

data class VideoResponse(
    val page: Int,
    val per_page: Int,
    val videos: List<VideoApiModel>
)