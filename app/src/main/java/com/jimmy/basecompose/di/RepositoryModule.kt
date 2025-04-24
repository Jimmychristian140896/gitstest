package com.jimmy.basecompose.di

import com.jimmy.basecompose.data.repository.ArticleRepositoryImpl
import com.jimmy.basecompose.data.repository.AuthRepositoryImpl
import com.jimmy.basecompose.data.repository.BlogRepositoryImpl
import com.jimmy.basecompose.data.repository.NewsSiteRepositoryImpl
import com.jimmy.basecompose.data.repository.RecentSearchRepositoryImpl
import com.jimmy.basecompose.data.repository.ReportRepositoryImpl
import com.jimmy.basecompose.data.repository.SessionRepositoryImpl
import com.jimmy.basecompose.domain.repository.ArticleRepository
import com.jimmy.basecompose.domain.repository.AuthRepository
import com.jimmy.basecompose.domain.repository.BlogRepository
import com.jimmy.basecompose.domain.repository.NewsSiteRepository
import com.jimmy.basecompose.domain.repository.RecentSearchRepository
import com.jimmy.basecompose.domain.repository.ReportRepository
import com.jimmy.basecompose.domain.repository.SessionRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::ArticleRepositoryImpl) { bind<ArticleRepository>() }
    singleOf(::BlogRepositoryImpl) { bind<BlogRepository>() }
    singleOf(::ReportRepositoryImpl) { bind<ReportRepository>() }
    singleOf(::NewsSiteRepositoryImpl) { bind<NewsSiteRepository>() }
    singleOf(::RecentSearchRepositoryImpl) { bind<RecentSearchRepository>() }
    singleOf(::SessionRepositoryImpl) { bind<SessionRepository>() }
    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
}