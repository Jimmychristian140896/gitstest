package com.jimmy.basecompose.presentation.listarticle

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.jimmy.basecompose.core.data.asUiText
import com.jimmy.basecompose.core.data.onFailure
import com.jimmy.basecompose.core.data.onSuccess
import com.jimmy.basecompose.data.local.dao.ArticleDao
import com.jimmy.basecompose.domain.model.ArticleType
import com.jimmy.basecompose.domain.repository.ArticleRepository
import com.jimmy.basecompose.domain.repository.BlogRepository
import com.jimmy.basecompose.domain.repository.NewsSiteRepository
import com.jimmy.basecompose.domain.repository.ReportRepository
import com.jimmy.basecompose.navigation.Route
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListArticleViewModel(
    private val articleRepository: ArticleRepository,
    private val blogRepository: BlogRepository,
    private val reportRepository: ReportRepository,
    private val newsSiteRepository: NewsSiteRepository,
    private val articleDao: ArticleDao,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val routeList = savedStateHandle.toRoute<Route.List>()
    val type = routeList.type
    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(ListArticleState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                //articleDao.deleteArticlesByType(ArticleType.ARTICLE)

                initState()
                getLastLoadedArticles()
                getNewsSite()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ListArticleState()
        )

    private val _eventChannel = Channel<ListArticleEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun onAction(action: ListArticleAction) {
        when (action) {
            is ListArticleAction.OnArticleClick -> {
                viewModelScope.launch {
                    _eventChannel.send(ListArticleEvent.OnArticleClick(action.article))
                }
            }
            ListArticleAction.OnFilter -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        isShowFilterDialog = true
                    ) }
                }
            }
            is ListArticleAction.OnFilterSelected -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        filter = action.filter,
                        isShowFilterDialog = false
                    ) }
                    getList()
                }
            }
            ListArticleAction.OnHideFilterDialog -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        isShowFilterDialog = false
                    ) }
                }
            }
            ListArticleAction.OnHideSortDialog -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        isShowSortDialog = false
                    ) }
                }
            }
            ListArticleAction.OnSearch -> {
                viewModelScope.launch {
                    _eventChannel.send(ListArticleEvent.OnSearch(_state.value.search ?: ""))

                    /*_state.update { it.copy(
                        recentSearch = it.recentSearch + it.search
                    ) }
                    getList()*/
                }
            }
            is ListArticleAction.OnSearchChange -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        search = action.search
                    ) }
                    getList()
                }
            }
            ListArticleAction.OnSort -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        isShowSortDialog = true
                    ) }
                }
            }
            is ListArticleAction.OnSortSelected -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        sort = action.sortType,
                        isShowSortDialog = false
                    ) }
                    getList()
                }
            }

            ListArticleAction.NavigateToSearch -> {
                viewModelScope.launch {
                    _eventChannel.send(ListArticleEvent.OnSearch(_state.value.search ?: ""))
                }
            }

            ListArticleAction.NavigateBack -> {
                viewModelScope.launch {
                    _eventChannel.send(ListArticleEvent.NavigateBack)
                }
            }
            else -> TODO("Handle actions")
        }
    }

    private fun initState() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    type = type
                )
            }

        }

    }

    private fun getLastLoadedArticles() {
        viewModelScope.launch {
            _state.update { it.copy(
                isLoadingArticle = true
            ) }
            when (type) {
                ArticleType.ARTICLE -> articleRepository.getLastLoadedArticles()
                ArticleType.BLOG -> blogRepository.getLastLoadedBlogs()
                ArticleType.REPORT -> reportRepository.getLastLoadedReports()
            }.onSuccess { articles ->
                if(articles == null || articles.isEmpty()) {
                    getList()
                } else {
                    _state.update {
                        it.copy(
                            isLoadingArticle = false,
                            articles = articles
                        )
                    }
                }
            }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoadingArticle = false,
                            errorArticle = error.asUiText()
                        ) }
                }

        }
    }

    private fun getList() {
        viewModelScope.launch {
            _state.update { it.copy(
                isLoadingArticle = true
            ) }
            when (type) {
                ArticleType.ARTICLE -> articleRepository.getArticles(
                    saveToLocal = true,
                    search = state.value.search,
                    filter = state.value.filter,
                    sort = state.value.sort
                )
                ArticleType.BLOG -> blogRepository.getBlogs(
                    saveToLocal = true,
                    search = state.value.search,
                    filter = state.value.filter,
                    sort = state.value.sort)
                ArticleType.REPORT -> reportRepository.getReports(
                    saveToLocal = true,
                    search = state.value.search,
                    filter = state.value.filter,
                    sort = state.value.sort
                )
            }.onSuccess { articles ->
                _state.update {
                    it.copy(
                        isLoadingArticle = false,
                        articles = articles
                    )
                }
            }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoadingArticle = false,
                            errorArticle = error.asUiText()
                        ) }
                }

        }
    }
    private fun getNewsSite() {
        viewModelScope.launch {
            _state.update { it.copy(
                isLoading = true
            ) }
            newsSiteRepository.getNewsSite()
                .onSuccess { newsSite ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            sites = newsSite
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                        ) }
                }

        }
    }

}