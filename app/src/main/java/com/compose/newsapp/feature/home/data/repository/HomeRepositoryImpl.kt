package com.compose.newsapp.feature.home.data.repository

import com.compose.newsapp.feature.home.data.api.HomeService
import com.compose.newsapp.feature.home.domain.model.Article
import com.compose.newsapp.feature.home.domain.repository.HomeRepository
import com.compose.newsapp.utils.API_KEY
import com.compose.newsapp.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val homeService: HomeService) :
    HomeRepository {

    override suspend fun getLatestNews(country: String): Flow<NetworkResult<List<Article>>> {
        return flow {
            emit(NetworkResult.Loading(true))
            val response = try {
                homeService.getLatestNews(apiKey = API_KEY, country = country)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(NetworkResult.Error(e.message.toString()))
                emit(NetworkResult.Loading(false))
                return@flow
            }
            val filteredArticles = response.articles.filter { it.urlToImage != null }
            emit(NetworkResult.Success(filteredArticles))
            emit(NetworkResult.Loading(false))
        }

    }


    override suspend fun getNewsTopic(topic: String): Flow<NetworkResult<List<Article>>> {
        return flow {
            emit(NetworkResult.Loading(true))
            val response = try {
                homeService.getNewsTopics(apiKey = API_KEY, q = topic)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(NetworkResult.Error(e.message.toString()))
                emit(NetworkResult.Loading(false))
                return@flow
            }
            val filteredArticles = response.articles.filter { it.urlToImage != null }
            emit(NetworkResult.Success(filteredArticles))
            emit(NetworkResult.Loading(false))
        }
    }
}