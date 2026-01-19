package com.ybelik.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index

@Entity(
    tableName = "articles",
    primaryKeys = ["url", "topic"],
    foreignKeys = [
        ForeignKey(
            entity = SubscriptionEntity::class,
            parentColumns = ["topic"],
            childColumns = ["topic"],
            onDelete = CASCADE
        )
    ],
    indices = [Index("topic")]
)
data class ArticleEntity(
    val title: String,
    val description: String,
    val imageUrl: String?,
    val source: String,
    val publishedAt: Long,
    val url: String,
    val topic: String
)
