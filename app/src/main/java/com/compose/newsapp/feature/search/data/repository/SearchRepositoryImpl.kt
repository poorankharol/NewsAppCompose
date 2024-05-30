package com.compose.newsapp.feature.search.data.repository

import com.compose.newsapp.feature.home.data.api.SearchService
import com.compose.newsapp.feature.home.domain.model.NewsTopicsResponse
import com.compose.newsapp.feature.search.domain.repository.SearchRepository
import com.compose.newsapp.utils.API_KEY
import com.compose.newsapp.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val searchService: SearchService) :
    SearchRepository {

    override suspend fun searchNews(q: String): Flow<NetworkResult<NewsTopicsResponse>> {
        return flow {
            emit(NetworkResult.Loading(true))
            val response = try {
                searchService.searchNews(apiKey = API_KEY, q = q)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(NetworkResult.Error(e.message.toString()))
                emit(NetworkResult.Loading(false))
                return@flow
            }
            //val filteredArticles = response.articles.filter { it.urlToImage != null }
            emit(NetworkResult.Success(response))
            emit(NetworkResult.Loading(false))
        }
    }
}