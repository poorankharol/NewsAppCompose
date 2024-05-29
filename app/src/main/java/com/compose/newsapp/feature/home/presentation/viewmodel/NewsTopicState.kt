package com.compose.newsapp.feature.home.presentation.viewmodel

import com.compose.newsapp.feature.home.domain.model.Article


data class NewsTopicState(
    val isLoading: Boolean = false,
    val articles: List<Article> = emptyList(),
    val error: String? = null,
)
