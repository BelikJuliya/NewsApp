package com.ybelik.news.di

import com.ybelik.domain.NotificationHelper
import com.ybelik.domain.repoository.NewsRepository
import com.ybelik.domain.repoository.SettingsRepository
import com.ybelik.domain.usecase.subscriptions.StartRefreshDataUseCase
import com.ybelik.news.NotificationHelperImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    @Singleton
    fun bindNotificationHelper(impl: NotificationHelperImpl): NotificationHelper

    companion object {

        @Provides
        fun provideStartRefreshDataUseCase(
            newsRepository: NewsRepository,
            settingsRepository: SettingsRepository
        ): StartRefreshDataUseCase {
            return StartRefreshDataUseCase(
                newsRepository = newsRepository,
                settingsRepository = settingsRepository
            )
        }
    }
}