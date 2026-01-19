package com.ybelik.data.local.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDAO {

    @Query("SELECT * FROM subscriptions")
    fun getAllSubscriptions(): Flow<List<SubscriptionEntity>>

    @Insert(onConflict = IGNORE)
    suspend fun addSubscription(subscriptionEntity: SubscriptionEntity)

    @Transaction
    @Delete
    suspend fun deleteSubscription(subscriptionEntity: SubscriptionEntity)

    @Query("SELECT * FROM articles WHERE topic IN (:topics) ORDER BY publishedAt DESC ")
    fun getAllArticlesByTopics(topics: List<String>): Flow<List<ArticleEntity>>

    @Insert(onConflict = IGNORE)
    suspend fun addArticles(list: List<ArticleEntity>)

    @Query("DELETE FROM articles WHERE topic IN (:topics)")
    suspend fun deleteArticlesByTopics(list: List<String>)
}