package com.example.vk_video.data.network.impl

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.vk_video.data.network.ApiResponse
import com.example.vk_video.data.network.NetworkClient
import com.example.vk_video.data.network.PopularVideosRequest
import com.example.vk_video.data.network.Response
import com.example.vk_video.domain.api.PexelsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.android.BuildConfig

class RetrofitNetworkClient(
    private val pexelsApiService: PexelsApiService,
    private val connectivityManager: ConnectivityManager
) : NetworkClient {

    companion object {
        const val SUCCESS = 200
        const val BAD_REQUEST = 400
        const val NOT_FOUND = 404
        const val NO_CONNECTION = -1
    }

    override suspend fun doRequest(request: Any): ApiResponse {
        if (!isConnected()) {
            return ApiResponse.ErrorResponse(resultCode = NO_CONNECTION)
        }

        return when (request) {
            is PopularVideosRequest -> getPopularVideosResponse(request)
            else -> ApiResponse.ErrorResponse(resultCode = BAD_REQUEST)
        }
    }

    private suspend fun getPopularVideosResponse(request: PopularVideosRequest): ApiResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = pexelsApiService.getPopularVideos(
                    apiKey = "YbT94I6G93A6Qdqn5H67uTRXyH3YEnv1nmAHmRvyQSMS32Xw6ady6KpB",
                    page = request.page,
                    perPage = request.perPage
                )

                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    ApiResponse.VideoResponse(
                        resultCode = RetrofitNetworkClient.SUCCESS,
                        page = body.page,
                        per_page = body.per_page,
                        videos = body.videos
                    )
                } else {
                    ApiResponse.ErrorResponse(resultCode = response.code())
                }
            } catch (e: Exception) {
                ApiResponse.ErrorResponse(resultCode = RetrofitNetworkClient.BAD_REQUEST)
            }
        }
    }

    private fun isConnected(): Boolean {
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities?.run {
            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } ?: false
    }

}