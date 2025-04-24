package com.jimmy.basecompose.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.jimmy.basecompose.core.composable.ObserveAsEvents
import com.jimmy.basecompose.domain.model.ArticleType
import com.jimmy.basecompose.navigation.Route
import com.jimmy.basecompose.presentation.home.composable.ArticleSection
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreenRoot(
    navHostController: NavHostController
) {
    val viewModel: HomeViewModel = koinViewModel<HomeViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.eventChannel) { event ->
        when (event) {
            is HomeEvent.OnArticleClick -> {
                navHostController.navigate(Route.Detail(event.article.type, event.article.id))
            }
            HomeEvent.OnSeeMoreArticleClick -> {
                navHostController.navigate(Route.List(ArticleType.ARTICLE))

            }
            HomeEvent.OnSeeMoreBlogClick -> {
                navHostController.navigate(Route.List(ArticleType.BLOG))

            }
            HomeEvent.OnSeeMoreReportClick -> {
                navHostController.navigate(Route.List(ArticleType.REPORT))

            }
            else -> {
            }
        }
    }
    HomeScreen(
        state = state,
        onAction = viewModel::onAction
    )
}


@Preview
@Composable
private fun Preview() {
    HomeScreen(
        state = HomeState(),
        onAction = {}
    )

}

@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = state.greeting,
            modifier = Modifier
                .padding(top = 32.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Text(
            text = state.user,
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        ArticleSection(
            title = "Article",
            articles = state.articles,
            isLoading = state.isLoadingArticle,
            onArticleClick = {
                onAction(HomeAction.OnArticleClick(it))
            },
            onSeeMoreClick = {
                onAction(HomeAction.OnSeeMoreArticleClick)
            }
        )
        ArticleSection(
            title = "Blog",
            articles = state.blogs,
            isLoading = state.isLoadingBlog,
            onArticleClick = {
                onAction(HomeAction.OnArticleClick(it))
            },
            onSeeMoreClick = {
                onAction(HomeAction.OnSeeMoreBlogClick)
            }
        )
        ArticleSection(
            title = "Report",
            articles = state.reports,
            isLoading = state.isLoadingReport,
            onArticleClick = {
                onAction(HomeAction.OnArticleClick(it))
            },
            onSeeMoreClick = {
                onAction(HomeAction.OnSeeMoreReportClick)
            }
        )

    }
}
