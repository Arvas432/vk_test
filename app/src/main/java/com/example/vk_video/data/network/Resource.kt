package com.example.vk_video.data.network

import com.example.vk_video.domain.Video

sealed class Resource {
    data class Success(val data: List<Video>, val page: Int, val pages: Int, val total: Int) : Resource()
    data class Error(val code: Int) : Resource()

}