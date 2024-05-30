package com.compose.newsapp.feature.search.domain.repository

import com.compose.newsapp.feature.home.domain.model.NewsTopicsResponse
import com.compose.newsapp.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun searchNews(q: String): Flow<NetworkResult<NewsTopicsResponse>>
}