package com.compose.newsapp.feature.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.newsapp.feature.search.domain.usecase.SearchNewsUseCase
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
class SearchViewModel @Inject constructor(private val searchNewsUseCase: SearchNewsUseCase) :
    ViewModel() {
    private val _searchNewsState = MutableStateFlow(SearchNewsState())
    val searchNewsState = _searchNewsState.asStateFlow()

    fun searchNews(topic: String) {
        viewModelScope.launch {
            searchNewsUseCase(topic)
                .flowOn(Dispatchers.IO)
                .onStart {
                    _searchNewsState.value = _searchNewsState.value.copy(isLoading = true)
                }
                .collect { result ->
                    when (result) {
                        is NetworkResult.Loading -> {
                            _searchNewsState.value =
                                _searchNewsState.value.copy(isLoading = result.isLoading)
                        }

                        is NetworkResult.Success -> {
                            _searchNewsState.value = _searchNewsState.value.copy(
                                searchData = result.data,
                                isLoading = false
                            )
                        }

                        is NetworkResult.Error -> {
                            _searchNewsState.value = _searchNewsState.value.copy(
                                error = result.message,
                                isLoading = false
                            )
                        }
                    }
                }
        }
    }
}