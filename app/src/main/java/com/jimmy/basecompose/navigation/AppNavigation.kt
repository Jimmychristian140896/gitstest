package com.jimmy.basecompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jimmy.basecompose.core.composable.WithSessionCheck
import com.jimmy.basecompose.presentation.detailarticle.DetailArticleScreenRoot
import com.jimmy.basecompose.presentation.home.HomeScreenRoot
import com.jimmy.basecompose.presentation.listarticle.ListArticleScreenRoot
import com.jimmy.basecompose.presentation.login.LoginScreenRoot
import com.jimmy.basecompose.presentation.register.RegisterScreenRoot
import com.jimmy.basecompose.presentation.searcharticle.SearchArticleScreenRoot

@Composable
fun AppNavigation(
    isLogin: Boolean,
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController()
    ) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = if(isLogin) Route.Home else Route.Login
    ) {
        composable<Route.Login> {
            LoginScreenRoot(
                navHostController
            )
        }
        composable<Route.Register> {
            RegisterScreenRoot(
                navHostController
            )
        }
        composable<Route.Home> {
            WithSessionCheck(
                navHostController
            ) {
                HomeScreenRoot(
                    navHostController
                )
            }

        }
        composable<Route.ListArticle> {
            WithSessionCheck(
                navHostController
            ) {
                ListArticleScreenRoot(
                    navHostController
                )
            }
        }
        composable<Route.DetailArticle> {
            WithSessionCheck(
                navHostController
            ) {
                DetailArticleScreenRoot(
                    navHostController
                )
            }
        }
        composable<Route.SearchArticle> {
            WithSessionCheck(
                navHostController
            ) {
                SearchArticleScreenRoot(
                    navHostController
                )
            }
        }

    }
}