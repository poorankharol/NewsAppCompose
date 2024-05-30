package com.compose.newsapp.feature.home.data.api

import com.compose.newsapp.feature.home.domain.model.NewsTopicsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {


    @GET("everything")
    suspend fun searchNews(
        @Query("apiKey") apiKey: String,
        @Query("q") q: String,
    ): NewsTopicsResponse
}