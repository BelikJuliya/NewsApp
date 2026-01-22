package com.ybelik.domain.repoository

import com.ybelik.domain.model.Article
import com.ybelik.domain.model.RefreshConfig
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getAllSubscriptions(): Flow<List<String>>

    suspend fun addSubscription(topic: String)

    suspend fun updateArticlesForTopic(topic: String)

    suspend fun removeSubscription(topic: String)

    suspend fun updateArticlesForAllSubscription()

    fun getArticlesByTopics(topics: List<String>): Flow<List<Article>>

    suspend fun clearAllArticles(topics: List<String>)

    fun startBackgroundRefresh(refreshConfig: RefreshConfig)
}