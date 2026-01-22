package com.ybelik.news.di

import com.ybelik.domain.repoository.NewsRepository
import com.ybelik.domain.repoository.SettingsRepository
import com.ybelik.domain.usecase.settings.GetSettingsUseCase
import com.ybelik.domain.usecase.settings.UpdateIntervalUseCase
import com.ybelik.domain.usecase.settings.UpdateLanguageUseCase
import com.ybelik.domain.usecase.settings.UpdateNotificationsUseCase
import com.ybelik.domain.usecase.settings.UpdateWifiOnlyUseCase
import com.ybelik.domain.usecase.subscriptions.AddSubscriptionUseCase
import com.ybelik.domain.usecase.subscriptions.ClearAllArticlesUseCase
import com.ybelik.domain.usecase.subscriptions.GetAllSubscriptionUseCase
import com.ybelik.domain.usecase.subscriptions.GetArticlesByTopicUseCase
import com.ybelik.domain.usecase.subscriptions.RemoveSubscriptionUseCase
import com.ybelik.domain.usecase.subscriptions.StartRefreshDataUseCase
import com.ybelik.domain.usecase.subscriptions.UpdateSubscribedArticlesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    // Subscriptions
    @Provides
    fun provideAddSubscriptionUseCase(
        newsRepository: NewsRepository,
        settingsRepository: SettingsRepository
    ): AddSubscriptionUseCase {
        return AddSubscriptionUseCase(
            newsRepository = newsRepository,
            settingsRepository = settingsRepository
        )
    }

    @Provides
    fun provideClearAllArticlesUseCase(repository: NewsRepository): ClearAllArticlesUseCase {
        return ClearAllArticlesUseCase(repository)
    }

    @Provides
    fun provideGetAllSubscriptionUseCase(repository: NewsRepository): GetAllSubscriptionUseCase {
        return GetAllSubscriptionUseCase(repository)
    }

    @Provides
    fun provideGetArticlesByTopicUseCase(repository: NewsRepository): GetArticlesByTopicUseCase {
        return GetArticlesByTopicUseCase(repository)
    }

    @Provides
    fun provideRemoveSubscriptionUseCase(repository: NewsRepository): RemoveSubscriptionUseCase {
        return RemoveSubscriptionUseCase(repository)
    }

    @Provides
    fun provideUpdateSubscribedArticlesUseCase(
        newsRepository: NewsRepository,
        settingsRepository: SettingsRepository
    ): UpdateSubscribedArticlesUseCase {
        return UpdateSubscribedArticlesUseCase(
            newsRepository = newsRepository,
            settingsRepository = settingsRepository
        )
    }

    // Settings
    @Provides
    fun provideGetSettingsUseCase(repository: SettingsRepository): GetSettingsUseCase {
        return GetSettingsUseCase(repository)
    }

    @Provides
    fun provideUpdateLanguageUseCase(repository: SettingsRepository): UpdateLanguageUseCase {
        return UpdateLanguageUseCase(repository)
    }

    @Provides
    fun provideUpdateNotificationsUseCase(repository: SettingsRepository): UpdateNotificationsUseCase {
        return UpdateNotificationsUseCase(repository)
    }

    @Provides
    fun provideUpdateWifiOnlyUseCase(repository: SettingsRepository): UpdateWifiOnlyUseCase {
        return UpdateWifiOnlyUseCase(repository)
    }

    @Provides
    fun provideUpdateIntervalUseCase(repository: SettingsRepository): UpdateIntervalUseCase {
        return UpdateIntervalUseCase(repository)
    }

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