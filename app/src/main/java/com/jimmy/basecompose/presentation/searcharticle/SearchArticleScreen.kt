package com.jimmy.basecompose.presentation.searcharticle

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.jimmy.basecompose.core.composable.ObserveAsEvents
import com.jimmy.basecompose.presentation.searcharticle.composable.RecentSearchSection
import com.jimmy.basecompose.ui.theme.Black
import com.jimmy.basecompose.ui.theme.LightDivider
import com.jimmy.basecompose.ui.theme.Transparent
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchArticleScreenRoot(
    navHostController: NavHostController
) {
    val viewModel: SearchArticleViewModel = koinViewModel<SearchArticleViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.eventChannel) { event ->
        when (event) {
            SearchArticleEvent.GoBack -> {
                navHostController.navigateUp()
            }
            is SearchArticleEvent.OnSearch -> {
                navHostController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(
                        "search", state.search
                    )
                navHostController.popBackStack()
            }
            else -> {
            }
        }
    }
    SearchArticleScreen(
        state = state,
        onAction = viewModel::onAction
    )
}


@Preview
@Composable
private fun Preview() {
    SearchArticleScreen(
        state = SearchArticleState(),
        onAction = {}
    )

}

@Composable
fun SearchArticleScreen(
    state: SearchArticleState,
    onAction: (SearchArticleAction) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "Back",
                tint = Black,
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        onAction(SearchArticleAction.GoBack)
                    }
            )


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(LightDivider) // Light gray background
            ) {
                OutlinedTextField(
                    value = state.search,
                    onValueChange = {
                        onAction(SearchArticleAction.Search(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    },
                    trailingIcon = {
                        if (state.search.isNotEmpty()) {
                            IconButton(onClick = {
                                onAction(SearchArticleAction.Search(""))
                            }) {
                                Icon(Icons.Filled.Close, contentDescription = "Clear")
                            }
                        }
                    },
                    textStyle = TextStyle(
                        fontSize = 12.sp,

                        ),
                    placeholder = { Text("Search", fontSize = 12.sp) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Transparent,
                        unfocusedContainerColor = Transparent,
                        disabledContainerColor = Transparent,
                        focusedBorderColor = Transparent,
                        unfocusedBorderColor = Transparent,
                        disabledBorderColor = Transparent,
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text).copy(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            onAction(SearchArticleAction.SearchClicked)
                            keyboardController?.hide() // Hide keyboard
                        }
                    ),
                )
            }
        }
        RecentSearchSection(
            state = state,
            onAction = onAction
        )
    }
}
