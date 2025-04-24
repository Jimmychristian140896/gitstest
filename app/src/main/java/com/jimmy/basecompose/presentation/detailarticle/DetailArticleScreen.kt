package com.jimmy.basecompose.presentation.detailarticle

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.jimmy.basecompose.core.composable.ObserveAsEvents
import com.jimmy.basecompose.core.util.formatUtcToLocalDateTime
import com.jimmy.basecompose.core.util.getFirstSentence
import org.koin.androidx.compose.koinViewModel
import androidx.compose.ui.res.painterResource
import com.jimmy.basecompose.R
import com.jimmy.basecompose.ui.theme.Black
import com.jimmy.basecompose.ui.theme.White

@Composable
fun DetailArticleScreenRoot(
    navHostController: NavHostController
) {
    val viewModel: DetailArticleViewModel = koinViewModel<DetailArticleViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.eventChannel) { event ->
        when (event) {
            DetailArticleEvent.NavigateBack -> {
                navHostController.navigateUp()
            }

            else -> {
            }
        }
    }
    DetailArticleScreen(
        state = state,
        onAction = viewModel::onAction
    )
}


@Preview
@Composable
private fun Preview() {
    DetailArticleScreen(
        state = DetailArticleState(),
        onAction = {}
    )

}

@Composable
fun DetailArticleScreen(
    state: DetailArticleState,
    onAction: (DetailArticleAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            state.article?.let {
                AsyncImage(
                    model = state.article.imageUrl,
                    placeholder = painterResource(R.drawable.ic_image_placeholder),
                    error = painterResource(R.drawable.ic_image_placeholder),
                    contentDescription = state.article.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = state.article.title,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = state.article.publishedAt.formatUtcToLocalDateTime(),
                        style = MaterialTheme.typography.labelMedium
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = state.article.summary.getFirstSentence(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

        }

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        Box(
            modifier = Modifier
                .padding(16.dp)
                .clip(CircleShape)
                .background(White)
                .clickable {
                    onAction(DetailArticleAction.NavigateBack)
                }
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "Back",
                tint = Black,
                modifier = Modifier
                    .size(32.dp)
            )
        }
    }

}
