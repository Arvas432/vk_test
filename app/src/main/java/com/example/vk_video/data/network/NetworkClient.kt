package com.example.vk_video.data.network

interface NetworkClient {
    suspend fun doRequest(request: Any): ApiResponse
}