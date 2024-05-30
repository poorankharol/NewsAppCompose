package com.compose.newsapp.feature.search.domain.usecase

import com.compose.newsapp.feature.home.domain.model.NewsTopicsResponse
import com.compose.newsapp.feature.search.domain.repository.SearchRepository
import com.compose.newsapp.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchNewsUseCase @Inject constructor(private val repository: SearchRepository) {

    operator fun invoke(topic: String): Flow<NetworkResult<NewsTopicsResponse>> = flow {
        repository.searchNews(topic).collect { result ->
            emit(result)
        }
    }
}