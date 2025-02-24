package com.example.vk_video.domain.api

import com.example.vk_video.data.network.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PexelsApiService {
    @GET("videos/popular")
    suspend fun getPopularVideos(
        @Header("Authorization") apiKey: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): retrofit2.Response<VideoResponse>
}