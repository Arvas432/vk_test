package com.example.vk_video.ui.theme.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vk_video.data.network.Resource
import com.example.vk_video.data.network.impl.RetrofitNetworkClient
import com.example.vk_video.domain.ErrorType
import com.example.vk_video.domain.Video
import com.example.vk_video.domain.VideoRepository
import com.example.vk_video.domain.VideoState
import com.example.vk_video.util.SingleEventLiveData
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VideoViewModel(
    private val videoRepository: VideoRepository
) : ViewModel() {

    private val _videoState = MutableStateFlow<VideoState>(VideoState.Start)
    val videoState: StateFlow<VideoState> get() = _videoState

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> get() = _isRefreshing

    fun loadVideos() {
        viewModelScope.launch {
            _isRefreshing.value = true
            videoRepository.getPopularVideos(page = 0, perPage = 15)
                .collect { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            _videoState.value = VideoState.Error(
                                errorType = when (resource.code) {
                                    RetrofitNetworkClient.NO_CONNECTION -> ErrorType.NO_CONNECTION
                                    else -> ErrorType.SERVER_ERROR
                                }
                            )
                        }
                        is Resource.Success -> {
                            _videoState.value = VideoState.Content(
                                videos = resource.data,
                                canLoadMore = resource.pages > 1
                            )
                        }
                    }
                    _isRefreshing.value = false
                }
        }
    }

    fun loadNextPage() {
        // Реализация загрузки следующей страницы
    }
}