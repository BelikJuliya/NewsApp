package com.ybelik.data.mapper

import com.ybelik.data.local.entity.ArticleEntity
import com.ybelik.data.remote.response.ArticleResponse
import com.ybelik.data.remote.toTimeStamp
import com.ybelik.domain.model.Article

object ArticleMapper : ToDomainMapper<Article, ArticleResponse> {

    override fun toDomain(model: ArticleResponse): Article {
        return Article(
            title = model.title,
            description = model.description,
            imageUrl = model.urlToImage,
            source = SourceMapper.toDomain(model.sourceResponse),
            publishedAt = model.publishedAt.toTimeStamp(),
            url = model.url
        )
    }

    fun toEntity(remoteModel: ArticleResponse, topic: String): ArticleEntity {
        return ArticleEntity(
            title = remoteModel.title,
            description = remoteModel.description,
            imageUrl = remoteModel.urlToImage,
            source = SourceMapper.toDomain(remoteModel.sourceResponse),
            publishedAt = remoteModel.publishedAt.toTimeStamp(),
            url = remoteModel.url,
            topic = topic
        )
    }

    fun fromEntity(entity: ArticleEntity): Article {
        return Article(
            title = entity.title,
            description = entity.description,
            imageUrl = entity.imageUrl,
            source = entity.source,
            publishedAt = entity.publishedAt,
            url = entity.url
        )
    }
}