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
import com.compose.newsapp.common.Screens
import com.compose.newsapp.feature.home.presentation.HomeScreen
import com.compose.newsapp.feature.search.presentation.screen.SearchScreen

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
            HomeScreen()
        }

        composable(Screens.SEARCH.route) {
            SearchScreen()
        }
    }
}