package com.compose.newsapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.compose.newsapp.common.Screens
import com.compose.newsapp.feature.details.presentation.screen.DetailsScreen
import com.compose.newsapp.feature.home.domain.model.Article
import com.compose.newsapp.feature.home.domain.model.ArticleArgType
import com.compose.newsapp.feature.home.presentation.HomeScreen
import com.compose.newsapp.feature.search.presentation.screen.SearchScreen
import com.google.gson.Gson

@Composable
fun AppScaffold() {
    val navController = rememberNavController()

    Scaffold {
        AppNavHost(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        )
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screens.HOME.route,
        modifier = modifier
    ) {
        composable(Screens.HOME.route) {
            HomeScreen(navController)
        }

        composable(Screens.SEARCH.route) {
            SearchScreen(onUpButtonClick = { navController.popBackStack() })
        }

        composable(
            Screens.DETAIL.route,
            arguments = listOf(navArgument("article") { type = ArticleArgType() })
        ) { navBackStack ->
            val article = navBackStack.arguments?.getString("article")
                ?.let { Gson().fromJson(it, Article::class.java) }
            DetailsScreen(article, onUpButtonClick = { navController.popBackStack() })
        }
    }
}