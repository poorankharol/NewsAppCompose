package com.compose.newsapp.feature.home.domain.model

import com.google.gson.annotations.SerializedName

data class NewsTopicsResponse(

    @field:SerializedName("totalResults")
    val totalResults: Int? = null,

    @field:SerializedName("articles")
    val articles: List<Article> = emptyList(),

    @field:SerializedName("status")
    val status: String? = null,
)
