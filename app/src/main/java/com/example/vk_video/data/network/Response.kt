package com.example.vk_video.data.network

data class Response(
    var resultCode: Int = NO_CONNECTION,
    var data: Any? = null
) {
    companion object {
        const val SUCCESS = 200
        const val BAD_REQUEST = 400
        const val NOT_FOUND = 404
        const val NO_CONNECTION = -1
    }
}