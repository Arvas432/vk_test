package com.example.vk_video.domain

data class Video(
    val id: Int,
    val title: String,
    val duration: Int,
    val thumbnail: String,
    val videoUrl: String
)