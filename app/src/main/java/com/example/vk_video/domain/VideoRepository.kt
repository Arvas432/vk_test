package com.example.vk_video.domain

import com.example.vk_video.data.network.Resource
import com.example.vk_video.data.network.Result
import kotlinx.coroutines.flow.Flow

interface VideoRepository {
    suspend fun getPopularVideos(page: Int = 1, perPage: Int = 15) : Flow<Resource>
}