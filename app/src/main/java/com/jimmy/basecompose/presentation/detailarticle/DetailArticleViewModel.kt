package com.jimmy.basecompose.presentation.detailarticle

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.jimmy.basecompose.core.data.asUiText
import com.jimmy.basecompose.core.data.onFailure
import com.jimmy.basecompose.core.data.onSuccess
import com.jimmy.basecompose.domain.model.ArticleType
import com.jimmy.basecompose.domain.repository.ArticleRepository
import com.jimmy.basecompose.domain.repository.BlogRepository
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

class DetailArticleViewModel(

    private val articleRepository: ArticleRepository,
    private val blogRepository: BlogRepository,
    private val reportRepository: ReportRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val routeDetailArticle = savedStateHandle.toRoute<Route.DetailArticle>()
    val id = routeDetailArticle.id
    val type = routeDetailArticle.type

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(DetailArticleState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                initState()
                getDetail()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = DetailArticleState()
        )

    private val _eventChannel = Channel<DetailArticleEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun onAction(action: DetailArticleAction) {
        when (action) {
            DetailArticleAction.NavigateBack -> {
                viewModelScope.launch {
                    _eventChannel.send(DetailArticleEvent.NavigateBack)
                }
            }

            else -> TODO("Handle actions")
        }
    }

    private fun initState() {
        _state.update {
            it.copy(
                id = id,
                type = type
            )
        }
    }

    private fun getDetail() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            when (type) {
                ArticleType.ARTICLE -> articleRepository.getArticleById(id)
                ArticleType.BLOG -> blogRepository.getBlogById(id)
                ArticleType.REPORT -> reportRepository.getReportById(id)
            }.onSuccess { article ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        article = article
                    )
                }
            }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = error.asUiText()
                        )
                    }
                }

        }
    }

}