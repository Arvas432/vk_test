package com.example.vk_video.domain

data class VideoApiModel(
    val id: Int,
    val duration: Int,
    val url: String,
    val video_files: List<VideoFile>,
    val video_pictures: List<VideoPicture>
) {
    fun toVideo(): Video {
        return Video(
            id = id,
            title = url,
            duration = duration,
            thumbnail = video_pictures.firstOrNull()?.picture ?: "",
            videoUrl = video_files.firstOrNull { it.quality == "hd" }?.link
                ?: video_files.firstOrNull()?.link ?: ""
        )
    }
}