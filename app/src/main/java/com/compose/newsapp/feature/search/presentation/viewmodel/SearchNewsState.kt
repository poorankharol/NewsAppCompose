package com.compose.newsapp.feature.search.presentation.viewmodel

import com.compose.newsapp.feature.home.domain.model.NewsTopicsResponse


data class SearchNewsState(
    val isLoading: Boolean = false,
    val searchData: NewsTopicsResponse? = null,
    val error: String? = null,
)
