@file:OptIn(ExperimentalMaterial3Api::class)

package com.jimmy.basecompose.presentation.listarticle.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jimmy.basecompose.presentation.listarticle.SortType
import com.jimmy.basecompose.ui.theme.Black


@Preview
@Composable
private fun SortBottomSheetPreview() {
    SortBottomSheet(
        onDismiss = {},
        onSortSelected = {}
    )
}

@Composable
fun SortBottomSheet(
    selectedSortBy: SortType? = null,
    onDismiss: () -> Unit,
    onSortSelected: (SortType) -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Sort by",
                fontSize = 18.sp,
                color = Black,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(16.dp))
            SortType.entries.forEach { sortBy ->
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = sortBy.getDisplayName(),
                        fontSize = 12.sp,
                        color = Black,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )
                    RadioButton(
                        selected = selectedSortBy == sortBy,
                        onClick = {
                            onSortSelected(sortBy)
                        }
                    )
                }
            }
        }
    }
}