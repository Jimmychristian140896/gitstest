package com.jimmy.basecompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.jimmy.basecompose.presentation.login.LoginScreenRoot

@Composable
fun AppNavigation(
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier) {

    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = Route.Auth
    ) {
        navigation<Route.Auth>(
            startDestination = Route.Login
        ) {
            composable<Route.Login> {
                LoginScreenRoot()
            }
        }
    }
}