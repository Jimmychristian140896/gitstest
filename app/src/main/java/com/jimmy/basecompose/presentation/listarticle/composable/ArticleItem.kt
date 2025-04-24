package com.jimmy.basecompose.presentation.listarticle.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jimmy.basecompose.R
import com.jimmy.basecompose.core.util.formatUtcToLocalDateTime
import com.jimmy.basecompose.domain.model.Article
import com.jimmy.basecompose.domain.model.ArticleType

@Composable
fun ArticleItem(
    article: Article,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                onClick()
            }
    ) {
        AsyncImage(
            model = article.imageUrl,
            contentDescription = article.title,
            placeholder = painterResource(R.drawable.ic_image_placeholder),
            error = painterResource(R.drawable.ic_image_placeholder),
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = article.title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
        )
        Text(
            text = article.publishedAt.formatUtcToLocalDateTime(),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
        )
        if (article.type == ArticleType.ARTICLE || article.type == ArticleType.BLOG) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Launches",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier
                    )
                    article.launches?.forEach {
                        Text(
                            text = it.provider,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                        )
                    }
                }
                Spacer(Modifier.width(16.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Events",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier
                    )
                    article.events?.forEach {
                        Text(
                            text = it.provider,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}