package com.jimmy.basecompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.jimmy.basecompose.MainViewModel
import com.jimmy.basecompose.core.composable.WithSessionCheck
import com.jimmy.basecompose.domain.model.ArticleType
import com.jimmy.basecompose.presentation.detailarticle.DetailArticleScreenRoot
import com.jimmy.basecompose.presentation.home.HomeScreenRoot
import com.jimmy.basecompose.presentation.listarticle.ListArticleScreenRoot
import com.jimmy.basecompose.presentation.listarticle.ListArticleViewModel
import com.jimmy.basecompose.presentation.login.LoginScreenRoot
import com.jimmy.basecompose.presentation.register.RegisterScreenRoot
import com.jimmy.basecompose.presentation.searcharticle.SearchArticleScreenRoot
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(
    navHostController: NavHostController = rememberNavController(),
    isLogin: Boolean,
    modifier: Modifier = Modifier) {
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
        composable<Route.List> {
            WithSessionCheck(
                navHostController
            ) {
                ListArticleScreenRoot(
                    navHostController
                )
            }
        }
        composable<Route.Detail> {
            WithSessionCheck(
                navHostController
            ) {
                DetailArticleScreenRoot(
                    navHostController
                )
            }
        }
        composable<Route.Seach> {
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