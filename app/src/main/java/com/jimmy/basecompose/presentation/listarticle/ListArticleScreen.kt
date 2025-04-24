package com.jimmy.basecompose.presentation.listarticle

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.jimmy.basecompose.R
import com.jimmy.basecompose.core.composable.ObserveAsEvents
import com.jimmy.basecompose.domain.model.ArticleType
import com.jimmy.basecompose.navigation.Route
import com.jimmy.basecompose.presentation.listarticle.composable.ArticleItem
import com.jimmy.basecompose.presentation.listarticle.composable.FilterBottomSheet
import com.jimmy.basecompose.presentation.listarticle.composable.SortBottomSheet
import com.jimmy.basecompose.ui.theme.Black
import com.jimmy.basecompose.ui.theme.White
import com.jimmy.basecompose.ui.theme.TextFieldHintColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListArticleScreenRoot(
    navHostController: NavHostController
) {
    val viewModel: ListArticleViewModel = koinViewModel<ListArticleViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.eventChannel) { event ->
        when (event) {
            is ListArticleEvent.OnArticleClick -> {
                navHostController.navigate(Route.Detail(event.article.type, event.article.id))
            }
            is ListArticleEvent.OnSearch -> {
                navHostController.navigate(Route.Seach(event.search))
            }
            is ListArticleEvent.NavigateBack -> {
                navHostController.navigateUp()
            }
            else -> {
            }
        }
    }
    LaunchedEffect(navHostController.currentBackStackEntry) {

        navHostController.currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<String>("search")
            ?.observeForever { search ->
                if(search!=null) {
                    viewModel.onAction(ListArticleAction.OnSearchChange(search))
                }
            }
    }
    ListArticleScreen(
        state = state,
        onAction = viewModel::onAction
    )
}


@Preview
@Composable
private fun Preview() {
    ListArticleScreen(
        state = ListArticleState(),
        onAction = {}
    )

}

@Composable
fun ListArticleScreen(
    state: ListArticleState,
    onAction: (ListArticleAction) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = {
                    onAction(ListArticleAction.NavigateBack)
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
            Text(
                text = when (state.type) {
                    ArticleType.ARTICLE -> "Article"
                    ArticleType.BLOG -> "Blog"
                    ArticleType.REPORT -> "Report"
                },
                fontSize = 18.sp,
                color = Black,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

        }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(color = White)
                        .clickable {
                            onAction(ListArticleAction.NavigateToSearch)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        modifier = Modifier
                            .size(60.dp)
                            .padding(14.dp),
                        tint = TextFieldHintColor
                    )
                    Text(
                        text = state.search ?: "Search",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .weight(1f),
                        fontSize = 12.sp,
                        color = TextFieldHintColor
                    )

                }
                TextButton(
                    onClick = {
                        onAction(ListArticleAction.OnFilter)
                    }
                ) {
                    Text(
                        text = "Filter" + if (state.filter.size > 0) " (${state.filter.size})" else "",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                TextButton(
                    onClick = {
                        onAction(ListArticleAction.OnSort)
                    }
                ) {
                    Text(
                        text = "Sort" + if (state.sort != null) " (${state.sort.name})" else "",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            if (state.isLoadingArticle) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Loading...",
                        modifier = Modifier,
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),

                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.articles) {
                        ArticleItem(
                            article = it,
                            onClick = {
                                onAction(ListArticleAction.OnArticleClick(it))
                            }
                        )
                    }
                }
            }

        }
    }
    if(state.isShowFilterDialog) {
        FilterBottomSheet(
            sites = state.sites,
            selectedItem = state.filter,
            onApplySelected = {
                onAction(ListArticleAction.OnFilterSelected(it))
            },
            onDismiss = {
                onAction(ListArticleAction.OnHideFilterDialog)
            }
        )
    }
    if(state.isShowSortDialog) {
        SortBottomSheet(
            selectedSortBy = state.sort,
            onSortSelected = {
                onAction(ListArticleAction.OnSortSelected(it))
            },
            onDismiss = {
                onAction(ListArticleAction.OnHideSortDialog)
            }
        )
    }

}

