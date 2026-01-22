package com.ybelik.news.di

import com.ybelik.domain.NotificationHelper
import com.ybelik.news.NotificationHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    @Singleton
    fun bindNotificationHelper(impl: NotificationHelperImpl): NotificationHelper
}