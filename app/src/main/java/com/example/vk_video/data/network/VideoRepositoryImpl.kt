package com.example.vk_video.data.network

import android.util.Log
import com.example.vk_video.data.network.impl.RetrofitNetworkClient
import com.example.vk_video.domain.VideoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.Dispatchers

class VideoRepositoryImpl(
    private val networkClient: NetworkClient
) : VideoRepository {

    override suspend fun getPopularVideos(
        page: Int,
        perPage: Int
    ): Flow<Resource> = flow {
        val request = PopularVideosRequest(page = page, perPage = perPage)

        val response = networkClient.doRequest(request)
        Log.i("VideoRepositoryImpl", "Response code: ${response}")

        val resource = when (response) {
            is ApiResponse.VideoResponse -> {
                when (response.resultCode) {
                    RetrofitNetworkClient.SUCCESS -> Resource.Success(
                        data = response.videos.map { it.toVideo() },
                        page = response.page,
                        pages = response.per_page,
                        total = response.videos.size
                    )
                    else -> Resource.Error(code = response.resultCode)
                }
            }
            is ApiResponse.ErrorResponse -> {
                Resource.Error(code = response.resultCode)
            }
        }
        emit(resource)
    }
        .catch { e ->
            emit(Resource.Error(code = RetrofitNetworkClient.BAD_REQUEST))
        }
        .flowOn(Dispatchers.IO)
}