package com.compose.newsapp.feature.home.domain.repository

import com.compose.newsapp.feature.home.domain.model.Article
import com.compose.newsapp.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun getLatestNews(country: String): Flow<NetworkResult<List<Article>>>

    suspend fun getNewsTopic(topic: String): Flow<NetworkResult<List<Article>>>
}