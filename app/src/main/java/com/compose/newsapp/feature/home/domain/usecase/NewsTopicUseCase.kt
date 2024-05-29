package com.compose.newsapp.feature.home.domain.usecase

import com.compose.newsapp.feature.home.domain.model.Article
import com.compose.newsapp.feature.home.domain.repository.HomeRepository
import com.compose.newsapp.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsTopicUseCase @Inject constructor(private val repository: HomeRepository) {

    operator fun invoke(topic: String): Flow<NetworkResult<List<Article>>> = flow {
        repository.getNewsTopic(topic).collect { result ->
            emit(result)
        }
    }
}