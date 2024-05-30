package com.compose.newsapp.feature.home.domain.usecase

import com.compose.newsapp.feature.home.domain.model.Article
import com.compose.newsapp.feature.home.domain.repository.HomeRepository
import com.compose.newsapp.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LatestNewsUseCase @Inject constructor(private val repository: HomeRepository) {

    suspend operator fun invoke(country: String): Flow<NetworkResult<List<Article>>> = flow {
        repository.getLatestNews(country).collect { result ->
            emit(result)
        }
    }
}