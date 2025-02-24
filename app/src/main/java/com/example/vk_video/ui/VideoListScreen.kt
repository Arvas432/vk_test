package com.example.vk_video.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.vk_video.domain.ErrorType
import com.example.vk_video.domain.Video
import com.example.vk_video.domain.VideoState
import com.example.vk_video.ui.theme.viewmodel.VideoViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.koin.androidx.compose.koinViewModel

@Composable
fun VideoListScreen(
    viewModel: VideoViewModel = koinViewModel(), paddingValues: PaddingValues
) {
    val videoState by viewModel.videoState.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)
    LaunchedEffect(Unit) {
        viewModel.loadVideos()
    }

    SwipeRefresh(
        modifier = Modifier.padding(paddingValues),
        state = swipeRefreshState,
        onRefresh = { viewModel.loadVideos() }
    ) {
        when (val state = videoState) {
            is VideoState.Start -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Потяните вниз для обновления")
                }
            }

            is VideoState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is VideoState.Content -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.videos) {item -> VideoItem(item) }

                    if (state.canLoadMore) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                            LaunchedEffect(Unit) {
                                viewModel.loadNextPage()
                            }
                        }
                    }
                }
            }

            is VideoState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = when (state.errorType) {
                                ErrorType.NO_CONNECTION -> "Нет подключения к интернету"
                                ErrorType.SERVER_ERROR -> "Ошибка сервера"
                                ErrorType.EMPTY -> "Данные не найдены"
                            },
                            style = MaterialTheme.typography.h6
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.loadVideos() }) {
                            Text("Повторить")
                        }
                    }
                }
            }

            is VideoState.PageLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun VideoItem(
    video: Video,
    onItemClick: (Video) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(video) }
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column {
            GlideImage(
                model = video.thumbnail,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            ) {
                it.centerCrop()
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = video.title,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Text(
                text = "Длительность: ${video.duration} сек",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}