package com.jimmy.basecompose.domain.mappers

import com.jimmy.basecompose.data.remote.dto.ArticleDto
import com.jimmy.basecompose.data.remote.dto.AuthorDto
import com.jimmy.basecompose.data.remote.dto.BlogDto
import com.jimmy.basecompose.data.remote.dto.EventDto
import com.jimmy.basecompose.data.remote.dto.LaunchDto
import com.jimmy.basecompose.data.remote.dto.ReportDto
import com.jimmy.basecompose.data.remote.dto.SocialDto
import com.jimmy.basecompose.data.remote.dto.UserDto
import com.jimmy.basecompose.domain.model.Article
import com.jimmy.basecompose.domain.model.ArticleType
import com.jimmy.basecompose.domain.model.Author
import com.jimmy.basecompose.domain.model.Event
import com.jimmy.basecompose.domain.model.Launch
import com.jimmy.basecompose.domain.model.Social
import com.jimmy.basecompose.domain.model.User

fun UserDto.toUser() = User(
    username = username,
    password = password
)
fun User.toUserDto() = UserDto(
    username = username,
    password = password
)

fun SocialDto.toSocial() = Social(
    bluesky = bluesky,
    instagram = instagram,
    linkedin = linkedin,
    mastodon = mastodon,
    x = x,
    youtube = youtube
)

fun AuthorDto.toAuthor() = Author(
    name = name,
    socials = socials?.toSocial()
)

fun EventDto.toEvent() = Event(
    id = eventId.toString(),
    provider = provider
)

fun LaunchDto.toLaunch() = Launch(
    id = launchId,
    provider = provider
)

fun ArticleDto.toArticle() = Article(
    authors = authors.map { it.toAuthor() },
    events = events.map { it.toEvent() },
    featured = featured,
    id = id,
    imageUrl = imageUrl,
    launches = launches.map { it.toLaunch() },
    newsSite = newsSite,
    publishedAt = publishedAt,
    summary = summary,
    title = title,
    updatedAt = updatedAt,
    url = url,
    type = ArticleType.ARTICLE
)

fun BlogDto.toArticle() = Article(
    authors = authors.map { it.toAuthor() },
    events = events.map { it.toEvent() },
    featured = featured,
    id = id,
    imageUrl = imageUrl,
    launches = launches.map { it.toLaunch() },
    newsSite = newsSite,
    publishedAt = publishedAt,
    summary = summary,
    title = title,
    updatedAt = updatedAt,
    url = url,
    type = ArticleType.BLOG
)

fun ReportDto.toArticle() = Article(
    authors = authors.map { it.toAuthor() },
    id = id,
    imageUrl = imageUrl,
    newsSite = newsSite,
    publishedAt = publishedAt,
    summary = summary,
    title = title,
    updatedAt = updatedAt,
    url = url,
    type = ArticleType.REPORT
)


