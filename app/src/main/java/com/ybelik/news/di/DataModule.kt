package com.ybelik.news.di

import android.content.Context
import androidx.room.Room
import com.ybelik.data.local.NewsDataBase
import com.ybelik.data.local.entity.NewsDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    companion object {

        @Provides
        @Singleton
        fun provideDataBase(
            @ApplicationContext context: Context
        ): NewsDataBase {
            return Room.databaseBuilder(
                context = context,
                klass = NewsDataBase::class.java,
                name = "news.db"
            ).fallbackToDestructiveMigration(dropAllTables = true).build()
        }

        @Provides
        fun providesNotesDao(
            dataBase: NewsDataBase
        ): NewsDAO {
            return dataBase.newsDao()
        }
    }
}