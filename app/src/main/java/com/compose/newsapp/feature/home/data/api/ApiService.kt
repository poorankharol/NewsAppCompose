package com.compose.newsapp.feature.home.data.api

import com.compose.newsapp.feature.home.domain.model.LatestNewsResponse
import com.compose.newsapp.feature.home.domain.model.NewsTopicsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    suspend fun getLatestNews(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String,
    ): LatestNewsResponse

    @GET("everything")
    suspend fun getNewsTopics(
        @Query("apiKey") apiKey: String,
        @Query("q") q: String,
    ): NewsTopicsResponse
}