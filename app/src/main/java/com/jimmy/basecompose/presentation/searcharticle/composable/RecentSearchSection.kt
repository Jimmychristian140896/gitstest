@file:OptIn(ExperimentalLayoutApi::class)

package com.jimmy.basecompose.presentation.searcharticle.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jimmy.basecompose.presentation.searcharticle.SearchArticleAction
import com.jimmy.basecompose.presentation.searcharticle.SearchArticleState
import com.jimmy.basecompose.ui.theme.Black
import com.jimmy.basecompose.ui.theme.LightDivider


@Preview
@Composable
private fun RecentSearchSectionPreview() {
    RecentSearchSection(
        state = SearchArticleState(),
        onAction = {}
    )
}
@Composable
fun RecentSearchSection(
    state: SearchArticleState,
    onAction: (SearchArticleAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Recent Searches",
            fontSize = 14.sp,
            color = Black,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(16.dp))
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)

        ) {
            state.recentSearch.forEach {
                RecentSearchItem(
                    search = it,
                    onClick = {
                        onAction(SearchArticleAction.Search(it))
                        onAction(SearchArticleAction.SearchClicked)
                    }
                )
            }
        }


    }
}

@Composable
private fun RecentSearchItem(
    search: String,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .wrapContentWidth()
            .clip(RoundedCornerShape(50))
            .background(LightDivider)
            .clickable {
                onClick(search)
            }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = search,
            modifier = Modifier
                .size(16.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = search,
            fontSize = 12.sp,
            color = Black,
            maxLines = 1
        )
    }

}