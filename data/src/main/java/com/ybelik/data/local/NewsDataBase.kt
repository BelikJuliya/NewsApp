package com.ybelik.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ybelik.data.local.entity.ArticleEntity
import com.ybelik.data.local.entity.NewsDAO
import com.ybelik.data.local.entity.SubscriptionEntity

@Database(
    entities = [ArticleEntity::class, SubscriptionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDataBase : RoomDatabase() {

    abstract fun newsDao(): NewsDAO
}