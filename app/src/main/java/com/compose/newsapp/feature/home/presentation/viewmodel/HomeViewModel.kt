package com.compose.newsapp.feature.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.newsapp.feature.home.domain.usecase.LatestNewsUseCase
import com.compose.newsapp.feature.home.domain.usecase.NewsTopicUseCase
import com.compose.newsapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val latestNewsUseCase: LatestNewsUseCase,
    private val newsTopicUseCase: NewsTopicUseCase,
) :
    ViewModel() {

    private val _latestNewsState = MutableStateFlow(LatestNewsState())
    val latestNewsState = _latestNewsState.asStateFlow()

    private val _newsTopicState = MutableStateFlow(NewsTopicState())
    val newsTopicState = _newsTopicState.asStateFlow()


    init {
        getLatestNews()
        getNewsTopic("Healthy")
    }

    private fun getLatestNews() {
        viewModelScope.launch {
            latestNewsUseCase("in")
                .flowOn(Dispatchers.IO)
                .onStart {
                    _latestNewsState.value = _latestNewsState.value.copy(isLoading = true)
                }
                .collect { result ->
                    when (result) {
                        is NetworkResult.Loading -> {
                            _latestNewsState.value =
                                _latestNewsState.value.copy(isLoading = result.isLoading)
                        }

                        is NetworkResult.Success -> {
                            _latestNewsState.value = _latestNewsState.value.copy(
                                articles = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }

                        is NetworkResult.Error -> {
                            _latestNewsState.value = _latestNewsState.value.copy(
                                error = result.message,
                                isLoading = false
                            )
                        }
                    }
                }
        }
    }

    fun getNewsTopic(topic: String) {
        viewModelScope.launch {
            newsTopicUseCase(topic)
                .flowOn(Dispatchers.IO)
                .onStart {
                    _newsTopicState.value = _newsTopicState.value.copy(isLoading = true)
                }
                .collect { result ->
                    when (result) {
                        is NetworkResult.Loading -> {
                            _newsTopicState.value =
                                _newsTopicState.value.copy(isLoading = result.isLoading)
                        }

                        is NetworkResult.Success -> {
                            _newsTopicState.value = _newsTopicState.value.copy(
                                articles = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }

                        is NetworkResult.Error -> {
                            _newsTopicState.value = _newsTopicState.value.copy(
                                error = result.message,
                                isLoading = false
                            )
                        }
                    }
                }
        }
    }
}