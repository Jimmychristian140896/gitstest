package com.jimmy.basecompose.di

import com.jimmy.basecompose.MainViewModel
import com.jimmy.basecompose.domain.repository.UserRepository
import com.jimmy.basecompose.presentation.detailarticle.DetailArticleViewModel
import com.jimmy.basecompose.presentation.home.HomeViewModel
import com.jimmy.basecompose.presentation.listarticle.ListArticleViewModel
import com.jimmy.basecompose.presentation.login.LoginViewModel
import com.jimmy.basecompose.presentation.register.RegisterViewModel
import com.jimmy.basecompose.presentation.searcharticle.SearchArticleViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailArticleViewModel)
    viewModelOf(::ListArticleViewModel)
    viewModelOf(::SearchArticleViewModel)

}