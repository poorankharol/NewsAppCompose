package com.compose.newsapp.common

enum class Screens(val route: String) {
    HOME("home"),
    SEARCH("search"),
    DETAIL("detail/{article}"),
}