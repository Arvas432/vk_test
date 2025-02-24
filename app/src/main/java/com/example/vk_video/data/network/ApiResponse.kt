package com.example.vk_video.data.network

import com.example.vk_video.domain.VideoApiModel

sealed class ApiResponse {
    data class VideoResponse(
        val resultCode: Int,
        val page: Int,
        val per_page: Int,
        val videos: List<VideoApiModel>
    ) : ApiResponse()

    data class ErrorResponse(
        val resultCode: Int
    ) : ApiResponse()
}