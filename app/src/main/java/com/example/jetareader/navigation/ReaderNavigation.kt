package com.example.jetareader.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetareader.screens.ReaderSplashScreen
import com.example.jetareader.screens.home.Home

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

    }

}