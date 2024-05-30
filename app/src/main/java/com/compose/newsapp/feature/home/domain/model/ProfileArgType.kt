package com.compose.newsapp.feature.home.domain.model

import com.compose.newsapp.utils.JsonNavType
import com.google.gson.Gson

class ArticleArgType : JsonNavType<Article>() {
    override fun fromJsonParse(value: String): Article = Gson().fromJson(value, Article::class.java)
    override fun Article.getJsonParse(): String {
        return Gson().toJson(this)
    }
}