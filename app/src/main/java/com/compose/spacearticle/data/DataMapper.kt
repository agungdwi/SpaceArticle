package com.compose.spacearticle.data

import com.compose.spacearticle.data.local.entity.ArticleEntity
import com.compose.spacearticle.data.remote.response.ResultsItem
import com.compose.spacearticle.domain.model.Article

object DataMapper {

    fun mapResponseToDomain(input: ResultsItem): Article =
        Article(
            id = input.id,
            title = input.title,
            url = input.url,
            imageUrl = input.imageUrl,
            newsSite = input.newsSite,
            summary = input.summary,
            publishedAt = input.publishedAt,
        )

    fun mapEntityToDomain(input: ArticleEntity): Article =
        Article(
            id = input.id,
            title = input.title,
            url = input.url,
            imageUrl = input.imageUrl,
            newsSite = input.newsSite,
            summary = input.summary,
            publishedAt = input.publishedAt,
        )

    fun mapDomainToEntity(input: Article): ArticleEntity =
        ArticleEntity(
            id = input.id,
            title = input.title,
            url = input.url,
            imageUrl = input.imageUrl,
            newsSite = input.newsSite,
            summary = input.summary,
            publishedAt = input.publishedAt,
        )

}