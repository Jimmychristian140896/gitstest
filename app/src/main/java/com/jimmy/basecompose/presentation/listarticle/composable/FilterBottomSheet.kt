@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.jimmy.basecompose.presentation.listarticle.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jimmy.basecompose.ui.theme.Black
import com.jimmy.basecompose.ui.theme.Gray
import com.jimmy.basecompose.ui.theme.Green
import com.jimmy.basecompose.ui.theme.LightBackground
import com.jimmy.basecompose.ui.theme.White

class FilterBottomSheet {
}

@Preview
@Composable
private fun FilterBottomSheetPreview() {
    FilterBottomSheet(
        sites = emptyList(),
        selectedItem = emptyList(),
        onDismiss = {},
        onApplySelected = {}
    )
}

@Composable
fun FilterBottomSheet(
    sites: List<String>,
    selectedItem: List<String>,
    onDismiss: () -> Unit,
    onApplySelected: (List<String>) -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, // Skip the middle

    )
    var selectedItemLocal by remember { mutableStateOf<List<String>>(selectedItem) }
    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        modifier = modifier,
        sheetState = sheetState

    ) {
        Column(
            modifier = Modifier

        ) {
            Text(
                text = "Site",
                fontSize = 18.sp,
                color = Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(16.dp)
            )
            Spacer(Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                items(
                    sites
                ) { item ->
                    SiteItem(
                        item,
                        isSelected = selectedItemLocal.contains(item),
                        onClick = {
                            selectedItemLocal = if (selectedItemLocal.contains(item)) {
                                selectedItemLocal.filter { it != item }
                            } else {
                                selectedItemLocal + item
                            }
                        }
                    )
                }
            }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (selectedItemLocal.isNotEmpty()) {
                Button(
                    onClick = {
                        selectedItemLocal = emptyList()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightBackground
                    )
                ) {
                    Text(
                        "Reset",
                        color = Black
                    )
                }
            }
            Button(
                onClick = {
                    onApplySelected(selectedItemLocal)
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green
                )
            ) {
                Text(
                    "Apply",
                    color = White
                )
            }
        }

        }
    }
}

@Composable
fun SiteItem(
    site: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {


        Text(
            text = site,
            fontSize = 12.sp,
            color = Gray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Checkbox(
            checked = isSelected,
            onCheckedChange = {
                onClick()
            }
        )
    }
}