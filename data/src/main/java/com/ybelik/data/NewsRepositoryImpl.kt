package com.ybelik.data

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.ybelik.data.background.RefreshDataWorker
import com.ybelik.data.local.LocalDataSource
import com.ybelik.data.mapper.ArticleMapper
import com.ybelik.data.remote.RemoteDataSource
import com.ybelik.domain.model.Article
import com.ybelik.domain.model.Language
import com.ybelik.domain.model.RefreshConfig
import com.ybelik.domain.repoository.NewsRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.util.concurrent.TimeUnit

class NewsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val workManager: WorkManager
) : NewsRepository {

    override fun getAllSubscriptions(): Flow<List<String>> {
        return localDataSource.getAllSubscriptions().map { subscriptionsList ->
            subscriptionsList.map { it.topic }
        }
    }

    override suspend fun addSubscription(topic: String) {
        return localDataSource.addSubscription(topic = topic)
    }

    override suspend fun updateArticlesForTopic(topic: String, language: Language): Boolean {
        val articles = remoteDataSource.loadArticles(
            topic = topic,
            language = language
        ).map { articleResponse ->
            ArticleMapper.toEntity(
                remoteModel = articleResponse,
                topic = topic
            )
        }
        val addedArticlesIds = localDataSource.addArticles(articles = articles)
        return addedArticlesIds.any { it != -1L }
    }

    override suspend fun removeSubscription(topic: String) {
        localDataSource.removeSubscription(topic = topic)
    }

    override suspend fun updateArticlesForAllSubscription(language: Language): List<String> {
        val updatedTopics = mutableListOf<String>()
        val subscriptions = localDataSource.getAllSubscriptions().first()
        coroutineScope {
            subscriptions.forEach { subscription ->
                launch {
                    val isUpdated = updateArticlesForTopic(topic = subscription.topic, language = language)
                    if (isUpdated) {
                        updatedTopics.add(subscription.topic)
                    }
                }
            }
        }
        return updatedTopics
    }

    override fun getArticlesByTopics(topics: List<String>): Flow<List<Article>> {
        return localDataSource.getArticlesByTopics(topics = topics).map { articleEntities ->
            articleEntities.distinct().map { entity ->
                ArticleMapper.fromEntity(entity = entity)
            }
        }
    }

    override suspend fun clearAllArticles(topics: List<String>) {
        localDataSource.clearAllArticles(topics)
    }

    override fun startBackgroundRefresh(refreshConfig: RefreshConfig) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(
                if (refreshConfig.isWifiOnly) {
                    NetworkType.UNMETERED
                } else {
                    NetworkType.CONNECTED
                }
            )
            .setRequiresBatteryNotLow(true)
            .build()
        val request = PeriodicWorkRequestBuilder<RefreshDataWorker>(
            repeatInterval = 15L,
            repeatIntervalTimeUnit = TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()
        workManager.enqueueUniquePeriodicWork(
            uniqueWorkName = "Refresh data",
            existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            request = request,
        )
    }
}