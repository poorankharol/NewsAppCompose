package com.compose.newsapp.feature.home.domain.model

import android.net.Uri
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class LatestNewsResponse(

    @field:SerializedName("totalResults")
    val totalResults: Int? = null,

    @field:SerializedName("articles")
    val articles: List<Article> = emptyList(),

    @field:SerializedName("status")
    val status: String? = null,
)

data class Article(

    @field:SerializedName("publishedAt")
    val publishedAt: String? = null,

    @field:SerializedName("author")
    val author: String? = null,

    @field:SerializedName("urlToImage")
    val urlToImage: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("source")
    val source: Source? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("content")
    val content: String? = null,
) {
    override fun toString(): String = Uri.encode(Gson().toJson(this))
}

data class Source(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: String = "-1",
)



