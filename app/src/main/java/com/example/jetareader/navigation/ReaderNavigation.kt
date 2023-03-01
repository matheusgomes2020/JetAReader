package com.example.jetareader.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetareader.screens.ReaderSplashScreen
import com.example.jetareader.screens.details.BookDetailsScreen
import com.example.jetareader.screens.home.Home
import com.example.jetareader.screens.login.ReaderLoginScreen
import com.example.jetareader.screens.search.BookSearchViewModel
import com.example.jetareader.screens.search.SearchScreen
import com.example.jetareader.screens.stats.ReaderStatsScreen

@Composable
fun ReaderNavigation() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name ) {

        composable( ReaderScreens.SplashScreen.name ) {

            ReaderSplashScreen( navController = navController )

        }

        composable( ReaderScreens.ReaderHomeScreen.name ) {

            Home( navController = navController )

        }

        composable( ReaderScreens.ReaderStatsScreen.name ) {

            ReaderStatsScreen( navController = navController )

        }

        composable( ReaderScreens.LoginScreen.name ) {

            ReaderLoginScreen( navController = navController )

        }

        composable( ReaderScreens.SearchScreen.name ) {

            val searchViewModel = hiltViewModel<BookSearchViewModel>()

            SearchScreen( navController = navController , viewModel = searchViewModel)

        }

        composable( ReaderScreens.DetailScreen.name ) {

            BookDetailsScreen( navController = navController, bookId = "" )

        }

    }

}