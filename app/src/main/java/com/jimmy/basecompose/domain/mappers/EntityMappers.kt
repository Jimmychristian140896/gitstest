package com.jimmy.basecompose.domain.mappers

import com.jimmy.basecompose.data.local.entity.ArticleEntity
import com.jimmy.basecompose.data.local.entity.AuthorEntity
import com.jimmy.basecompose.data.local.entity.EventEntity
import com.jimmy.basecompose.data.local.entity.LaunchEntity
import com.jimmy.basecompose.data.local.entity.SocialEntity
import com.jimmy.basecompose.data.local.entity.UserEntity
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

fun UserEntity.toUser() = User(
    username = username,
    password = password
)
fun User.toUserEntity() = UserEntity(
    username = username,
    password = password
)

fun Social.toSocialEntity() = SocialEntity(
    bluesky = bluesky,
    instagram = instagram,
    linkedin = linkedin,
    mastodon = mastodon,
    x = x,
    youtube = youtube
)

fun Author.toAuthorEntity() = AuthorEntity(
    name = name,
    socials = socials?.toSocialEntity()
)

fun Event.toEventEntity() = EventEntity(
    id = id,
    provider = provider
)

fun Launch.toLaunchEntity() = LaunchEntity(
    id = id,
    provider = provider
)

fun Article.toArticleEntity() = ArticleEntity(
    authors = authors.map { it.toAuthorEntity() },
    events = events?.map { it.toEventEntity() },
    featured = featured,
    id = id,
    imageUrl = imageUrl,
    launches = launches?.map { it.toLaunchEntity() },
    newsSite = newsSite,
    publishedAt = publishedAt,
    summary = summary,
    title = title,
    updatedAt = updatedAt,
    url = url,
    type = type
)



fun SocialEntity.toSocial() = Social(
    bluesky = bluesky,
    instagram = instagram,
    linkedin = linkedin,
    mastodon = mastodon,
    x = x,
    youtube = youtube
)

fun AuthorEntity.toAuthor() = Author(
    name = name,
    socials = socials?.toSocial()
)

fun EventEntity.toEvent() = Event(
    id = id,
    provider = provider
)

fun LaunchEntity.toLaunch() = Launch(
    id = id,
    provider = provider
)

fun ArticleEntity.toArticle() = Article(
    authors = authors.map { it.toAuthor() },
    events = events?.map { it.toEvent() },
    featured = featured,
    id = id,
    imageUrl = imageUrl,
    launches = launches?.map { it.toLaunch() },
    newsSite = newsSite,
    publishedAt = publishedAt,
    summary = summary,
    title = title,
    updatedAt = updatedAt,
    url = url,
    type = type
)