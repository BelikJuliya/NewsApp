package com.ybelik.data.local

import com.ybelik.data.local.entity.ArticleEntity
import com.ybelik.data.local.entity.SubscriptionEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun getAllSubscriptions(): Flow<List<SubscriptionEntity>>

    suspend fun addSubscription(topic: String)

    suspend fun addArticles(articles: List<ArticleEntity>): List<Long>

    suspend fun removeSubscription(topic: String)

    fun getArticlesByTopics(topics: List<String>): Flow<List<ArticleEntity>>

    suspend fun clearAllArticles(topics: List<String>)

}