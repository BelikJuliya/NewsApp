package com.ybelik.news.di

import com.ybelik.domain.repoository.NewsRepository
import com.ybelik.domain.usecase.AddSubscriptionUseCase
import com.ybelik.domain.usecase.ClearAllArticlesUseCase
import com.ybelik.domain.usecase.GetAllSubscriptionUseCase
import com.ybelik.domain.usecase.GetArticlesByTopicUseCase
import com.ybelik.domain.usecase.RemoveSubscriptionUseCase
import com.ybelik.domain.usecase.UpdateSubscribedArticlesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideAddSubscriptionUseCase(repository: NewsRepository): AddSubscriptionUseCase {
        return AddSubscriptionUseCase(repository)
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
    fun provideUpdateSubscribedArticlesUseCase(repository: NewsRepository): UpdateSubscribedArticlesUseCase {
        return UpdateSubscribedArticlesUseCase(repository)
    }
}