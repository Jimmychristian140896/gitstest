package com.jimmy.basecompose.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jimmy.basecompose.core.data.asUiText
import com.jimmy.basecompose.core.data.onFailure
import com.jimmy.basecompose.core.data.onSuccess
import com.jimmy.basecompose.domain.repository.ArticleRepository
import com.jimmy.basecompose.domain.repository.AuthRepository
import com.jimmy.basecompose.domain.repository.BlogRepository
import com.jimmy.basecompose.domain.repository.ReportRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

class HomeViewModel(
    private val articleRepository: ArticleRepository,
    private val blogRepository: BlogRepository,
    private val reportRepository: ReportRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                getGreeting()
                getUser()
                getArticles()
                getBlogs()
                getReports()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = HomeState()
        )

    private val _eventChannel = Channel<HomeEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnArticleClick -> {
                _eventChannel.trySend(HomeEvent.OnArticleClick(action.article))
            }

            HomeAction.OnSeeMoreArticleClick -> {
                _eventChannel.trySend(HomeEvent.OnSeeMoreArticleClick)
            }

            HomeAction.OnSeeMoreBlogClick -> {
                _eventChannel.trySend(HomeEvent.OnSeeMoreBlogClick)
            }

            HomeAction.OnSeeMoreReportClick -> {
                _eventChannel.trySend(HomeEvent.OnSeeMoreReportClick)
            }

            else -> TODO("Handle actions")
        }
    }

    private fun getGreeting() {
        viewModelScope.launch {
            val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            val greeting = when (currentHour) {
                in 0..11 -> "Good Morning"
                in 12..17 -> "Good Afternoon"
                in 18..21 -> "Good Evening"
                in 21..24 -> "Good Night"
                else -> "Good Day"
            }
            _state.update {
                it.copy(
                    greeting = greeting
                )
            }
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            authRepository.getName()
                .onSuccess { user ->
                    _state.update {
                        it.copy(
                            user = user
                        )
                    }
                }
        }
    }

    private fun getArticles() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoadingArticle = true
                )
            }
            articleRepository.getArticles()
                .onSuccess { articles ->
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
                        )
                    }
                }

        }
    }

    private fun getBlogs() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoadingBlog = true
                )
            }
            blogRepository.getBlogs()
                .onSuccess { blogs ->
                    _state.update {
                        it.copy(
                            isLoadingBlog = false,
                            blogs = blogs
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoadingBlog = false,
                            errorBlog = error.asUiText()
                        )
                    }
                }

        }
    }

    private fun getReports() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoadingReport = true
                )
            }
            reportRepository.getReports()
                .onSuccess { reports ->
                    _state.update {
                        it.copy(
                            isLoadingReport = false,
                            reports = reports
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoadingReport = false,
                            errorReport = error.asUiText()
                        )
                    }
                }

        }
    }

}