package com.jimmy.basecompose.presentation.searcharticle

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.jimmy.basecompose.core.data.onSuccess
import com.jimmy.basecompose.domain.repository.RecentSearchRepository
import com.jimmy.basecompose.navigation.Route
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchArticleViewModel(
    private val recentSearchRepository: RecentSearchRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val routeSearch = savedStateHandle.toRoute<Route.SearchArticle>()
    val search = routeSearch.search

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(SearchArticleState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                initState()
                getRecentSearches()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = SearchArticleState()
        )

    private val _eventChannel = Channel<SearchArticleEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    private fun initState() {
        viewModelScope.launch {
            _state.update { it.copy(
                search = search
            ) }
        }
    }

    private fun getRecentSearches() {
        viewModelScope.launch {
            recentSearchRepository.getRecentSearches()
                .onSuccess { recentSearches ->
                    _state.update {
                        it.copy(
                            recentSearch = recentSearches
                        )
                    }
                }
        }
    }

    fun onAction(action: SearchArticleAction) {
        when (action) {
            SearchArticleAction.GoBack -> {
                viewModelScope.launch {
                    _eventChannel.trySend(SearchArticleEvent.GoBack)
                }
            }

            is SearchArticleAction.Search -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        search = action.search
                    ) }
                }
            }


            SearchArticleAction.SearchClicked -> {
                viewModelScope.launch {
                    recentSearchRepository.addRecentSearch(_state.value.search)
                    _eventChannel.trySend(SearchArticleEvent.OnSearch(_state.value.search))

                }
            }
            else -> TODO("Handle actions")
        }
    }

}