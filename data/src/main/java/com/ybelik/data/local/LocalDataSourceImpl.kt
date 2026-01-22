package com.ybelik.data.local

import com.ybelik.data.local.entity.ArticleEntity
import com.ybelik.data.local.entity.NewsDAO
import com.ybelik.data.local.entity.SubscriptionEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val newsDAO: NewsDAO
) : LocalDataSource {

    override fun getAllSubscriptions(): Flow<List<SubscriptionEntity>> {
        return newsDAO.getAllSubscriptions()
    }

    override suspend fun addSubscription(topic: String) {
        newsDAO.addSubscription(SubscriptionEntity(topic = topic))
    }

    override suspend fun addArticles(articles: List<ArticleEntity>): List<Long> {
        return newsDAO.addArticles(articles = articles)
    }

    override suspend fun removeSubscription(topic: String) {
        newsDAO.deleteSubscription(SubscriptionEntity(topic = topic))
    }

    override fun getArticlesByTopics(topics: List<String>): Flow<List<ArticleEntity>> {
        return newsDAO.getAllArticlesByTopics(topics = topics)
    }

    override suspend fun clearAllArticles(topics: List<String>) {
        newsDAO.deleteArticlesByTopics(topics = topics)
    }
}